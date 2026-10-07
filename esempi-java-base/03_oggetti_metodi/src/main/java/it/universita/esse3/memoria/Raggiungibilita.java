package it.universita.esse3.memoria;

import it.universita.esse3.riferimenti.Studente;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class Raggiungibilita {
    // Finche' esiste un riferimento forte, l'oggetto non viene raccolto
    public static boolean oggettoRaggiungibileRestaVivo() {
        Studente s = new Studente("123456");
        WeakReference<Studente> debole = new WeakReference<>(s);

        System.gc();

        boolean vivo = debole.get() != null;
        Reference.reachabilityFence(s);
        return vivo;
    }

    // Solo un candidato: il momento della raccolta non e' garantito, quindi non e' testato
    public static boolean oggettoNonRaggiungibileRaccoltoDopoGc() {
        Studente s = new Studente("123456");
        WeakReference<Studente> debole = new WeakReference<>(s);
        s = null;

        System.gc();
        return debole.get() == null;
    }
}
