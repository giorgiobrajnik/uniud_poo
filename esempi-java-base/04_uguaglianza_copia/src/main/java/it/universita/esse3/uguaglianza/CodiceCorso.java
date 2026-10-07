package it.universita.esse3.uguaglianza;

import java.util.Objects;

public final class CodiceCorso {
    private final String codice;

    public CodiceCorso(String codice) {
        this.codice = Objects.requireNonNull(codice);
    }

    public String valore() {
        return codice;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CodiceCorso)) {
            return false;
        }
        CodiceCorso that = (CodiceCorso) other;
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return codice.hashCode();
    }
}
