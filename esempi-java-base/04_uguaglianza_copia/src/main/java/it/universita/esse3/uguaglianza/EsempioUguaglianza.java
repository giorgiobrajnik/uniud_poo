package it.universita.esse3.uguaglianza;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Uguaglianza per valore. {@link AnnoAccademico} e {@link CodiceCorso} ridefiniscono
 * {@code equals}; {@link CodiceCorsoSenzaEquals} usa quello di {@code Object} (identità);
 * {@link CodiceCorsoOverload} sbaglia la firma e introduce un overload che le collezioni
 * non usano.
 */
public class EsempioUguaglianza {
    public static void main(String[] args) {
        System.out.println("--- AnnoAccademico: identità e valore ---");
        AnnoAccademico a1 = new AnnoAccademico(2026);
        AnnoAccademico a2 = new AnnoAccademico(2026);
        System.out.println("a1 == a2: " + (a1 == a2));
        System.out.println("a1.equals(a2): " + a1.equals(a2));

        System.out.println("--- Controesempio: equals non ridefinito ---");
        CodiceCorsoSenzaEquals s1 = new CodiceCorsoSenzaEquals("INF-POO");
        CodiceCorsoSenzaEquals s2 = new CodiceCorsoSenzaEquals("INF-POO");
        System.out.println("s1.equals(s2): " + s1.equals(s2));

        System.out.println("--- equals ridefinito ---");
        CodiceCorso c1 = new CodiceCorso("INF-POO");
        CodiceCorso c2 = new CodiceCorso("INF-POO");
        System.out.println("c1.equals(c2): " + c1.equals(c2));
        System.out.println("c1.equals(null): " + c1.equals(null));
        System.out.println("c1.equals(\"INF-POO\"): " + c1.equals("INF-POO"));
        System.out.println("con pattern: "
                + new CodiceCorsoConPattern("INF-POO").equals(new CodiceCorsoConPattern("INF-POO")));

        System.out.println("--- Controesempio: overload invece di override ---");
        CodiceCorsoOverload o1 = new CodiceCorsoOverload("INF-POO");
        CodiceCorsoOverload o2 = new CodiceCorsoOverload("INF-POO");
        Object o2ComeObject = o2;
        System.out.println("o1.equals(o2): " + o1.equals(o2));
        System.out.println("o1.equals((Object) o2): " + o1.equals(o2ComeObject));
        System.out.println("List.contains: " + List.of(o1).contains(o2));
        Set<CodiceCorsoOverload> insieme = new HashSet<>();
        insieme.add(o1);
        System.out.println("HashSet.contains: " + insieme.contains(o2));
    }
}
