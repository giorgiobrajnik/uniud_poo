package it.universita.esse3.valori;

import java.util.Objects;

// Value object scritto a mano: quasi tutto il codice è meccanico (vedi Importo come record)
public final class ImportoClassico {
    private final long centesimi;
    private final String valuta;

    public ImportoClassico(long centesimi, String valuta) {
        if (centesimi < 0) {
            throw new IllegalArgumentException("importo negativo");
        }
        this.centesimi = centesimi;
        this.valuta = Objects.requireNonNull(valuta);
    }

    public long centesimi() {
        return centesimi;
    }

    public String valuta() {
        return valuta;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ImportoClassico)) {
            return false;
        }
        ImportoClassico that = (ImportoClassico) other;
        return centesimi == that.centesimi && valuta.equals(that.valuta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(centesimi, valuta);
    }

    @Override
    public String toString() {
        return "ImportoClassico[centesimi=" + centesimi + ", valuta=" + valuta + "]";
    }
}
