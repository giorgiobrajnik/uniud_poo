package it.uniud.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EsportatoreResocontoTestualeTest {
    // SUT: EsportatoreResocontoTestuale.
    // Condizione di test: il resoconto di una pizzeria senza servizi viene scritto sul writer con data, cameriere e totale, e il conteggio delle esportazioni in corso torna a 0.
    @Test
    void esportaIlResocontoEFlushLaDestinazione() {
        Pizzeria pizzeria = Pizzeria.aiRizzi();
        Viste.ResocontoSerale resoconto = pizzeria.generaResoconto(LocalDate.of(2026, 9, 25));
        EsportatoreResocontoTestuale esportatore = new EsportatoreResocontoTestuale();
        StringWriter destinazione = new StringWriter();

        esportatore.esporta(resoconto, new PrintWriter(destinazione));

        assertTrue(destinazione.toString().contains("RESOCONTO 2026-09-25"));
        assertTrue(destinazione.toString().contains("C1 - Mario Rossi"));
        assertTrue(destinazione.toString().contains("TOTALE: 0"));
        assertEquals(0, esportatore.contaEsportazioniInCorso());
    }
}
