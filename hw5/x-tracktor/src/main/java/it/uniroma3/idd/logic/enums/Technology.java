package it.uniroma3.idd.logic.enums;

public enum Technology {

    SELENIUM("selenium"),
    JSOUP("jsoup");

    private String propName;

    Technology(String propName) {
        this.propName = propName;
    }

    public static Technology fromString(String technology) {
        for(Technology t:values()) {
            if(t.propName.equals(technology))
                return t;
        }
        return null;
    }
}
