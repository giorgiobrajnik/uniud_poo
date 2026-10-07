package it.universita.esse3.array;

import java.util.Arrays;

/**
 * Array come oggetti: valori di default, condivisione e copia.
 * {@code new int[3]} contiene {@code [0, 0, 0]}. Controesempio: con
 * {@code b = a} le due
 * variabili riferiscono lo stesso array e una modifica via {@code b} e'
 * visibile da
 * {@code a}. Per un array distinto si usano {@code clone()} o
 * {@code Arrays.copyOf}.
 * Controesempio ulteriore: se l'array contiene riferimenti ({@code Studente[]})
 * la copia
 * e' superficiale, quindi copia e originale condividono gli stessi oggetti
 * {@code Studente}.
 */
public class EsempioArray {
    public static void main(String[] args) {
        System.out.println("--- Valori di default ---");
        System.out.println("new int[3] = " + Arrays.toString(Array.valoriDiDefault()));

        System.out.println("--- Controesempio: b = a condivide l'array ---");
        System.out.println("a[0] dopo b[0] = 27: " + Array.primoElementoDiADopoModificaDiBAlias());

        System.out.println("--- Copie vere ---");
        System.out.println("clone():  a[0] = " + Array.primoElementoDiADopoModificaDiBClone());
        System.out.println("copyOf(): a[0] = " + Array.primoElementoDiADopoModificaDiBCopyOf());

        System.out.println("--- Controesempio: array di riferimenti ---");
        System.out.println("Crediti del primo studente dopo modifica della copia: "
                + Array.creditiDelPrimoStudenteDopoCopiaDellArray());
    }
}
