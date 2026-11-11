package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Contenitore delle viste di sola lettura restituite da {@link Pizzeria}.
 *
 * <p>Stato astratto: nessuno. Ogni vista nidificata è un record immutabile che
 * fotografa lo stato della pizzeria in un certo momento: {@code OrdineAttivo}
 * (un ordine non pagato con tavoli, cameriere, righe e importo parziale),
 * {@code DettaglioOrdine} (righe e totale di un ordine), {@code Conto} (gli ordini
 * da pagare di un tavolo a un istante, con totale), {@code ResocontoCameriere}
 * (tavoli serviti, coperti e incassi di un cameriere) e {@code ResocontoSerale}
 * (i resoconti di tutti i camerieri in un giorno, con incasso totale).</p>
 */
public class Viste {
    private Viste() {
    }

    public record OrdineAttivo(
            long idOrdine,
            long idServizio,
            List<Integer> tavoli,
            String cameriere,
            List<RigaOrdine> righe,
            StatoOrdine stato,
            LocalDateTime orario,
            BigDecimal importoParziale) {
        public OrdineAttivo {
            tavoli = List.copyOf(tavoli);
            righe = List.copyOf(righe);
        }
    }

    public record DettaglioOrdine(
            long idOrdine,
            long idServizio,
            List<RigaOrdine> righe,
            BigDecimal totale) {
        public DettaglioOrdine {
            righe = List.copyOf(righe);
        }
    }

    public record Conto(
            int numeroTavolo,
            LocalDateTime calcolatoAlle,
            List<DettaglioOrdine> ordini,
            BigDecimal totale) {
        public Conto {
            ordini = List.copyOf(ordini);
        }
    }

    public record ResocontoCameriere(
            String codiceCameriere,
            String nomeCameriere,
            int tavoliServiti,
            int coperti,
            BigDecimal incassoLordo,
            Map<CategoriaMenu, BigDecimal> incassiPerCategoria,
            Map<ModalitaPagamento, BigDecimal> incassiPerPagamento) {
        public ResocontoCameriere {
            incassiPerCategoria = Map.copyOf(incassiPerCategoria);
            incassiPerPagamento = Map.copyOf(incassiPerPagamento);
        }
    }

    public record ResocontoSerale(
            LocalDate giorno,
            List<ResocontoCameriere> camerieri,
            BigDecimal incassoTotale) {
        public ResocontoSerale {
            camerieri = List.copyOf(camerieri);
        }
    }
}
