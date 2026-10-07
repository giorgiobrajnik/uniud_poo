package it.universita.esse3.riferimenti;

public class Studente {
    private final String matricola;
    private int crediti;

    public Studente(String matricola) {
        this.matricola = matricola;
        this.crediti = 0;
    }

    public String getMatricola() {
        return matricola;
    }

    public int getCrediti() {
        return crediti;
    }

    public void aggiungiCrediti(int cfu) {
        crediti += cfu;
    }
}
