package it.uniroma3.idd.hw4.logic;

import it.uniroma3.idd.hw4.domain.ExtractedData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorLogicTest {

    private static final String URL = "http://www.mat.uniroma3.it/users/esposito/didattica.html";
    private static final String SAMPLE_XPATH = "//table[1]/tbody[1]/tr[1]/td[1]/span[1]";

    @Test
    void extractSimpleData() {
        List<String> xpaths = new ArrayList<>();
        xpaths.add(SAMPLE_XPATH);

        ExtractorLogic extractorLogic = new ExtractorLogic();
        ExtractedData extractedData = extractorLogic.extractData(URL, xpaths);

        assertEquals("Corsi degli anni passati", extractedData.getXpath2data().get(SAMPLE_XPATH));
    }

}