package it.uniroma3.idd.hw2.engine.index;

import it.uniroma3.idd.hw2.engine.entity.ResultEntry;
import org.apache.lucene.search.ScoreDoc;

import java.util.Map;

public interface Searcher {
    Map<ResultEntry, Float> search(String queryString);
}
