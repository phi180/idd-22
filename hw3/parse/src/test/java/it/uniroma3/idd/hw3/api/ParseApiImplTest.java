package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.api.ParseApi;
import it.uniroma3.idd.entity.TableVO;
import it.uniroma3.idd.hw3.TestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ParseApiImplTest {

    private static final String JSON_PATH = TestUtils.getPropertiesFullPath("sample.json");

    private ParseApi parseApi;

    @Test
    void testParseOnSampleInput() throws IOException {
        parseApi = new ParseApiImpl();

        TableVO tableVO = parseApi.parse(FileUtils.readFileToString(new File(JSON_PATH), "UTF-8"));

        // Testing column names
        assertEquals("Team 1", tableVO.getColumns().get(0).getHeader());
        assertEquals("Team 2", tableVO.getColumns().get(2).getHeader());

        // Testing cell content
        assertEquals("4–3", tableVO.getColumns().get(1).getCells().get(1).getContent());

        // Testing max dimensions
        assertEquals(38, tableVO.getMaxNumRows());
        assertEquals(3, tableVO.getMaxNumColumns());
    }
}