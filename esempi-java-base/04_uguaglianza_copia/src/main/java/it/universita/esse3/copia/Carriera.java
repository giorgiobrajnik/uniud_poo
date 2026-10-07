package it.universita.esse3.copia;

import java.util.ArrayList;
import java.util.List;

/**
 * Livelli di copia di una carriera: copia superficiale (la lista è condivisa), copia con
 * nuova lista (gli elementi sono condivisi) e copia profonda (anche gli esami sono
 * duplicati). Assegnare il riferimento ({@code c2 = c1}) non crea alcuna copia.
 */
public final class Carriera {
    private final String matricola;
    private final List<EsameSuperato> esami;

    public Carriera(String matricola) {
        this(matricola, new ArrayList<>());
    }

    private Carriera(String matricola, List<EsameSuperato> esami) {
        this.matricola = matricola;
        this.esami = esami;
    }

    public String matricola() {
        return matricola;
    }

    public void registraEsame(EsameSuperato esame) {
        esami.add(esame);
    }

    public int numeroEsami() {
        return esami.size();
    }

    public EsameSuperato esame(int indice) {
        return esami.get(indice);
    }

    // CONTROESEMPIO: nuovo oggetto esterno, ma la lista è la stessa
    public Carriera copiaSuperficiale() {
        return new Carriera(matricola, esami);
    }

    // CONTROESEMPIO: nuova lista, ma gli EsameSuperato (mutabili) sono gli stessi
    public Carriera copiaConNuovaLista() {
        return new Carriera(matricola, new ArrayList<>(esami));
    }

    public Carriera copiaProfonda() {
        List<EsameSuperato> copie = new ArrayList<>();
        for (EsameSuperato esame : esami) {
            copie.add(new EsameSuperato(esame));
        }
        return new Carriera(matricola, copie);
    }
}
