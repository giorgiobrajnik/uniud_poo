package it.universita.esse3.valori;

import java.util.List;
import java.util.Objects;

public record PianoStudi(String matricola, List<String> corsi) {
    public PianoStudi {
        Objects.requireNonNull(matricola);
        corsi = List.copyOf(corsi);
    }
}
