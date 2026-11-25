package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Missione: rappresentare l'identità e i dati anagrafici essenziali di uno studente.
 * Invariante di classe: matricola valida, nome ed email non vuoti.
 */
@EqualsAndHashCode(of = "id")
@ToString
public final class Student {
    /* RI: campi non null; name/email non blank. AF: terna id, nome, email. */
    private final StudentId id;
    private final String name;
    private final String email;

    /**
     * Crea uno studente.
     * @param id matricola
     * @param name nome visualizzato
     * @param email indirizzo istituzionale
     * @throws NullPointerException se id è null
     * @throws IllegalArgumentException se nome o email sono vuoti
     * @post Lo stato astratto coincide con i tre argomenti normalizzati.
     */
    public Student(StudentId id, String name, String email) {
        this.id = Objects.requireNonNull(id, "id");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome vuoto");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email vuota");
        this.name = name.trim();
        this.email = email.trim();
    }

    /** @return la matricola; @post This è invariato. */
    public StudentId id() { return id; }

    /** @return il nome; @post This è invariato. */
    public String name() { return name; }

    /** @return l'email; @post This è invariato. */
    public String email() { return email; }
}
