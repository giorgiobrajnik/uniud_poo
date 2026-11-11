package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Calcola il resoconto di un giorno a partire da camerieri, servizi e ordini.
 *
 * <p>Stato astratto: nessuno. Non conserva nulla tra una chiamata e l'altra e non
 * modifica i dati ricevuti.</p>
 */
class GeneratoreResoconti {
    /**
     * {@summary PRE: giorno, camerieri, servizi e ordini non null; ogni servizio pagato ha un cameriere presente in camerieri.<br>
     * POST: restituisce il resoconto del giorno con una voce per ogni cameriere, nell'ordine di camerieri, che considera solo i servizi pagati in quel giorno; l'incasso totale è la somma degli incassi lordi. Gli argomenti non vengono modificati.}
     */
    Viste.ResocontoSerale genera(
            LocalDate giorno,
            List<Cameriere> camerieri,
            List<ServizioTavolo> servizi,
            List<Ordine> ordini) {
        Map<String, Accumulatore> perCameriere = new LinkedHashMap<>();
        for (Cameriere cameriere : camerieri) {
            perCameriere.put(cameriere.getCodice(), new Accumulatore(cameriere));
        }

        for (ServizioTavolo servizio : servizi) {
            LocalDateTime pagatoAlle = servizio.getPagatoAlle();
            if (pagatoAlle != null && pagatoAlle.toLocalDate().equals(giorno)) {
                accumulaServizio(
                        perCameriere.get(servizio.getCameriere().getCodice()), servizio, ordini);
            }
        }

        List<Viste.ResocontoCameriere> righe = new ArrayList<>();
        BigDecimal totale = BigDecimal.ZERO;
        for (Accumulatore accumulatore : perCameriere.values()) {
            Viste.ResocontoCameriere vista = accumulatore.creaVista();
            righe.add(vista);
            totale = totale.add(vista.incassoLordo());
        }
        return new Viste.ResocontoSerale(giorno, righe, totale);
    }

    /**
     * {@summary PRE: accumulatore è quello del cameriere del servizio; il servizio è pagato.<br>
     * POST: l'accumulatore comprende tavoli, coperti e incassi (per categoria e per modalità di pagamento) del servizio.}
     */
    private void accumulaServizio(
            Accumulatore accumulatore, ServizioTavolo servizio, List<Ordine> ordini) {
        accumulatore.tavoliServiti += servizio.getTavoli().size();
        accumulatore.coperti += servizio.getCoperti();
        for (Ordine ordine : ordini) {
            if (ordine.getIdServizio() == servizio.getId()) {
                for (RigaOrdine riga : ordine.getRighe()) {
                    accumulatore.aggiungi(riga);
                }
            }
        }
        ModalitaPagamento modalita = servizio.getModalitaPagamento();
        accumulatore.perPagamento.put(
                modalita,
                accumulatore.perPagamento.get(modalita).add(accumulatore.totaleUltimoServizio));
        accumulatore.totaleUltimoServizio = BigDecimal.ZERO;
    }

    private static class Accumulatore {
        private final Cameriere cameriere;
        private final EnumMap<CategoriaMenu, BigDecimal> perCategoria =
                new EnumMap<>(CategoriaMenu.class);
        private final EnumMap<ModalitaPagamento, BigDecimal> perPagamento =
                new EnumMap<>(ModalitaPagamento.class);
        private int tavoliServiti;
        private int coperti;
        private BigDecimal totaleUltimoServizio = BigDecimal.ZERO;

        /**
         * {@summary PRE: cameriere non null.<br>
         * POST: crea un accumulatore con tutti gli importi a zero.}
         */
        private Accumulatore(Cameriere cameriere) {
            this.cameriere = cameriere;
            for (CategoriaMenu categoria : CategoriaMenu.values()) {
                perCategoria.put(categoria, BigDecimal.ZERO);
            }
            for (ModalitaPagamento modalita : ModalitaPagamento.values()) {
                perPagamento.put(modalita, BigDecimal.ZERO);
            }
        }

        /**
         * {@summary PRE: riga non null.<br>
         * POST: l'importo della riga è sommato alla sua categoria e al totale del servizio in corso.}
         */
        private void aggiungi(RigaOrdine riga) {
            BigDecimal importo = riga.calcolaTotale();
            perCategoria.put(riga.getCategoria(), perCategoria.get(riga.getCategoria()).add(importo));
            totaleUltimoServizio = totaleUltimoServizio.add(importo);
        }

        /**
         * {@summary PRE: nessuna.<br>
         * POST: restituisce il resoconto del cameriere; l'incasso lordo è la somma degli incassi per categoria.}
         */
        private Viste.ResocontoCameriere creaVista() {
            BigDecimal lordo = BigDecimal.ZERO;
            for (BigDecimal importo : perCategoria.values()) {
                lordo = lordo.add(importo);
            }
            return new Viste.ResocontoCameriere(
                    cameriere.getCodice(),
                    cameriere.getNomeCompleto(),
                    tavoliServiti,
                    coperti,
                    lordo,
                    perCategoria,
                    perPagamento);
        }
    }
}
