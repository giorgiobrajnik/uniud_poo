package it.universita.esse3.valori;

import java.util.Objects;

public record Importo(long centesimi, String valuta) {
    public Importo {
        if (centesimi < 0) {
            throw new IllegalArgumentException("importo negativo");
        }
        Objects.requireNonNull(valuta);
    }

    public Importo somma(Importo altro) {
        if (!valuta.equals(altro.valuta)) {
            throw new IllegalArgumentException("valute diverse");
        }
        return new Importo(centesimi + altro.centesimi, valuta);
    }
}
