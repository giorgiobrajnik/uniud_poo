package it.universita.esse3.finali;

/**
 * La parola chiave {@code final}: che cosa garantisce davvero.
 * Costanti {@code static final}, campi {@code final} assegnati nel costruttore
 * o alla
 * dichiarazione, variabili locali e parametri {@code final}, e una variabile
 * assegnata
 * una sola volta per ogni percorso di esecuzione. Controesempi: {@code final}
 * su un
 * riferimento blocca la variabile ma non l'oggetto, quindi una lista, un array
 * o uno
 * {@code StringBuilder} {@code final} possono comunque cambiare contenuto. Le
 * riassegnazioni di variabili {@code final} non compilano (vedi i test).
 */
public class EsempioFinali {
    public static void main(String[] args) {
        System.out.println("--- static final ---");
        System.out.println("Voti: " + RegoleCarriera.VOTO_MINIMO + " ... " + RegoleCarriera.VOTO_MASSIMO);

        System.out.println("--- Campi final ---");
        Appello appello = new Appello(81L, 10);
        appello.prenota();
        System.out.println("id " + appello.getId() + ", corso " + appello.getCodiceCorso() + ", posti "
                + appello.getPostiDisponibili());

        System.out.println("--- Variabili e parametri final ---");
        System.out.println("cfuEsame = " + FinalNeiRiferimenti.cfuLocaleFinale());
        System.out.println("voto (superato) = " + FinalNeiRiferimenti.votoAssegnatoUnaSolaVolta(true));
        System.out.println("voto (non superato) = " + FinalNeiRiferimenti.votoAssegnatoUnaSolaVolta(false));
        System.out.println("parametro final = " + FinalNeiRiferimenti.parametroFinale(27));

        System.out.println("--- Controesempi: final sul riferimento non congela l'oggetto ---");
        System.out.println("Elementi della lista final: " + FinalNeiRiferimenti.elementiDiUnaListaFinale());
        System.out.println("voti[0] dell'array final:   " + FinalNeiRiferimenti.votoDiUnArrayFinaleModificato());
        System.out.println("StringBuilder final:        " + FinalNeiRiferimenti.noteDiUnStringBuilderFinale());

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println("vedi Final* in src/test/resources/non-compilabili");
    }
}
