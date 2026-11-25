package it.uniud.tda.polynomial.mutabile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class MutablePolynomialTest {
    @Test
    void gliAliasOsservanoSetCoefficient() {
        MutablePolynomial polynomial = MutablePolynomial.of(1);
        MutablePolynomial alias = polynomial;
        polynomial.setCoefficient(2, 3);
        assertEquals(BigInteger.valueOf(3), alias.coefficient(2));
        assertEquals(2, alias.degree());
    }

    @Test
    void addizioneConSeStessoUsaLoStatoPrecedente() {
        MutablePolynomial polynomial = MutablePolynomial.of(1, -2);
        polynomial.addInPlace(polynomial);
        assertEquals(MutablePolynomial.of(2, -4), polynomial);
    }

    @Test
    void prodottoConSeStessoEValutazioneSonoCorretti() {
        MutablePolynomial polynomial = MutablePolynomial.of(1, 1);
        polynomial.multiplyInPlace(polynomial);
        assertEquals(MutablePolynomial.of(1, 2, 1), polynomial);
        assertEquals(BigInteger.valueOf(16), polynomial.evaluate(3));
    }

    @Test
    void laCopiaNonCondivideLoStatoMutabile() {
        MutablePolynomial original = MutablePolynomial.of(1, 2);
        MutablePolynomial copy = original.copy();
        original.clear();
        assertTrue(original.isZero());
        assertEquals(MutablePolynomial.of(1, 2), copy);
    }

    @Test
    void fallimentiNonCambianoLoStato() {
        MutablePolynomial polynomial = MutablePolynomial.of(1);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                polynomial.setCoefficient(-1, 10);
            }
        });
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() {
                MutablePolynomial.of((int[]) null);
            }
        });
        assertEquals(MutablePolynomial.of(1), polynomial);
    }

    @Test
    void converteGliIntSenzaLimitareIRisultatiA32Bit() {
        MutablePolynomial polynomial = MutablePolynomial.of(Integer.MAX_VALUE);
        polynomial.multiplyInPlace(polynomial);

        assertEquals(BigInteger.valueOf(Integer.MAX_VALUE).pow(2), polynomial.coefficient(0));
    }
}
