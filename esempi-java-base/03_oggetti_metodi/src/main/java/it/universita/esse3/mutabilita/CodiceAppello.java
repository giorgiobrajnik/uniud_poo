package it.universita.esse3.mutabilita;

// Immutabile: campo private e final, nessun setter, valore String immutabile
public class CodiceAppello {
    private final String valore;

    public CodiceAppello(String valore) {
        this.valore = valore;
    }

    public String getValore() {
        return valore;
    }
}
