package it.universita.esse3.stringhe;

public class ConversioniStringhe {
    public static int parseCfu(String testo) {
        return Integer.parseInt(testo);
    }

    public static long parseId(String testo) {
        return Long.parseLong(testo);
    }

    public static double parseMedia(String testo) {
        return Double.parseDouble(testo);
    }

    // CONTROESEMPIO: qualunque testo diverso da "true" (senza badare alle maiuscole) vale false
    public static boolean parseAttivo(String testo) {
        return Boolean.parseBoolean(testo);
    }

    public static String cfuComeTesto(int cfu) {
        return Integer.toString(cfu);
    }

    public static String idComeTesto(long id) {
        return String.valueOf(id);
    }
}
