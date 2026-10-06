package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.omonimie.report.ReportEsami;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PackageEImportTest {
    @TempDir
    Path output;

    @Test
    void stessoNomeSempliceInPackageDiversiSonoTipiDiversi() {
        assertNotEquals(it.universita.esse3.omonimie.esami.Esame.class,
                it.universita.esse3.omonimie.appelli.Esame.class);
        assertEquals("it.universita.esse3.omonimie.esami.Esame",
                it.universita.esse3.omonimie.esami.Esame.class.getName());
        assertEquals("it.universita.esse3.omonimie.appelli.Esame",
                it.universita.esse3.omonimie.appelli.Esame.class.getName());
    }

    @Test
    void costantiOmonimeInPackageDiversi() {
        assertEquals("iscritto", it.universita.esse3.omonimie.carriere.Stato.ISCRITTO);
        assertEquals("confermata", it.universita.esse3.omonimie.appelli.Stato.CONFERMATA);
        assertEquals("pagata", it.universita.esse3.omonimie.tasse.Stato.PAGATA);
    }

    @Test
    void nomeCompletamenteQualificatoDisambiguaIlTipo() {
        var esameAppello = new it.universita.esse3.omonimie.appelli.Esame("POO", LocalDate.of(2026, 2, 10), "Aula 5");
        assertEquals("Materia: Programmazione orientata agli oggetti, aula: Aula 5",
                new ReportEsami().generaReport(esameAppello));
    }

    @Test
    void controesempioDueImportConLoStessoNomeSemplice() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "DueImportStessoNome.java.txt", "it.universita.esse3.omonimie.report.DueImportStessoNome", output);
        assertTrue(errori.contains("compiler.err.already.defined.single.import"), errori.toString());
    }

    @Test
    void controesempioMetodoUsatoSulTipoSbagliato() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "MetodoInesistente.java.txt", "it.universita.esse3.omonimie.report.MetodoInesistente", output);
        assertTrue(errori.contains("compiler.err.cant.resolve.location.args"), errori.toString());
    }

    @Test
    void controesempioClassePublicInFileConNomeDiverso() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore(
                "NomeFileDiverso.java.txt", "it.universita.esse3.client.NomeFile", output);
        assertTrue(errori.contains("compiler.err.class.public.should.be.in.file"), errori.toString());
    }
}
