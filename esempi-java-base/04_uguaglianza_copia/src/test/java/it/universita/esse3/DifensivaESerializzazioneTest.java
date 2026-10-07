package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.universita.esse3.difensiva.AppelloEsposto;
import it.universita.esse3.difensiva.AppelloProtetto;
import it.universita.esse3.difensiva.EsameModificabile;
import it.universita.esse3.difensiva.EsameRegistrato;
import it.universita.esse3.difensiva.LibrettoConSnapshot;
import it.universita.esse3.difensiva.LibrettoElementiMutabili;
import it.universita.esse3.difensiva.LibrettoImmutabile;
import it.universita.esse3.serializzazione.CopiaPerSerializzazione;
import it.universita.esse3.serializzazione.ReportConRisorsa;
import it.universita.esse3.serializzazione.ReportSerializzabile;
import java.io.NotSerializableException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DifensivaESerializzazioneTest {
    @Test
    void controesempioListaDelChiamanteCondivisa() {
        List<String> aule = new ArrayList<>(List.of("Aula A"));
        AppelloEsposto appello = new AppelloEsposto(aule);
        aule.clear();
        assertEquals(0, appello.numeroAule());
    }

    @Test
    void controesempioGetterEspostoAggiraLeRegole() {
        AppelloEsposto appello = new AppelloEsposto(List.of("Aula A"));
        appello.chiudiPrenotazioni();
        appello.getIscritti().add("999999");
        appello.getIscritti().add("999999");
        assertEquals(List.of("999999", "999999"), appello.getIscritti());
    }

    @Test
    void copiaInIngressoEInUscita() {
        List<String> aule = new ArrayList<>(List.of("Aula A"));
        AppelloProtetto appello = new AppelloProtetto(aule);
        aule.clear();
        assertEquals(1, appello.numeroAule());

        appello.prenota("123456");
        assertThrows(UnsupportedOperationException.class, () -> appello.iscritti().add("999999"));
        assertThrows(IllegalStateException.class, () -> appello.prenota("123456"));
        appello.chiudiPrenotazioni();
        assertThrows(IllegalStateException.class, () -> appello.prenota("654321"));
    }

    @Test
    void conElementiImmutabiliBastaCopiareLaLista() {
        List<EsameRegistrato> esami = new ArrayList<>(List.of(new EsameRegistrato("POO", 27)));
        LibrettoImmutabile libretto = new LibrettoImmutabile("123456", esami);
        esami.clear();
        assertEquals(List.of(new EsameRegistrato("POO", 27)), libretto.esami());
        assertThrows(UnsupportedOperationException.class, () -> libretto.esami().clear());
    }

    @Test
    void controesempioElementiMutabili() {
        LibrettoElementiMutabili libretto = new LibrettoElementiMutabili("123456",
                List.of(new EsameModificabile("POO", 27)));
        libretto.esami().get(0).setVoto(18);
        assertEquals(18, libretto.votoDi(0));
    }

    @Test
    void snapshotImmutabili() {
        LibrettoConSnapshot libretto = new LibrettoConSnapshot("123456");
        libretto.registra("POO", 27);
        List<EsameRegistrato> prima = libretto.esami();
        libretto.correggiVoto(0, 30);
        assertEquals(List.of(new EsameRegistrato("POO", 27)), prima);
        assertEquals(List.of(new EsameRegistrato("POO", 30)), libretto.esami());
        assertThrows(IllegalArgumentException.class, () -> libretto.correggiVoto(0, 31));
    }

    @Test
    void copiaPerSerializzazioneEIndipendente() {
        ReportSerializzabile originale = new ReportSerializzabile("Esami 2026");
        originale.aggiungi("POO");
        ReportSerializzabile copia = CopiaPerSerializzazione.copia(originale);
        copia.aggiungi("BASI-DATI");
        assertEquals(List.of("POO"), originale.voci());
        assertEquals(List.of("POO", "BASI-DATI"), copia.voci());
    }

    @Test
    void controesempioCampoTransientPerso() {
        ReportSerializzabile originale = new ReportSerializzabile("Esami 2026");
        originale.calcolaCache();
        assertEquals("calcolata: 0 voci", originale.cache());
        assertNull(CopiaPerSerializzazione.copia(originale).cache());
    }

    @Test
    void controesempioOggettoNonSerializzabile() {
        UncheckedIOException e = assertThrows(UncheckedIOException.class,
                () -> CopiaPerSerializzazione.copia(new ReportConRisorsa("Esami 2026")));
        assertInstanceOf(NotSerializableException.class, e.getCause());
    }
}
