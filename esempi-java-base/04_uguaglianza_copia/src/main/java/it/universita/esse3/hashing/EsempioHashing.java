package it.universita.esse3.hashing;

import java.util.HashSet;
import java.util.Set;

/**
 * Contratto tra {@code equals} e {@code hashCode}. {@link AppelloIdSenzaHash} ridefinisce
 * solo {@code equals} e non viene ritrovato da un {@code HashSet}; {@link StudenteEmail}
 * usa un campo mutabile e perde l'elemento dopo la mutazione; {@link StudenteMatricola}
 * usa la sola matricola, stabile. Due oggetti diversi possono avere lo stesso hash
 * (collisione).
 */
public class EsempioHashing {
    public static void main(String[] args) {
        System.out.println("--- Controesempio: equals senza hashCode ---");
        AppelloIdSenzaHash a = new AppelloIdSenzaHash("INF-POO", 3);
        AppelloIdSenzaHash b = new AppelloIdSenzaHash("INF-POO", 3);
        Set<AppelloIdSenzaHash> senzaHash = new HashSet<>();
        senzaHash.add(a);
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("contains(b): " + senzaHash.contains(b));

        System.out.println("--- equals e hashCode coerenti ---");
        Set<AppelloId> conHash = new HashSet<>();
        conHash.add(new AppelloId("INF-POO", 3));
        System.out.println("contains(altra istanza uguale): " + conHash.contains(new AppelloId("INF-POO", 3)));

        System.out.println("--- Collisione: stesso hash, non uguali ---");
        System.out.println("\"Aa\".hashCode(): " + "Aa".hashCode());
        System.out.println("\"BB\".hashCode(): " + "BB".hashCode());
        System.out.println("\"Aa\".equals(\"BB\"): " + "Aa".equals("BB"));

        System.out.println("--- Controesempio: campo mutabile in hashCode ---");
        StudenteEmail anna = new StudenteEmail("Anna Rossi", "anna@old.example");
        Set<StudenteEmail> iscritti = new HashSet<>();
        iscritti.add(anna);
        anna.setEmail("anna@new.example");
        System.out.println("contains(anna) dopo setEmail: " + iscritti.contains(anna));
        System.out.println("size: " + iscritti.size());

        System.out.println("--- Controesempio: tutti i campi ---");
        StudenteTuttiICampi t = new StudenteTuttiICampi("Anna Rossi", "anna@example", "Informatica");
        Set<StudenteTuttiICampi> insiemeT = new HashSet<>();
        insiemeT.add(t);
        t.setCorsoDiStudio("Matematica");
        System.out.println("contains(t) dopo cambio di corso: " + insiemeT.contains(t));

        System.out.println("--- Identità sulla matricola ---");
        StudenteMatricola m = new StudenteMatricola("123456", "Anna Rossi", "anna@old.example");
        Set<StudenteMatricola> insiemeM = new HashSet<>();
        insiemeM.add(m);
        m.setNome("Anna Maria Rossi");
        m.setEmail("anna@new.example");
        System.out.println("contains(m) dopo le modifiche: " + insiemeM.contains(m));
        System.out.println("contains(stessa matricola): "
                + insiemeM.contains(new StudenteMatricola("123456", "altro", "altro")));
    }
}
