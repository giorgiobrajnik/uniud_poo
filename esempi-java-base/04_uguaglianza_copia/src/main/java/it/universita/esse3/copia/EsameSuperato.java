package it.universita.esse3.copia;

// Mutabile: il voto può essere corretto; il costruttore di copia duplica l'esame
public class EsameSuperato {
    private final String corso;
    private int voto;

    public EsameSuperato(String corso, int voto) {
        this.corso = corso;
        this.voto = voto;
    }

    public EsameSuperato(EsameSuperato original) {
        this(original.corso, original.voto);
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
