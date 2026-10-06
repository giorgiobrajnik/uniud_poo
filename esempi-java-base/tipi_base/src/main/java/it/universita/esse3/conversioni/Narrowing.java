package it.universita.esse3.conversioni;

// CONTROESEMPI: il cast compila sempre, ma applica soltanto le regole del linguaggio
public class Narrowing {
    public static byte aByte(int valore) {
        return (byte) valore;
    }

    public static int troncaVersoZero(double valore) {
        return (int) valore;
    }

    // Tiene solo i 32 bit meno significativi
    public static int aInt(long valore) {
        return (int) valore;
    }

    public static boolean idSopravvive(long idStudente) {
        return idStudente == aInt(idStudente);
    }
}
