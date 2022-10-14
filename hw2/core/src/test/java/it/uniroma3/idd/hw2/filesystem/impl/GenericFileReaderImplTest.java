package it.uniroma3.idd.hw2.filesystem.impl;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericFileReaderImplTest {

    private static final String TEST_DIRNAME = "./testdir";
    private static final String TEST_FILENAME = "/test.txt";
    private static final String FILE_CONTENT = "test file";

    private GenericFileReaderImpl fileReader;

    @BeforeAll
    static void initTestEnv() {
        generateDirectory();
        generateFiles();
    }

    @BeforeEach
    void setUp() {
        fileReader = new GenericFileReaderImpl();
    }

    @AfterAll
    static void tearDown() {
        File testDir = new File(TEST_DIRNAME);
        try {
            FileUtils.deleteDirectory(testDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReadContentStandardCase() throws IOException {
        assertEquals(FILE_CONTENT,FileUtils.readFileToString(new File(TEST_DIRNAME + TEST_FILENAME), "UTF-8"));
    }

    private static void generateDirectory() {
        File testDir = new File(TEST_DIRNAME);
        if (!testDir.exists()){
            testDir.mkdirs();
        }
    }

    private static void generateFiles() {
        File testFile = new File(TEST_DIRNAME + TEST_FILENAME);
        try {
            FileUtils.writeStringToFile(testFile, FILE_CONTENT, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}