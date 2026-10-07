package it.universita.esse3.virgolamobile;

// Scomposizione di un double nei campi IEEE 754 (1 bit di segno, 11 di esponente, 52 di mantissa)
public class Ieee754 {
    public static final int BIAS = 1023;

    public static int segno(double valore) {
        return (int) (Double.doubleToLongBits(valore) >>> 63);
    }

    public static int esponenteMemorizzato(double valore) {
        return (int) ((Double.doubleToLongBits(valore) >>> 52) & 0x7FF);
    }

    public static int esponenteEffettivo(double valore) {
        return esponenteMemorizzato(valore) - BIAS;
    }

    public static long mantissa(double valore) {
        return Double.doubleToLongBits(valore) & 0xFFFFFFFFFFFFFL;
    }

    // Ricostruisce il valore dai tre campi (solo numeri normalizzati)
    public static double ricostruisci(double valore) {
        double frazione = mantissa(valore) / (double) (1L << 52);
        double segnoMoltiplicatore = segno(valore) == 0 ? 1.0 : -1.0;
        return segnoMoltiplicatore * (1 + frazione) * Math.pow(2, esponenteEffettivo(valore));
    }
}
