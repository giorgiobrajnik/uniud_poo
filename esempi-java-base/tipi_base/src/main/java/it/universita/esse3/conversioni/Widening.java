package it.universita.esse3.conversioni;

public class Widening {
    public static long daIntALong(int valore) {
        return valore;
    }

    public static int daCharAInt(char lettera) {
        return lettera;
    }

    public static double daLongADouble(long valore) {
        return valore;
    }

    // CONTROESEMPIO: la conversione e' widening ma non sempre esatta
    public static boolean longDoubleSenzaPerdita(long valore) {
        return (long) daLongADouble(valore) == valore;
    }
}
