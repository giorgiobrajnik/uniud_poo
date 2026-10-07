package it.universita.esse3.copia;

import java.util.ArrayList;
import java.util.List;

// Nodo di un grafo di oggetti: ogni elemento di collegati è un riferimento verso un altro nodo
public final class Nodo {
    private final String nome;
    private final List<Nodo> collegati = new ArrayList<>();

    public Nodo(String nome) {
        this.nome = nome;
    }

    public String nome() {
        return nome;
    }

    public void collega(Nodo altro) {
        collegati.add(altro);
    }

    public Nodo collegato(int indice) {
        return collegati.get(indice);
    }

    public int numeroCollegati() {
        return collegati.size();
    }
}
