package it.uniroma3.idd.domain.enums;

public enum Granularity {

    COLUMN,
    CELL
    ;

    public static Granularity fromString(String granularity) {
        Granularity gran = null;
        for(Granularity g:Granularity.values()) {
            if(g.name().equals(granularity))
                return g;
        }
        return gran;
    }

}
