package it.universita.esse3.visibilita;

import java.time.LocalDate;

public class Appello {
    private final String codiceCorso;
    private final LocalDate dataProva;
    private int postiDisponibili;

    public Appello(String codiceCorso, LocalDate dataProva, int postiDisponibili) {
        if (postiDisponibili < 0) {
            throw new IllegalArgumentException("I posti non possono essere negativi");
        }
        this.codiceCorso = codiceCorso;
        this.dataProva = dataProva;
        this.postiDisponibili = postiDisponibili;
    }

    public boolean puoPrenotarsi(Studente s) {
        return CalcoloPostiDisponibili.almenoUnPosto(postiDisponibili) && !s.haDebiti();
    }

    public void prenotaStudente(Studente s) {
        if (puoPrenotarsi(s)) {
            postiDisponibili--;
        }
    }

    public String getCodiceCorso() {
        return codiceCorso;
    }

    public LocalDate getDataProva() {
        return dataProva;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }
}
