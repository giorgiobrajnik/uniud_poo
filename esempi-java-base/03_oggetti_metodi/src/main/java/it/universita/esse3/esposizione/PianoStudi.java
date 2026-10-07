package it.universita.esse3.esposizione;

import java.util.List;

public class PianoStudi {
    private final List<String> codiciInsegnamento;

    // Copia difensiva
    public PianoStudi(List<String> codiciInsegnamento) {
        this.codiciInsegnamento = List.copyOf(codiciInsegnamento);
    }

    public int numeroInsegnamenti() {
        return codiciInsegnamento.size();
    }
}
