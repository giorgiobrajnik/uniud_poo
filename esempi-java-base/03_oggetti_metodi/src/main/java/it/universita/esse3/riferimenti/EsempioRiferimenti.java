package it.universita.esse3.riferimenti;

/**
 * Oggetti, riferimenti e {@code null}.
 * Ogni esecuzione di {@code new} crea un oggetto distinto, anche con argomenti identici:
 * due {@code Studente} con la stessa matricola non sono lo stesso oggetto ({@code ==} e'
 * {@code false}) e le loro modifiche sono indipendenti. Controesempio di "copia":
 * {@code s2 = s1} copia il riferimento, non l'oggetto, quindi modificare {@code s2}
 * modifica anche {@code s1}. Una variabile riferimento puo' valere {@code null}: usarla
 * come se identificasse un oggetto lancia una {@link NullPointerException}.
 */
public class EsempioRiferimenti {
    public static void main(String[] args) {
        System.out.println("--- new crea oggetti distinti ---");
        System.out.println("new Studente(\"123456\") == new Studente(\"123456\")? " + Riferimenti.newCreaOggettiDistinti());
        System.out.println("Crediti di luca dopo aver modificato anna: " + Riferimenti.creditiDiLucaDopoModificaDiAnna());

        System.out.println("--- Controesempio: l'assegnamento non copia l'oggetto ---");
        System.out.println("s2 = s1; s1 == s2? " + Riferimenti.assegnamentoCondivideLOggetto());
        System.out.println("Crediti di s1 dopo s2.aggiungiCrediti(6): " + Riferimenti.creditiDiS1DopoModificaViaS2());

        System.out.println("--- null ---");
        Studente nessuno = Riferimenti.nessunOggetto();
        System.out.println("riferimento = " + nessuno);
        try {
            Riferimenti.matricolaDi(nessuno);
        } catch (NullPointerException e) {
            System.out.println("matricolaDi(null): NullPointerException");
        }
    }
}
