package it.uniud.pizzeria;

import java.io.PrintWriter;

/**
 * Traduce un resoconto in testo.
 *
 * <p>Stato astratto: il numero di esportazioni attualmente in corso, mai negativo;
 * vale 0 quando nessuna esportazione è in esecuzione.</p>
 */
public class EsportatoreResocontoTestuale {
    private int esportazioniInCorso;

    /**
     * Scrive e rende visibile il resoconto, senza chiudere il writer del chiamante.
     *
     * <p>PRE: resoconto e destinazione non null. POST: il testo è stato scritto e
     * sottoposto a flush; il conteggio delle esportazioni in corso torna al valore
     * precedente alla chiamata.</p>
     *
     * <p>TODO: metodo non completo: contratto e comportamento per il fallimento
     * della scrittura da definire.</p>
     */
    public void esporta(Viste.ResocontoSerale resoconto, PrintWriter destinazione) {
        esportazioniInCorso++;
        destinazione.write(formatta(resoconto));
        destinazione.flush();
        if (destinazione.checkError()) {
            System.out.println("TODO: gestire casi anomali");
        }
        esportazioniInCorso--;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce il numero di esportazioni in corso; lo stato è invariato.}
     */
    public int contaEsportazioniInCorso() {
        return esportazioniInCorso;
    }

    /**
     * {@summary PRE: resoconto non null.<br>
     * POST: restituisce il testo del resoconto: una riga con la data, una per ogni cameriere (codice, nome, incasso lordo, coperti) e una con il totale; lo stato è invariato.}
     */
    private String formatta(Viste.ResocontoSerale resoconto) {
        StringBuilder testo = new StringBuilder()
                .append("RESOCONTO ").append(resoconto.giorno()).append('\n');
        for (Viste.ResocontoCameriere riga : resoconto.camerieri()) {
            testo.append(riga.codiceCameriere()).append(" - ")
                    .append(riga.nomeCameriere()).append(": ")
                    .append(riga.incassoLordo()).append(" euro, ")
                    .append(riga.coperti()).append(" coperti\n");
        }
        return testo.append("TOTALE: ").append(resoconto.incassoTotale()).append(" euro\n")
                .toString();
    }
}
