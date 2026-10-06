package it.universita.esse3.assegnamento;

public class EsempioAssegnamento {
    public static void main(String[] args) {
        System.out.println("--- Esempio: entrambi i rami inizializzano ---");
        System.out.println("esame superato: " + VotoDaEsito.voto(true));
        System.out.println("esame non superato: " + VotoDaEsito.voto(false));

        System.out.println("--- Campi: valori di default ---");
        ValoriDiDefault campi = new ValoriDiDefault();
        System.out.println("int: " + campi.getVoto() + ", boolean: " + campi.isVerbalizzato()
                + ", String: " + campi.getMatricola());

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println("vedi LetturaSenzaAssegnamento e UnSoloRamoAssegna in src/test/resources/non-compilabili");
    }
}
