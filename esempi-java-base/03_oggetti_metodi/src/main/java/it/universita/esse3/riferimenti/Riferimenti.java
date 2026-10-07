package it.universita.esse3.riferimenti;

public class Riferimenti {
    // new crea sempre un oggetto distinto, anche con argomenti identici
    public static boolean newCreaOggettiDistinti() {
        Studente a = new Studente("123456");
        Studente b = new Studente("123456");
        return a == b;
    }

    public static int creditiDiLucaDopoModificaDiAnna() {
        Studente anna = new Studente("100001");
        Studente luca = new Studente("100002");
        anna.aggiungiCrediti(6);
        return luca.getCrediti();
    }

    // CONTROESEMPIO di "copia": l'assegnamento copia il riferimento, non l'oggetto
    public static boolean assegnamentoCondivideLOggetto() {
        Studente s1 = new Studente("123456");
        Studente s2 = s1;
        return s1 == s2;
    }

    public static int creditiDiS1DopoModificaViaS2() {
        Studente s1 = new Studente("123456");
        Studente s2 = s1;
        s2.aggiungiCrediti(6);
        return s1.getCrediti();
    }

    public static Studente nessunOggetto() {
        return null;
    }

    // CONTROESEMPIO: con s == null lancia NullPointerException
    public static String matricolaDi(Studente s) {
        return s.getMatricola();
    }
}
