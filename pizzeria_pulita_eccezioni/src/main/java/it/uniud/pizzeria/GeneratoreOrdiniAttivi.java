package it.uniud.pizzeria;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Costruisce la vista degli ordini non ancora pagati.
 *
 * <p>Stato astratto: nessuno. Non conserva nulla tra una chiamata e l'altra e non
 * modifica i dati ricevuti.</p>
 */
class GeneratoreOrdiniAttivi {
    /**
     * {@summary PRE: ordini e servizi non null; ogni ordine appartiene a un servizio presente nel registro.<br>
     * POST: restituisce le viste non modificabili degli ordini non pagati, in ordine di creazione (a parità, per id). Gli argomenti non vengono modificati.}
     */
    List<Viste.OrdineAttivo> genera(List<Ordine> ordini, RegistroServizi servizi) {
        return ordini.stream()
                .filter(ordine -> ordine.getStato() != StatoOrdine.PAGATO)
                .sorted(Comparator.comparing(Ordine::getCreatoAlle).thenComparingLong(Ordine::getId))
                .map(ordine -> creaVista(ordine, servizi.richiedi(ordine.getIdServizio())))
                .toList();
    }

    /**
     * {@summary PRE: ordine e servizio non null; l'ordine appartiene al servizio.<br>
     * POST: restituisce la vista dell'ordine con tavoli, cameriere, righe, stato, orario e importo parziale; lo stato è invariato.}
     */
    private Viste.OrdineAttivo creaVista(Ordine ordine, ServizioTavolo servizio) {
        List<Integer> numeriTavoli = new ArrayList<>();
        for (Tavolo tavolo : servizio.getTavoli()) {
            numeriTavoli.add(tavolo.getNumero());
        }
        return new Viste.OrdineAttivo(
                ordine.getId(),
                ordine.getIdServizio(),
                numeriTavoli,
                servizio.getCameriere().getNomeCompleto(),
                ordine.getRighe(),
                ordine.getStato(),
                ordine.getCreatoAlle(),
                ordine.calcolaTotale());
    }
}
