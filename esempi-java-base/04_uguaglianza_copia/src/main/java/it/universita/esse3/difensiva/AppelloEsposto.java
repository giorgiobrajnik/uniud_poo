package it.universita.esse3.difensiva;

import java.util.ArrayList;
import java.util.List;

// CONTROESEMPIO: conserva la lista ricevuta ed espone quella interna dal getter
public class AppelloEsposto {
    private final List<String> aule;
    private final List<String> iscritti = new ArrayList<>();
    private boolean aperto = true;

    public AppelloEsposto(List<String> aule) {
        this.aule = aule;
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

    public List<String> getIscritti() {
        return iscritti;
    }
}
