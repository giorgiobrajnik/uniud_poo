package it.universita.esse3.difensiva;

import java.util.ArrayList;
import java.util.List;

public class AppelloProtetto {
    private final List<String> aule;
    private final List<String> iscritti = new ArrayList<>();
    private boolean aperto = true;

    public AppelloProtetto(List<String> aule) {
        this.aule = List.copyOf(aule);
    }

    public int numeroAule() {
        return aule.size();
    }

    public void chiudiPrenotazioni() {
        aperto = false;
    }

    public void prenota(String matricola) {
        if (!aperto) {
            throw new IllegalStateException("prenotazioni chiuse");
        }
        if (iscritti.contains(matricola)) {
            throw new IllegalStateException("già prenotato");
        }
        iscritti.add(matricola);
    }

    public List<String> iscritti() {
        return List.copyOf(iscritti);
    }
}
