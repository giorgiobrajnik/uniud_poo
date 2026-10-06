package it.universita.esse3.overflow;

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
