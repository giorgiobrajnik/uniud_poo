package it.uniud.pizzeria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Facciata applicativa; consente di usare il dominio senza occuparsi di UI o persistenza.
 * Coordina i collaboratori {@link Sala}, {@link Menu}, {@link RegistroServizi},
 * {@link RegistroOrdini}, {@link GeneratoreOrdiniAttivi}, {@link CalcolatoreConto} e
 * {@link GeneratoreResoconti}, ciascuno con una sola responsabilità.
 *
 * <p>Stato astratto: una pizzeria è descritta da:</p>
 * <ul>
 *   <li>una configurazione fissa: l'elenco dei tavoli, quello dei camerieri (ognuno
 *       responsabile di un insieme di tavoli) e il menu, cioè un insieme di pietanze
 *       identificate da un codice univoco;</li>
 *   <li>un insieme di servizi, ciascuno con un identificativo intero positivo
 *       univoco: associa uno o più tavoli a un cameriere, con un numero di coperti
 *       e un istante di apertura. Un servizio è attivo finché non viene pagato; poi
 *       ha modalità e istante di pagamento;</li>
 *   <li>un insieme di ordini, ciascuno con un identificativo intero positivo
 *       univoco e legato a un solo servizio. Un ordine contiene righe (pietanza,
 *       prezzo, quantità positiva, al più una per pietanza) e si trova in uno stato
 *       tra IN_PREPARAZIONE, SERVITO e PAGATO.</li>
 * </ul>
 *
 * <p>Invarianti: un tavolo ha al più un servizio attivo; gli ordini di un servizio
 * pagato sono tutti pagati; un ordine non precede l'apertura del suo servizio; gli
 * identificativi già assegnati non vengono riutilizzati. Configurazione e menu non
 * cambiano dopo la costruzione; le operazioni pubbliche modificano solo servizi e
 * ordini.</p>
 */
public class Pizzeria {
    private final Sala sala;
    private final Menu menu;
    private final RegistroServizi servizi = new RegistroServizi();
    private final RegistroOrdini ordini = new RegistroOrdini();
    private final GeneratoreOrdiniAttivi generatoreOrdiniAttivi = new GeneratoreOrdiniAttivi();
    private final CalcolatoreConto calcolatoreConto = new CalcolatoreConto();
    private final GeneratoreResoconti generatoreResoconti = new GeneratoreResoconti();

    /**
     * {@summary PRE: tavoli, camerieri e menu non null; i codici delle pietanze sono univoci.<br>
     * POST: crea una pizzeria senza servizi né ordini, con copie della configurazione ricevuta.<br>
     * TODO: metodo non completo: contratto e comportamento per i codici duplicati da definire.}
     */
    Pizzeria(List<Tavolo> tavoli, List<Cameriere> camerieri, List<Pietanza> menu) {
        this.sala = new Sala(tavoli, camerieri);
        this.menu = new Menu(menu);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una nuova pizzeria Ai Rizzi, senza servizi né ordini.}
     */
    public static Pizzeria aiRizzi() {
        return new ConfigurazioneAiRizzi().crea();
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la lista non modificabile dei tavoli; lo stato è invariato.}
     */
    public List<Tavolo> getTavoli() {
        return sala.getTavoli();
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la lista non modificabile dei camerieri; lo stato è invariato.}
     */
    public List<Cameriere> getCamerieri() {
        return sala.getCamerieri();
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una copia non modificabile delle pietanze del menu; lo stato è invariato.}
     */
    public List<Pietanza> getMenu() {
        return menu.getPietanze();
    }

    /**
     * {@summary PRE: nessuna (codice può essere null).<br>
     * POST: lo stato della pizzeria è invariato.<br>
     * TODO: completare il contratto e il comportamento per il caso in cui la pietanza non esiste.}
     */
    public void cercaPietanza(String codice) {
        if (!menu.contiene(codice)) {
            System.out.println("TODO: gestire casi anomali");
        }
    }

    /**
     * Apre un servizio associando uno o più tavoli liberi allo stesso cameriere.
     *
     * <p>PRE: numeriTavoli non è vuota, non contiene duplicati, tutti i tavoli
     * esistono e appartengono alla stessa zona; 1 &lt;= coperti &lt;= capienza totale;
     * l'apertura non precede la chiusura dell'ultimo servizio sugli stessi tavoli.
     * POST: esiste un nuovo servizio attivo con i dati indicati; gli altri servizi
     * sono invariati. Il risultato è l'identificativo del nuovo servizio: un
     * intero positivo, univoco tra tutti i servizi della pizzeria, da usare in
     * {@link #registraOrdine} e {@link #pagaServizio}.</p>
     *
     * <p>TODO: metodo non completo: contratto e comportamento per tavoli occupati
     * o precondizioni violate da definire.</p>
     */
    public long apriServizio(
            List<Integer> numeriTavoli, int coperti, LocalDateTime apertoAlle) {
        Objects.requireNonNull(numeriTavoli, "numeriTavoli");
        Objects.requireNonNull(apertoAlle, "apertoAlle");
        if (coperti <= 0) {
            System.out.println("TODO: gestire casi anomali");
            return 0;
        }
        List<Tavolo> tavoliRichiesti = sala.validaConfigurazione(numeriTavoli, coperti);
        if (tavoliRichiesti.isEmpty()) {
            return 0;
        }
        Cameriere cameriere = sala.trovaCameriere(tavoliRichiesti.getFirst().getNumero());
        return servizi.apri(tavoliRichiesti, cameriere, coperti, apertoAlle);
    }

    /**
     * Registra una nuova fase d'ordine e restituisce il suo identificativo.
     *
     * <p>PRE: quantità positiva, servizio e pietanza esistenti, istante non
     * precedente all'apertura. POST: aggiunge esattamente un ordine al servizio.</p>
     *
     * <p>TODO: metodo non completo: contratto e comportamento per pietanza non
     * disponibile o precondizioni violate da definire.</p>
     */
    public long registraOrdine(
            long idServizio,
            LocalDateTime istante,
            String codicePietanza,
            int quantita) {
        if (quantita <= 0) {
            System.out.println("TODO: gestire casi anomali");
            return 0;
        }
        Objects.requireNonNull(istante, "istante");
        ServizioTavolo servizio = servizi.richiediAttivo(idServizio);
        if (servizio == null) {
            return 0;
        }
        if (istante.isBefore(servizio.getApertoAlle())) {
            System.out.println("TODO: gestire casi anomali");
            return 0;
        }
        Pietanza pietanza = menu.richiedi(codicePietanza);
        if (pietanza == null) {
            return 0;
        }
        if (!pietanza.isDisponibile(istante)) {
            System.out.println("TODO: gestire casi anomali");
            return 0;
        }
        return ordini.registra(idServizio, istante, pietanza, quantita);
    }

    /**
     * {@summary PRE: quantità positiva.<br>
     * POST: se l'ordine esiste, non è pagato e la pietanza esiste ed è disponibile, la riga è inserita o incrementata.<br>
     * TODO: definire il contratto per i casi anomali (ordine inesistente o pagato, pietanza inesistente o non disponibile).}
     */
    public void provaAdAggiungerePietanza(
            long idOrdine, String codicePietanza, int quantita) {
        if (quantita <= 0) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        Ordine ordine = ordini.trova(idOrdine);
        if (ordine == null || ordine.getStato() == StatoOrdine.PAGATO) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        if (!menu.contiene(codicePietanza)) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        Pietanza pietanza = menu.richiedi(codicePietanza);
        if (!pietanza.isDisponibile(ordine.getCreatoAlle())) {
            System.out.println("TODO: gestire casi anomali");
            return;
        }
        ordine.aggiungi(pietanza, quantita);
    }

    /**
     * {@summary PRE: l'ordine idOrdine esiste ed è IN_PREPARAZIONE.<br>
     * POST: l'ordine è SERVITO; gli altri ordini e i servizi sono invariati.<br>
     * TODO: metodo non completo: contratto e comportamento per ordine inesistente o non in preparazione da definire.}
     */
    public void segnaOrdineServito(long idOrdine) {
        Ordine ordine = ordini.richiedi(idOrdine);
        if (ordine != null) {
            ordine.segnaServito();
        }
    }

    /**
     * Paga atomicamente tutti gli ordini di un servizio.
     *
     * <p>PRE: il servizio esiste, è attivo, contiene almeno un ordine e l'istante
     * non precede alcun ordine. POST: servizio e ordini risultano pagati con la
     * modalità e l'istante indicati.</p>
     *
     * <p>TODO: metodo non completo: contratto e comportamento per servizio
     * inesistente o già pagato, senza ordini o con pagamento anticipato da definire.</p>
     */
    public void pagaServizio(
            long idServizio, ModalitaPagamento modalita, LocalDateTime pagatoAlle) {
        Objects.requireNonNull(modalita, "modalita");
        Objects.requireNonNull(pagatoAlle, "pagatoAlle");
        ServizioTavolo servizio = servizi.richiediAttivo(idServizio);
        if (servizio == null) {
            return;
        }
        if (ordini.pagaDelServizio(idServizio, modalita, pagatoAlle)) {
            servizio.paga(modalita, pagatoAlle);
        }
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una fotografia non modificabile degli ordini non pagati, in ordine di creazione (a parità, per id); lo stato è invariato.}
     */
    public List<Viste.OrdineAttivo> elencaOrdiniAttivi() {
        return generatoreOrdiniAttivi.genera(ordini.elencaTutti(), servizi);
    }

    /**
     * Calcola il conto storico del tavolo all'istante richiesto.
     *
     * <p>PRE: il tavolo esiste. POST: comprende esattamente gli ordini già creati
     * e non ancora pagati a quell'istante; non modifica alcuno stato.</p>
     *
     * <p>TODO: metodo non completo: contratto e comportamento per il tavolo
     * inesistente da definire.</p>
     */
    public Viste.Conto calcolaConto(int numeroTavolo, LocalDateTime istante) {
        if (sala.richiediTavolo(numeroTavolo) == null) {
            return null;
        }
        return calcolatoreConto.calcola(numeroTavolo, istante, servizi.elencaTutti(), ordini.elencaTutti());
    }

    /**
     * {@summary PRE: giorno non null.<br>
     * POST: restituisce il resoconto dei servizi pagati in quel giorno, con una voce per ogni cameriere; lo stato è invariato.}
     */
    public Viste.ResocontoSerale generaResoconto(LocalDate giorno) {
        Objects.requireNonNull(giorno, "giorno");
        return generatoreResoconti.genera(
                giorno, sala.getCamerieri(), servizi.elencaTutti(), ordini.elencaTutti());
    }
}
