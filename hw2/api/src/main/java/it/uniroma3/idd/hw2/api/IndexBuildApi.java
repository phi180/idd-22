package it.uniroma3.idd.hw2.api;

public interface IndexBuildApi {

    /**
     * @param dirPath directory where files to be indexed are stored
     * */
    void buildIndex(String dirPath);

}
