package it.uniud.tda.intranet;

/** Segnala una verbalizzazione non consentita. */
public final class GradeRegistrationException extends IllegalStateException {
    /**
     * Crea l'errore.
     * @param message regola di verbalizzazione violata
     * @post L'eccezione contiene message.
     */
    public GradeRegistrationException(String message) { super(message); }
}
