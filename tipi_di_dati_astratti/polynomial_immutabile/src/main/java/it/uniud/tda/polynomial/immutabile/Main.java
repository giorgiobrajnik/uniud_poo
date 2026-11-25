package it.uniud.tda.polynomial.immutabile;

/** Esegue un piccolo scenario d'uso del polinomio immutabile. */
public final class Main {
    /**
     * Impedisce l'istanziazione della classe dimostrativa priva di stato.
     * @post È costruita un'istanza senza stato osservabile.
     */
    private Main() { }

    /**
     * Costruisce e combina polinomi senza modificare gli operandi.
     *
     * @param args non utilizzati
     * @post Stampa esempi di creazione, osservazione e produzione di nuovi valori.
     */
    public static void main(String[] args) {
        Polynomial p = Polynomial.of(1, 2, 3);
        Polynomial q = Polynomial.monomial(-3, 2)
                .withCoefficient(0, 4);

        System.out.println("p(x)       = " + p);
        System.out.println("q(x)       = " + q);
        System.out.println("p + q      = " + p.add(q));
        System.out.println("p * q      = " + p.multiply(q));
        System.out.println("p'(x)      = " + p.derivative());
        System.out.println("p(2)       = " + p.evaluate(2));
        System.out.println("p è invariato = " + p);
    }
}
