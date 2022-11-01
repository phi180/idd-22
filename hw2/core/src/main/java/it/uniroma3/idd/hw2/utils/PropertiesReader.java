package it.uniroma3.idd.hw2.utils;

import it.uniroma3.idd.hw2.utils.enums.TokenFilters;
import it.uniroma3.idd.hw2.utils.enums.Tokenizers;
import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.TokenizerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class PropertiesReader {

    private static final Logger logger = Logger.getLogger(PropertiesReader.class.toString());
    private static final String PROPERTIES_FILE = "lucene.properties";
    private static final String SPLIT = ",";

    private static String getPropertiesFullPath(String filename) {
        URL resource = PropertiesReader.class.getClassLoader().getResource(filename);
        File file = null;
        try {
            file = Paths.get(resource.toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        logger.info("getPropertiesFullPath() - path="+file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public static Class<TokenizerFactory> readTokenizer() {
        Class<? extends TokenizerFactory> tokenizerFactory = null;

        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            String tokenizer = prop.getProperty("tokenizer");
            tokenizerFactory = Tokenizers.fromPropertyValue(tokenizer).getTokenizer();
            logger.info("readTokenizer() - tokenizer="+tokenizer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return (Class<TokenizerFactory>) tokenizerFactory;
    }

    public static List<Class<TokenFilterFactory>> readTokenFilters() {
        List<Class<TokenFilterFactory>> tokenFiltersFactory = new ArrayList<>();

        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            String[] tokenFilters = prop.getProperty("filters").split(SPLIT);
            for(String tokenFilter : tokenFilters) {
                tokenFiltersFactory.add((Class<TokenFilterFactory>)TokenFilters.fromPropertyValue(tokenFilter).getTokenFilter());
            }
            logger.info("readTokenFilters() - filters="+tokenFilters);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return tokenFiltersFactory;
    }

    public static Boolean readExplainProperty() {
        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            String explain = prop.getProperty("explain");
            logger.info("readExplainProperty() - explain="+explain);
            return Boolean.valueOf(explain);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static Map<String,String> readTokenizerFactoryParams() {
        Map<String,String> param2val = new HashMap<>();

        try (InputStream input = new FileInputStream(getPropertiesFullPath(PROPERTIES_FILE))) {
            Properties prop = new Properties();
            prop.load(input);
            String paramsProp = prop.getProperty("tokenizerParams");
            logger.info("readTokenizerFactoryParams() - params="+paramsProp);

            if(!paramsProp.equals("")) {
                String[] params = paramsProp.split(",");
                for (int i = 0; i < params.length; i += 2) {
                    param2val.put(params[i], params[i + 1]);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return param2val;
    }

}
