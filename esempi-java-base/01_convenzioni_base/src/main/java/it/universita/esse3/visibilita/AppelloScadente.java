package it.universita.esse3.visibilita;

import java.time.LocalDate;

// CONTROESEMPIO: campi pubblici e nessun controllo delle precondizioni
public class AppelloScadente {
    public String codiceCorso;
    public LocalDate dataProva;
    public int postiDisponibili;

    public void prenotaStudente(Studente s) {
        postiDisponibili--;
    }
}
