package it.uniud.pizzeria;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Archivio dei servizi e regole di occupazione dei tavoli.
 *
 * <p>Stato astratto: un insieme di servizi, ognuno con un identificativo intero
 * positivo univoco assegnato in ordine crescente a partire da 1. Gli identificativi
 * già assegnati non vengono riutilizzati. Invariante: un tavolo ha al più un servizio
 * attivo.</p>
 */
class RegistroServizi {
    private final Map<Long, ServizioTavolo> servizi = new LinkedHashMap<>();
    private long prossimoId = 1;

    /**
     * {@summary PRE: tavoli, cameriere e apertoAlle non null; coperti positivo; i tavoli sono già stati validati dalla sala.<br>
     * POST: se nessun tavolo ha un servizio attivo né un servizio chiuso dopo apertoAlle, aggiunge un nuovo servizio attivo e restituisce il suo identificativo.<br>
     * TODO: metodo non completo: contratto e comportamento per tavoli occupati o apertura anticipata da definire.}
     */
    long apri(List<Tavolo> tavoli, Cameriere cameriere, int coperti, LocalDateTime apertoAlle) {
        if (!sonoDisponibili(tavoli, apertoAlle)) {
            return 0;
        }
        long id = prossimoId++;
        servizi.put(id, new ServizioTavolo(id, tavoli, cameriere, coperti, apertoAlle));
        return id;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce il servizio con quell'id.<br>
     * TODO: metodo non completo: contratto e comportamento per il servizio inesistente da definire.}
     */
    ServizioTavolo richiedi(long id) {
        ServizioTavolo servizio = servizi.get(id);
        if (servizio == null) {
            System.out.println("TODO: gestire casi anomali");
            return null;
        }
        return servizio;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce il servizio con quell'id se è attivo.<br>
     * TODO: metodo non completo: contratto e comportamento per servizio inesistente o già pagato da definire.}
     */
    ServizioTavolo richiediAttivo(long id) {
        ServizioTavolo servizio = richiedi(id);
        if (servizio == null) {
            return null;
        }
        if (!servizio.isAttivo()) {
            System.out.println("TODO: gestire casi anomali");
            return null;
        }
        return servizio;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una copia di tutti i servizi, in ordine di apertura; lo stato è invariato.}
     */
    List<ServizioTavolo> elencaTutti() {
        return List.copyOf(servizi.values());
    }

    /**
     * {@summary PRE: richiesti e nuovaApertura non null.<br>
     * POST: restituisce true se nessun tavolo ha un servizio attivo né un servizio chiuso dopo nuovaApertura; lo stato è invariato.<br>
     * TODO: metodo non completo: contratto e comportamento per tavoli occupati o apertura anticipata da definire.}
     */
    private boolean sonoDisponibili(List<Tavolo> richiesti, LocalDateTime nuovaApertura) {
        for (Tavolo tavolo : richiesti) {
            for (ServizioTavolo servizio : servizi.values()) {
                if (servizio.isAttivo() && servizio.includeTavolo(tavolo.getNumero())) {
                    System.out.println("TODO: gestire casi anomali");
                    return false;
                }
            }
            for (ServizioTavolo servizio : servizi.values()) {
                if (servizio.includeTavolo(tavolo.getNumero())
                        && servizio.getPagatoAlle() != null
                        && nuovaApertura.isBefore(servizio.getPagatoAlle())) {
                    System.out.println("TODO: gestire casi anomali");
                    return false;
                }
            }
        }
        return true;
    }
}
