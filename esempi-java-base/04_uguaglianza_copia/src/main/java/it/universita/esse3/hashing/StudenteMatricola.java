package it.universita.esse3.hashing;

import java.util.Objects;

// Entità: equals e hashCode sulla sola matricola, stabile e immutabile
public class StudenteMatricola {
    private final String matricola;
    private String nome;
    private String email;

    public StudenteMatricola(String matricola, String nome, String email) {
        this.matricola = Objects.requireNonNull(matricola);
        this.nome = nome;
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StudenteMatricola)) {
            return false;
        }
        StudenteMatricola that = (StudenteMatricola) other;
        return matricola.equals(that.matricola);
    }

    @Override
    public int hashCode() {
        return matricola.hashCode();
    }
}
