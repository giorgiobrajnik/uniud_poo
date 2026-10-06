package it.universita.esse3.blocchi;

import java.util.ArrayList;
import java.util.List;

// CONTROESEMPIO: compila, ma l'indentazione inganna (non riformattare)
// @formatter:off
public class ControlloSogliaSenzaGraffe {
    public static List<String> controlla(int creditiAcquisiti) {
        List<String> azioni = new ArrayList<>();
        if (creditiAcquisiti < 12)
            azioni.add("messaggio sotto soglia");
            azioni.add("invio numero sostegno"); // eseguita SEMPRE
        return azioni;
    }
}
// @formatter:on
