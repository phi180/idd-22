package it.uniroma3.idd.hw3.logic;

import it.uniroma3.idd.hw3.api.ParseApi;
import it.uniroma3.idd.hw3.api.StatsApi;
import it.uniroma3.idd.entity.CellVO;
import it.uniroma3.idd.entity.ColumnVO;
import it.uniroma3.idd.entity.StatisticsVO;
import it.uniroma3.idd.entity.ColumnarTableVO;
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
    private final static String EMPTY_SPACE = " ";

    private ParseApi parseApi = new ParseApiImpl();

    public void createIndex(String datasetPath) throws IOException {
        float precision = Float.parseFloat(PropertiesReader.getProperty(PRECISION_PROPERTY));

        StatisticsVO statisticsVO = null;
        if(precision < 1f)
            statisticsVO = this.getStatistics(datasetPath);

        DatasetBuffer datasetBuffer = null;

        try {
            datasetBuffer = new DatasetBuffer(datasetPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Directory directory = this.getIndexDirectory(PropertiesReader.getProperty(INDEX_PATH_PROPERTY));

        CustomAnalyzer.Builder contentAnalyzerBuilder = CustomAnalyzer.builder()
                .withTokenizer(WhitespaceTokenizerFactory.class)
                .addTokenFilter(LowerCaseFilterFactory.class)
                .addTokenFilter(WordDelimiterGraphFilterFactory.class)
                .addTokenFilter(StopFilterFactory.class, "words", STOPWORDS_FILE);

        Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
        perFieldAnalyzers.put(CONTENT, contentAnalyzerBuilder.build());

        Analyzer analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), perFieldAnalyzers);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter writer = new IndexWriter(directory, config);

        long tables = 0L;
        boolean datasetEnded = false;
        do {
            String line = datasetBuffer.readNextLine();

            if(!datasetBuffer.isEnded()) {
                ColumnarTableVO tableVO = parseApi.parseByColumn(line);

                for (Map.Entry<Integer, ColumnVO> columnVOEntry : tableVO.getColumns().entrySet()) {
                    Integer columnNum = columnVOEntry.getKey();
                    ColumnVO columnVO = columnVOEntry.getValue();

                    int distinctColumnTokens = Utils.countDistinctTokens(columnVO, precision);

                    if (chebyshevCondition(distinctColumnTokens, precision, statisticsVO)) {
                        insertColumnInIndex(writer, tableVO.getOid(), columnNum, columnVO);
                    }
                }

                if (tables % N_TABLES == 0)
                    logger.info("IndexCreationLogic - createIndex(): # tables analyzed: " + tables);
                tables++;
            } else {
                datasetEnded = true;
            }
        } while(!datasetEnded);

        writer.commit();
        writer.close();
        directory.close();
    }

    public void dropIndex() throws IOException {
        Directory directory = this.getIndexDirectory(PropertiesReader.getProperty(INDEX_PATH_PROPERTY));
        IndexWriterConfig config = new IndexWriterConfig();
        IndexWriter writer = new IndexWriter(directory, config);
        writer.deleteAll();
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
        StringBuilder content = new StringBuilder();

        for(CellVO cellVO: columnVO.getCells().values()) {
            content.append(cellVO.getContent()).append(EMPTY_SPACE);
        }

        Document doc = generateDocument(tableOid, colnum,content.toString());

        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document generateDocument(String tableOid, int columnNum, String cellContent) {
        Document doc = new Document();
        doc.add(new StringField(TABLE_ID, tableOid, Field.Store.YES));
        doc.add(new StringField(COLUMN_NUM, String.valueOf(columnNum), Field.Store.YES));
        doc.add(new TextField(CONTENT, cellContent, Field.Store.NO));

        return doc;
    }

    private StatisticsVO getStatistics(String datasetPath) {
        StatsApi statsApi = new StatsApiImpl();
        return statsApi.runStatistics(datasetPath);
    }

    private boolean chebyshevCondition(double numberOfTokens, float precision, StatisticsVO statisticsVO) {
        if(precision == 1f)
            return true;

        return Math.abs(numberOfTokens-statisticsVO.getMeanDistinctElementsInColumns()) <
                Utils.getChebyshevKValue(precision)*statisticsVO.getStandardDevDistinctElements2Columns();
    }

}
