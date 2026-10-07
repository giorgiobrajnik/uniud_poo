package it.universita.esse3.overflow;

/**
 * Overflow degli interi.
 * Controesempio: {@code Integer.MAX_VALUE + 1} non genera eccezioni e vale
 * {@code Integer.MIN_VALUE}, perche' il risultato "gira" all'estremo opposto
 * dell'intervallo (complemento a due). Esempi corretti: {@code Math.addExact} e
 * {@code Math.multiplyExact} lanciano {@link ArithmeticException} quando il
 * risultato non
 * e' rappresentabile, e un tipo piu' ampio ({@code long}) contiene il
 * risultato. Conviene
 * scegliere il tipo in base all'intervallo necessario, non perche' "e' un
 * numero".
 */
public class EsempioOverflow {
    public static void main(String[] args) {
        int massimo = Integer.MAX_VALUE;

        System.out.println("--- Controesempio: overflow silenzioso ---");
        System.out.println("MAX_VALUE + 1 = " + Overflow.incrementaSenzaControllo(massimo));

        System.out.println("--- Esempio: operazioni *Exact ---");
        try {
            Overflow.incrementaConControllo(massimo);
        } catch (ArithmeticException e) {
            System.out.println("addExact: " + e.getMessage());
        }
        try {
            Overflow.moltiplicaConControllo(100_000, 100_000);
        } catch (ArithmeticException e) {
            System.out.println("multiplyExact: " + e.getMessage());
        }

        System.out.println("--- Esempio: tipo piu ampio ---");
        System.out.println("MAX_VALUE + 1L = " + Overflow.incrementaComeLong(massimo));
    }
}
