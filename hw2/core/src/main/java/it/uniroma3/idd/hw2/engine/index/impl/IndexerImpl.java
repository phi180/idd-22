package it.uniroma3.idd.hw2.engine.index.impl;

import it.uniroma3.idd.hw2.engine.index.Indexer;
import it.uniroma3.idd.hw2.filesystem.DirectorySeeker;
import it.uniroma3.idd.hw2.filesystem.Extensions;
import it.uniroma3.idd.hw2.filesystem.impl.DirectorySeekerImpl;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.uniroma3.idd.hw2.utils.constants.Constants.CONTENT;
import static it.uniroma3.idd.hw2.utils.constants.Constants.TITLE;

public class IndexerImpl implements Indexer {
    private String dirWithFiles;
    private String indexDir;

    private Class<TokenizerFactory> tokenizerFactoryClass;
    private List<Class<TokenFilterFactory>> tokenFilterFactoryClasses;

    private IndexerImpl(IndexerBuilder indexerBuilder) {
        this.dirWithFiles = indexerBuilder.dirWithFiles;
        this.indexDir = indexerBuilder.indexDir;
        this.tokenFilterFactoryClasses = indexerBuilder.tokenFilterFactoryClasses;
        this.tokenizerFactoryClass = indexerBuilder.tokenizerFactoryClass;
    }

    public static class IndexerBuilder {
        private String dirWithFiles;
        private String indexDir;

        private Class<TokenizerFactory> tokenizerFactoryClass;
        private List<Class<TokenFilterFactory>> tokenFilterFactoryClasses;

        public IndexerBuilder(String dirWithFiles,String indexDir) {
            this.dirWithFiles = dirWithFiles;
            this.indexDir = indexDir;
        }

        public IndexerBuilder setTokenizer(Class<TokenizerFactory> tokenizerFactoryClass) {
            this.tokenizerFactoryClass = tokenizerFactoryClass;
            return this;
        }

        public IndexerBuilder setTokenFilterClasses(List<Class<TokenFilterFactory>> tokenFilterFactoryClasses) {
            this.tokenFilterFactoryClasses = tokenFilterFactoryClasses;
            return this;
        }

        public IndexerImpl build() {
            return new IndexerImpl(this);
        }

    }

    @Override
    public void buildIndex() {
        DirectorySeeker directorySeeker = new DirectorySeekerImpl(dirWithFiles);

        Directory directory = null;
        IndexWriter writer = null;

        Analyzer defaultAnalyzer = new StandardAnalyzer();

        Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();

        perFieldAnalyzers.put(TITLE, new WhitespaceAnalyzer());
        CustomAnalyzer.Builder contentAnalyzerBuilder = null;
        try {
            contentAnalyzerBuilder = CustomAnalyzer.builder()
                            .withTokenizer(this.tokenizerFactoryClass);
            for(Class<TokenFilterFactory> tokenFilterFactory : this.tokenFilterFactoryClasses) {
                contentAnalyzerBuilder.addTokenFilter(tokenFilterFactory);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        perFieldAnalyzers.put(CONTENT, contentAnalyzerBuilder.build());

        Analyzer analyzer = new PerFieldAnalyzerWrapper(defaultAnalyzer, perFieldAnalyzers);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try {
            directory = FSDirectory.open(Paths.get(indexDir));
            writer = new IndexWriter(directory, config);
            writer.deleteAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(directorySeeker.hasNext()) {
            String fileToBeIndexed = directorySeeker.next();

            // what if extension is not listed in enum??
            Extensions extensions = Extensions.fromString(FilenameUtils.getExtension(fileToBeIndexed));

            Document doc = new Document();
            doc.add(new StringField(TITLE, fileToBeIndexed, Field.Store.YES));

            try {
                doc.add(new TextField(CONTENT, extensions.getFileReader().readContent(fileToBeIndexed), Field.Store.NO));
                writer.addDocument(doc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            writer.commit();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
