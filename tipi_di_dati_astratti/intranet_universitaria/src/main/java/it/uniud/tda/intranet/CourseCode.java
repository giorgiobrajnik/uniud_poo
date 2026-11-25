package it.uniud.tda.intranet;

import java.util.Locale;

/**
 * Missione: identificare un insegnamento con un codice normalizzato.
 * Stato astratto: una sigla da 2-5 lettere e tre cifre.
 */
public record CourseCode(String value) {
    /* RI: value soddisfa [A-Z]{2,5}[0-9]{3}. AF: value è il codice astratto. */

    /**
     * Crea un codice, normalizzando le lettere in maiuscolo.
     * @param value codice testuale
     * @throws IllegalArgumentException se il formato non è valido
     * @post Il codice astratto è la versione maiuscola di value.
     */
    public CourseCode {
        String normalized = value == null ? null : value.toUpperCase(Locale.ITALIAN);
        if (normalized == null || !normalized.matches("[A-Z]{2,5}[0-9]{3}")) {
            throw new IllegalArgumentException("Codice corso non valido: " + value);
        }
        value = normalized;
    }

    /**
     * Osserva il codice normalizzato.
     * @return codice in maiuscolo
     * @post This è invariato.
     */
    public String value() {
        return value;
    }
}
