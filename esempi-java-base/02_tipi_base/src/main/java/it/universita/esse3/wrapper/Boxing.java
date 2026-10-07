package it.universita.esse3.wrapper;

import java.util.ArrayList;
import java.util.List;

public class Boxing {
    public static Integer boxingEsplicito(int cfu) {
        return Integer.valueOf(cfu);
    }

    public static Integer autoboxing(int cfu) {
        return cfu;
    }

    public static int unboxing(Integer voto) {
        return voto;
    }

    public static List<Integer> votiConAutoboxing() {
        List<Integer> voti = new ArrayList<>();
        voti.add(30);
        voti.add(27);
        return voti;
    }

    // CONTROESEMPIO: compila, ma con voto == null l'unboxing lancia NullPointerException
    public static int votoNumerico(Integer voto) {
        int votoNumerico = voto;
        return votoNumerico;
    }

    public static int votoNumericoODefault(Integer voto, int valoreDefault) {
        if (voto == null) {
            return valoreDefault;
        }
        return voto;
    }
}
