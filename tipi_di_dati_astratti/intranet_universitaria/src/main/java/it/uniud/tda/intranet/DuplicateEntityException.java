package it.uniud.tda.intranet;

/** Segnala il tentativo di registrare due entità con lo stesso identificatore. */
public final class DuplicateEntityException extends IllegalStateException {
    /**
     * Crea l'errore.
     * @param message descrizione del duplicato
     * @post L'eccezione contiene message.
     */
    public DuplicateEntityException(String message) { super(message); }
}
