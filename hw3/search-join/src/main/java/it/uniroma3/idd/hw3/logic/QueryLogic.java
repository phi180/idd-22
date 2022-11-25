package it.uniroma3.idd.hw3.logic;

import it.uniroma3.idd.hw3.domain.result.ResultColumn;
import it.uniroma3.idd.hw3.utils.PropertiesReader;
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

import static it.uniroma3.idd.hw3.utils.Constants.*;

public class QueryLogic {

    private static final Logger logger = Logger.getLogger(QueryLogic.class.toString());

    private static final int N_QUERY_RESULTS = 50;

    public List<ResultColumn> query(List<String[]> groupOfTokens, int k) {
        logger.info("QueryLogic - query(): k="+k);

        Map<ResultColumn, Integer> set2count = new HashMap<>();

        Path path = Paths.get(PropertiesReader.getProperty(INDEX_PATH_PROPERTY));

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
                    List<ResultColumn> results = runQuery(searcher, booleanQueryBuilder.build());
                    for(ResultColumn result: results) {
                        set2count.putIfAbsent(result, 0);
                        set2count.put(result, set2count.get(result)+1);
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

    /** PRIVATE METHODS */

    private List<ResultColumn> runQuery(IndexSearcher searcher, Query query) throws IOException {
        logger.info("QueryLogic - runQuery(): numDocs="+searcher.getIndexReader().numDocs());

        List<ResultColumn> results = new ArrayList<>();

        TopDocs hits = searcher.search(query, N_QUERY_RESULTS);
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc scoreDoc = hits.scoreDocs[i];
            Document doc = searcher.doc(scoreDoc.doc);

            ResultColumn result = new ResultColumn(doc.get(TABLE_ID),
                    Long.valueOf(doc.get(COLUMN_NUM)));

            results.add(result);
        }

        return results;
    }

}
