package it.universita.esse3.hashing;

import java.util.Objects;

// CONTROESEMPIO: «tutti i campi» non definiscono l'identità di uno studente e possono cambiare
public class StudenteTuttiICampi {
    private final String nome;
    private final String email;
    private String corsoDiStudio;

    public StudenteTuttiICampi(String nome, String email, String corsoDiStudio) {
        this.nome = nome;
        this.email = email;
        this.corsoDiStudio = corsoDiStudio;
    }

    public void setCorsoDiStudio(String corsoDiStudio) {
        this.corsoDiStudio = corsoDiStudio;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StudenteTuttiICampi)) {
            return false;
        }
        StudenteTuttiICampi that = (StudenteTuttiICampi) other;
        return Objects.equals(nome, that.nome)
                && Objects.equals(email, that.email)
                && Objects.equals(corsoDiStudio, that.corsoDiStudio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, corsoDiStudio);
    }
}
