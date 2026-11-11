package it.uniud.pizzeria;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Programma dimostrativo.
 *
 * <p>Stato astratto: nessuno; classe di sole funzioni statiche.</p>
 */
public class Main {
    // Costruttore privato: la classe ha solo metodi statici e nessuno stato, quindi
    // non deve essere istanziata (senza di esso Java ne creerebbe uno pubblico).
    private Main() {
    }

    /**
     * {@summary PRE: args non viene usato.<br>
     * POST: stampa a video una dimostrazione di utilizzo della pizzeria; nessuno stato persiste dopo la chiamata.}
     */
    public static void main(String[] args) {
        Pizzeria pizzeria = Pizzeria.aiRizzi();
        LocalDateTime venerdi = LocalDateTime.of(2026, 9, 25, 19, 0);

        long servizio = pizzeria.apriServizio(List.of(2, 3), 7, venerdi);
        long primoOrdine = pizzeria.registraOrdine(servizio, venerdi, "B001", 4);
        pizzeria.provaAdAggiungerePietanza(primoOrdine, "A001", 2);
        pizzeria.segnaOrdineServito(primoOrdine);
        pizzeria.registraOrdine(servizio, venerdi.plusMinutes(25), "P001", 7);

        pizzeria.cercaPietanza("P001");
        pizzeria.provaAdAggiungerePietanza(999, "P001", 1);

                
        // OPERAZIONE 1 DEL TEMA: visualizzare gli ordini attivi (non ancora pagati).
        System.out.println("Ordini attivi: " + pizzeria.elencaOrdiniAttivi());
        // OPERAZIONE 2 DEL TEMA: calcolare il conto di un tavolo in un momento specifico.
        System.out.println("Conto: "
                + pizzeria.calcolaConto(2, venerdi.plusHours(1)).totale());

        LocalDateTime pagamento = venerdi.plusHours(2);
        pizzeria.pagaServizio(servizio, ModalitaPagamento.CARTA, pagamento);
        // OPERAZIONE 3 DEL TEMA: generare il resoconto serale (incassi per cameriere,
        // categoria e modalità di pagamento).
        Viste.ResocontoSerale resoconto = pizzeria.generaResoconto(pagamento.toLocalDate());

        EsportatoreResocontoTestuale esportatore = new EsportatoreResocontoTestuale();
        StringWriter destinazione = new StringWriter();
        esportatore.esporta(resoconto, new PrintWriter(destinazione));
        System.out.println(destinazione);
        // caso anomalo: tentativo di registrare un ordine con pietanza non disponibile.
        long servizioLunedi = pizzeria.apriServizio(
                List.of(6), 2, LocalDateTime.of(2026, 9, 28, 19, 0));
        pizzeria.registraOrdine(
                servizioLunedi,
                LocalDateTime.of(2026, 9, 28, 19, 5),
                "P002",
                1);
    }
}
