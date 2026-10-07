package it.universita.esse3.difensiva;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Con elementi immutabili basta copiare la struttura della lista
public final class LibrettoImmutabile {
    private final String studenteId;
    private final List<EsameRegistrato> esami;

    public LibrettoImmutabile(String studenteId, List<EsameRegistrato> esami) {
        this.studenteId = Objects.requireNonNull(studenteId);
        this.esami = new ArrayList<>(esami);
    }

    public String studenteId() {
        return studenteId;
    }

    public List<EsameRegistrato> esami() {
        return List.copyOf(esami);
    }
}
