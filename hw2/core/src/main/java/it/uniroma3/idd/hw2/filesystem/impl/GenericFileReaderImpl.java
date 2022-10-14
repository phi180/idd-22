package it.uniroma3.idd.hw2.filesystem.impl;

import it.uniroma3.idd.hw2.filesystem.CustomFileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenericFileReaderImpl implements CustomFileReader {

    @Override
    public String readContent(String path) {
        Path filePath = Path.of(path);
        String fileContent = "";

        try {
            fileContent = Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Error while reading file");
        }

        return fileContent;
    }

}
