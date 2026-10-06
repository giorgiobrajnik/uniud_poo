package it.universita.esse3.blocchi;

import java.util.ArrayList;
import java.util.List;

public class ControlloSoglia {
    public static final int SOGLIA_CFU = 12;

    // Restituisce le azioni eseguite, per poterle osservare nei test
    public static List<String> controlla(int creditiAcquisiti) {
        List<String> azioni = new ArrayList<>();
        if (creditiAcquisiti < SOGLIA_CFU) {
            azioni.add("messaggio sotto soglia");
            azioni.add("invio numero sostegno");
        }
        return azioni;
    }
}
