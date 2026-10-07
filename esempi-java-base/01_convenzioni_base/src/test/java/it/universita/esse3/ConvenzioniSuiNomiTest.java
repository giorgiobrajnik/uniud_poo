package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.nomi.CarrieraStudente;
import it.universita.esse3.nomi.NomiNonConvenzionali;
import org.junit.jupiter.api.Test;

class ConvenzioniSuiNomiTest {
    @Test
    void esempioConvenzionale() {
        assertTrue(new CarrieraStudente(180).haCompletatoCrediti());
        assertFalse(new CarrieraStudente(179).haCompletatoCrediti());
    }

    @Test
    void controesempioNomeNonConvenzionaleCompilaComunque() {
        assertEquals(6, NomiNonConvenzionali.cfuConNomeNonConvenzionale());
    }

    @Test
    void javaDistingueMaiuscoleEMinuscole() {
        assertEquals(111, NomiNonConvenzionali.sommaIdentificatoriDiversi());
    }
}
