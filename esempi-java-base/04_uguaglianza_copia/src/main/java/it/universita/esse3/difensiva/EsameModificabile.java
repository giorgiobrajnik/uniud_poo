package it.universita.esse3.difensiva;

// Mutabile: il voto può essere corretto
public class EsameModificabile {
    private final String corso;
    private int voto;

    public EsameModificabile(String corso, int voto) {
        this.corso = corso;
        this.voto = voto;
    }

    public String corso() {
        return corso;
    }

    public int voto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }
}
