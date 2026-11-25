package it.uniud.tda.intranet;

/** Segnala che un comando nomina un'entità non registrata. */
public final class EntityNotFoundException extends IllegalArgumentException {
    /**
     * Crea l'errore.
     * @param message descrizione dell'entità assente
     * @post L'eccezione contiene message.
     */
    public EntityNotFoundException(String message) { super(message); }
}
