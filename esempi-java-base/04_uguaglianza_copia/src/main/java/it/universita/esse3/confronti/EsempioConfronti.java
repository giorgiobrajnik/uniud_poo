package it.universita.esse3.confronti;

import java.util.Arrays;
import java.util.Objects;

/**
 * Confronti da non confondere: stringhe, wrapper, {@code Objects.equals}, array.
 * {@code ==} su riferimenti confronta l'identità, anche quando la JVM condivide
 * alcune istanze (letterali, piccoli {@code Integer}). Gli array non ridefiniscono
 * {@code equals}: si usano {@code Arrays.equals} e {@code Arrays.hashCode}.
 */
public class EsempioConfronti {
    public static void main(String[] args) {
        System.out.println("--- Stringhe ---");
        System.out.println("new == new: " + ConfrontoStringhe.newConDoppioUguale());
        System.out.println("new equals new: " + ConfrontoStringhe.newConEquals());
        System.out.println("letterale == letterale: " + ConfrontoStringhe.letteraliConDoppioUguale());
        System.out.println("letterale == new: " + ConfrontoStringhe.letteraleContraNewConDoppioUguale());

        System.out.println("--- Wrapper ---");
        System.out.println("100 == 100: " + ConfrontoWrapper.piccoliConDoppioUguale());
        System.out.println("1000 == 1000: " + ConfrontoWrapper.grandiConDoppioUguale());
        System.out.println("1000 equals 1000: " + ConfrontoWrapper.grandiConEquals());
        System.out.println("Integer(1).equals(Long(1)): " + ConfrontoWrapper.integerEqualsLong());
        System.out.println("int 1000 == int 1000: " + ConfrontoWrapper.primitiviConDoppioUguale());

        System.out.println("--- Objects.equals ---");
        System.out.println("equals(null, null): " + Objects.equals(null, null));
        System.out.println("equals(\"a\", null): " + Objects.equals("a", null));

        System.out.println("--- Controesempio: un campo che non dovrebbe contare ---");
        PrenotazioneConNote n1 = new PrenotazioneConNote("123456", "INF-POO-3", null);
        PrenotazioneConNote n2 = new PrenotazioneConNote("123456", "INF-POO-3", "da verificare");
        System.out.println("equals con note diverse: " + n1.equals(n2));
        Prenotazione p1 = new Prenotazione("123456", "INF-POO-3", null);
        Prenotazione p2 = new Prenotazione("123456", "INF-POO-3", "da verificare");
        System.out.println("equals ignorando le note: " + p1.equals(p2));

        System.out.println("--- Array ---");
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("Arrays.equals(a, b): " + Arrays.equals(a, b));
        System.out.println("Controesempio, campo array con equals: "
                + new VotiSbagliato(30, 27).equals(new VotiSbagliato(30, 27)));
        System.out.println("Con Arrays.equals: " + new Voti(30, 27).equals(new Voti(30, 27)));
    }
}
