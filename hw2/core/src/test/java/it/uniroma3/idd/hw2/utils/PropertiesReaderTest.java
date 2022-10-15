package it.uniroma3.idd.hw2.utils;

import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesReaderTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testTokenizerRead() {
        assertEquals(WhitespaceTokenizerFactory.class, PropertiesReader.readTokenizer());
    }

    @Test
    void testTokenFilterRead() {
        List<Class<TokenFilterFactory>> tokenFilters = PropertiesReader.readTokenFilters();
        assertEquals(LowerCaseFilterFactory.class, tokenFilters.get(0));
        assertEquals(WordDelimiterGraphFilterFactory.class, tokenFilters.get(1));
    }
}