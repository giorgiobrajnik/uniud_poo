package it.universita.esse3.finali;

import java.util.ArrayList;
import java.util.List;

// final blocca la variabile, non l'oggetto a cui punta
public class FinalNeiRiferimenti {
    public static int cfuLocaleFinale() {
        final int cfuEsame = 6;
        return cfuEsame;
    }

    // Un'unica assegnazione per ogni percorso di esecuzione
    public static int votoAssegnatoUnaSolaVolta(boolean esameSuperato) {
        final int voto;
        if (esameSuperato) {
            voto = 30;
        } else {
            voto = 18;
        }
        return voto;
    }

    public static int parametroFinale(final int voto) {
        return voto;
    }

    // CONTROESEMPIO di immutabilita: il riferimento e' final, la lista no
    public static int elementiDiUnaListaFinale() {
        final List<String> appelli = new ArrayList<>();
        appelli.add("POO-2026-01");
        return appelli.size();
    }

    public static int votoDiUnArrayFinaleModificato() {
        final int[] voti = {18, 24, 30};
        voti[0] = 27;
        return voti[0];
    }

    public static String noteDiUnStringBuilderFinale() {
        final StringBuilder note = new StringBuilder();
        note.append("prima riga");
        return note.toString();
    }
}
