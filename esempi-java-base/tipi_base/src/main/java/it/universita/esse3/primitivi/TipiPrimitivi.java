package it.universita.esse3.primitivi;

import java.util.List;

public class TipiPrimitivi {
    public static long idEvento() {
        return 4_000_000_000L;
    }

    // Il separatore _ serve solo alla leggibilita
    public static boolean underscoreNonCambiaIlValore() {
        return 4_000_000_000L == 4000000000L;
    }

    // Letterale con parte decimale: double; con suffisso F: float
    public static List<String> tipiDeiLetterali() {
        Object conSuffissoF = 1.5F;
        Object senzaSuffisso = 1.5;
        Object traApici = 'A';
        Object traVirgolette = "A";
        return List.of(
                conSuffissoF.getClass().getSimpleName(),
                senzaSuffisso.getClass().getSimpleName(),
                traApici.getClass().getSimpleName(),
                traVirgolette.getClass().getSimpleName());
    }

    // Un char e' una unita UTF-16: alcuni simboli ne richiedono due
    public static int unitaUtf16(String testo) {
        return testo.length();
    }

    public static int caratteriUnicode(String testo) {
        return testo.codePointCount(0, testo.length());
    }
}
