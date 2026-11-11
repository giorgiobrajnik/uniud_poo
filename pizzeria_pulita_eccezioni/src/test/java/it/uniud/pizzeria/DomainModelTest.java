package it.uniud.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DomainModelTest {
    private static final LocalDateTime VENERDI = LocalDateTime.of(2026, 9, 25, 18, 0);

    // SUT: Tavolo, Cameriere e AreaTavolo.
    // Condizione di test: i costruttori accettano coperti ai limiti dell'area; capienza, nome completo e tavoli serviti sono corretti.
    @Test
    void validaTavoliECamerieri() {
        assertEquals(8, AreaTavolo.INTERNA.getCapienzaMassima());
        assertEquals(6, AreaTavolo.ESTERNA.getCapienzaMassima());
        assertEquals(2, new Tavolo(1, 2, AreaTavolo.INTERNA).getCoperti());
        assertEquals(8, new Tavolo(1, 8, AreaTavolo.INTERNA).getCoperti());
        assertEquals(6, new Tavolo(16, 6, AreaTavolo.ESTERNA).getCoperti());

        Cameriere cameriere = new Cameriere("C", "Ada", "Lovelace", Set.of(1));
        assertEquals("Ada Lovelace", cameriere.getNomeCompleto());
        assertTrue(cameriere.serve(1));
        assertFalse(cameriere.serve(2));
    }

    // SUT: Pietanza e RigaOrdine.
    // Condizione di test: una pietanza è disponibile solo nei suoi giorni, è uguale a un'altra con lo stesso codice; una riga calcola il totale e aumenta la quantità.
    @Test
    void validaPietanzaERigaOrdine() {
        Pietanza pizza = pizzaFineSettimana();
        assertTrue(pizza.isDisponibile(VENERDI));
        assertFalse(pizza.isDisponibile(VENERDI.plusDays(3)));
        assertEquals(pizza, new Pietanza(
                "P", "Altro nome", "Altra descrizione", new BigDecimal("9"),
                CategoriaMenu.PIZZE, Set.of(DayOfWeek.FRIDAY)));
        assertNotEquals(pizza, new Pietanza(
                "X", "Pizza", "Descrizione", new BigDecimal("9"),
                CategoriaMenu.PIZZE, Set.of(DayOfWeek.FRIDAY)));

        RigaOrdine riga = new RigaOrdine(pizza, 2);
        assertEquals(new BigDecimal("20.00"), riga.calcolaTotale());
        assertEquals(5, riga.creaConQuantitaAggiuntiva(3).getQuantita());
    }

    // SUT: Ordine.
    // Condizione di test: un ordine può essere servito e poi pagato; totale ed eraDaPagareA sono corretti agli istanti prima, durante e dopo il pagamento.
    @Test
    void ordineMantieneIlProprioCicloDiVita() {
        Pietanza pizza = pizzaFineSettimana();
        Ordine ordine = new Ordine(1, 2, VENERDI, pizza, 1);
        assertEquals(new BigDecimal("10.00"), ordine.calcolaTotale());
        assertTrue(ordine.eraDaPagareA(VENERDI));
        assertFalse(ordine.eraDaPagareA(VENERDI.minusNanos(1)));
        ordine.segnaServito();
        ordine.paga(ModalitaPagamento.CARTA, VENERDI.plusHours(1));
        assertTrue(ordine.eraDaPagareA(VENERDI.plusMinutes(30)));
        assertFalse(ordine.eraDaPagareA(VENERDI.plusHours(1)));
    }

    private Pietanza pizzaFineSettimana() {
        return new Pietanza(
                "P",
                "Pizza",
                "Descrizione",
                new BigDecimal("10.00"),
                CategoriaMenu.PIZZE,
                EnumSet.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
    }
}
