package it.universita.esse3.finali;

public class Appello {
    private final long id;
    private final String codiceCorso = "POO";
    private int postiDisponibili;

    public Appello(long id, int postiDisponibili) {
        this.id = id;
        this.postiDisponibili = postiDisponibili;
    }

    public void prenota() {
        postiDisponibili--;
    }

    public long getId() {
        return id;
    }

    public String getCodiceCorso() {
        return codiceCorso;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }
}
