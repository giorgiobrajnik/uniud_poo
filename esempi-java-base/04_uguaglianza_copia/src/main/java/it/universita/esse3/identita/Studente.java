package it.universita.esse3.identita;

// Nessun equals: eredita quello di Object, che confronta l'identità
public class Studente {
    private final String matricola;
    private final String nome;

    public Studente(String matricola, String nome) {
        this.matricola = matricola;
        this.nome = nome;
    }

    public String matricola() {
        return matricola;
    }

    public String nome() {
        return nome;
    }
}
