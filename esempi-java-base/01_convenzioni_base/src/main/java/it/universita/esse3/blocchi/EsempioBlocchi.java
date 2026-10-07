package it.universita.esse3.blocchi;

/**
 * Blocchi e indentazione: usare sempre le parentesi graffe.
 * {@link ControlloSoglia} racchiude entrambe le azioni nel blocco dell'{@code if}: sopra
 * soglia non succede nulla, sotto soglia vengono eseguite entrambe. Controesempio:
 * {@link ControlloSogliaSenzaGraffe} omette le graffe e l'indentazione fa credere che la
 * seconda istruzione dipenda dall'{@code if}, mentre viene eseguita sempre. Java legge
 * solo la prima istruzione dopo la condizione; l'indentazione non ha valore sintattico.
 */
public class EsempioBlocchi {
    public static void main(String[] args) {
        System.out.println("--- Esempio: con le graffe ---");
        System.out.println("30 CFU: " + ControlloSoglia.controlla(30));
        System.out.println(" 6 CFU: " + ControlloSoglia.controlla(6));

        System.out.println("--- Controesempio: senza graffe ---");
        System.out.println("30 CFU: " + ControlloSogliaSenzaGraffe.controlla(30));
        System.out.println(" 6 CFU: " + ControlloSogliaSenzaGraffe.controlla(6));
    }
}
