package it.universita.esse3.memoria;

/**
 * Modello concettuale della memoria: stack, heap e garbage collection.
 * Ogni invocazione di metodo ha un frame con parametri e variabili locali;
 * controesempio:
 * una ricorsione senza fine esaurisce lo stack e lancia
 * {@link StackOverflowError}. Gli
 * oggetti vivono nello heap finche' sono raggiungibili: un oggetto con un
 * riferimento
 * forte non viene raccolto, mentre dopo {@code s = null} diventa solo un
 * candidato (il
 * momento della raccolta non e' garantito). Controesempio: una lista statica
 * che cresce
 * trattiene oggetti inutili ma ancora raggiungibili.
 */
public class EsempioMemoria {
    public static void main(String[] args) {
        System.out.println("--- Frame di attivazione ---");
        System.out.println("sommaCfu(6, 9) = " + Frame.sommaCfu(6, 9));

        System.out.println("--- Controesempio: ricorsione infinita ---");
        System.out.println("Frame creati prima dello StackOverflowError: "
                + Frame.profonditaRaggiuntaPrimaDelloStackOverflow());

        System.out.println("--- Raggiungibilita e garbage collection ---");
        System.out.println("Raggiungibile resta vivo? " + Raggiungibilita.oggettoRaggiungibileRestaVivo());
        System.out.println("Dopo s = null e System.gc(), raccolto? "
                + Raggiungibilita.oggettoNonRaggiungibileRaccoltoDopoGc() + " (non garantito)");

        System.out.println("--- Controesempio: oggetti inutili ma raggiungibili ---");
        for (int i = 0; i < 1000; i++) {
            LogTemporaneo.registra("evento " + i);
        }
        System.out.println("Elementi trattenuti dalla lista statica: " + LogTemporaneo.dimensione());
    }
}
