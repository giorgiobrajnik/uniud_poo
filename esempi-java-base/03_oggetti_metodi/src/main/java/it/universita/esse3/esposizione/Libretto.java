package it.universita.esse3.esposizione;

import java.util.ArrayList;
import java.util.List;

public class Libretto {
    private final List<Integer> voti = new ArrayList<>();

    public void registra(int voto) {
        voti.add(voto);
    }

    public List<Integer> getVoti() {
        return List.copyOf(voti);
    }

    public int numeroVoti() {
        return voti.size();
    }
}
