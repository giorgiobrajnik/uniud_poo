package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.primitivi.TipiPrimitivi;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PrimitiviTest {
    @TempDir
    Path output;

    @Test
    void intervalli() {
        assertEquals(-128, Byte.MIN_VALUE);
        assertEquals(127, Byte.MAX_VALUE);
        assertEquals(-32768, Short.MIN_VALUE);
        assertEquals(2147483647, Integer.MAX_VALUE);
        assertEquals(9223372036854775807L, Long.MAX_VALUE);
    }

    @Test
    void letterali() {
        assertEquals(4_000_000_000L, TipiPrimitivi.idEvento());
        assertTrue(TipiPrimitivi.underscoreNonCambiaIlValore());
        assertEquals(List.of("Float", "Double", "Character", "String"), TipiPrimitivi.tipiDeiLetterali());
    }

    @Test
    void charEUnaUnitaUtf16() {
        assertEquals(1, TipiPrimitivi.unitaUtf16("A"));
        assertEquals(1, TipiPrimitivi.caratteriUnicode("A"));
        assertEquals(2, TipiPrimitivi.unitaUtf16("\uD83D\uDE00"));
        assertEquals(1, TipiPrimitivi.caratteriUnicode("\uD83D\uDE00"));
    }

    @Test
    void controesempioBooleanNonEUnIntero() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "BooleanEInteri.java.txt", "it.universita.esse3.client.BooleanEInteri", output);
        assertEquals(List.of("compiler.err.prob.found.req", "compiler.err.prob.found.req"), errori);
    }
}
