package it.universita.esse3.omonimie.appelli;

import java.time.LocalDateTime;

// Prenotazione di uno studente a un appello
public class Prenotazione {
    private final String matricola;
    private final String codiceEsame;
    private final LocalDateTime dataPrenotazione;

    public Prenotazione(String matricola, String codiceEsame, LocalDateTime dataPrenotazione) {
        this.matricola = matricola;
        this.codiceEsame = codiceEsame;
        this.dataPrenotazione = dataPrenotazione;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getCodiceEsame() {
        return codiceEsame;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }
}
