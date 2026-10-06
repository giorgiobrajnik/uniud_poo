package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.conversioni.Narrowing;
import it.universita.esse3.conversioni.PromozioniEDivisioni;
import it.universita.esse3.conversioni.Widening;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ConversioniTest {
    @TempDir
    Path output;

    @Test
    void widening() {
        assertEquals(180L, Widening.daIntALong(180));
        assertEquals(65, Widening.daCharAInt('A'));
    }

    @Test
    void controesempioWideningLongDoubleCheFaPerdereInformazione() {
        assertTrue(Widening.longDoubleSenzaPerdita(9_007_199_254_740_992L));
        assertFalse(Widening.longDoubleSenzaPerdita(9_007_199_254_740_993L));
    }

    @Test
    void controesempioCastAByteDiValoreNonRappresentabile() {
        assertEquals(-126, Narrowing.aByte(130));
        assertEquals(100, Narrowing.aByte(100));
    }

    @Test
    void castADoubleTroncaVersoZero() {
        assertEquals(27, Narrowing.troncaVersoZero(27.9));
        assertEquals(-27, Narrowing.troncaVersoZero(-27.9));
    }

    @Test
    void controesempioCastDiIdLongAInt() {
        assertEquals(-794_967_296, Narrowing.aInt(3_500_000_000L));
        assertFalse(Narrowing.idSopravvive(3_500_000_000L));
        assertTrue(Narrowing.idSopravvive(1_000L));
    }

    @Test
    void sommaDiByteEUnInt() {
        assertEquals(30, PromozioniEDivisioni.sommaByte((byte) 10, (byte) 20));
        assertEquals("Integer", PromozioniEDivisioni.tipoDellaSommaDiByte());
    }

    @Test
    void controesempioDivisioneIntera() {
        assertEquals(0.0, PromozioniEDivisioni.rapportoConDivisioneIntera(2, 3));
    }

    @Test
    void divisioneInVirgolaMobile() {
        assertEquals(2.0 / 3, PromozioniEDivisioni.rapportoConCast(2, 3), 1e-12);
        assertEquals(2.0 / 3, PromozioniEDivisioni.rapportoConLetterale(2), 1e-12);
        assertEquals(37.0 / 52, PromozioniEDivisioni.tassoPresenza(37, 52), 1e-12);
    }

    @Test
    void controesempioByteDaSomma() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "ByteDaSomma.java.txt", "it.universita.esse3.client.ByteDaSomma", output);
        assertEquals(List.of("compiler.err.prob.found.req"), errori);
    }

    @Test
    void controesempioNarrowingSenzaCast() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "IntDaDouble.java.txt", "it.universita.esse3.client.IntDaDouble", output);
        assertEquals(List.of("compiler.err.prob.found.req"), errori);
    }
}
