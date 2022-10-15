package it.uniroma3.idd.hw2.filesystem;

import it.uniroma3.idd.hw2.filesystem.impl.GenericFileReaderImpl;
import lombok.Getter;

@Getter
public enum Extensions {

    TXT("txt", new GenericFileReaderImpl()),
    ;

    Extensions(String extension, CustomFileReader customFileReader) {
        this.extension = extension;
        this.fileReader = customFileReader;
    }

    private String extension;
    private CustomFileReader fileReader;

    public static Extensions fromString(String extension) {
        for(Extensions ext : Extensions.values()) {
            if(ext.getExtension().equals(extension))
                return ext;
        }

        return null;
    }

}
