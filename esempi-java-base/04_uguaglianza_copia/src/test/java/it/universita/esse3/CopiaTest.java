package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.copia.AppelloEsame;
import it.universita.esse3.copia.AttivitaPiano;
import it.universita.esse3.copia.Carriera;
import it.universita.esse3.copia.CopiaGrafo;
import it.universita.esse3.copia.CopieDiCollezioni;
import it.universita.esse3.copia.EsameSuperato;
import it.universita.esse3.copia.Nodo;
import it.universita.esse3.copia.PianoDiStudi;
import it.universita.esse3.copia.PrenotazioneAppello;
import it.universita.esse3.copia.StudenteId;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CopiaTest {
    private static Carriera carrieraConUnEsame() {
        Carriera c = new Carriera("123456");
        c.registraEsame(new EsameSuperato("POO", 27));
        return c;
    }

    @Test
    void assegnareUnRiferimentoNonCopia() {
        Carriera c1 = carrieraConUnEsame();
        Carriera c2 = c1;
        c2.registraEsame(new EsameSuperato("BASI-DATI", 28));
        assertTrue(c1 == c2);
        assertEquals(2, c1.numeroEsami());
    }

    @Test
    void controesempioCopiaSuperficialeCondivideLaLista() {
        Carriera c1 = carrieraConUnEsame();
        Carriera copia = c1.copiaSuperficiale();
        copia.registraEsame(new EsameSuperato("BASI-DATI", 28));
        assertFalse(c1 == copia);
        assertEquals(2, c1.numeroEsami());
    }

    @Test
    void controesempioNuovaListaMaElementiCondivisi() {
        Carriera c1 = carrieraConUnEsame();
        Carriera copia = c1.copiaConNuovaLista();
        copia.registraEsame(new EsameSuperato("ALGEBRA", 24));
        copia.esame(0).setVoto(30);
        assertEquals(1, c1.numeroEsami());
        assertEquals(30, c1.esame(0).voto());
    }

    @Test
    void copiaProfondaEIndipendente() {
        Carriera c1 = carrieraConUnEsame();
        Carriera copia = c1.copiaProfonda();
        copia.esame(0).setVoto(18);
        copia.registraEsame(new EsameSuperato("FISICA", 21));
        assertEquals(27, c1.esame(0).voto());
        assertEquals(1, c1.numeroEsami());
    }

    @Test
    void collezioniEArray() {
        assertEquals(30, CopieDiCollezioni.votoOriginaleDopoModificaViaCopiaArrayList());
        assertEquals(30, CopieDiCollezioni.votoOriginaleDopoModificaViaCopyOf());
        assertTrue(CopieDiCollezioni.copyOfNonPermetteAggiunte());
        assertArrayEquals(new int[] {2, 1}, CopieDiCollezioni.vistaNonModificabileContraCopyOf());
        assertEquals(27, CopieDiCollezioni.primoValoreOriginaleDopoCloneDiPrimitivi());
        assertEquals(30, CopieDiCollezioni.votoOriginaleDopoCloneDiRiferimenti());
    }

    @Test
    void costruttoreDiCopiaDelPiano() {
        PianoDiStudi piano = new PianoDiStudi(new StudenteId("123456"));
        piano.aggiungi(new AttivitaPiano("POO", 9));
        PianoDiStudi snapshot = PianoDiStudi.snapshotOf(piano);
        piano.attivita(0).setCrediti(6);
        assertEquals(9, snapshot.attivita(0).crediti());
        assertEquals(piano.studenteId(), snapshot.studenteId());
        assertNotEquals(piano, snapshot);
    }

    @Test
    void controesempioDeepCopyCiecaDiUnEntita() {
        AppelloEsame appello = new AppelloEsame("INF-POO-3", LocalDate.of(2026, 6, 10));
        PrenotazioneAppello prenotazione = new PrenotazioneAppello("123456", appello);
        PrenotazioneAppello condivisa = prenotazione.copiaCondivisa();
        PrenotazioneAppello cieca = prenotazione.copiaProfondaCieca();
        appello.sposta(LocalDate.of(2026, 6, 11));
        assertEquals(LocalDate.of(2026, 6, 11), condivisa.appello().data());
        assertEquals(LocalDate.of(2026, 6, 10), cieca.appello().data());
    }

    @Test
    void copiaIngenuaPerdeLaCondivisione() {
        Nodo radice = new Nodo("radice");
        Nodo p1 = new Nodo("p1");
        Nodo p2 = new Nodo("p2");
        Nodo condiviso = new Nodo("appello");
        radice.collega(p1);
        radice.collega(p2);
        p1.collega(condiviso);
        p2.collega(condiviso);
        Nodo ingenua = CopiaGrafo.copiaIngenua(radice);
        Nodo conMappa = CopiaGrafo.copiaConMappa(radice);
        assertFalse(ingenua.collegato(0).collegato(0) == ingenua.collegato(1).collegato(0));
        assertTrue(conMappa.collegato(0).collegato(0) == conMappa.collegato(1).collegato(0));
        assertFalse(conMappa.collegato(0).collegato(0) == condiviso);
    }

    @Test
    void controesempioCopiaIngenuaDiUnCiclo() {
        Nodo studente = new Nodo("studente");
        Nodo carriera = new Nodo("carriera");
        studente.collega(carriera);
        carriera.collega(studente);
        assertThrows(StackOverflowError.class, () -> CopiaGrafo.copiaIngenua(studente));
        Nodo copia = CopiaGrafo.copiaConMappa(studente);
        assertTrue(copia.collegato(0).collegato(0) == copia);
        assertFalse(copia == studente);
    }
}
