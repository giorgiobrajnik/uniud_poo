package it.universita.esse3.serializzazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class ReportSerializzabile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String titolo;
    private final List<String> voci = new ArrayList<>();
    // CONTROESEMPIO: i campi transient non vengono copiati
    private transient String cache;

    public ReportSerializzabile(String titolo) {
        this.titolo = titolo;
    }

    public void aggiungi(String voce) {
        voci.add(voce);
    }

    public void calcolaCache() {
        cache = "calcolata: " + voci.size() + " voci";
    }

    public String titolo() {
        return titolo;
    }

    public List<String> voci() {
        return voci;
    }

    public String cache() {
        return cache;
    }
}
