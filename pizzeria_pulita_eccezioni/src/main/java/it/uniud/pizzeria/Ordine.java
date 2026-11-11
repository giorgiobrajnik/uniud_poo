package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

/**
 * Richiesta di pietanze fatta durante un servizio.
 *
 * <p>Stato astratto: un identificativo, l'identificativo del servizio a cui appartiene,
 * l'istante di creazione, una sequenza non vuota di righe (al più una per pietanza),
 * uno stato (IN_PREPARAZIONE, poi SERVITO, poi PAGATO) e, se pagato, modalità e
 * istante di pagamento. Le righe si possono modificare solo finché l'ordine non è
 * pagato.</p>
 */
@Getter
class Ordine {
    private final long id;
    private final long idServizio;
    private final LocalDateTime creatoAlle;
    private final List<RigaOrdine> righe = new ArrayList<>();
    private StatoOrdine stato = StatoOrdine.IN_PREPARAZIONE;
    private ModalitaPagamento modalitaPagamento;
    private LocalDateTime pagatoAlle;

    /**
     * {@summary PRE: creatoAlle non null; pietanza non null; quantita &gt; 0.<br>
     * POST: crea un ordine IN_PREPARAZIONE con id, idServizio e istante indicati, contenente una sola riga con la pietanza e la quantità indicate.}
     */
    Ordine(long id, long idServizio, LocalDateTime creatoAlle, Pietanza pietanza, int quantita) {
        this.id = id;
        this.idServizio = idServizio;
        this.creatoAlle = Objects.requireNonNull(creatoAlle, "creatoAlle");
        aggiungi(pietanza, quantita);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una copia non modificabile delle righe, nell'ordine di inserimento; lo stato è invariato.}
     */
    List<RigaOrdine> getRighe() {
        return List.copyOf(righe);
    }

    /**
     * {@summary PRE: l'ordine non è PAGATO pietanza non null; quantita &gt; 0.<br>
     * POST: se esiste già una riga per la pietanza, la sua quantità aumenta di quantita; altrimenti viene aggiunta una nuova riga. Lo stato dell'ordine non cambia.<br>
     * TODO: metodo non completo: contratto e comportamento per ordine pagato o quantita non positiva da definire.}
     */
    void aggiungi(Pietanza pietanza, int quantita) {
        if (stato == StatoOrdine.PAGATO) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        if (quantita <= 0) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        for (int indice = 0; indice < righe.size(); indice++) {
            RigaOrdine riga = righe.get(indice);
            if (riga.getCodicePietanza().equals(pietanza.getCodice())) {
                righe.set(indice, riga.creaConQuantitaAggiuntiva(quantita));
                return;
            }
        }
        righe.add(new RigaOrdine(pietanza, quantita));
    }

    /**
     * {@summary PRE: l'ordine è IN_PREPARAZIONE.<br>
     * POST: l'ordine è SERVITO.<br>
     * TODO: metodo non completo: contratto e comportamento per ordine non in preparazione da definire.}
     */
    void segnaServito() {
        if (stato != StatoOrdine.IN_PREPARAZIONE) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        stato = StatoOrdine.SERVITO;
    }

    /**
     * {@summary PRE: l'ordine non è PAGATO; modalita e istante non null.<br>
     * POST: l'ordine è PAGATO con la modalità e l'istante indicati.<br>
     * TODO: metodo non completo: contratto e comportamento per ordine già pagato da definire.}
     */
    void paga(ModalitaPagamento modalita, LocalDateTime istante) {
        if (stato == StatoOrdine.PAGATO) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        modalitaPagamento = modalita;
        pagatoAlle = istante;
        stato = StatoOrdine.PAGATO;
    }

    /**
     * {@summary PRE: istante non null.<br>
     * POST: restituisce true se e solo se l'ordine era stato creato non dopo istante e a quell'istante non era ancora stato pagato (o lo è stato dopo); lo stato è invariato.}
     */
    boolean eraDaPagareA(LocalDateTime istante) {
        boolean eraGiaCreato = !creatoAlle.isAfter(istante);
        boolean nonEraAncoraPagato = pagatoAlle == null || pagatoAlle.isAfter(istante);
        return eraGiaCreato && nonEraAncoraPagato;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la somma dei totali delle righe; lo stato è invariato.}
     */
    BigDecimal calcolaTotale() {
        BigDecimal totale = BigDecimal.ZERO;
        for (RigaOrdine riga : righe) {
            totale = totale.add(riga.calcolaTotale());
        }
        return totale;
    }
}
