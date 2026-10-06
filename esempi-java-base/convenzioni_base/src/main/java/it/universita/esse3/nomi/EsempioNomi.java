package it.universita.esse3.nomi;

public class EsempioNomi {
    public static void main(String[] args) {
        System.out.println("--- Esempio: nomi convenzionali ---");
        System.out.println("179 CFU, laurea completata? " + new CarrieraStudente(179).haCompletatoCrediti());
        System.out.println("180 CFU, laurea completata? " + new CarrieraStudente(180).haCompletatoCrediti());

        System.out.println("--- Controesempio: nome valido ma non convenzionale ---");
        System.out.println("NumeroCFU = " + NomiNonConvenzionali.cfuConNomeNonConvenzionale());

        System.out.println("--- Maiuscole e minuscole contano ---");
        System.out.println("studente + Studente + STUDENTE = " + NomiNonConvenzionali.sommaIdentificatoriDiversi());
    }
}
