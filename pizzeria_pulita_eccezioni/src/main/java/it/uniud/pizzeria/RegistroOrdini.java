package it.uniud.pizzeria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Archivio degli ordini e loro pagamento per servizio.
 *
 * <p>Stato astratto: un insieme di ordini, ognuno con un identificativo intero
 * positivo univoco assegnato in ordine crescente a partire da 1 e legato a un solo
 * servizio. Gli identificativi già assegnati non vengono riutilizzati.</p>
 */
class RegistroOrdini {
    private final Map<Long, Ordine> ordini = new LinkedHashMap<>();
    private long prossimoId = 1;

    /**
     * {@summary PRE: istante e pietanza non null; quantita positiva; il servizio esiste ed è attivo e la pietanza è disponibile (verificato dal chiamante).<br>
     * POST: aggiunge un nuovo ordine IN_PREPARAZIONE al servizio e restituisce il suo identificativo.}
     */
    long registra(long idServizio, LocalDateTime istante, Pietanza pietanza, int quantita) {
        long id = prossimoId++;
        ordini.put(id, new Ordine(id, idServizio, istante, pietanza, quantita));
        return id;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce l'ordine con quell'id, o null se non esiste; lo stato è invariato.}
     */
    Ordine trova(long id) {
        return ordini.get(id);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce l'ordine con quell'id.<br>
     * TODO: metodo non completo: contratto e comportamento per l'ordine inesistente da definire.}
     */
    Ordine richiedi(long id) {
        Ordine ordine = ordini.get(id);
        if (ordine == null) {
            System.out.println("TODO: gestire casi anomali");
            return null;
        }
        return ordine;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una copia di tutti gli ordini, in ordine di registrazione; lo stato è invariato.}
     */
    List<Ordine> elencaTutti() {
        return List.copyOf(ordini.values());
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce gli ordini del servizio, in ordine di registrazione; lo stato è invariato.}
     */
    List<Ordine> elencaDelServizio(long idServizio) {
        List<Ordine> risultato = new ArrayList<>();
        for (Ordine ordine : ordini.values()) {
            if (ordine.getIdServizio() == idServizio) {
                risultato.add(ordine);
            }
        }
        return risultato;
    }

    /**
     * Paga tutti gli ordini del servizio, o nessuno.
     *
     * <p>PRE: modalita e pagatoAlle non null. POST: se il servizio ha almeno un ordine
     * e pagatoAlle non precede alcun ordine, tutti gli ordini del servizio sono
     * PAGATI con modalità e istante indicati e il risultato è true.</p>
     *
     * <p>TODO: metodo non completo: contratto e comportamento per servizio senza
     * ordini o pagamento anticipato da definire.</p>
     */
    boolean pagaDelServizio(long idServizio, ModalitaPagamento modalita, LocalDateTime pagatoAlle) {
        List<Ordine> daPagare = elencaDelServizio(idServizio);
        if (daPagare.isEmpty()) {
            System.out.println("TODO: gestire casi anomali");
            return false;
        }
        for (Ordine ordine : daPagare) {
            if (pagatoAlle.isBefore(ordine.getCreatoAlle())) {
                System.out.println("TODO: gestire casi anomali");
                return false;
            }
        }
        for (Ordine ordine : daPagare) {
            ordine.paga(modalita, pagatoAlle);
        }
        return true;
    }
}
