package it.universita.pizzeria;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        piz x = new piz();
        LocalDateTime adesso = LocalDateTime.now();

        long s = x.apri(List.of(1), 3);
        piz.O o1 = x.ordina(s, adesso, "B001", 2);
        x.aggiungi(o1.n, "A001", 1);
        x.stato(o1.n, "SERVITO");
        piz.O o2 = x.ordina(s, adesso.plusMinutes(20), "P001", 3);
        x.aggiungi(o2.n, "D001", 2);

        // 1. Visualizzare gli ordini attivi in tempo reale.
        List<PizzeriaDto.OrdineAttivo> ordiniAttivi = x.visualizzaOrdiniAttivi();
        ordiniAttivi.forEach(System.out::println);

        // 2. Calcolare il conto di un tavolo in un momento specifico.
        PizzeriaDto.Conto conto = x.contoDettagliato(1, adesso.plusMinutes(30));
        System.out.println(conto);

        LocalDateTime pagamento = adesso.plusMinutes(40);
        x.paga(s, "CARTA", pagamento);

        // 3. Generare il resoconto serale con gli incassi ripartiti per cameriere.
        PizzeriaDto.ResocontoSerale resoconto = x.stampaResoconto(pagamento.toLocalDate());
        System.out.println(resoconto);
    }
}
