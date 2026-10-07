package it.universita.esse3.primitivi;

/**
 * Tipi primitivi: intervalli, letterali e {@code char}.
 * Stampa gli intervalli di {@code byte}, {@code short}, {@code int} e {@code long}, il
 * valore dei letterali con suffisso {@code L} e con separatore {@code _}, e i tipi dei
 * letterali {@code 1.5F}, {@code 1.5}, {@code 'A'} e {@code "A"}. Mostra che un
 * {@code char} e' una sola unita' UTF-16: un simbolo Unicode fuori dal piano base occupa
 * due {@code char} anche se e' un solo carattere. Controesempio (non compila): un
 * {@code boolean} non e' un intero, quindi {@code boolean b = 1;} e' un errore.
 */
public class EsempioPrimitivi {
    public static void main(String[] args) {
        System.out.println("--- Intervalli degli interi ---");
        System.out.println("byte:  " + Byte.MIN_VALUE + " ... " + Byte.MAX_VALUE);
        System.out.println("short: " + Short.MIN_VALUE + " ... " + Short.MAX_VALUE);
        System.out.println("int:   " + Integer.MIN_VALUE + " ... " + Integer.MAX_VALUE);
        System.out.println("long:  " + Long.MIN_VALUE + " ... " + Long.MAX_VALUE);

        System.out.println("--- Letterali ---");
        System.out.println("idEvento = " + TipiPrimitivi.idEvento());
        System.out.println("4_000_000_000L == 4000000000L? " + TipiPrimitivi.underscoreNonCambiaIlValore());
        System.out.println("Tipi di 1.5F, 1.5, 'A', \"A\": " + TipiPrimitivi.tipiDeiLetterali());

        System.out.println("--- char e UTF-16 ---");
        String sorriso = "\uD83D\uDE00";
        System.out.println("Unita UTF-16 (char): " + TipiPrimitivi.unitaUtf16(sorriso));
        System.out.println("Caratteri Unicode:   " + TipiPrimitivi.caratteriUnicode(sorriso));

        System.out.println("--- Controesempio: boolean non e' un intero ---");
        System.out.println("vedi src/test/resources/non-compilabili/BooleanEInteri.java.txt");
    }
}
