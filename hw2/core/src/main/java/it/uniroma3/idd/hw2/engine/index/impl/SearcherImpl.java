package it.uniroma3.idd.hw2.engine.index.impl;

import it.uniroma3.idd.hw2.engine.entity.ResultEntry;
import it.uniroma3.idd.hw2.engine.index.Searcher;
import it.uniroma3.idd.hw2.utils.PropertiesReader;
import it.uniroma3.idd.hw2.utils.StatsWriter;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static it.uniroma3.idd.hw2.utils.constants.Constants.*;

public class SearcherImpl implements Searcher {

    private static final int SEARCH_RESULTS = 10;

    @Override
    public Map<ResultEntry, Float> search(String queryString) {
        Map<ResultEntry, Float> results = null;

        Path path = Paths.get(INDEX_DIR);

        TermQuery query = new TermQuery(new Term(CONTENT, queryString));
        try (Directory directory = FSDirectory.open(path)) {
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                results = runQuery(searcher, query, PropertiesReader.readExplainProperty());
            } finally {
                directory.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    private Map<ResultEntry, Float> runQuery(IndexSearcher searcher, TermQuery query, boolean explain) throws IOException {
        Map<ResultEntry, Float> results = new HashMap<>();

        Long timestamp = new Date().getTime();

        TopDocs hits = searcher.search(query, SEARCH_RESULTS);
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc scoreDoc = hits.scoreDocs[i];
            Document doc = searcher.doc(scoreDoc.doc);

            ResultEntry resultEntry = new ResultEntry();
            resultEntry.setDocId(scoreDoc.doc);
            resultEntry.setTitle(doc.get(TITLE));
            results.put(resultEntry,scoreDoc.score);

            if (explain) {
                Explanation explanation = searcher.explain(query, scoreDoc.doc);
                StatsWriter.writeStats(query.getTerm().bytes().toString(), timestamp,explanation);
            }
        }

        return results;
    }

}
