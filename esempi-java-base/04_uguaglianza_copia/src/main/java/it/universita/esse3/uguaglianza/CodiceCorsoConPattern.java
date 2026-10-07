package it.universita.esse3.uguaglianza;

import java.util.Objects;

// Stessa semantica di CodiceCorso, con instanceof + pattern variable (Java 16+)
public final class CodiceCorsoConPattern {
    private final String codice;

    public CodiceCorsoConPattern(String codice) {
        this.codice = Objects.requireNonNull(codice);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CodiceCorsoConPattern that)) {
            return false;
        }
        return codice.equals(that.codice);
    }

    @Override
    public int hashCode() {
        return codice.hashCode();
    }
}
