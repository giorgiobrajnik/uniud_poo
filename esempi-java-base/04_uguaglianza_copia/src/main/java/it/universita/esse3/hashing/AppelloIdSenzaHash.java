package it.universita.esse3.hashing;

// CONTROESEMPIO: equals ridefinito, hashCode no
public final class AppelloIdSenzaHash {
    private final String codiceCorso;
    private final int progressivo;

    public AppelloIdSenzaHash(String codiceCorso, int progressivo) {
        this.codiceCorso = codiceCorso;
        this.progressivo = progressivo;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AppelloIdSenzaHash)) {
            return false;
        }
        AppelloIdSenzaHash that = (AppelloIdSenzaHash) other;
        return progressivo == that.progressivo
                && codiceCorso.equals(that.codiceCorso);
    }
}
