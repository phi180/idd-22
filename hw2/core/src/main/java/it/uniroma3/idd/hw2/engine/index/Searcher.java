package it.uniroma3.idd.hw2.engine.index;

import it.uniroma3.idd.hw2.engine.entity.ResultEntry;
import org.apache.lucene.search.ScoreDoc;

import java.util.Map;
import java.util.Set;

public interface Searcher {
    Set<ResultEntry> search(String queryString);
    Set<ResultEntry> searchPhraseQuery(String queryString);
    Set<ResultEntry> searchWithParser(String queryString);
}
