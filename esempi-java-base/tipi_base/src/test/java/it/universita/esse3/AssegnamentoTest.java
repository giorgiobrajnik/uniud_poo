package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import it.universita.esse3.assegnamento.ValoriDiDefault;
import it.universita.esse3.assegnamento.VotoDaEsito;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AssegnamentoTest {
    @TempDir
    Path output;

    @Test
    void entrambiIRamiAssegnano() {
        assertEquals(30, VotoDaEsito.voto(true));
        assertEquals(18, VotoDaEsito.voto(false));
    }

    @Test
    void icampiHannoUnValoreDiDefault() {
        ValoriDiDefault campi = new ValoriDiDefault();
        assertEquals(0, campi.getVoto());
        assertFalse(campi.isVerbalizzato());
        assertNull(campi.getMatricola());
    }

    @Test
    void controesempioLetturaSenzaAssegnamento() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore("LetturaSenzaAssegnamento.java.txt",
                "it.universita.esse3.client.LetturaSenzaAssegnamento", output);
        assertEquals(List.of("compiler.err.var.might.not.have.been.initialized"), errori);
    }

    @Test
    void controesempioUnSoloRamoAssegna() throws IOException, URISyntaxException {
        List<String> errori = CompilatoreDiControesempi.codiciErrore("UnSoloRamoAssegna.java.txt",
                "it.universita.esse3.client.UnSoloRamoAssegna", output);
        assertEquals(List.of("compiler.err.var.might.not.have.been.initialized"), errori);
    }
}
