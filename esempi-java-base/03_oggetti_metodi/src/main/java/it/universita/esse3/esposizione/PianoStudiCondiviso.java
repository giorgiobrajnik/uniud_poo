package it.universita.esse3.esposizione;

import java.util.List;

// CONTROESEMPIO: campo final, ma la lista del chiamante resta condivisa
public class PianoStudiCondiviso {
    private final List<String> codiciInsegnamento;

    public PianoStudiCondiviso(List<String> codiciInsegnamento) {
        this.codiciInsegnamento = codiciInsegnamento;
    }

    public int numeroInsegnamenti() {
        return codiciInsegnamento.size();
    }
}
