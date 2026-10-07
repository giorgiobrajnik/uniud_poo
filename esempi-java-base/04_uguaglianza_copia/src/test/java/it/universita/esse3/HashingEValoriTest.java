package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.confronti.ConfrontoStringhe;
import it.universita.esse3.confronti.ConfrontoWrapper;
import it.universita.esse3.confronti.Prenotazione;
import it.universita.esse3.confronti.PrenotazioneConNote;
import it.universita.esse3.confronti.Voti;
import it.universita.esse3.confronti.VotiSbagliato;
import it.universita.esse3.hashing.AppelloId;
import it.universita.esse3.hashing.AppelloIdSenzaHash;
import it.universita.esse3.hashing.StudenteEmail;
import it.universita.esse3.hashing.StudenteMatricola;
import it.universita.esse3.hashing.StudenteTuttiICampi;
import it.universita.esse3.valori.Importo;
import it.universita.esse3.valori.ImportoClassico;
import it.universita.esse3.valori.PianoStudi;
import it.universita.esse3.valori.PianoStudiSuperficiale;
import it.universita.esse3.valori.StudenteRecord;
import it.universita.esse3.valori.StudenteRecordPerMatricola;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class HashingEValoriTest {
    @Test
    void controesempioEqualsSenzaHashCode() {
        AppelloIdSenzaHash a = new AppelloIdSenzaHash("INF-POO", 3);
        AppelloIdSenzaHash b = new AppelloIdSenzaHash("INF-POO", 3);
        Set<AppelloIdSenzaHash> insieme = new HashSet<>();
        insieme.add(a);
        assertTrue(a.equals(b));
        assertFalse(insieme.contains(b));
    }

    @Test
    void equalsEHashCodeCoerenti() {
        Set<AppelloId> insieme = new HashSet<>();
        insieme.add(new AppelloId("INF-POO", 3));
        assertTrue(insieme.contains(new AppelloId("INF-POO", 3)));
    }

    @Test
    void collisioneLecita() {
        assertEquals("Aa".hashCode(), "BB".hashCode());
        assertNotEquals("Aa", "BB");
    }

    @Test
    void controesempioCampoMutabileNelloHash() {
        StudenteEmail anna = new StudenteEmail("Anna Rossi", "anna@old.example");
        Set<StudenteEmail> insieme = new HashSet<>();
        insieme.add(anna);
        anna.setEmail("anna@new.example");
        assertFalse(insieme.contains(anna));
        assertEquals(1, insieme.size());
    }

    @Test
    void controesempioTuttiICampi() {
        StudenteTuttiICampi s = new StudenteTuttiICampi("Anna Rossi", "anna@example", "Informatica");
        Set<StudenteTuttiICampi> insieme = new HashSet<>();
        insieme.add(s);
        s.setCorsoDiStudio("Matematica");
        assertFalse(insieme.contains(s));
    }

    @Test
    void identitaSullaMatricola() {
        StudenteMatricola m = new StudenteMatricola("123456", "Anna Rossi", "anna@old.example");
        Set<StudenteMatricola> insieme = new HashSet<>();
        insieme.add(m);
        m.setNome("Anna Maria Rossi");
        m.setEmail("anna@new.example");
        assertTrue(insieme.contains(m));
        assertTrue(insieme.contains(new StudenteMatricola("123456", "altro", "altro")));
    }

    @Test
    void recordEClasseClassicaHannoLaStessaSemantica() {
        Importo a = new Importo(12000, "EUR");
        Importo b = new Importo(12000, "EUR");
        assertFalse(a == b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(new Importo(24000, "EUR"), a.somma(b));
        assertEquals(new ImportoClassico(12000, "EUR"), new ImportoClassico(12000, "EUR"));
        assertEquals("Importo[centesimi=12000, valuta=EUR]", a.toString());
    }

    @Test
    void recordValidaNelCostruttoreCompatto() {
        assertThrows(IllegalArgumentException.class, () -> new Importo(-1, "EUR"));
        assertThrows(IllegalArgumentException.class, () -> new Importo(1, "EUR").somma(new Importo(1, "USD")));
    }

    @Test
    void controesempioImmutabilitaSuperficialeDelRecord() {
        List<String> lista = new ArrayList<>(List.of("POO"));
        PianoStudiSuperficiale superficiale = new PianoStudiSuperficiale("123456", lista);
        lista.add("BASI-DATI");
        assertEquals(2, superficiale.corsi().size());
    }

    @Test
    void copiaDifensivaNelCostruttoreCompatto() {
        List<String> lista = new ArrayList<>(List.of("POO"));
        PianoStudi piano = new PianoStudi("123456", lista);
        lista.add("BASI-DATI");
        assertEquals(List.of("POO"), piano.corsi());
    }

    @Test
    void controesempioRecordPerUnEntita() {
        assertNotEquals(new StudenteRecord("123456", "Anna Rossi"),
                new StudenteRecord("123456", "Anna Maria Rossi"));
        assertEquals(new StudenteRecordPerMatricola("123456", "Anna Rossi"),
                new StudenteRecordPerMatricola("123456", "Anna Maria Rossi"));
    }

    @Test
    void stringhe() {
        assertFalse(ConfrontoStringhe.newConDoppioUguale());
        assertTrue(ConfrontoStringhe.newConEquals());
        assertTrue(ConfrontoStringhe.letteraliConDoppioUguale());
        assertFalse(ConfrontoStringhe.letteraleContraNewConDoppioUguale());
    }

    @Test
    void wrapper() {
        assertTrue(ConfrontoWrapper.piccoliConDoppioUguale());
        assertTrue(ConfrontoWrapper.grandiConEquals());
        assertFalse(ConfrontoWrapper.integerEqualsLong());
        assertTrue(ConfrontoWrapper.primitiviConDoppioUguale());
    }

    @Test
    void campoCheNonDovrebbeContare() {
        assertNotEquals(new PrenotazioneConNote("123456", "INF-POO-3", null),
                new PrenotazioneConNote("123456", "INF-POO-3", "da verificare"));
        assertEquals(new Prenotazione("123456", "INF-POO-3", null),
                new Prenotazione("123456", "INF-POO-3", "da verificare"));
    }

    @Test
    void array() {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        assertFalse(a.equals(b));
        assertTrue(Arrays.equals(a, b));
        assertNotEquals(new VotiSbagliato(30, 27), new VotiSbagliato(30, 27));
        assertEquals(new Voti(30, 27), new Voti(30, 27));
        assertEquals(new Voti(30, 27).hashCode(), new Voti(30, 27).hashCode());
    }
}
