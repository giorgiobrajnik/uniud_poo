package it.universita.esse3.parametri;

import it.universita.esse3.riferimenti.Studente;

public class PassaggioParametri {
    private static void incrementa(int x) {
        x++;
    }

    // Il parametro primitivo riceve una copia del valore
    public static int primitivoNelChiamanteNonCambia() {
        int n = 10;
        incrementa(n);
        return n;
    }

    public static void assegnaCrediti(Studente s) {
        s.aggiungiCrediti(6);
    }

    // Il parametro riceve una copia del riferimento: l'oggetto condiviso cambia
    public static int creditiDopoAssegnaCrediti() {
        Studente studente = new Studente("123456");
        assegnaCrediti(studente);
        return studente.getCrediti();
    }

    private static void sostituisci(Studente s) {
        s = new Studente("999999");
    }

    // CONTROESEMPIO di "passaggio per riferimento": riassegnare il parametro non tocca il chiamante
    public static String matricolaDopoSostituisci() {
        Studente originale = new Studente("123456");
        sostituisci(originale);
        return originale.getMatricola();
    }

    public static void accreditaCfu(Studente s, int cfu) {
        s.aggiungiCrediti(cfu);
        cfu = 0;
    }

    public static int[] creditiECfuDopoAccreditaCfu() {
        Studente studente = new Studente("145732");
        int crediti = 6;
        accreditaCfu(studente, crediti);
        return new int[] {studente.getCrediti(), crediti};
    }
}
