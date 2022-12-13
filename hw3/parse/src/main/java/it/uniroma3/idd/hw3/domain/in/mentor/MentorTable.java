package it.uniroma3.idd.hw3.domain.in.mentor;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MentorTable {

    private MentorTableId _id;

    private String className;

    private String id;

    private List<MentorCell> cells = new ArrayList<>();

    private String beginIndex;

    private String endIndex;

    private String referenceContext;

    private String type;

    private String classe;

    private MentorTableMaxDimensions maxDimensions;

    private List<String> headersCleaned = new ArrayList<>();

    private Integer keyColumn;

}
