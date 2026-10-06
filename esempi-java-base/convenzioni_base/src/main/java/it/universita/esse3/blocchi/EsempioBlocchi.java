package it.universita.esse3.blocchi;

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
