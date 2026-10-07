package it.universita.esse3.variabili;

public class CampoConThis {
    private int crediti;

    public CampoConThis(int crediti) {
        this.crediti = crediti;
    }

    public int getCrediti() {
        return crediti;
    }

    public void aggiungiCrediti(int cfu) {
        this.crediti = this.crediti + cfu;
    }
}
