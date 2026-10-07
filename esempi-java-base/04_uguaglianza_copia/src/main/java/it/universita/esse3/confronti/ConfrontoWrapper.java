package it.universita.esse3.confronti;

public final class ConfrontoWrapper {
    private ConfrontoWrapper() {
    }

    // Vero per la cache dei piccoli Integer (-128..127)
    public static boolean piccoliConDoppioUguale() {
        Integer x = 100;
        Integer y = 100;
        return x == y;
    }

    // CONTROESEMPIO: tipicamente false, non farci affidamento
    public static boolean grandiConDoppioUguale() {
        Integer p = 1000;
        Integer q = 1000;
        return p == q;
    }

    public static boolean grandiConEquals() {
        Integer p = 1000;
        Integer q = 1000;
        return p.equals(q);
    }

    // CONTROESEMPIO: equals confronta anche il tipo del wrapper
    public static boolean integerEqualsLong() {
        return Integer.valueOf(1).equals(Long.valueOf(1));
    }

    public static boolean primitiviConDoppioUguale() {
        int a = 1000;
        int b = 1000;
        return a == b;
    }
}
