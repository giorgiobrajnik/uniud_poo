package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Missione: rappresentare un numero valido di crediti formativi (CFU).
 * Invariante di classe: il valore è compreso tra 1 e 30.
 */
@EqualsAndHashCode
@ToString
public final class CreditUnits {
    /* RI: 1 <= value <= 30. AF: value è il numero astratto di CFU. */
    private final int value;

    /**
     * Crea una quantità di crediti.
     * @param value numero di CFU
     * @throws IllegalArgumentException se value non è in [1,30]
     * @post Il numero astratto di CFU è value.
     */
    public CreditUnits(int value) {
        if (value < 1 || value > 30) {
            throw new IllegalArgumentException("CFU fuori intervallo: " + value);
        }
        this.value = value;
    }

    /**
     * Osserva i crediti.
     * @return valore tra 1 e 30
     * @post This è invariato.
     */
    public int value() {
        return value;
    }
}
