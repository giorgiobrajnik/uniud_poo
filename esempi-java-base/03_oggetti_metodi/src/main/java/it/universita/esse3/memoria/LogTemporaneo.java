package it.universita.esse3.memoria;

import java.util.ArrayList;
import java.util.List;

// CONTROESEMPIO: gli elementi restano raggiungibili per tutta la vita del processo
public class LogTemporaneo {
    private static final List<String> logTemporaneo = new ArrayList<>();

    public static void registra(String messaggio) {
        logTemporaneo.add(messaggio);
    }

    public static int dimensione() {
        return logTemporaneo.size();
    }
}
