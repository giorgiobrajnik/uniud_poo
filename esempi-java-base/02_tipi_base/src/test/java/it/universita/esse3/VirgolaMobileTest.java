package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.virgolamobile.Approssimazione;
import it.universita.esse3.virgolamobile.Denaro;
import it.universita.esse3.virgolamobile.Ieee754;
import it.universita.esse3.virgolamobile.ValoriSpeciali;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class VirgolaMobileTest {
    @Test
    void controesempioSommaApprossimata() {
        assertEquals(0.30000000000000004, Approssimazione.zeroUnoPiuZeroDue());
        assertFalse(Approssimazione.sommaUgualeATreDecimi());
        assertTrue(Approssimazione.sommaVicinaATreDecimi(1e-9));
    }

    @Test
    void campiIeee754DiCinqueEMezzo() {
        assertEquals(0, Ieee754.segno(5.5));
        assertEquals(1025, Ieee754.esponenteMemorizzato(5.5));
        assertEquals(2, Ieee754.esponenteEffettivo(5.5));
        assertEquals(0b011L << 49, Ieee754.mantissa(5.5));
        assertEquals(5.5, Ieee754.ricostruisci(5.5));
    }

    @Test
    void segnoNegativo() {
        assertEquals(1, Ieee754.segno(-5.5));
        assertEquals(-5.5, Ieee754.ricostruisci(-5.5));
    }

    @Test
    void valoriSpeciali() {
        assertTrue(Double.isNaN(ValoriSpeciali.zeroSuZero()));
        assertEquals(Double.POSITIVE_INFINITY, ValoriSpeciali.unoSuZero());
        assertEquals(Double.NEGATIVE_INFINITY, ValoriSpeciali.menoUnoSuZero());
    }

    @Test
    void nanNonEUgualeANeppureASeStesso() {
        double nan = ValoriSpeciali.zeroSuZero();
        assertFalse(nan == nan);
    }

    @Test
    void divisioneInteraPerZeroLanciaEccezione() {
        assertThrows(ArithmeticException.class, () -> ValoriSpeciali.divisioneInteraPerZero(1, 0));
    }

    @Test
    void controesempioDenaroConDouble() {
        assertNotEquals(1.0, Denaro.sommaDieciVolteConDouble());
    }

    @Test
    void denaroEsattoConCentesimiEBigDecimal() {
        assertEquals(100L, Denaro.sommaDieciVolteInCentesimi());
        assertEquals(new BigDecimal("1.0"), Denaro.sommaDieciVolteConBigDecimal());
    }

    @Test
    void controesempioBigDecimalCostruitoDaDouble() {
        assertNotEquals(new BigDecimal("0.1"), Denaro.bigDecimalDaDouble());
    }
}
