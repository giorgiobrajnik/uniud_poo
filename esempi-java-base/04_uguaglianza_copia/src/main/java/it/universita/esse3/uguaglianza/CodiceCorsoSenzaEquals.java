package it.universita.esse3.uguaglianza;

import java.util.Objects;

// CONTROESEMPIO: la JVM non può indovinare quali campi definiscono l'uguaglianza
public final class CodiceCorsoSenzaEquals {
    private final String codice;

    public CodiceCorsoSenzaEquals(String codice) {
        this.codice = Objects.requireNonNull(codice);
    }

    public String valore() {
        return codice;
    }
}
