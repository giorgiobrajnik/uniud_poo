package it.universita.esse3.omonimie.esami;

// Esame come attivita didattica da seguire
public class Esame {
    private final String codiceEsame;
    private final String materia;
    private final int creditiForniti;

    public Esame(String codiceEsame, String materia, int creditiForniti) {
        this.codiceEsame = codiceEsame;
        this.materia = materia;
        this.creditiForniti = creditiForniti;
    }

    public String getCodiceEsame() {
        return codiceEsame;
    }

    public String getMateria() {
        return materia;
    }

    public int getCreditiForniti() {
        return creditiForniti;
    }
}
