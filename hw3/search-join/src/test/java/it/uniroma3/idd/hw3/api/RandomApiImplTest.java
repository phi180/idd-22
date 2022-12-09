package it.uniroma3.idd.hw3.api;

import it.uniroma3.idd.entity.ColumnarTableVO;
import it.uniroma3.idd.hw3.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomApiImplTest {

    private static final String DATASET_PATH = Utils.getPropertiesFullPath("dataset/tables.json");

    private List<String> testOids = Arrays.asList("citta4895843uu394","ubuntu3489u934","presidenti4834r439");

    @Test
    void testGetRandomTable() {
        RandomApi randomApi = new RandomApiImpl();
        ColumnarTableVO tableVO = randomApi.getRandomTable(DATASET_PATH);

        assertTrue(testOids.stream().anyMatch(oid -> tableVO.getOid().equals(oid)));
    }

}