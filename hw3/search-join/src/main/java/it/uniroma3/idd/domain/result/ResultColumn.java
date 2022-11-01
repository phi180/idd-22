package it.uniroma3.idd.domain.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultColumn {

    private String tableId;
    private Long columnNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultColumn that = (ResultColumn) o;
        return Objects.equals(tableId, that.tableId) && Objects.equals(columnNum, that.columnNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, columnNum);
    }

    @Override
    public String toString() {
        return this.tableId + ":" + this.columnNum;
    }
}
