package it.universita.esse3.confronti;

import java.util.Arrays;

// CONTROESEMPIO: equals su un campo array confronta i riferimenti, non gli elementi
public final class VotiSbagliato {
    private final int[] valori;

    public VotiSbagliato(int... valori) {
        this.valori = valori.clone();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof VotiSbagliato)) {
            return false;
        }
        VotiSbagliato that = (VotiSbagliato) other;
        return this.valori.equals(that.valori);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(valori);
    }
}
