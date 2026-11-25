package it.uniud.tda.matrix;

/** Segnala che le dimensioni di due matrici sono incompatibili con un'operazione. */
public final class DimensionMismatchException extends IllegalArgumentException {
    /**
     * Crea l'errore con una spiegazione delle dimensioni incompatibili.
     *
     * @param message descrizione del fallimento
     * @post L'eccezione contiene il messaggio ricevuto.
     */
    public DimensionMismatchException(String message) {
        super(message);
    }
}
