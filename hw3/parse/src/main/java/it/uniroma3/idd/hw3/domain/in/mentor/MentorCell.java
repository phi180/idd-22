package it.uniroma3.idd.hw3.domain.in.mentor;

import lombok.Data;

import java.util.List;

@Data
public class MentorCell {

    private String className;

    private String innerHTML;

    private boolean isHeader;

    private String type;

    private MentorCoordinates Coordinates;

    private String cleanedText;

    private List<MentorCellRows> Rows;

}
