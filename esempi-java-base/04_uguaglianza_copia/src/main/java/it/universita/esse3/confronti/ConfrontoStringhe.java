package it.universita.esse3.confronti;

public final class ConfrontoStringhe {
    private ConfrontoStringhe() {
    }

    // CONTROESEMPIO: == su due String create con new
    public static boolean newConDoppioUguale() {
        String a = new String("POO");
        String b = new String("POO");
        return a == b;
    }

    public static boolean newConEquals() {
        String a = new String("POO");
        String b = new String("POO");
        return a.equals(b);
    }

    // Vero perché i letterali sono condivisi dalla JVM: non è un motivo per usare ==
    public static boolean letteraliConDoppioUguale() {
        String a = "POO";
        String b = "POO";
        return a == b;
    }

    public static boolean letteraleContraNewConDoppioUguale() {
        String a = "POO";
        String b = new String("POO");
        return a == b;
    }
}
