package it.uniud.tda.polynomial.immutabile;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {
    @Test
    void normalizzaGliZeriFinaliESpecificaIlPolinomioNullo() {
        Polynomial zero = Polynomial.of(0, 0);
        assertTrue(zero.isZero());
        assertEquals(-1, zero.degree());
        assertEquals("0", zero.toString());
    }

    @Test
    void sommaSottrazioneEProdottoNonCambianoGliOperandi() {
        Polynomial p = Polynomial.of(1, 2);
        Polynomial q = Polynomial.of(3, -2);

        assertEquals(Polynomial.of(4), p.add(q));
        assertEquals(Polynomial.of(-2, 4), p.subtract(q));
        assertEquals(Polynomial.of(3, 4, -4), p.multiply(q));
        assertEquals(Polynomial.of(1, 2), p);
        assertEquals(Polynomial.of(3, -2), q);
    }

    @Test
    void varianteDerivataEValutazioneRispettanoIContratti() {
        Polynomial p = Polynomial.of(1, 0, 3);
        Polynomial changed = p.withCoefficient(1, 2);

        assertEquals(BigInteger.ZERO, p.coefficient(1));
        assertEquals(BigInteger.TWO, changed.coefficient(1));
        assertEquals(Polynomial.of(0, 6), p.derivative());
        assertEquals(BigInteger.valueOf(13), p.evaluate(2));
    }

    @Test
    void converteGliIntSenzaLimitareIRisultatiA32Bit() {
        Polynomial maximum = Polynomial.of(Integer.MAX_VALUE);
        BigInteger expected = BigInteger.valueOf(Integer.MAX_VALUE).pow(2);

        assertEquals(BigInteger.valueOf(Integer.MAX_VALUE), maximum.coefficient(0));
        assertEquals(expected, maximum.multiply(maximum).coefficient(0));
    }

    @Test
    void rifiutaEsponentiNegativiEArrayNull() {
        Polynomial p = Polynomial.zero();
        assertThrows(IllegalArgumentException.class, () -> p.coefficient(-1));
        assertThrows(NullPointerException.class, () -> Polynomial.of((int[]) null));
    }
}
