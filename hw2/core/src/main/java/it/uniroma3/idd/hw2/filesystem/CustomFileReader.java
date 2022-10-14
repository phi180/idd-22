package it.uniroma3.idd.hw2.filesystem;

import java.io.IOException;

public interface CustomFileReader {
    String readContent(String path) throws IOException;

}
