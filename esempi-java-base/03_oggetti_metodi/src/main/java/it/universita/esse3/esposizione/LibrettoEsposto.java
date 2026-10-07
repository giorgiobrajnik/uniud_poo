package it.universita.esse3.esposizione;

import java.util.ArrayList;
import java.util.List;

// CONTROESEMPIO: private e final non bastano, il getter espone la rappresentazione
public class LibrettoEsposto {
    private final List<Integer> voti = new ArrayList<>();

    public List<Integer> getVoti() {
        return voti;
    }

    public int numeroVoti() {
        return voti.size();
    }
}
