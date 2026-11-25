package it.uniud.tda.intranet;

/**
 * Missione: rappresentare una matricola valida come valore.
 * Stato astratto e invariante di classe: una sequenza di esattamente sei cifre.
 */
public record StudentId(String value) {
    /* RI: value soddisfa [0-9]{6}. AF: value è la matricola astratta. */

    /**
     * Crea una matricola.
     * @param value testo della matricola
     * @throws IllegalArgumentException se il formato non è valido
     * @post La matricola astratta coincide con value.
     */
    public StudentId {
        if (value == null || !value.matches("[0-9]{6}")) {
            throw new IllegalArgumentException("Matricola non valida: " + value);
        }
    }

    /**
     * Osserva la sequenza della matricola.
     * @return le sei cifre
     * @post This è invariato.
     */
    public String value() {
        return value;
    }
}
