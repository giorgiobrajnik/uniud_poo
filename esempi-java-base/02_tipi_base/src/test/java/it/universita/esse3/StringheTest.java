package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.stringhe.ConversioniStringhe;
import org.junit.jupiter.api.Test;

class StringheTest {
    @Test
    void parsing() {
        assertEquals(12, ConversioniStringhe.parseCfu("12"));
        assertEquals(9_000_000_001L, ConversioniStringhe.parseId("9000000001"));
        assertEquals(27.5, ConversioniStringhe.parseMedia("27.5"));
        assertTrue(ConversioniStringhe.parseAttivo("true"));
    }

    @Test
    void controesempioParsingDiTestoNonValido() {
        assertThrows(NumberFormatException.class, () -> ConversioniStringhe.parseCfu("trenta"));
    }

    @Test
    void controesempioParseBooleanNonSegnalaErrori() {
        assertTrue(ConversioniStringhe.parseAttivo("TRUE"));
        assertFalse(ConversioniStringhe.parseAttivo("vero"));
    }

    @Test
    void daNumeroAStringa() {
        assertEquals("12", ConversioniStringhe.cfuComeTesto(12));
        assertEquals("123456789", ConversioniStringhe.idComeTesto(123456789L));
    }
}
