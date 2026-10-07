package it.universita.esse3.omonimie.appelli;

import java.time.LocalDate;

// Esame come istanza di prova che uno studente sostiene
public class Esame {
    private final String codiceEsame;
    private final LocalDate data;
    private final String aula;

    public Esame(String codiceEsame, LocalDate data, String aula) {
        this.codiceEsame = codiceEsame;
        this.data = data;
        this.aula = aula;
    }

    public String getCodiceEsame() {
        return codiceEsame;
    }

    public LocalDate getData() {
        return data;
    }

    public String getAula() {
        return aula;
    }
}
