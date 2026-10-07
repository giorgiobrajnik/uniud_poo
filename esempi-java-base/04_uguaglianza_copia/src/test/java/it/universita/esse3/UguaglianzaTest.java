package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.universita.esse3.contratto.EqualsIncoerente;
import it.universita.esse3.contratto.Misura;
import it.universita.esse3.contratto.Persona;
import it.universita.esse3.contratto.StudentePersona;
import it.universita.esse3.identita.RepositoryStudenti;
import it.universita.esse3.identita.Studente;
import it.universita.esse3.uguaglianza.AnnoAccademico;
import it.universita.esse3.uguaglianza.CodiceCorso;
import it.universita.esse3.uguaglianza.CodiceCorsoConPattern;
import it.universita.esse3.uguaglianza.CodiceCorsoOverload;
import it.universita.esse3.uguaglianza.CodiceCorsoSenzaEquals;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UguaglianzaTest {
    @Test
    void identitaDiRiferimenti() {
        Studente s1 = new Studente("123456", "Anna Rossi");
        Studente s2 = s1;
        Studente s3 = new Studente("123456", "Anna Rossi");
        assertTrue(s1 == s2);
        assertFalse(s1 == s3);
        assertNotEquals(s1, s3);
    }

    @Test
    void stessaEntitaDiDominioIstanzeDiverse() {
        RepositoryStudenti repository = new RepositoryStudenti();
        Studente d1 = repository.load("123456");
        Studente d2 = repository.load("123456");
        assertFalse(d1 == d2);
        assertEquals(d1.matricola(), d2.matricola());
    }

    @Test
    void uguaglianzaPerValore() {
        AnnoAccademico a1 = new AnnoAccademico(2026);
        AnnoAccademico a2 = new AnnoAccademico(2026);
        assertFalse(a1 == a2);
        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    @Test
    void controesempioEqualsNonRidefinito() {
        assertNotEquals(new CodiceCorsoSenzaEquals("INF-POO"), new CodiceCorsoSenzaEquals("INF-POO"));
    }

    @Test
    void equalsRidefinito() {
        CodiceCorso c = new CodiceCorso("INF-POO");
        assertEquals(c, new CodiceCorso("INF-POO"));
        assertNotEquals(c, null);
        assertNotEquals(c, "INF-POO");
        assertEquals(new CodiceCorsoConPattern("INF-POO"), new CodiceCorsoConPattern("INF-POO"));
    }

    @Test
    void controesempioOverloadInveceDiOverride() {
        CodiceCorsoOverload o1 = new CodiceCorsoOverload("INF-POO");
        CodiceCorsoOverload o2 = new CodiceCorsoOverload("INF-POO");
        Object o2ComeObject = o2;
        assertTrue(o1.equals(o2));
        assertFalse(o1.equals(o2ComeObject));
        assertFalse(List.of(o1).contains(o2));
        Set<CodiceCorsoOverload> insieme = new HashSet<>();
        insieme.add(o1);
        assertFalse(insieme.contains(o2));
    }

    @Test
    void controesempioSimmetria() {
        Persona p = new Persona("Anna Rossi");
        StudentePersona s = new StudentePersona("Anna Rossi", "123456");
        assertTrue(p.equals(s));
        assertFalse(s.equals(p));
    }

    @Test
    void controesempioTransitivita() {
        Misura a = new Misura(20.0);
        Misura b = new Misura(20.8);
        Misura c = new Misura(21.6);
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertFalse(a.equals(c));
    }

    @Test
    void controesempioConsistenza() {
        EqualsIncoerente e = new EqualsIncoerente();
        assertNotEquals(e.equals(e), e.equals(e));
    }
}
