package it.universita.esse3.contratto;

import java.util.Objects;

// Classe non final: una sottoclasse che aggiunge dati rilevanti rompe la simmetria
public class Persona {
    private final String nome;

    public Persona(String nome) {
        this.nome = Objects.requireNonNull(nome);
    }

    public String nome() {
        return nome;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Persona)) {
            return false;
        }
        Persona that = (Persona) other;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}
