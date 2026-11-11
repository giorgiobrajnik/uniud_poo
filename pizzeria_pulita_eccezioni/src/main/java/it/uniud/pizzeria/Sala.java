package it.uniud.pizzeria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Configurazione fisica della sala: tavoli e camerieri.
 *
 * <p>Stato astratto: l'elenco dei tavoli e quello dei camerieri, ognuno responsabile
 * di un insieme di tavoli. Immutabile dopo la costruzione. Non conosce servizi,
 * ordini o menu.</p>
 */
class Sala {
    private final List<Tavolo> tavoli;
    private final List<Cameriere> camerieri;

    /**
     * {@summary PRE: tavoli e camerieri non null.<br>
     * POST: crea una sala con copie non modificabili degli elenchi ricevuti.}
     */
    Sala(List<Tavolo> tavoli, List<Cameriere> camerieri) {
        this.tavoli = List.copyOf(tavoli);
        this.camerieri = List.copyOf(camerieri);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la lista non modificabile dei tavoli; lo stato è invariato.}
     */
    List<Tavolo> getTavoli() {
        return tavoli;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la lista non modificabile dei camerieri; lo stato è invariato.}
     */
    List<Cameriere> getCamerieri() {
        return camerieri;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce il tavolo con quel numero.<br>
     * TODO: metodo non completo: contratto e comportamento per il tavolo inesistente da definire.}
     */
    Tavolo richiediTavolo(int numero) {
        for (Tavolo tavolo : tavoli) {
            if (tavolo.getNumero() == numero) {
                return tavolo;
            }
        }
        System.out.println("TODO: gestire casi anomali");
        return null;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce il primo cameriere che serve il tavolo.<br>
     * TODO: metodo non completo: contratto e comportamento per il tavolo senza cameriere da definire.}
     */
    Cameriere trovaCameriere(int numeroTavolo) {
        for (Cameriere cameriere : camerieri) {
            if (cameriere.serve(numeroTavolo)) {
                return cameriere;
            }
        }
        System.out.println("TODO: gestire casi anomali");
        return null;
    }

    /**
     * {@summary PRE: numeriTavoli non null; coperti positivo.<br>
     * POST: restituisce i tavoli richiesti, nello stesso ordine, se esistono, sono distinti, della stessa zona e con capienza totale almeno coperti; lo stato è invariato.<br>
     * TODO: metodo non completo: contratto e comportamento per le configurazioni non valide da definire.}
     */
    List<Tavolo> validaConfigurazione(List<Integer> numeriTavoli, int coperti) {
        if (numeriTavoli.isEmpty()) {
            System.out.println("TODO: gestire casi anomali");
            return List.of();
        }
        if (new HashSet<>(numeriTavoli).size() != numeriTavoli.size()) {
            System.out.println("TODO: gestire casi anomali");
            return List.of();
        }
        List<Tavolo> richiesti = new ArrayList<>();
        for (int numero : numeriTavoli) {
            Tavolo tavolo = richiediTavolo(numero);
            if (tavolo == null) {
                return List.of();
            }
            richiesti.add(tavolo);
        }
        Cameriere cameriere = trovaCameriere(richiesti.getFirst().getNumero());
        if (cameriere == null) {
            return List.of();
        }
        int capienza = 0;
        for (Tavolo tavolo : richiesti) {
            if (!cameriere.serve(tavolo.getNumero())) {
                System.out.println("TODO: gestire casi anomali");
                return List.of();
            }
            capienza += tavolo.getCoperti();
        }
        if (coperti > capienza) {
            System.out.println("TODO: gestire casi anomali");
            return List.of();
        }
        return richiesti;
    }
}
