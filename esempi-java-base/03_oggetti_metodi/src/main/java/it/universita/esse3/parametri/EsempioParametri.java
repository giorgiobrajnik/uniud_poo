package it.universita.esse3.parametri;

import java.util.Arrays;

/**
 * Passaggio dei parametri: Java passa sempre per valore.
 * Un parametro primitivo riceve una copia del valore, quindi la variabile del chiamante
 * non cambia. Un parametro riferimento riceve una copia del riferimento: il metodo puo'
 * modificare l'oggetto condiviso e il chiamante lo vede. Controesempio di passaggio "per
 * riferimento": riassegnare il parametro ({@code s = new Studente(...)}) cambia solo la
 * copia locale, e la variabile del chiamante continua a riferire l'oggetto originale.
 * Modificare un oggetto e riassegnare una variabile sono operazioni diverse.
 */
public class EsempioParametri {
    public static void main(String[] args) {
        System.out.println("--- Parametro primitivo ---");
        System.out.println("n dopo incrementa(n): " + PassaggioParametri.primitivoNelChiamanteNonCambia());

        System.out.println("--- Parametro riferimento ---");
        System.out.println("Crediti dopo assegnaCrediti(studente): " + PassaggioParametri.creditiDopoAssegnaCrediti());

        System.out.println("--- Controesempio: riassegnare il parametro ---");
        System.out.println("Matricola dopo sostituisci(originale): " + PassaggioParametri.matricolaDopoSostituisci());

        System.out.println("--- Oggetto e primitivo insieme ---");
        System.out.println("[crediti dello studente, crediti locale] = "
                + Arrays.toString(PassaggioParametri.creditiECfuDopoAccreditaCfu()));
    }
}
