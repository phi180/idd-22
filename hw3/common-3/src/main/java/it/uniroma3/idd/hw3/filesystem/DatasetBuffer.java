package it.uniroma3.idd.hw3.filesystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DatasetBuffer {

    private boolean isEnded;
    private int currentLine = 0;

    private int BUFFER_SIZE = 8192;

    private BufferedReader reader;

    public DatasetBuffer(String datasetPath) throws FileNotFoundException {
        this.isEnded = false;
        this.reader = new BufferedReader(new FileReader(datasetPath), BUFFER_SIZE);
    }

    public String readNextLine() {
        String line = "";
        try {
            line = reader.readLine();
            currentLine++;
            if(line==null || line.trim().length()==0) {
                reader.close();
                this.isEnded = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
            System.out.println("Current line = "+ currentLine);
            line = "";
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
            if(line==null || line.trim().length()==0) {
                reader.close();
                this.isEnded = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
            line = "";
        }

        return line;
    }

    public boolean isEnded() {
        return this.isEnded;
    }

}
