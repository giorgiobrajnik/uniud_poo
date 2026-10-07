package it.universita.esse3.difensiva;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Gli esami restano modificabili solo da dentro; il client osserva snapshot immutabili
public final class LibrettoConSnapshot {
    private final String studenteId;
    private final List<EsameModificabile> esami = new ArrayList<>();

    public LibrettoConSnapshot(String studenteId) {
        this.studenteId = Objects.requireNonNull(studenteId);
    }

    public void registra(String corso, int voto) {
        esami.add(new EsameModificabile(corso, voto));
    }

    public void correggiVoto(int indice, int nuovoVoto) {
        if (nuovoVoto < 18 || nuovoVoto > 30) {
            throw new IllegalArgumentException("voto non valido");
        }
        esami.get(indice).setVoto(nuovoVoto);
    }

    public List<EsameRegistrato> esami() {
        return esami.stream()
                .map(e -> new EsameRegistrato(e.corso(), e.voto()))
                .toList();
    }
}
