package it.universita.esse3.copia;

import java.time.LocalDate;

// Entità condivisa: ha una sola identità amministrativa, che può cambiare data
public class AppelloEsame {
    private final String codice;
    private LocalDate data;

    public AppelloEsame(String codice, LocalDate data) {
        this.codice = codice;
        this.data = data;
    }

    public AppelloEsame(AppelloEsame original) {
        this(original.codice, original.data);
    }

    public String codice() {
        return codice;
    }

    public LocalDate data() {
        return data;
    }

    public void sposta(LocalDate nuovaData) {
        this.data = nuovaData;
    }
}
