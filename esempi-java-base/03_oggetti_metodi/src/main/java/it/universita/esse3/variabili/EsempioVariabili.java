package it.universita.esse3.variabili;

/**
 * Categorie di variabili, scope, {@code this} e metodi {@code static}.
 * Una variabile locale esiste solo nel blocco che la dichiara; blocchi separati possono
 * riusare lo stesso nome, mentre un blocco interno non puo' ridichiararne uno gia' visibile
 * (vedi i controesempi non compilabili). {@link SessioneUtente} ha un campo di istanza per
 * oggetto e un campo {@code static} condiviso. Controesempio: {@link CampoNascosto} assegna
 * il parametro a se stesso; {@link CampoConThis} usa {@code this.crediti}. Un metodo
 * {@code static} non ha {@code this}.
 */
public class EsempioVariabili {
    public static void main(String[] args) {
        System.out.println("--- Scope delle variabili locali ---");
        System.out.println("verificaCarriera(6):  " + Scope.verificaCarriera(6));
        System.out.println("verificaCarriera(30): " + Scope.verificaCarriera(30));
        System.out.println("mancanti dichiarati prima del blocco: " + Scope.mancantiDichiaratiPrimaDelBlocco(6));
        System.out.println("blocco interno vede soglia: " + Scope.bloccoInternoVedeLeVariabiliEsterne(6, true));
        System.out.println("stesso nome in blocchi separati: " + Scope.stessoNomeInBlocchiSeparati(true, true));

        System.out.println("--- Campo statico condiviso, campo di istanza no ---");
        SessioneUtente anna = new SessioneUtente("anna");
        SessioneUtente luca = new SessioneUtente("luca");
        System.out.println(anna.getUsername() + ", " + luca.getUsername() + ", sessioni create: "
                + SessioneUtente.getSessioniCreate());

        System.out.println("--- this e shadowing ---");
        System.out.println("Controesempio, crediti = crediti: " + new CampoNascosto(6).getCrediti());
        System.out.println("this.crediti = crediti:           " + new CampoConThis(6).getCrediti());

        System.out.println("--- Metodo static ---");
        System.out.println("votoValido(27) = " + ConversioniVoto.votoValido(27));
        System.out.println("votoValido(15) = " + ConversioniVoto.votoValido(15));

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println("vedi MancantiFuoriScope, ShadowingInBloccoInterno e ThisInMetodoStatic "
                + "in src/test/resources/non-compilabili");
    }
}
