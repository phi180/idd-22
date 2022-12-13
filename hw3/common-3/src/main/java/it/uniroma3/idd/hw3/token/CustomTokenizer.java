package it.uniroma3.idd.hw3.token;

public class CustomTokenizer {

    public static String[] tokenize(String text) {
        return text.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
    }

}
