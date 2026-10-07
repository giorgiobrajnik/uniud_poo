package it.universita.esse3.mutabilita;

import java.util.ArrayList;
import java.util.List;

public class ListeCondivise {
    // CONTROESEMPIO di copia: copia e appelli sono la stessa lista
    public static List<String> appelliDopoAggiuntaViaAlias() {
        List<String> appelli = new ArrayList<>();
        appelli.add("POO-2026-01");

        List<String> copia = appelli;
        copia.add("BASI-DATI-2026-02");
        return appelli;
    }

    public static List<String> appelliDopoAggiuntaSuUnaCopia() {
        List<String> appelli = new ArrayList<>();
        appelli.add("POO-2026-01");

        List<String> copia = new ArrayList<>(appelli);
        copia.add("BASI-DATI-2026-02");
        return appelli;
    }

    // CONTROESEMPIO: la copia e' superficiale, gli elementi mutabili restano condivisi
    public static String elementoDopoCopiaSuperficiale() {
        List<StringBuilder> a = new ArrayList<>();
        a.add(new StringBuilder("POO"));

        List<StringBuilder> b = new ArrayList<>(a);
        b.get(0).append("-LAB");
        return a.get(0).toString();
    }

    public static String elementoDopoCopiaProfonda() {
        List<StringBuilder> a = new ArrayList<>();
        a.add(new StringBuilder("POO"));

        List<StringBuilder> b = new ArrayList<>();
        for (StringBuilder elemento : a) {
            b.add(new StringBuilder(elemento));
        }
        b.get(0).append("-LAB");
        return a.get(0).toString();
    }

    // Esercizio di tracciamento: una sola ArrayList, due riferimenti
    public static List<String> contenutoDellaListaDellEsercizio() {
        List<String> a = new ArrayList<>();
        a.add("POO");

        List<String> b = a;

        String s = "LAB";
        b.add(s);

        s = s + "-2026";
        return a;
    }

    public static String valoreFinaleDiSDellEsercizio() {
        String s = "LAB";
        s = s + "-2026";
        return s;
    }
}
