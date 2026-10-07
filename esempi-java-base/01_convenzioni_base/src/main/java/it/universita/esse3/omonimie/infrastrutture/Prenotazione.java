package it.universita.esse3.omonimie.infrastrutture;

import java.time.LocalDate;

// Prenotazione di uno spazio fisico
public class Prenotazione {
    private final String aula;
    private final LocalDate data;
    private final String docente;

    public Prenotazione(String aula, LocalDate data, String docente) {
        this.aula = aula;
        this.data = data;
        this.docente = docente;
    }

    public String getAula() {
        return aula;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDocente() {
        return docente;
    }
}
