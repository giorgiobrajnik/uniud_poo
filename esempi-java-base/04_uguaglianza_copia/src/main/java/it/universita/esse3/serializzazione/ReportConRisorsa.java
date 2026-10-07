package it.universita.esse3.serializzazione;

import java.io.Serializable;

// CONTROESEMPIO: un campo non serializzabile (qui una risorsa) fa fallire la copia
public final class ReportConRisorsa implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String titolo;
    private final Object risorsa = new Object();

    public ReportConRisorsa(String titolo) {
        this.titolo = titolo;
    }

    public String titolo() {
        return titolo;
    }

    public Object risorsa() {
        return risorsa;
    }
}
