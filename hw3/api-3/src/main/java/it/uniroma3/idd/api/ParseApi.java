package it.uniroma3.idd.api;

import it.uniroma3.idd.entity.TableVO;

public interface ParseApi {

    /**
     * @param inputJsonText raw json entry from dataset
     * @return a structured table associated to the input, output is cleaned
     * */
    TableVO parse(String inputJsonText);

    /**
     * @param inputJsonText raw json entry from dataset
     * @return a structured table associated to the input, not filtering html code
     * */
    TableVO parseRaw(String inputJsonText);

}
