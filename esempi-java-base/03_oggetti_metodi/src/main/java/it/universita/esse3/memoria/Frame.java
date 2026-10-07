package it.universita.esse3.memoria;

public class Frame {
    // Ogni invocazione ha il proprio frame con parametri e variabili locali
    public static int sommaCfu(int a, int b) {
        int totale = a + b;
        return totale;
    }

    private static int profondita;

    private static void scendi() {
        profondita++;
        scendi();
    }

    // CONTROESEMPIO: la ricorsione senza fine esaurisce lo stack
    public static int profonditaRaggiuntaPrimaDelloStackOverflow() {
        profondita = 0;
        try {
            scendi();
        } catch (StackOverflowError e) {
            return profondita;
        }
        return -1;
    }
}
