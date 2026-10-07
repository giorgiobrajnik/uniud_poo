package it.universita.esse3.assegnamento;

public class VotoDaEsito {
    // Entrambi i rami assegnano: la lettura e' ammessa dal compilatore
    public static int voto(boolean esameSuperato) {
        int voto;

        if (esameSuperato) {
            voto = 30;
        } else {
            voto = 18;
        }

        return voto;
    }
}
