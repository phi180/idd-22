package it.uniroma3.idd.hw2.filesystem.impl;

import it.uniroma3.idd.hw2.filesystem.DirectorySeeker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectorySeekerImpl implements DirectorySeeker {

    private List<File> filesToBeIndexed = new ArrayList<>();
    private Integer currentIndex = 0;

    public DirectorySeekerImpl(String dirPath) {
        listFiles(dirPath, filesToBeIndexed);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < filesToBeIndexed.size();
    }

    @Override
    public String next() {
        File indexedFile = filesToBeIndexed.get(currentIndex);
        currentIndex++;

        return indexedFile.getAbsolutePath();
    }

    public void listFiles(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listFiles(file.getAbsolutePath(), files);
                }
            }
    }

}
