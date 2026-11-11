package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcola il conto storico di un tavolo.
 *
 * <p>Stato astratto: nessuno. Non conserva nulla tra una chiamata e l'altra e non
 * modifica i dati ricevuti.</p>
 */
class CalcolatoreConto {
    /**
     * {@summary PRE: istante, servizi e ordini non null; il tavolo esiste (verificato dal chiamante).<br>
     * POST: restituisce il conto con esattamente gli ordini dei servizi che includono il tavolo, già creati e non ancora pagati all'istante, e il loro totale. Gli argomenti non vengono modificati.}
     */
    Viste.Conto calcola(
            int numeroTavolo,
            LocalDateTime istante,
            List<ServizioTavolo> servizi,
            List<Ordine> ordini) {
        List<Long> serviziDelTavolo = new ArrayList<>();
        for (ServizioTavolo servizio : servizi) {
            if (servizio.includeTavolo(numeroTavolo)) {
                serviziDelTavolo.add(servizio.getId());
            }
        }
        List<Viste.DettaglioOrdine> dettagli = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;
        for (Ordine ordine : ordini) {
            if (serviziDelTavolo.contains(ordine.getIdServizio()) && ordine.eraDaPagareA(istante)) {
                dettagli.add(new Viste.DettaglioOrdine(
                        ordine.getId(), ordine.getIdServizio(), ordine.getRighe(), ordine.calcolaTotale()));
                totale = totale.add(ordine.calcolaTotale());
            }
        }
        return new Viste.Conto(numeroTavolo, istante, dettagli, totale);
    }
}
