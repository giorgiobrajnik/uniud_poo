package it.universita.esse3.difensiva;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// CONTROESEMPIO: List.copyOf protegge la lista, non gli elementi mutabili
public final class LibrettoElementiMutabili {
    private final String studenteId;
    private final List<EsameModificabile> esami;

    public LibrettoElementiMutabili(String studenteId, List<EsameModificabile> esami) {
        this.studenteId = Objects.requireNonNull(studenteId);
        this.esami = new ArrayList<>(esami);
    }

    public List<EsameModificabile> esami() {
        return List.copyOf(esami);
    }

    public int votoDi(int indice) {
        return esami.get(indice).voto();
    }
}
