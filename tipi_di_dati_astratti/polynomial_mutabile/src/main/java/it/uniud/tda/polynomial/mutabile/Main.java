package it.uniud.tda.polynomial.mutabile;

/** Mostra modificatori, copia e alias di un polinomio mutabile. */
public final class Main {
    /**
     * Impedisce l'istanziazione della classe dimostrativa priva di stato.
     * @post È costruita un'istanza senza stato osservabile.
     */
    private Main() { }

    /**
     * Esegue uno scenario dimostrativo.
     *
     * @param args non utilizzati
     * @post Stampa gli effetti osservabili dei modificatori e della copia.
     */
    public static void main(String[] args) {
        MutablePolynomial accumulator = MutablePolynomial.of(1, 2);
        MutablePolynomial alias = accumulator;
        MutablePolynomial snapshot = accumulator.copy();

        accumulator.setCoefficient(2, 3);
        accumulator.addInPlace(MutablePolynomial.of(4, -2));

        System.out.println("accumulatore = " + accumulator);
        System.out.println("alias        = " + alias + " (osserva le modifiche)");
        System.out.println("copia        = " + snapshot + " (indipendente)");
        accumulator.multiplyInPlace(accumulator);
        System.out.println("quadrato     = " + accumulator);
    }
}
