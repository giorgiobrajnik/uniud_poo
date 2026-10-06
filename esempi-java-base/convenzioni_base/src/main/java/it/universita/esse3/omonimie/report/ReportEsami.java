package it.universita.esse3.omonimie.report;

import it.universita.esse3.omonimie.esami.Esame;

public class ReportEsami {
    // Esame importato e' esami.Esame; l'altro tipo omonimo e' qualificato per
    // esteso
    public String generaReport(it.universita.esse3.omonimie.appelli.Esame esameAppello) {
        Esame esameDidattico = caricaEsameDidattico(esameAppello.getCodiceEsame());
        return "Materia: " + esameDidattico.getMateria() + ", aula: " + esameAppello.getAula();
    }

    private Esame caricaEsameDidattico(String codiceEsame) {
        return new Esame(codiceEsame, "Programmazione orientata agli oggetti", 6);
    }
}
