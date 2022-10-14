package it.uniroma3.idd.hw2.engine.index.impl;

import it.uniroma3.idd.hw2.engine.index.Indexer;
import it.uniroma3.idd.hw2.filesystem.DirectorySeeker;
import it.uniroma3.idd.hw2.filesystem.Extensions;
import it.uniroma3.idd.hw2.filesystem.impl.DirectorySeekerImpl;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
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

public class IndexerImpl implements Indexer {

    private final static String TITLE = "title";
    private final static String CONTENT = "content";

    private String dirWithFiles;
    private String indexDir;

    private Tokenizer tokenizer;
    private List<Class<TokenFilter>> tokenFilterClasses;

    private IndexerImpl(IndexerBuilder indexerBuilder) {
        this.dirWithFiles = indexerBuilder.dirWithFiles;
        this.indexDir = indexerBuilder.indexDir;
        this.tokenFilterClasses = indexerBuilder.tokenFilterClasses;
        this.tokenizer = indexerBuilder.tokenizer;
    }

    public static class IndexerBuilder {
        private String dirWithFiles;
        private String indexDir;

        private Tokenizer tokenizer;
        private List<Class<TokenFilter>> tokenFilterClasses;

        public IndexerBuilder(String dirWithFiles,String indexDir) {
            this.dirWithFiles = dirWithFiles;
            this.indexDir = indexDir;
        }

        public void setTokenizer(Tokenizer tokenizer) {
            this.tokenizer = tokenizer;
        }

        public void setTokenFilterClasses(List<Class<TokenFilter>> tokenFilterClasses) {
            this.tokenFilterClasses = tokenFilterClasses;
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

        // TODO
        Analyzer analyzer = null;//new PerFieldAnalyzerWrapper(defaultAnalyzer, perFieldAnalyzers);
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

            // and... what if extension is not listed in enum??
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
