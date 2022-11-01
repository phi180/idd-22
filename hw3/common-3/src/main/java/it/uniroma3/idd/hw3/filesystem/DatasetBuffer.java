package it.uniroma3.idd.hw3.filesystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatasetBuffer {

    private boolean isEnded;

    private int currentLine = 0;
    private BufferedReader reader;

    public DatasetBuffer(String datasetPath) throws FileNotFoundException {
        this.isEnded = false;
        this.reader = new BufferedReader(new FileReader(datasetPath));
    }

    public String readNextLine() {
        String line = "";
        try {
            line = reader.readLine();
            currentLine++;
            if(line==null || line.length()==0) {
                reader.close();
                this.isEnded = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return line;
    }

    public String readLine(int lineNumber) {
        String line = "";
        try {
            for(int i = currentLine;i<lineNumber;i++) {
                reader.readLine();
            }
            line = reader.readLine();
            if(line==null || line.length()==0) {
                reader.close();
                this.isEnded = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return line;
    }

    public boolean isEnded() {
        return this.isEnded;
    }

}
