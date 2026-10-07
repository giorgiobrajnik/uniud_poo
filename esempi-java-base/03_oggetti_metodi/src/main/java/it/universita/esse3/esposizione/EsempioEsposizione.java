package it.universita.esse3.esposizione;

import java.util.ArrayList;
import java.util.List;

/**
 * Esposizione della rappresentazione e copia difensiva.
 * Un campo {@code private final} non basta a proteggere lo stato se punta a un
 * oggetto
 * mutabile. Controesempi: {@link PianoStudiCondiviso} conserva la lista del
 * chiamante, che
 * puo' cambiarla dopo la costruzione; {@link LibrettoEsposto} restituisce la
 * lista interna
 * dal getter, e un client ci aggiunge un voto non previsto. Versioni protette:
 * {@link PianoStudi} copia la lista nel costruttore con {@code List.copyOf} e
 * {@link Libretto} restituisce una copia non modificabile.
 */
public class EsempioEsposizione {
    public static void main(String[] args) {
        System.out.println("--- Controesempio: lista del chiamante condivisa ---");
        List<String> corsi = new ArrayList<>();
        corsi.add("POO");
        PianoStudiCondiviso condiviso = new PianoStudiCondiviso(corsi);
        corsi.add("BASI-DATI");
        System.out.println("Insegnamenti nel piano: " + condiviso.numeroInsegnamenti());

        System.out.println("--- Copia difensiva ---");
        corsi = new ArrayList<>();
        corsi.add("POO");
        PianoStudi piano = new PianoStudi(corsi);
        corsi.add("BASI-DATI");
        System.out.println("Insegnamenti nel piano: " + piano.numeroInsegnamenti());

        System.out.println("--- Controesempio: getter che espone la rappresentazione ---");
        LibrettoEsposto esposto = new LibrettoEsposto();
        esposto.getVoti().add(99);
        System.out.println("Voti nel libretto: " + esposto.numeroVoti());

        System.out.println("--- Getter con copia ---");
        Libretto libretto = new Libretto();
        libretto.registra(30);
        try {
            libretto.getVoti().add(99);
        } catch (UnsupportedOperationException e) {
            System.out.println("La copia non e' modificabile");
        }
        System.out.println("Voti nel libretto: " + libretto.numeroVoti());
    }
}
