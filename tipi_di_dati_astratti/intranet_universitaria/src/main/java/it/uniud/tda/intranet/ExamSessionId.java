package it.uniud.tda.intranet;

/** Identificatore valore non vuoto di un appello. */
public record ExamSessionId(String value) {
    /* RI: value non null e non blank. AF: value identifica astrattamente l'appello. */

    /**
     * Crea un identificatore di appello.
     * @param value testo dell'identificatore
     * @throws IllegalArgumentException se value è null o blank
     * @post Il valore astratto coincide con value senza spazi esterni.
     */
    public ExamSessionId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Identificatore appello vuoto");
        }
        value = value.trim();
    }

    /**
     * Osserva l'identificatore.
     * @return identificatore non vuoto
     * @post This è invariato.
     */
    public String value() {
        return value;
    }
}
