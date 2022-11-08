package it.uniroma3.idd.enums;

public enum DownloadEngine {
    HTML_UNIT("htmlunit")
    ;

    DownloadEngine(String propertyName) {
        this.propertyName = propertyName;
    }

    final String propertyName;

    public static DownloadEngine fromString(String propertyName) {
        for(DownloadEngine de : DownloadEngine.values()) {
            if(de.propertyName.equals(propertyName)) {
                return de;
            }
        }
        return null;
    }
}
