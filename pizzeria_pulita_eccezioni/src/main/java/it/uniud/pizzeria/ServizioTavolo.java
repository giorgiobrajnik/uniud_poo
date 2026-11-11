package it.uniud.pizzeria;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * Occupazione di uno o più tavoli da parte di un gruppo di clienti, dall'apertura al pagamento.
 *
 * <p>Stato astratto: un identificativo, un elenco non vuoto di tavoli, il cameriere
 * responsabile, il numero di coperti e l'istante di apertura (fissi dalla creazione),
 * più un eventuale pagamento (modalità e istante). Il servizio è attivo finché non
 * è stato pagato; una volta pagato non torna attivo.</p>
 *
 * <p>Differenza da {@link Ordine}: il servizio è la "sessione" del gruppo ai tavoli e
 * decide quando un tavolo è occupato; l'ordine è una richiesta di pietanze fatta
 * durante quella sessione. Un servizio ha quindi molti ordini (uno per ogni fase di
 * ordinazione) e non conosce le pietanze, mentre ogni ordine appartiene a un solo
 * servizio, tramite {@code idServizio}, e contiene le righe con pietanze e quantità.
 * Anche il ciclo di vita è diverso: un servizio è attivo o pagato, un ordine è
 * in preparazione, servito o pagato. Il pagamento del servizio paga tutti i suoi ordini.</p>
 */
@Getter
class ServizioTavolo {
    private final long id;
    private final List<Tavolo> tavoli;
    private final Cameriere cameriere;
    private final int coperti;
    private final LocalDateTime apertoAlle;
    private ModalitaPagamento modalitaPagamento;
    private LocalDateTime pagatoAlle;

    /**
     * {@summary PRE: tavoli non null; cameriere e apertoAlle non null.<br>
     * POST: crea un servizio attivo con i dati indicati; i tavoli sono una copia della lista ricevuta.}
     */
    ServizioTavolo(
            long id,
            List<Tavolo> tavoli,
            Cameriere cameriere,
            int coperti,
            LocalDateTime apertoAlle) {
        this.id = id;
        this.tavoli = List.copyOf(tavoli);
        this.cameriere = Objects.requireNonNull(cameriere, "cameriere");
        this.coperti = coperti;
        this.apertoAlle = Objects.requireNonNull(apertoAlle, "apertoAlle");
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce true se e solo se tra i tavoli del servizio c'è quello con numero numeroTavolo; lo stato è invariato.}
     */
    boolean includeTavolo(int numeroTavolo) {
        for (Tavolo tavolo : tavoli) {
            if (tavolo.getNumero() == numeroTavolo) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce true se e solo se il servizio non è ancora stato pagato; lo stato è invariato.}
     */
    boolean isAttivo() {
        return pagatoAlle == null;
    }

    /**
     * {@summary PRE: modalita e istante non null.<br>
     * POST: il servizio è pagato con la modalità e l'istante indicati (non più attivo).<br>
     * TODO: metodo non completo: contratto e comportamento per modalita o istante null da definire.}
     */
    void paga(ModalitaPagamento modalita, LocalDateTime istante) {
        modalitaPagamento = modalita;
        pagatoAlle = istante;
    }
}
