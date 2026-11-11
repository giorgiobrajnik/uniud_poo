package it.uniud.pizzeria;

import java.util.Set;
import lombok.Value;

/**
 * Cameriere della pizzeria.
 *
 * <p>Stato astratto: un codice, un nome e un cognome non vuoti, e un insieme non
 * vuoto di numeri di tavolo di cui è responsabile (la sua zona). Immutabile.</p>
 */
@Value
public class Cameriere {
    String codice;
    String nome;
    String cognome;
    Set<Integer> numeriTavoli;

    /**
     * {@summary PRE: codice, nome e cognome non null né vuoti; numeriTavoli non null e non vuoto.<br>
     * POST: crea un cameriere con i dati indicati; la sua zona è una copia di numeriTavoli.<br>
     * TODO: metodo non completo: contratto e comportamento per i dati non validi da definire.}
     */
    public Cameriere(String codice, String nome, String cognome, Set<Integer> numeriTavoli) {
        if (codice == null || codice.isBlank()) {
System.out.println("TODO: gestire casi anomali");
        }
        if (nome == null || nome.isBlank()) {
System.out.println("TODO: gestire casi anomali");
        }
        if (cognome == null || cognome.isBlank()) {
System.out.println("TODO: gestire casi anomali");
        }
        this.codice = codice;
        this.nome = nome;
        this.cognome = cognome;
        if (numeriTavoli.isEmpty()) {
System.out.println("TODO: gestire casi anomali");
        }
        this.numeriTavoli = Set.copyOf(numeriTavoli);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce "nome cognome"; lo stato è invariato.}
     */
    public String getNomeCompleto() {
        return nome + " " + cognome;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce true se e solo se numeroTavolo appartiene alla zona del cameriere; lo stato è invariato.}
     */
    public boolean serve(int numeroTavolo) {
        return numeriTavoli.contains(numeroTavolo);
    }
}
