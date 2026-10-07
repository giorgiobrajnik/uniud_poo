package it.universita.esse3.contratto;

import java.util.Objects;

// CONTROESEMPIO: persona.equals(studente) è true ma studente.equals(persona) è false
public class StudentePersona extends Persona {
    private final String matricola;

    public StudentePersona(String nome, String matricola) {
        super(nome);
        this.matricola = Objects.requireNonNull(matricola);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StudentePersona)) {
            return false;
        }
        StudentePersona that = (StudentePersona) other;
        return super.equals(that) && matricola.equals(that.matricola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome(), matricola);
    }
}
