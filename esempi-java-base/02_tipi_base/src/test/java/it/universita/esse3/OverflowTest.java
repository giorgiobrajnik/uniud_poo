package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.universita.esse3.overflow.Overflow;
import org.junit.jupiter.api.Test;

class OverflowTest {
    @Test
    void controesempioOverflowSilenzioso() {
        assertEquals(Integer.MIN_VALUE, Overflow.incrementaSenzaControllo(Integer.MAX_VALUE));
    }

    @Test
    void operazioniExactSegnalanoOverflow() {
        assertThrows(ArithmeticException.class, () -> Overflow.incrementaConControllo(Integer.MAX_VALUE));
        assertThrows(ArithmeticException.class, () -> Overflow.moltiplicaConControllo(100_000, 100_000));
        assertEquals(11, Overflow.incrementaConControllo(10));
    }

    @Test
    void tipoPiuAmpioRappresentaIlRisultato() {
        assertEquals(2_147_483_648L, Overflow.incrementaComeLong(Integer.MAX_VALUE));
    }
}
