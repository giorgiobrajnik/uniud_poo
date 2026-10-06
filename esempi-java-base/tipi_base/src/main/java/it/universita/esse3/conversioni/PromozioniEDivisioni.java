package it.universita.esse3.conversioni;

public class PromozioniEDivisioni {
    // byte + byte e' un int
    public static int sommaByte(byte a, byte b) {
        return a + b;
    }

    public static String tipoDellaSommaDiByte() {
        byte a = 10;
        byte b = 20;
        Object risultato = a + b;
        return risultato.getClass().getSimpleName();
    }

    // CONTROESEMPIO: divisione tra int, poi conversione a double
    public static double rapportoConDivisioneIntera(int promossi, int iscritti) {
        return promossi / iscritti;
    }

    public static double rapportoConCast(int promossi, int iscritti) {
        return (double) promossi / iscritti;
    }

    public static double rapportoConLetterale(int promossi) {
        return promossi / 3.0;
    }

    public static double tassoPresenza(int presenti, int iscritti) {
        return (double) presenti / iscritti;
    }
}
