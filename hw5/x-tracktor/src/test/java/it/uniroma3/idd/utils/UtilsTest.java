package it.uniroma3.idd.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void writeUrlsToFile() {
        Utils.writeUrlsToFile("test", Arrays.asList("a","b"));
    }
}