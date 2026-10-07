package it.universita.esse3.valori;

import java.util.List;

// CONTROESEMPIO: componenti final, ma la lista del chiamante resta condivisa e modificabile
public record PianoStudiSuperficiale(String matricola, List<String> corsi) {
}
