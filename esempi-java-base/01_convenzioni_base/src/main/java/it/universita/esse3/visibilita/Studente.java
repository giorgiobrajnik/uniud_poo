package it.universita.esse3.visibilita;

public class Studente {
    private final String matricola;
    private int creditiAcquisiti;
    private final boolean debiti;

    public Studente(String matricola, int creditiAcquisiti, boolean debiti) {
        if (creditiAcquisiti < 0) {
            throw new IllegalArgumentException("I crediti non possono essere negativi");
        }
        this.matricola = matricola;
        this.creditiAcquisiti = creditiAcquisiti;
        this.debiti = debiti;
    }

    public String getMatricola() {
        return matricola;
    }

    public int getCreditiAcquisiti() {
        return creditiAcquisiti;
    }

    public boolean haDebiti() {
        return debiti;
    }

    public void aggiungiCrediti(int cfu) {
        if (cfu < 0) {
            throw new IllegalArgumentException("I crediti da aggiungere non possono essere negativi");
        }
        creditiAcquisiti += cfu;
    }
}
