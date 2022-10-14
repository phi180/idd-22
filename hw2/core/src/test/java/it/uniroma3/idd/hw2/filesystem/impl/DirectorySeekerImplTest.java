package it.uniroma3.idd.hw2.filesystem.impl;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DirectorySeekerImplTest {

    private static final String TEST_DIRNAME = "./testdir";
    private static final String TEST_SUBDIRNAME = "/a";
    private static final String TEST_FILENAME = "/B.txt";
    private static final String TEST_SUBFILENAME = "/A.txt";
    private static final String FILE_CONTENT = "test file";

    private DirectorySeekerImpl directorySeeker;

    @BeforeAll
    static void initTestEnv() {
        generateDirectory();
        generateFiles();
    }

    @BeforeEach
    void setUp() {
        directorySeeker = new DirectorySeekerImpl(TEST_DIRNAME);
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
    void testListAllFiles() {
        String file1 = directorySeeker.next();
        assertTrue(file1.endsWith("A.txt"));
        String file2 = directorySeeker.next();
        assertTrue(file2.endsWith("B.txt"));
    }

    private static void generateDirectory() {
        File testDir = new File(TEST_DIRNAME);
        if (!testDir.exists()){
            testDir.mkdirs();
        }
        testDir = new File(TEST_DIRNAME+TEST_SUBDIRNAME);
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
        testFile = new File(TEST_DIRNAME + TEST_SUBDIRNAME + TEST_SUBFILENAME);
        try {
            FileUtils.writeStringToFile(testFile, FILE_CONTENT, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}