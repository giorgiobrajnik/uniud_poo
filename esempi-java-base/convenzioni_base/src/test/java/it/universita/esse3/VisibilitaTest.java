package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.visibilita.Appello;
import it.universita.esse3.visibilita.AppelloScadente;
import it.universita.esse3.visibilita.Studente;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class VisibilitaTest {
    @TempDir
    Path output;

    private final Studente regolare = new Studente("123", 60, false);
    private final Studente conDebiti = new Studente("456", 60, true);

    @Test
    void apiPubblicaMantieneLoStatoValido() {
        Appello appello = new Appello("POO", LocalDate.of(2026, 2, 10), 1);
        assertTrue(appello.puoPrenotarsi(regolare));
        appello.prenotaStudente(regolare);
        assertEquals(0, appello.getPostiDisponibili());

        appello.prenotaStudente(regolare);
        assertEquals(0, appello.getPostiDisponibili());
    }

    @Test
    void studenteConDebitiNonPuoPrenotarsi() {
        Appello appello = new Appello("POO", LocalDate.of(2026, 2, 10), 10);
        appello.prenotaStudente(conDebiti);
        assertEquals(10, appello.getPostiDisponibili());
    }

    @Test
    void costruttoreRifiutaStatoNonValido() {
        assertThrows(IllegalArgumentException.class, () -> new Appello("POO", LocalDate.now(), -1));
        assertThrows(IllegalArgumentException.class, () -> new Studente("789", -500, false));
    }

    @Test
    void controesempioCampiPubbliciPermettonoStatoNonValido() {
        AppelloScadente appello = new AppelloScadente();
        appello.postiDisponibili = -1000;
        appello.prenotaStudente(regolare);
        assertEquals(-1001, appello.postiDisponibili);
    }

    @Test
    void controesempioAccessoACampoPrivato() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "AccessoCampoPrivato.java.txt", "it.universita.esse3.client.AccessoCampoPrivato", output);
        assertTrue(errori.contains("compiler.err.report.access"), errori.toString());
    }

    @Test
    void controesempioAccessoAClassePackagePrivateDaAltroPackage() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "AccessoClassePackagePrivate.java.txt",
                "it.universita.esse3.client.AccessoClassePackagePrivate", output);
        assertTrue(errori.contains("compiler.err.not.def.public.cant.access"), errori.toString());
    }
}
