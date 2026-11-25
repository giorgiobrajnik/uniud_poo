package it.uniud.tda.intranet;

/** Segnala che una richiesta di iscrizione o cancellazione non è ammissibile. */
public final class EnrollmentException extends IllegalStateException {
    /**
     * Crea l'errore.
     * @param message regola di iscrizione violata
     * @post L'eccezione contiene message.
     */
    public EnrollmentException(String message) { super(message); }
}
