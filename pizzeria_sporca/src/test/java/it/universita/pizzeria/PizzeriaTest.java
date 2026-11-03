package it.universita.pizzeria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PizzeriaTest {
    private piz x;

    @BeforeEach
    void prepara() {
        x = new piz();
    }

    @Test
    void configuraVenticinqueTavoliSeiCamerieriEMenuCompleto() {
        assertEquals(25, x.t.size());
        int interni = 0;
        int esterni = 0;
        for (piz.T tavolo : x.t) {
            if (tavolo.i) {
                interni++;
                assertTrue(tavolo.p >= 2 && tavolo.p <= 8);
            } else {
                esterni++;
                assertTrue(tavolo.p >= 2 && tavolo.p <= 6);
            }
        }
        assertEquals(15, interni);
        assertEquals(10, esterni);

        assertEquals(6, x.c.size());
        Set<Integer> assegnati = new HashSet<>();
        for (piz.C c : x.c) {
            assertFalse(c.z.isEmpty());
            for (Integer tavolo : c.z) {
                assertTrue(assegnati.add(tavolo));
            }
        }
        assertEquals(25, assegnati.size());

        Set<String> categorie = new HashSet<>();
        for (piz.P p : x.m) {
            categorie.add(p.k);
        }
        assertEquals(Set.of("PIZZE", "ANTIPASTI", "PRIMI", "SECONDI_CARNE",
                "SECONDI_PESCE", "CONTORNI", "DOLCI", "BEVANDE"), categorie);
    }

    @Test
    void rispettaLaDisponibilitaSettimanaleDellePietanze() {
        piz.P specialita = x.piatto("P002");
        assertEquals("FRIDAY SATURDAY SUNDAY", specialita.g);

        long venerdi = x.apri(List.of(1), 2);
        x.ordina(venerdi, LocalDateTime.of(2026, 9, 25, 19, 0), "P002", 1);

        long lunedi = x.apri(List.of(2), 2);
        boolean errore = false;
        try {
            x.ordina(lunedi, LocalDateTime.of(2026, 9, 28, 19, 0), "P002", 1);
        } catch (IllegalStateException e) {
            errore = true;
        }
        assertTrue(errore);
    }

    @Test
    void gestisceOrdiniInPiuFasiEContoAUnIstante() {
        LocalDateTime inizio = LocalDateTime.of(2026, 9, 25, 18, 30);
        long servizio = x.apri(List.of(1), 3);

        piz.O prima = x.ordina(servizio, inizio, "B001", 2);
        x.aggiungi(prima.n, "A001", 1);
        x.stato(prima.n, "SERVITO");
        piz.O seconda = x.ordina(servizio, inizio.plusMinutes(20), "P001", 3);
        x.aggiungi(seconda.n, "D001", 1);

        assertEquals(13.0, x.conto(servizio, inizio.plusMinutes(10)), 0.001);
        assertEquals(44.5, x.conto(servizio, inizio.plusMinutes(30)), 0.001);
        assertEquals(2, x.attivi().size());

        List<PizzeriaDto.OrdineAttivo> attivi = x.visualizzaOrdiniAttivi();
        assertEquals(List.of(1), attivi.get(0).tavoli());
        assertEquals("Mario Rossi", attivi.get(0).nomeCameriere());
        assertEquals("Acqua", attivi.get(0).pietanze().get(0).nomePietanza());
        assertEquals(2, attivi.get(0).pietanze().get(0).quantita());
        assertEquals("SERVITO", attivi.get(0).stato());
        assertEquals(13.0, attivi.get(0).importoParziale(), 0.001);

        PizzeriaDto.Conto conto = x.contoDettagliato(1, inizio.plusMinutes(30));
        assertEquals(2, conto.ordini().size());
        assertEquals(44.5, conto.totale(), 0.001);

        LocalDateTime pagamento = inizio.plusMinutes(40);
        x.paga(servizio, "CARTA", pagamento);
        assertEquals(44.5, x.conto(servizio, pagamento.minusSeconds(1)), 0.001);
        assertEquals(0.0, x.conto(servizio, pagamento), 0.001);
        assertTrue(x.attivi().isEmpty());
    }

    @Test
    void distingueGruppiSuccessiviSulloStessoTavolo() {
        LocalDateTime t0 = LocalDateTime.of(2026, 9, 25, 18, 0);
        long primoGruppo = x.apri(List.of(3), 4);
        x.ordina(primoGruppo, t0, "P001", 1);
        x.paga(primoGruppo, "CONTANTI", t0.plusHours(1));

        long secondoGruppo = x.apri(List.of(3), 2);
        x.ordina(secondoGruppo, t0.plusHours(1).plusMinutes(15), "PR01", 1);

        assertEquals(8.5, x.conto(3, t0.plusMinutes(30)), 0.001);
        assertEquals(0.0, x.conto(3, t0.plusHours(1).plusMinutes(5)), 0.001);
        assertEquals(12.0, x.conto(3, t0.plusHours(1).plusMinutes(20)), 0.001);
        assertEquals(0.0, x.conto(primoGruppo, t0.plusHours(2)), 0.001);
        assertEquals(12.0, x.conto(secondoGruppo, t0.plusHours(2)), 0.001);
    }

    @Test
    void gestisceTavoliUnitiDellaStessaZona() {
        LocalDateTime ora = LocalDateTime.of(2026, 9, 25, 20, 0);
        long servizio = x.apri(List.of(1, 2), 7);
        piz.O ordine = x.ordina(servizio, ora, "P001", 2);

        assertEquals(List.of(1, 2), ordine.t);
        assertEquals("C1", ordine.c);
        assertEquals(17.0, x.conto(1, ora.plusMinutes(1)), 0.001);
        assertEquals(17.0, x.conto(2, ora.plusMinutes(1)), 0.001);
        boolean errore = false;
        try {
            x.apri(List.of(5, 6), 6);
        } catch (IllegalArgumentException e) {
            errore = true;
        }
        assertTrue(errore);
    }

    @Test
    void generaIlResocontoSeraleCompletoPerCameriere() {
        LocalDate giorno = LocalDate.of(2026, 9, 25);
        LocalDateTime ora = giorno.atTime(18, 0);

        long gruppoGrande = x.apri(List.of(1, 2), 7);
        piz.O primo = x.ordina(gruppoGrande, ora, "P001", 2);
        x.aggiungi(primo.n, "B002", 2);
        x.ordina(gruppoGrande, ora.plusMinutes(30), "D001", 2);
        x.paga(gruppoGrande, "CONTANTI", ora.plusHours(2));

        long altroCameriere = x.apri(List.of(6), 4);
        piz.O secondo = x.ordina(altroCameriere, ora.plusMinutes(10), "A001", 1);
        x.aggiungi(secondo.n, "PR01", 2);
        x.paga(altroCameriere, "CARTA", ora.plusHours(2).plusMinutes(10));

        long secondoTurno = x.apri(List.of(1), 2);
        x.ordina(secondoTurno, ora.plusHours(3), "P001", 1);
        x.paga(secondoTurno, "BUONI_PASTO", ora.plusHours(4));

        long domani = x.apri(List.of(2), 2);
        x.ordina(domani, ora.plusHours(4), "P001", 10);
        x.paga(domani, "CARTA", ora.plusDays(1));

        piz.X c1 = x.resoconto(giorno).get(0);
        assertEquals(3, c1.tavoli);
        assertEquals(9, c1.coperti);
        assertEquals(46.5, c1.lordo, 0.001);
        assertEquals(25.5, c1.pizze, 0.001);
        assertEquals(9.0, c1.bevande, 0.001);
        assertEquals(12.0, c1.dolci, 0.001);
        assertEquals(38.0, c1.contanti, 0.001);
        assertEquals(8.5, c1.buoni, 0.001);

        piz.X c2 = x.resoconto(giorno).get(1);
        assertEquals(1, c2.tavoli);
        assertEquals(4, c2.coperti);
        assertEquals(31.0, c2.lordo, 0.001);
        assertEquals(7.0, c2.antipasti, 0.001);
        assertEquals(24.0, c2.primi, 0.001);
        assertEquals(31.0, c2.carta, 0.001);

        assertEquals(6, x.resoconto(giorno).size());
        assertEquals(0.0, x.resoconto(giorno).get(5).lordo, 0.001);
        PizzeriaDto.ResocontoSerale dto = x.stampaResoconto(giorno);
        assertEquals(giorno, dto.giorno());
        assertEquals(3, dto.camerieri().get(0).tavoliServiti());
        assertEquals(9, dto.camerieri().get(0).coperti());
        assertEquals(46.5, dto.camerieri().get(0).incassoLordo(), 0.001);
        assertEquals(77.5, dto.incassoTotale(), 0.001);
    }
}
