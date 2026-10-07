package it.universita.esse3.nomi;

/**
 * Convenzioni sui nomi (UpperCamelCase, lowerCamelCase, costanti in maiuscolo).
 * Mostra una classe con nomi convenzionali ({@link CarrieraStudente}: campo, costante
 * {@code static final} e metodo) e un controesempio ({@link NomiNonConvenzionali}):
 * {@code NumeroCFU} e' un identificatore valido ma contrario alla convenzione, quindi
 * il compilatore lo accetta. Mostra anche che Java distingue maiuscole e minuscole:
 * {@code studente}, {@code Studente} e {@code STUDENTE} sono tre variabili diverse.
 * Una convenzione serve a uniformare il codice, non a evitare errori di compilazione.
 */
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
