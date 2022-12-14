package it.uniroma3.idd.hw2.api;

import it.uniroma3.idd.hw2.engine.index.Indexer;
import it.uniroma3.idd.hw2.engine.index.impl.IndexerImpl;
import it.uniroma3.idd.hw2.utils.PropertiesReader;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.TokenizerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static it.uniroma3.idd.hw2.utils.constants.Constants.INDEX_DIR;
import static it.uniroma3.idd.hw2.utils.constants.Constants.INDEX_STATS;

@Component
public class IndexApiImpl implements IndexApi {

    @Override
    public void buildIndex(String dirPath) {
        try {
            FileUtils.forceMkdir(new File(INDEX_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Class<TokenizerFactory> tokenizerFactory = PropertiesReader.readTokenizer();
        List<Class<TokenFilterFactory>> tokenFilterFactories = PropertiesReader.readTokenFilters();

        Indexer indexer = new IndexerImpl.IndexerBuilder(dirPath,INDEX_DIR)
                .setTokenizer(tokenizerFactory)
                .setTokenFilterClasses(tokenFilterFactories)
                .build();

        try {
            indexer.buildIndex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteIndex() {
        try {
            FileUtils.forceDelete(new File(INDEX_DIR));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteIndexAndStats() {
        try {
            FileUtils.forceDelete(new File(INDEX_DIR));
            FileUtils.forceDelete(new File(INDEX_STATS));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
