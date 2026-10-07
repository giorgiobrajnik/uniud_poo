package it.universita.esse3.uguaglianza;

import java.util.Objects;

// CONTROESEMPIO: equals(CodiceCorsoOverload) è un overload, non ridefinisce equals(Object)
public final class CodiceCorsoOverload {
    private final String codice;

    public CodiceCorsoOverload(String codice) {
        this.codice = Objects.requireNonNull(codice);
    }

    // Senza @Override il compilatore non segnala l'errore
    public boolean equals(CodiceCorsoOverload other) {
        return codice.equals(other.codice);
    }

    @Override
    public int hashCode() {
        return codice.hashCode();
    }
}
