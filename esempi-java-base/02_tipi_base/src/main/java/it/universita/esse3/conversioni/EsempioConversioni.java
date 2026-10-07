package it.universita.esse3.conversioni;

/**
 * Conversioni tra tipi primitivi.
 * Widening implicito ({@code int} a {@code long}, {@code char} a {@code int});
 * controesempio:
 * {@code long} a {@code double} e' ammesso ma perde precisione oltre 2^53. Il
 * cast
 * (narrowing) compila sempre ma applica solo le regole del linguaggio:
 * {@code (byte) 130}
 * vale -126, {@code (int) -27.9} vale -27, e {@code (int)} su un id
 * {@code long} troppo
 * grande lo rende negativo. Mostra che {@code byte + byte} e' un {@code int} e
 * che la
 * divisione tra {@code int} e' intera: {@code 2 / 3} vale 0, mentre
 * {@code (double) 2 / 3}
 * vale circa 0,667.
 */
public class EsempioConversioni {
    public static void main(String[] args) {
        System.out.println("--- Widening ---");
        System.out.println("int 180 -> long: " + Widening.daIntALong(180));
        System.out.println("char 'A' -> int: " + Widening.daCharAInt('A'));
        long grande = 9_007_199_254_740_993L;
        System.out.println("long " + grande + " -> double: " + (long) Widening.daLongADouble(grande));
        System.out.println("Controesempio, nessuna perdita? " + Widening.longDoubleSenzaPerdita(grande));

        System.out.println("--- Narrowing e cast ---");
        System.out.println("(byte) 130   = " + Narrowing.aByte(130));
        System.out.println("(int) 27.9   = " + Narrowing.troncaVersoZero(27.9));
        System.out.println("(int) -27.9  = " + Narrowing.troncaVersoZero(-27.9));

        long idStudente = 3_500_000_000L;
        System.out.println("ID originale (long): " + idStudente);
        System.out.println("ID castato (int):    " + Narrowing.aInt(idStudente));
        System.out.println("Sono uguali? " + Narrowing.idSopravvive(idStudente));

        System.out.println("--- Promozioni numeriche ---");
        System.out.println("byte + byte e' di tipo " + PromozioniEDivisioni.tipoDellaSommaDiByte());

        System.out.println("--- Divisione intera ---");
        System.out.println("Controesempio, 2 / 3 in int: " + PromozioniEDivisioni.rapportoConDivisioneIntera(2, 3));
        System.out.println("(double) 2 / 3:             " + PromozioniEDivisioni.rapportoConCast(2, 3));
        System.out.println("2 / 3.0:                    " + PromozioniEDivisioni.rapportoConLetterale(2));
        System.out.println("Tasso di presenza 37/52:    " + PromozioniEDivisioni.tassoPresenza(37, 52));

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println("vedi ByteDaSomma e IntDaDouble in src/test/resources/non-compilabili");
    }
}
