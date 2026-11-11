package it.uniud.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PizzeriaTest {
    private static final LocalDateTime VENERDI = LocalDateTime.of(2026, 9, 25, 18, 0);
    private static final LocalDateTime LUNEDI = LocalDateTime.of(2026, 9, 28, 18, 0);

    private Pizzeria pizzeria;

    @BeforeEach
    void prepara() {
        pizzeria = Pizzeria.aiRizzi();
    }

    // SUT: Pizzeria.aiRizzi.
    // Condizione di test: la pizzeria Ai Rizzi ha 25 tavoli (15 interni e 10 esterni), 6 camerieri che coprono tutti i tavoli e un menu con tutte le categorie.
    @Test
    void configuraCorrettamenteLaPizzeriaAiRizzi() {
        assertEquals(25, pizzeria.getTavoli().size());
        assertEquals(15, pizzeria.getTavoli().stream()
                .filter(tavolo -> tavolo.getArea() == AreaTavolo.INTERNA).count());
        assertEquals(10, pizzeria.getTavoli().stream()
                .filter(tavolo -> tavolo.getArea() == AreaTavolo.ESTERNA).count());
        assertEquals(6, pizzeria.getCamerieri().size());
        assertEquals(25, pizzeria.getCamerieri().stream()
                .flatMap(cameriere -> cameriere.getNumeriTavoli().stream()).distinct().count());
        assertEquals(EnumSet.allOf(CategoriaMenu.class), pizzeria.getMenu().stream()
                .map(Pietanza::getCategoria)
                .collect(java.util.stream.Collectors.toSet()));
    }

    // SUT: Pizzeria.apriServizio, registraOrdine, elencaOrdiniAttivi e calcolaConto.
    // Condizione di test: un servizio su due tavoli della stessa zona, con un ordine, compare tra gli ordini attivi con i tavoli, il cameriere e l'importo giusti, e lo stesso importo risulta nel conto.
    @Test
    void apreUnServizioConTavoliUnitiDellaStessaZona() {
        long servizio = pizzeria.apriServizio(List.of(1, 2), 6, VENERDI);
        long ordine = pizzeria.registraOrdine(servizio, VENERDI, "P001", 2);

        Viste.OrdineAttivo vista = pizzeria.elencaOrdiniAttivi().getFirst();
        assertEquals(ordine, vista.idOrdine());
        assertEquals(List.of(1, 2), vista.tavoli());
        assertEquals("Mario Rossi", vista.cameriere());
        assertEquals(new BigDecimal("17.00"), vista.importoParziale());
        assertEquals(new BigDecimal("17.00"), pizzeria.calcolaConto(2, VENERDI).totale());
    }

    // SUT: Pizzeria.registraOrdine, provaAdAggiungerePietanza e segnaOrdineServito.
    // Condizione di test: due fasi d'ordine nello stesso servizio sono ordinate per orario; aggiungere la stessa pietanza accorpa la quantità, una nuova pietanza aggiunge una riga.
    @Test
    void registraFasiOrdineEAccorpaLaStessaPietanza() {
        long servizio = pizzeria.apriServizio(List.of(1), 2, VENERDI);
        long secondo = pizzeria.registraOrdine(
                servizio, VENERDI.plusMinutes(20), "P001", 1);
        long primo = pizzeria.registraOrdine(servizio, VENERDI, "B001", 1);

        pizzeria.provaAdAggiungerePietanza(primo, "B001", 2);
        pizzeria.provaAdAggiungerePietanza(primo, "A001", 1);
        pizzeria.segnaOrdineServito(primo);

        List<Viste.OrdineAttivo> attivi = pizzeria.elencaOrdiniAttivi();
        assertEquals(List.of(primo, secondo), attivi.stream()
                .map(Viste.OrdineAttivo::idOrdine).toList());
        assertEquals(2, attivi.getFirst().righe().size());
        assertEquals(3, attivi.getFirst().righe().getFirst().getQuantita());
        assertEquals(StatoOrdine.SERVITO, attivi.getFirst().stato());
    }

    // SUT: Pizzeria.calcolaConto.
    // Condizione di test: due servizi successivi sullo stesso tavolo producono conti diversi a seconda dell'istante.
    @Test
    void calcolaIlContoStoricoEDistingueGruppiSuccessivi() {
        long primoServizio = pizzeria.apriServizio(List.of(3), 4, VENERDI);
        pizzeria.registraOrdine(primoServizio, VENERDI, "P001", 1);
        pizzeria.pagaServizio(
                primoServizio, ModalitaPagamento.CONTANTI, VENERDI.plusHours(1));

        long secondoServizio = pizzeria.apriServizio(
                List.of(3), 2, VENERDI.plusHours(1).plusMinutes(10));
        pizzeria.registraOrdine(
                secondoServizio, VENERDI.plusHours(1).plusMinutes(15), "PR01", 1);

        assertEquals(new BigDecimal("8.50"),
                pizzeria.calcolaConto(3, VENERDI.plusMinutes(30)).totale());
        assertEquals(BigDecimal.ZERO,
                pizzeria.calcolaConto(3, VENERDI.plusHours(1)).totale());
        assertEquals(new BigDecimal("12.00"),
                pizzeria.calcolaConto(3, VENERDI.plusHours(2)).totale());
    }

    // SUT: Pizzeria.generaResoconto.
    // Condizione di test: servizi di camerieri diversi, pagati con modalità diverse, producono incassi corretti per categoria, modalità e totale; un servizio pagato il giorno dopo non è incluso.
    @Test
    void generaIlResocontoSeraleCompletoPerOgniCameriere() {
        long servizioC1 = pizzeria.apriServizio(List.of(2, 3), 7, VENERDI);
        long ordineC1 = pizzeria.registraOrdine(servizioC1, VENERDI, "P001", 2);
        pizzeria.provaAdAggiungerePietanza(ordineC1, "B002", 2);
        pizzeria.registraOrdine(servizioC1, VENERDI.plusMinutes(20), "D001", 2);
        pizzeria.pagaServizio(
                servizioC1, ModalitaPagamento.CONTANTI, VENERDI.plusHours(2));

        long servizioC2 = pizzeria.apriServizio(List.of(7), 4, VENERDI);
        long ordineC2 = pizzeria.registraOrdine(servizioC2, VENERDI, "A001", 1);
        pizzeria.provaAdAggiungerePietanza(ordineC2, "PR01", 2);
        pizzeria.pagaServizio(
                servizioC2, ModalitaPagamento.CARTA, VENERDI.plusHours(2));

        long domani = pizzeria.apriServizio(List.of(1), 2, VENERDI.plusHours(3));
        pizzeria.registraOrdine(domani, VENERDI.plusHours(3), "P001", 10);
        pizzeria.pagaServizio(domani, ModalitaPagamento.CARTA, VENERDI.plusDays(1));

        Viste.ResocontoSerale resoconto = pizzeria.generaResoconto(VENERDI.toLocalDate());
        assertEquals(6, resoconto.camerieri().size());
        Viste.ResocontoCameriere c1 = resoconto.camerieri().getFirst();
        assertEquals(2, c1.tavoliServiti());
        assertEquals(7, c1.coperti());
        assertEquals(new BigDecimal("38.00"), c1.incassoLordo());
        assertEquals(new BigDecimal("17.00"), c1.incassiPerCategoria().get(CategoriaMenu.PIZZE));
        assertEquals(new BigDecimal("9.00"), c1.incassiPerCategoria().get(CategoriaMenu.BEVANDE));
        assertEquals(new BigDecimal("38.00"), c1.incassiPerPagamento().get(ModalitaPagamento.CONTANTI));

        Viste.ResocontoCameriere c2 = resoconto.camerieri().get(1);
        assertEquals(new BigDecimal("31.00"), c2.incassoLordo());
        assertEquals(new BigDecimal("31.00"), c2.incassiPerPagamento().get(ModalitaPagamento.CARTA));
        assertEquals(BigDecimal.ZERO, resoconto.camerieri().get(5).incassoLordo());
        assertEquals(new BigDecimal("69.00"), resoconto.incassoTotale());
    }
}
