package it.uniroma3.idd.logic;

import it.uniroma3.idd.domain.lucene.LuceneTable;
import it.uniroma3.idd.domain.lucene.LuceneTableCell;
import it.uniroma3.idd.domain.lucene.LuceneTableColumn;
import it.uniroma3.idd.domain.result.ResultColumn;
import it.uniroma3.idd.utils.PropertiesReader;
import it.uniroma3.idd.utils.StatsWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static it.uniroma3.idd.utils.Constants.*;

public class QueryLogic {

    private static final Logger logger = Logger.getLogger(QueryLogic.class.toString());
    private static final Boolean EXPLAIN = PropertiesReader.readExplain();
    private static final int N_QUERY_RESULTS = 50;

    public List<ResultColumn> query(List<String[]> groupOfTokens, int k) {
        logger.info("QueryLogic - query(): k="+k);

        Map<ResultColumn, Integer> set2count = new HashMap<>();

        Path path = Paths.get(PropertiesReader.getIndexFolderPath());

        try (Directory directory = FSDirectory.open(path)) {
            try (IndexReader reader = DirectoryReader.open(directory)) {
                for(String[] cellTokens:groupOfTokens) {
                    logger.info("QueryLogic - query(): token="+Arrays.asList(cellTokens));

                    BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
                    for(String token : cellTokens) {
                        Query termQuery = new TermQuery(new Term(CONTENT, token));
                        booleanQueryBuilder.add(new BooleanClause(termQuery, BooleanClause.Occur.SHOULD));
                    }

                    IndexSearcher searcher = new IndexSearcher(reader);
                    Map<ResultColumn, Integer> results2occurrences = runQuery(searcher, booleanQueryBuilder.build());
                    for(Map.Entry<ResultColumn, Integer> result: results2occurrences.entrySet()) {
                        set2count.putIfAbsent(result.getKey(), 0);
                        set2count.put(result.getKey(), set2count.get(result.getKey())+result.getValue());
                    }
                }
            } finally {
                directory.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // group results by number of occurrences
        Map<Integer,List<ResultColumn>> set2CountTopK = new TreeMap<>();
        for(Map.Entry<ResultColumn, Integer> entry : set2count.entrySet()) {
            set2CountTopK.putIfAbsent(entry.getValue(), new ArrayList<>());
            set2CountTopK.get(entry.getValue()).add(entry.getKey());
        }

        // Get last k elements from a tree map
        List<ResultColumn> results = new ArrayList<>();
        List<Integer> keys = new ArrayList<>(set2CountTopK.keySet());
        Collections.reverse(keys);
        for(int i = 0;i<Integer.min(k,keys.size());i++) {
            List<ResultColumn> entries = set2CountTopK.get(keys.get(i));
            if(entries != null)
                results.addAll(entries);
        }

        return results;
    }

    public boolean existsColumnInIndex(String tableId,Long columnNum) {
        boolean existsTable;
        Path path = Paths.get(PropertiesReader.getIndexFolderPath());

        try (Directory directory = FSDirectory.open(path)) {
            try (IndexReader reader = DirectoryReader.open(directory)) {
                BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
                Query termQueryTable = new TermQuery(new Term(TABLE_ID, tableId));
                Query termQueryColumn = new TermQuery(new Term(COLUMN_NUM, String.valueOf(columnNum)));
                booleanQueryBuilder.add(new BooleanClause(termQueryTable, BooleanClause.Occur.MUST))
                        .add(new BooleanClause(termQueryColumn, BooleanClause.Occur.MUST));
                IndexSearcher searcher = new IndexSearcher(reader);
                existsTable = runQuery(searcher, booleanQueryBuilder.build()).size() > 0;
            } finally {
                directory.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return existsTable;
    }

    /** PRIVATE METHODS */

    private Map<ResultColumn,Integer> runQuery(IndexSearcher searcher, Query query) throws IOException {
        logger.info("QueryLogic - runQuery(): numDocs="+searcher.getIndexReader().numDocs());

        Long beginTimestamp = new Date().getTime();
        if (EXPLAIN)
            StatsWriter.initStatsFile(query.toString(CONTENT),beginTimestamp);

        Map<ResultColumn,Integer> results2occurrences = new HashMap<>();

        TopDocs hits = searcher.search(query, N_QUERY_RESULTS);
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc scoreDoc = hits.scoreDocs[i];
            Document doc = searcher.doc(scoreDoc.doc);

            ResultColumn result = new ResultColumn(doc.get(TABLE_ID),
                    Long.valueOf(doc.get(COLUMN_NUM)));

            results2occurrences.put(result,1);

            if (EXPLAIN) {
                Explanation explanation = searcher.explain(query, scoreDoc.doc);
                StatsWriter.writeStats(beginTimestamp, result.toString(), explanation);
            }
        }

        if (EXPLAIN) {
            StatsWriter.appendElapsedTimeAndHits(beginTimestamp, new Date().getTime(),hits.totalHits.value);
        }

        return results2occurrences;
    }

}
