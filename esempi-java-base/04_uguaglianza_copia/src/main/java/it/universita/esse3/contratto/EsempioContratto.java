package it.universita.esse3.contratto;

import it.universita.esse3.uguaglianza.CodiceCorso;

/**
 * Contratto di {@code equals}: riflessiva, simmetrica, transitiva, consistente, mai
 * uguale a {@code null}. {@link CodiceCorso} lo rispetta; {@link StudentePersona}
 * rompe la simmetria, {@link Misura} la transitività, {@link EqualsIncoerente} la
 * consistenza.
 */
public class EsempioContratto {
    public static void main(String[] args) {
        System.out.println("--- Proprietà rispettate da CodiceCorso ---");
        CodiceCorso x = new CodiceCorso("INF-POO");
        CodiceCorso y = new CodiceCorso("INF-POO");
        CodiceCorso z = new CodiceCorso("INF-POO");
        System.out.println("riflessiva: " + x.equals(x));
        System.out.println("simmetrica: " + (x.equals(y) == y.equals(x)));
        System.out.println("transitiva: " + (x.equals(y) && y.equals(z) && x.equals(z)));
        System.out.println("x.equals(null): " + x.equals(null));

        System.out.println("--- Controesempio: simmetria ---");
        Persona p = new Persona("Anna Rossi");
        StudentePersona s = new StudentePersona("Anna Rossi", "123456");
        System.out.println("p.equals(s): " + p.equals(s));
        System.out.println("s.equals(p): " + s.equals(p));

        System.out.println("--- Controesempio: transitività ---");
        Misura a = new Misura(20.0);
        Misura b = new Misura(20.8);
        Misura c = new Misura(21.6);
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("b.equals(c): " + b.equals(c));
        System.out.println("a.equals(c): " + a.equals(c));

        System.out.println("--- Controesempio: consistenza ---");
        EqualsIncoerente e = new EqualsIncoerente();
        System.out.println("e.equals(e) due volte: " + e.equals(e) + ", " + e.equals(e));
    }
}
