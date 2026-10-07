package it.universita.esse3.mutabilita;

public class StudenteImmutabile {
    private final String matricola;
    private final int crediti;

    public StudenteImmutabile(String matricola, int crediti) {
        this.matricola = matricola;
        this.crediti = crediti;
    }

    public String getMatricola() {
        return matricola;
    }

    public int getCrediti() {
        return crediti;
    }

    // Restituisce un nuovo oggetto invece di modificare lo stato
    public StudenteImmutabile aggiungiCrediti(int cfu) {
        return new StudenteImmutabile(this.matricola, this.crediti + cfu);
    }
}
