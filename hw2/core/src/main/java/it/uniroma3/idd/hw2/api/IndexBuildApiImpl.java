package it.uniroma3.idd.hw2.api;

import it.uniroma3.idd.hw2.engine.index.Indexer;
import it.uniroma3.idd.hw2.engine.index.impl.IndexerImpl;
import it.uniroma3.idd.hw2.utils.PropertiesReader;
import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.TokenizerFactory;

import java.io.File;
import java.util.List;

import static it.uniroma3.idd.hw2.utils.constants.Constants.INDEX_DIR;

public class IndexBuildApiImpl implements IndexBuildApi {

    @Override
    public void buildIndex(String dirPath) {
        File indexDir = new File(INDEX_DIR);
        if (!indexDir.exists()){
            indexDir.mkdirs();
        }

        Class<TokenizerFactory> tokenizerFactory = PropertiesReader.readTokenizer();
        List<Class<TokenFilterFactory>> tokenFilterFactories = PropertiesReader.readTokenFilters();

        Indexer indexer = new IndexerImpl.IndexerBuilder(dirPath,INDEX_DIR)
                .setTokenizer(tokenizerFactory)
                .setTokenFilterClasses(tokenFilterFactories)
                .build();

        indexer.buildIndex();
    }
}
