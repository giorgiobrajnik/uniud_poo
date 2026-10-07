package it.universita.esse3.mutabilita;

// CONTROESEMPIO: campo non final e setter pubblico, quindi l'aliasing e' pericoloso
public class CodiceAppelloModificabile {
    private String valore;

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }
}
