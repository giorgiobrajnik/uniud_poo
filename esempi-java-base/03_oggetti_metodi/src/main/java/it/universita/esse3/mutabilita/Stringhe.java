package it.universita.esse3.mutabilita;

public class Stringhe {
    // s = s + "D" non modifica l'oggetto "ABC"
    public static String[] originaleEDopoConcatenazione() {
        String s = "ABC";
        String originale = s;
        s = s + "D";
        return new String[] {originale, s};
    }

    public static String aDopoLaConcatenazioneDiB() {
        String a = "POO";
        String b = a;
        b = b + "-LAB";
        return a;
    }

    // CONTROESEMPIO: StringBuilder e' mutabile, quindi a vede la modifica fatta tramite b
    public static String stringBuilderCondiviso() {
        StringBuilder a = new StringBuilder("Studente ");
        StringBuilder b = a;
        b.append("123456");
        return a.toString();
    }
}
