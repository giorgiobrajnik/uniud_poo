package it.universita.esse3.overflow;

public class Overflow {
    // CONTROESEMPIO: nessuna eccezione, il risultato "gira" all'estremo opposto
    public static int incrementaSenzaControllo(int valore) {
        return valore + 1;
    }

    public static int incrementaConControllo(int valore) {
        return Math.addExact(valore, 1);
    }

    public static int moltiplicaConControllo(int a, int b) {
        return Math.multiplyExact(a, b);
    }

    // Con un tipo piu ampio il risultato e' rappresentabile
    public static long incrementaComeLong(int valore) {
        return valore + 1L;
    }
}
