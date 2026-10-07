package it.universita.esse3.array;

import it.universita.esse3.riferimenti.Studente;
import java.util.Arrays;

public class Array {
    public static int[] valoriDiDefault() {
        return new int[3];
    }

    // CONTROESEMPIO di copia: b e a sono lo stesso array
    public static int primoElementoDiADopoModificaDiBAlias() {
        int[] a = {18, 24, 30};
        int[] b = a;
        b[0] = 27;
        return a[0];
    }

    public static int primoElementoDiADopoModificaDiBClone() {
        int[] a = {18, 24, 30};
        int[] b = a.clone();
        b[0] = 27;
        return a[0];
    }

    public static int primoElementoDiADopoModificaDiBCopyOf() {
        int[] a = {18, 24, 30};
        int[] b = Arrays.copyOf(a, a.length);
        b[0] = 27;
        return a[0];
    }

    // CONTROESEMPIO: la copia di un array di riferimenti e' superficiale
    public static int creditiDelPrimoStudenteDopoCopiaDellArray() {
        Studente[] studenti = {new Studente("100001")};
        Studente[] copia = studenti.clone();
        copia[0].aggiungiCrediti(6);
        return studenti[0].getCrediti();
    }
}
