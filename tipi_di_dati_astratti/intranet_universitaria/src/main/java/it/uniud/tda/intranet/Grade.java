package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;

/**
 * Missione: rappresentare un voto universitario verbalizzabile.
 * Stato astratto: valore in [18,30] ed eventuale lode, ammessa solo col 30.
 */
@EqualsAndHashCode
public final class Grade {
    /* RI: 18 <= value <= 30; honors implica value == 30. AF: coppia voto/lode. */
    private final int value;
    private final boolean honors;

    /**
     * Costruisce un voto dalla coppia concreta valore/lode.
     *
     * @param value valore numerico
     * @param honors presenza della lode
     * @throws IllegalArgumentException se la coppia viola il RI
     * @post I campi coincidono con gli argomenti e il RI vale.
     */
    private Grade(int value, boolean honors) {
        if (value < 18 || value > 30) {
            throw new IllegalArgumentException("Voto fuori intervallo: " + value);
        }
        if (honors && value != 30) {
            throw new IllegalArgumentException("La lode richiede 30");
        }
        this.value = value;
        this.honors = honors;
    }

    /**
     * Crea un voto senza lode.
     * @param value valore numerico richiesto
     * @return il voto richiesto senza lode
     * @throws IllegalArgumentException se value è fuori intervallo
     * @post result.numericValue() == value e !result.hasHonors().
     */
    public static Grade of(int value) {
        return new Grade(value, false);
    }

    /**
     * Crea il voto 30 e lode.
     * @return trenta con lode
     * @post result.numericValue() == 30 e result.hasHonors().
     */
    public static Grade withHonors() {
        return new Grade(30, true);
    }

    /**
     * Osserva il valore numerico usato per la media.
     * @return intero tra 18 e 30; la lode vale numericamente 30
     * @post This è invariato.
     */
    public int numericValue() {
        return value;
    }

    /**
     * Osserva la lode.
     * @return true se e solo se il voto è trenta e lode
     * @post This è invariato.
     */
    public boolean hasHonors() {
        return honors;
    }

    /**
     * Verifica se questo voto soddisfa una soglia minima.
     * @param minimum soglia
     * @return true se il valore numerico è almeno quello di minimum
     * @throws NullPointerException se minimum è null
     * @post This e minimum sono invariati.
     */
    public boolean isAtLeast(Grade minimum) {
        if (minimum == null) throw new NullPointerException("minimum");
        return value >= minimum.value;
    }

    /**
     * Descrive il voto.
     * @return numero seguito da " e lode" quando presente
     * @post This è invariato.
     */
    @Override
    public String toString() {
        return value + (honors ? " e lode" : "");
    }
}
