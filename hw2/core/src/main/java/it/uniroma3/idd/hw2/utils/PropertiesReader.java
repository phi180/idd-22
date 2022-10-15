package it.uniroma3.idd.hw2.utils;

import it.uniroma3.idd.hw2.utils.enums.TokenFilters;
import it.uniroma3.idd.hw2.utils.enums.Tokenizers;
import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.TokenizerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesReader {

    private static final String PROPERTIES_FILE = "src/main/resources/application.properties";
    private static final String SPLIT = ",";

    public static Class<TokenizerFactory> readTokenizer() {
        Class<? extends TokenizerFactory> tokenizerFactory = null;

        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            String tokenizer = prop.getProperty("tokenizer");
            tokenizerFactory = Tokenizers.fromPropertyValue(tokenizer).getTokenizer();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return (Class<TokenizerFactory>) tokenizerFactory;
    }

    public static List<Class<TokenFilterFactory>> readTokenFilters() {
        List<Class<TokenFilterFactory>> tokenFiltersFactory = new ArrayList<>();

        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            String[] tokenFilters = prop.getProperty("filters").split(SPLIT);
            for(String tokenFilter : tokenFilters) {
                tokenFiltersFactory.add((Class<TokenFilterFactory>)TokenFilters.fromPropertyValue(tokenFilter).getTokenFilter());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return tokenFiltersFactory;
    }

}
