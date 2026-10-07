package it.universita.esse3.hashing;

import java.util.Objects;

public final class AppelloId {
    private final String codiceCorso;
    private final int progressivo;

    public AppelloId(String codiceCorso, int progressivo) {
        this.codiceCorso = Objects.requireNonNull(codiceCorso);
        this.progressivo = progressivo;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppelloId)) {
            return false;
        }
        AppelloId that = (AppelloId) other;
        return progressivo == that.progressivo
                && codiceCorso.equals(that.codiceCorso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codiceCorso, progressivo);
    }
}
