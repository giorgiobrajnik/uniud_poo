package it.universita.esse3.mutabilita;

import it.universita.esse3.riferimenti.Studente;

/**
 * Mutabilita', immutabilita' e aliasing.
 * Con un oggetto mutabile, due variabili che lo condividono (alias) osservano
 * le stesse
 * modifiche; con {@link StudenteImmutabile} ogni operazione restituisce un
 * nuovo oggetto e
 * l'originale non cambia. {@code String} e' immutabile ({@code s = s + "D"}
 * produce un
 * altro valore), {@code StringBuilder} e' mutabile. Una lista condivisa e una
 * copia
 * superficiale si comportano come alias degli elementi; la copia profonda li
 * duplica.
 * {@link CodiceAppello} e' immutabile; {@link CodiceAppelloModificabile} e' il
 * controesempio.
 */
public class EsempioMutabilita {
    public static void main(String[] args) {
        System.out.println("--- Oggetto mutabile e aliasing ---");
        Studente s1 = new Studente("123456");
        Studente s2 = s1;
        s2.aggiungiCrediti(6);
        System.out.println("s1.getCrediti() dopo s2.aggiungiCrediti(6): " + s1.getCrediti());

        System.out.println("--- Oggetto immutabile ---");
        StudenteImmutabile i1 = new StudenteImmutabile("123456", 0);
        StudenteImmutabile i2 = i1.aggiungiCrediti(6);
        System.out.println("i1: " + i1.getCrediti() + ", i2: " + i2.getCrediti());

        System.out.println("--- String e StringBuilder ---");
        String[] stringhe = Stringhe.originaleEDopoConcatenazione();
        System.out.println("originale: " + stringhe[0] + ", dopo s = s + \"D\": " + stringhe[1]);
        System.out.println("a dopo b = b + \"-LAB\": " + Stringhe.aDopoLaConcatenazioneDiB());
        System.out.println("Controesempio, StringBuilder condiviso: " + Stringhe.stringBuilderCondiviso());

        System.out.println("--- Liste ---");
        System.out.println("Controesempio, alias: " + ListeCondivise.appelliDopoAggiuntaViaAlias());
        System.out.println("Copia:                " + ListeCondivise.appelliDopoAggiuntaSuUnaCopia());
        System.out.println("Controesempio, copia superficiale: " + ListeCondivise.elementoDopoCopiaSuperficiale());
        System.out.println("Copia profonda:                    " + ListeCondivise.elementoDopoCopiaProfonda());
        System.out.println("Esercizio: lista = " + ListeCondivise.contenutoDellaListaDellEsercizio()
                + ", s = " + ListeCondivise.valoreFinaleDiSDellEsercizio());

        System.out.println("--- Tipo immutabile e controesempio ---");
        CodiceAppello c = new CodiceAppello("POO-2026-01");
        System.out.println("CodiceAppello: " + c.getValore());
        CodiceAppelloModificabile a = new CodiceAppelloModificabile();
        CodiceAppelloModificabile b = a;
        a.setValore("POO-2026-02");
        System.out.println("Controesempio, b.getValore() senza averlo toccato: " + b.getValore());

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println("vedi CodiceAppello* in src/test/resources/non-compilabili");
    }
}
