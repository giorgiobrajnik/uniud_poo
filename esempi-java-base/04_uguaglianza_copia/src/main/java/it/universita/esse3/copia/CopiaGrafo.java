package it.universita.esse3.copia;

import java.util.IdentityHashMap;
import java.util.Map;

public final class CopiaGrafo {
    private CopiaGrafo() {
    }

    // CONTROESEMPIO: duplica i nodi condivisi e non termina in presenza di cicli
    public static Nodo copiaIngenua(Nodo nodo) {
        Nodo copia = new Nodo(nodo.nome());
        for (int i = 0; i < nodo.numeroCollegati(); i++) {
            copia.collega(copiaIngenua(nodo.collegato(i)));
        }
        return copia;
    }

    public static Nodo copiaConMappa(Nodo nodo) {
        return copiaConMappa(nodo, new IdentityHashMap<>());
    }

    // La mappa originale -> copia preserva condivisioni e cicli
    private static Nodo copiaConMappa(Nodo nodo, Map<Nodo, Nodo> giaCopiati) {
        Nodo esistente = giaCopiati.get(nodo);
        if (esistente != null) {
            return esistente;
        }
        Nodo copia = new Nodo(nodo.nome());
        giaCopiati.put(nodo, copia);
        for (int i = 0; i < nodo.numeroCollegati(); i++) {
            copia.collega(copiaConMappa(nodo.collegato(i), giaCopiati));
        }
        return copia;
    }
}
