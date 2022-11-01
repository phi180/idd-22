package it.uniroma3.idd.hw2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResultsDTO {

    private List<ResultEntryDTO> resultListDTO = new ArrayList<>();

}
