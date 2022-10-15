package it.uniroma3.idd.hw2.utils.enums;

import lombok.Getter;
import org.apache.lucene.analysis.TokenizerFactory;
import org.apache.lucene.analysis.core.WhitespaceTokenizerFactory;

@Getter
public enum Tokenizers {

    WHITESPACE("WhitespaceTokenizerFactory", WhitespaceTokenizerFactory.class)
    ;

    Tokenizers(String property, Class<? extends TokenizerFactory> tokenizer) {
        this.property = property;
        this.tokenizer = tokenizer;
    }

    private String property;
    private Class<? extends TokenizerFactory> tokenizer;

    public static Tokenizers fromPropertyValue(String property) {
        for(Tokenizers t: Tokenizers.values()) {
            if(t.getProperty().equals(property))
                return t;
        }
        return null;
    }

}
