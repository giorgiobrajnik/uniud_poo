package it.universita.esse3.assegnamento;

/**
 * Variabili locali e definite assignment.
 * Una variabile locale non ha un valore di default: il compilatore pretende che
 * sia
 * sicuramente assegnata prima di ogni lettura. {@link VotoDaEsito} la assegna
 * in entrambi
 * i rami di un {@code if}/{@code else} e compila. Controesempi (non compilano,
 * vedi i
 * test): leggere una variabile mai assegnata, oppure assegnarla in un solo ramo
 * e leggerla
 * dopo. I campi, invece, ricevono un valore di default
 * ({@link ValoriDiDefault}):
 * {@code 0}, {@code false} o {@code null}.
 */
public class EsempioAssegnamento {
    public static void main(String[] args) {
        System.out.println("--- Esempio: entrambi i rami inizializzano ---");
        System.out.println("esame superato: " + VotoDaEsito.voto(true));
        System.out.println("esame non superato: " + VotoDaEsito.voto(false));

        System.out.println("--- Campi: valori di default ---");
        ValoriDiDefault campi = new ValoriDiDefault();
        System.out.println("int: " + campi.getVoto() + ", boolean: " + campi.isVerbalizzato()
                + ", String: " + campi.getMatricola());

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println("vedi LetturaSenzaAssegnamento e UnSoloRamoAssegna in src/test/resources/non-compilabili");
    }
}
