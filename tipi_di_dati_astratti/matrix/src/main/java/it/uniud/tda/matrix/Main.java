package it.uniud.tda.matrix;

import java.math.BigInteger;
import java.util.Optional;

/** Esegue un esempio di algebra matriciale immutabile. */
public final class Main {
    /**
     * Impedisce l'istanziazione della classe dimostrativa priva di stato.
     * @post È costruita un'istanza senza stato osservabile.
     */
    private Main() { }

    /**
     * Costruisce, osserva e combina matrici.
     *
     * @param args non utilizzati
     * @post Stampa risultati e un esempio di sentinella Optional.
     */
    public static void main(String[] args) {
        Matrix a = Matrix.of(new int[][]{
                {1, 2, 3},
                {4, 5, 6}
        });
        Matrix b = a.transpose();

        System.out.println("A       = " + a);
        System.out.println("A^T     = " + b);
        System.out.println("A A^T   = " + a.multiply(b));

        Optional<BigInteger> trace = a.trace();
        System.out.println("tr(A)   = " + (trace.isPresent() ? trace.get() : "non definita"));

        Optional<BigInteger> entry = a.tryEntry(9, 9);
        System.out.println("A(9,9)  = " + (entry.isPresent() ? entry.get() : "indice assente"));
    }
}
