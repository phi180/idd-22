package it.uniroma3.idd.hw2.engine.index.impl;

import it.uniroma3.idd.hw2.engine.index.Indexer;
import it.uniroma3.idd.hw2.filesystem.DirectorySeeker;
import it.uniroma3.idd.hw2.filesystem.Extensions;
import it.uniroma3.idd.hw2.filesystem.impl.DirectorySeekerImpl;
import it.uniroma3.idd.hw2.utils.PropertiesReader;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;
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

    private static final String STOPWORDS_FILE = "stopwords.txt";

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
    public void buildIndex() throws IOException {
        DirectorySeeker directorySeeker = new DirectorySeekerImpl(dirWithFiles);

        Directory directory = null;
        IndexWriter writer = null;

        Analyzer defaultAnalyzer = new StandardAnalyzer();
        Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
        perFieldAnalyzers.put(TITLE, new WhitespaceAnalyzer());
        CustomAnalyzer.Builder contentAnalyzerBuilder = null;
        contentAnalyzerBuilder = CustomAnalyzer.builder()
                        .withTokenizer(this.tokenizerFactoryClass, PropertiesReader.readTokenizerFactoryParams());

        for(Class<TokenFilterFactory> tokenFilterFactory : this.tokenFilterFactoryClasses) {
            contentAnalyzerBuilder.addTokenFilter(tokenFilterFactory);
        }

        contentAnalyzerBuilder.addTokenFilter(StopFilterFactory.NAME, "words", STOPWORDS_FILE);
        perFieldAnalyzers.put(CONTENT, contentAnalyzerBuilder.build());

        Analyzer analyzer = new PerFieldAnalyzerWrapper(defaultAnalyzer, perFieldAnalyzers);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setCodec(new SimpleTextCodec());

        directory = FSDirectory.open(Paths.get(indexDir));
        writer = new IndexWriter(directory, config);
        writer.deleteAll();

        while(directorySeeker.hasNext()) {
            String fileToBeIndexed = directorySeeker.next();

            Extensions extension = Extensions.fromString(FilenameUtils.getExtension(fileToBeIndexed));
            if(extension != null) {
                Document doc = new Document();
                doc.add(new StringField(TITLE, fileToBeIndexed, Field.Store.YES));
                doc.add(new TextField(CONTENT, extension.getFileReader().readContent(fileToBeIndexed), Field.Store.NO));
                writer.addDocument(doc);
            }
        }

        writer.commit();
        writer.close();
    }
}
