package it.universita.esse3.virgolamobile;

public class EsempioVirgolaMobile {
    public static void main(String[] args) {
        System.out.println("--- Approssimazione ---");
        System.out.println("0.1 + 0.2 = " + Approssimazione.zeroUnoPiuZeroDue());
        System.out.println("0.1 + 0.2 == 0.3? " + Approssimazione.sommaUgualeATreDecimi());
        System.out.println("|0.1 + 0.2 - 0.3| < 1e-9? " + Approssimazione.sommaVicinaATreDecimi(1e-9));

        System.out.println("--- IEEE 754 di 5.5 ---");
        double valore = 5.5;
        System.out.println("segno:        " + Ieee754.segno(valore));
        System.out.println("esponente:    " + Ieee754.esponenteMemorizzato(valore) + " (effettivo "
                + Ieee754.esponenteEffettivo(valore) + ")");
        System.out.println("mantissa:     " + Long.toBinaryString(Ieee754.mantissa(valore)));
        System.out.println("ricostruito:  " + Ieee754.ricostruisci(valore));

        System.out.println("--- Valori speciali ---");
        System.out.println("0.0 / 0.0  = " + ValoriSpeciali.zeroSuZero());
        System.out.println("1.0 / 0.0  = " + ValoriSpeciali.unoSuZero());
        System.out.println("-1.0 / 0.0 = " + ValoriSpeciali.menoUnoSuZero());
        try {
            ValoriSpeciali.divisioneInteraPerZero(1, 0);
        } catch (ArithmeticException e) {
            System.out.println("1 / 0 (int): " + e);
        }

        System.out.println("--- Denaro ---");
        System.out.println("Controesempio, 10 x 0.1 con double: " + Denaro.sommaDieciVolteConDouble());
        System.out.println("10 x 10 centesimi:                  " + Denaro.sommaDieciVolteInCentesimi());
        System.out.println("10 x BigDecimal(\"0.1\"):             " + Denaro.sommaDieciVolteConBigDecimal());
        System.out.println("Controesempio, new BigDecimal(0.1): " + Denaro.bigDecimalDaDouble());
    }
}
