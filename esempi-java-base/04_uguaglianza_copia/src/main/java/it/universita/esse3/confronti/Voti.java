package it.universita.esse3.confronti;

import java.util.Arrays;

public final class Voti {
    private final int[] valori;

    public Voti(int... valori) {
        this.valori = valori.clone();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Voti)) {
            return false;
        }
        Voti that = (Voti) other;
        return Arrays.equals(this.valori, that.valori);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(valori);
    }
}
