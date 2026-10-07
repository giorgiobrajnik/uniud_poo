package it.universita.esse3.copia;

public class AttivitaPiano {
    private final String nome;
    private int crediti;

    public AttivitaPiano(String nome, int crediti) {
        this.nome = nome;
        this.crediti = crediti;
    }

    public AttivitaPiano(AttivitaPiano original) {
        this(original.nome, original.crediti);
    }

    public String nome() {
        return nome;
    }

    public int crediti() {
        return crediti;
    }

    public void setCrediti(int crediti) {
        this.crediti = crediti;
    }
}
