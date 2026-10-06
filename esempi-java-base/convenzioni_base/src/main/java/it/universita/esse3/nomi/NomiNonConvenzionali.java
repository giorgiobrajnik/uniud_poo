package it.universita.esse3.nomi;

// CONTROESEMPIO: sintatticamente valido, ma contrario alle convenzioni
public class NomiNonConvenzionali {
    public static int cfuConNomeNonConvenzionale() {
        int NumeroCFU = 6;
        return NumeroCFU;
    }

    // Java distingue maiuscole e minuscole: tre identificatori diversi
    public static int sommaIdentificatoriDiversi() {
        int studente = 1;
        int Studente = 10;
        int STUDENTE = 100;
        return studente + Studente + STUDENTE;
    }
}
