package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.universita.esse3.blocchi.ControlloSoglia;
import it.universita.esse3.blocchi.ControlloSogliaSenzaGraffe;
import java.util.List;
import org.junit.jupiter.api.Test;

class BlocchiTest {
    @Test
    void conGraffeNienteAccadeSopraSoglia() {
        assertEquals(List.of(), ControlloSoglia.controlla(30));
    }

    @Test
    void conGraffeEntrambeLeAzioniSottoSoglia() {
        assertEquals(List.of("messaggio sotto soglia", "invio numero sostegno"), ControlloSoglia.controlla(6));
    }

    @Test
    void controesempioSenzaGraffeLaSecondaIstruzioneEseguitaSempre() {
        assertEquals(List.of("invio numero sostegno"), ControlloSogliaSenzaGraffe.controlla(30));
    }
}
