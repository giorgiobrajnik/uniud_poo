package it.universita.esse3.conversioni;

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
