package it.uniroma3.idd.hw2.engine.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ResultEntry implements Comparable<ResultEntry> {

    private Integer docId;

    private String title;
    private Float rankWeight;

    @Override
    public int compareTo(ResultEntry other) {
        int compare = other.rankWeight.compareTo(this.rankWeight);
        if(compare==0)
            compare = docId.compareTo(other.docId);

        return compare;
    }
}
