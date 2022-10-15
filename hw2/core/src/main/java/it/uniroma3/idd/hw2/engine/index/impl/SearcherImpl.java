package it.uniroma3.idd.hw2.engine.index.impl;

import it.uniroma3.idd.hw2.engine.entity.ResultEntry;
import it.uniroma3.idd.hw2.engine.index.Searcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInfos;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static it.uniroma3.idd.hw2.utils.constants.Constants.*;

public class SearcherImpl implements Searcher {

    @Override
    public Map<ResultEntry, Float> search(String queryString) {
        Map<ResultEntry, Float> results = null;

        Path path = Paths.get(INDEX_DIR);

        Query query = new TermQuery(new Term(CONTENT, queryString));
        try (Directory directory = FSDirectory.open(path)) {
            try (IndexReader reader = DirectoryReader.open(directory)) {
                IndexSearcher searcher = new IndexSearcher(reader);
                results = runQuery(searcher, query);
            } finally {
                directory.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return results;
    }

    private Map<ResultEntry, Float> runQuery(IndexSearcher searcher, Query query) throws IOException {
        return runQuery(searcher, query, false);
    }

    private Map<ResultEntry, Float> runQuery(IndexSearcher searcher, Query query, boolean explain) throws IOException {
        Map<ResultEntry, Float> results = new HashMap<>();

        TopDocs hits = searcher.search(query, 10);
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc scoreDoc = hits.scoreDocs[i];
            Document doc = searcher.doc(scoreDoc.doc);

            ResultEntry resultEntry = new ResultEntry();
            resultEntry.setDocId(scoreDoc.doc);
            resultEntry.setTitle(doc.get(TITLE));
            results.put(resultEntry,scoreDoc.score);

            if (explain) {
                Explanation explanation = searcher.explain(query, scoreDoc.doc);
                System.out.println(explanation);
            }
        }

        return results;
    }

}
