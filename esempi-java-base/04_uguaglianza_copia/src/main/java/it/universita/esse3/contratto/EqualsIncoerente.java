package it.universita.esse3.contratto;

// CONTROESEMPIO: il risultato cambia a parità di stato (viola la consistenza)
public final class EqualsIncoerente {
    private static int chiamate = 0;

    @Override
    public boolean equals(Object other) {
        chiamate++;
        return chiamate % 2 == 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
