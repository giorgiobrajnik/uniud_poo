package it.universita.pizzeria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class PizzeriaDto {
    private PizzeriaDto() {
    }

    private static String importo(double valore) {
        return String.format(Locale.ROOT, "%.2f", valore);
    }

    public record Riga(
            String codicePietanza,
            String nomePietanza,
            int quantita,
            double prezzoUnitario,
            double totale) {

        @Override
        public String toString() {
            return quantita + " x " + nomePietanza + " @ " + importo(prezzoUnitario)
                    + " = " + importo(totale);
        }
    }

    public record OrdineAttivo(
            long numeroOrdine,
            List<Integer> tavoli,
            String codiceCameriere,
            String nomeCameriere,
            List<Riga> pietanze,
            String stato,
            LocalDateTime orario,
            double importoParziale) {

        public OrdineAttivo {
            tavoli = List.copyOf(tavoli);
            pietanze = List.copyOf(pietanze);
        }

        @Override
        public String toString() {
            StringBuilder risultato = new StringBuilder();
            risultato.append("Ordine ").append(numeroOrdine)
                    .append(" - tavolo/i ").append(tavoli).append('\n');
            risultato.append("Cameriere: ").append(nomeCameriere)
                    .append(" (").append(codiceCameriere).append(")\n");
            risultato.append("Orario: ").append(orario)
                    .append(" - stato: ").append(stato).append('\n');
            for (Riga riga : pietanze) {
                risultato.append(riga).append('\n');
            }
            risultato.append("Parziale: ").append(importo(importoParziale));
            return risultato.toString();
        }
    }

    public record OrdineConto(
            long numeroOrdine,
            long numeroServizio,
            List<Riga> pietanze,
            double totale) {

        public OrdineConto {
            pietanze = List.copyOf(pietanze);
        }

        @Override
        public String toString() {
            StringBuilder risultato = new StringBuilder();
            risultato.append("Ordine ").append(numeroOrdine)
                    .append(" servizio ").append(numeroServizio).append('\n');
            for (Riga riga : pietanze) {
                risultato.append(riga).append('\n');
            }
            risultato.append("Totale ordine: ").append(importo(totale));
            return risultato.toString();
        }
    }

    public record Conto(
            int tavolo,
            LocalDateTime calcolatoAlle,
            List<OrdineConto> ordini,
            double totale) {

        public Conto {
            ordini = List.copyOf(ordini);
        }

        @Override
        public String toString() {
            StringBuilder risultato = new StringBuilder();
            risultato.append("Conto tavolo ").append(tavolo)
                    .append(" alle ").append(calcolatoAlle).append('\n');
            for (OrdineConto ordine : ordini) {
                risultato.append(ordine).append('\n');
            }
            risultato.append("Totale: ").append(importo(totale));
            return risultato.toString();
        }
    }

    public record ResocontoCameriere(
            String codiceCameriere,
            String nomeCameriere,
            int tavoliServiti,
            int coperti,
            double incassoLordo,
            Map<String, Double> incassiPerCategoria,
            Map<String, Double> incassiPerPagamento) {

        public ResocontoCameriere {
            incassiPerCategoria = Map.copyOf(incassiPerCategoria);
            incassiPerPagamento = Map.copyOf(incassiPerPagamento);
        }

        @Override
        public String toString() {
            StringBuilder risultato = new StringBuilder();
            risultato.append(codiceCameriere).append(' ').append(nomeCameriere).append('\n');
            risultato.append("Tavoli: ").append(tavoliServiti)
                    .append(" - coperti: ").append(coperti).append('\n');
            risultato.append("Lordo: ").append(importo(incassoLordo)).append('\n');
            risultato.append("Pizze: ").append(importo(incassiPerCategoria.get("PIZZE")))
                    .append(" - antipasti: ").append(importo(incassiPerCategoria.get("ANTIPASTI")))
                    .append(" - primi: ").append(importo(incassiPerCategoria.get("PRIMI")))
                    .append(" - carne: ").append(importo(incassiPerCategoria.get("SECONDI_CARNE")))
                    .append(" - pesce: ").append(importo(incassiPerCategoria.get("SECONDI_PESCE")))
                    .append(" - contorni: ").append(importo(incassiPerCategoria.get("CONTORNI")))
                    .append(" - dolci: ").append(importo(incassiPerCategoria.get("DOLCI")))
                    .append(" - bevande: ").append(importo(incassiPerCategoria.get("BEVANDE"))).append('\n');
            risultato.append("Contanti: ").append(importo(incassiPerPagamento.get("CONTANTI")))
                    .append(" - carta: ").append(importo(incassiPerPagamento.get("CARTA")))
                    .append(" - buoni pasto: ").append(importo(incassiPerPagamento.get("BUONI_PASTO")));
            return risultato.toString();
        }
    }

    public record ResocontoSerale(
            LocalDate giorno,
            List<ResocontoCameriere> camerieri,
            double incassoTotale) {

        public ResocontoSerale {
            camerieri = List.copyOf(camerieri);
        }

        @Override
        public String toString() {
            StringBuilder risultato = new StringBuilder("RESOCONTO ").append(giorno).append('\n');
            for (ResocontoCameriere cameriere : camerieri) {
                risultato.append(cameriere).append("\n\n");
            }
            risultato.append("Incasso totale: ").append(importo(incassoTotale));
            return risultato.toString();
        }
    }
}
