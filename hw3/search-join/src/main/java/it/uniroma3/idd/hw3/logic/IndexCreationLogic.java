package it.uniroma3.idd.hw3.logic;

import it.uniroma3.idd.hw3.api.ParseApi;
import it.uniroma3.idd.hw3.api.StatsApi;
import it.uniroma3.idd.hw3.domain.enums.Granularity;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.entity.TableVO;
import it.uniroma3.idd.hw3.api.ParseApiImpl;
import it.uniroma3.idd.hw3.api.StatsApiImpl;
import it.uniroma3.idd.hw3.filesystem.DatasetBuffer;
import it.uniroma3.idd.hw3.utils.PropertiesReader;
import it.uniroma3.idd.hw3.utils.Utils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static it.uniroma3.idd.hw3.utils.Constants.*;

public class IndexCreationLogic {

    private final Logger logger = Logger.getLogger(IndexCreationLogic.class.toString());

    private static final String STOPWORDS_FILE = "stopwords.txt";

    private static final Granularity INDEX_GRANULARITY = PropertiesReader.getGranularity();

    private ParseApi parseApi = new ParseApiImpl();

    public void createIndex(String datasetPath) throws IOException {
        float precision = PropertiesReader.getPrecision();
        StatisticsVO statisticsVO = this.getStatistics(datasetPath);

        DatasetBuffer datasetBuffer = null;

        try {
            datasetBuffer = new DatasetBuffer(datasetPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Directory directory = this.getIndexDirectory(PropertiesReader.getIndexFolderPath());

        CustomAnalyzer.Builder contentAnalyzerBuilder = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(WordDelimiterGraphFilterFactory.class)
                .addTokenFilter(StopFilterFactory.class, "words", STOPWORDS_FILE);

        Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
        perFieldAnalyzers.put(CONTENT, contentAnalyzerBuilder.build());

        Analyzer analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), perFieldAnalyzers);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // config.setCodec(new SimpleTextCodec());

        IndexWriter writer = new IndexWriter(directory, config);
        writer.deleteAll();

        long tables = 0L;
        while(!datasetBuffer.isEnded()) {
            String line = datasetBuffer.readNextLine();
            TableVO tableVO = parseApi.parse(line);
            if (tableVO == null)
                break;

            for(Map.Entry<Integer, ColumnVO> columnVOEntry: tableVO.getColumns().entrySet()) {

                int distinctColumnTokens = Utils.countDistinctTokens(columnVOEntry.getValue(),precision);
                if(chebychevCondition(distinctColumnTokens,precision,statisticsVO)) {

                    if(INDEX_GRANULARITY.equals(Granularity.CELL))
                        insertCellsInIndex(writer, tableVO.getOid(), columnVOEntry.getKey(),columnVOEntry.getValue());
                    else if(INDEX_GRANULARITY.equals(Granularity.COLUMN))
                        insertColumnInIndex(writer, tableVO.getOid(), columnVOEntry.getKey(),columnVOEntry.getValue());
                }
            }

            if(tables % N_TABLES == 0)
                logger.info("IndexCreationLogic - createIndex(): # tables analyzed: " + tables++);
        }

        writer.commit();
        writer.close();
        directory.close();
    }

    /** PRIVATE METHODS */

    private Directory getIndexDirectory(String indexPath) {
        Path path = Paths.get(indexPath);
        Directory directory = null;

        try {
            directory = FSDirectory.open(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return directory;
    }

    private void insertColumnInIndex(IndexWriter writer, String tableOid, Integer colnum, ColumnVO columnVO) {
        final String EMPTY_SPACE = " ";
        StringBuilder content = new StringBuilder();

        for(CellVO cellVO: columnVO.getCells().values()) {
            content.append(cellVO.getContent()).append(EMPTY_SPACE);
        }

        Document doc = generateDocument(tableOid, colnum, 0,content.toString(), false);

        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertCellsInIndex(IndexWriter writer, String tableOid, Integer colnum, ColumnVO columnVO) throws IOException {
        String header = columnVO.getHeader();
        if(header!=null) {
            Document headerDoc = generateDocument(tableOid, colnum, 0,
                    header, true);
            writer.addDocument(headerDoc);
        }

        columnVO.getCells().entrySet().parallelStream().forEach(
                cellVOEntry -> {
                    Document doc = generateDocument(tableOid, colnum, cellVOEntry.getKey(),
                            cellVOEntry.getValue().getContent(), false);
                    try {
                        writer.addDocument(doc);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    private Document generateDocument(String tableOid, int columnNum, int rowNum, String cellContent, boolean isHeader) {
        Document doc = new Document();
        doc.add(new StringField(TABLE_ID, tableOid, Field.Store.YES));
        doc.add(new StringField(COLUMN_NUM, String.valueOf(columnNum), Field.Store.YES));
        doc.add(new TextField(CONTENT, cellContent, Field.Store.NO));

        if(INDEX_GRANULARITY.equals(Granularity.CELL)) {
            doc.add(new StringField(ROW_NUM, String.valueOf(rowNum), Field.Store.YES));
            doc.add(new StringField(IS_HEADER, String.valueOf(isHeader), Field.Store.NO));
        }

        return doc;
    }

    private StatisticsVO getStatistics(String datasetPath) {
        StatsApi statsApi = new StatsApiImpl();
        return statsApi.runStatistics(datasetPath);
    }

    private boolean chebychevCondition(double numberOfTokens, float precision, StatisticsVO statisticsVO) {
        if(precision == 1f)
            return true;

        return Math.abs(numberOfTokens-statisticsVO.getMeanDistinctElementsInColumns()) <
                Utils.getChebychevKValue(precision)*statisticsVO.getStandardDevDistinctElements2Columns();
    }

}