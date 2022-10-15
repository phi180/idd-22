package it.uniroma3.idd.hw2.utils.enums;

import lombok.Getter;
import org.apache.lucene.analysis.TokenFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterGraphFilterFactory;

@Getter
public enum TokenFilters {

    LOWER_CASE("LowerCaseFilterFactory", LowerCaseFilterFactory.class),
    WORD_DELIMITER_GRAPH("WordDelimiterGraphFilterFactory", WordDelimiterGraphFilterFactory.class)
    ;

    TokenFilters(String property, Class<? extends TokenFilterFactory> tokenFilter) {
        this.property = property;
        this.tokenFilter = tokenFilter;
    }

    private String property;
    private Class<? extends TokenFilterFactory> tokenFilter;

    public static TokenFilters fromPropertyValue(String property) {
        for(TokenFilters tf : TokenFilters.values()) {
            if(tf.getProperty().equals(property))
                return tf;
        }
        return null;
    }

}
