package it.universita.esse3.contratto;

// CONTROESEMPIO: uguaglianza «a meno di una tolleranza», non transitiva
public final class Misura {
    private final double valore;

    public Misura(double valore) {
        this.valore = valore;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Misura)) {
            return false;
        }
        Misura that = (Misura) other;
        return Math.abs(valore - that.valore) <= 1.0;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
