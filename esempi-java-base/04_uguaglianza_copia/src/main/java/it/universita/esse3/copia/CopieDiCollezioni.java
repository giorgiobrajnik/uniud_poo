package it.universita.esse3.copia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CopieDiCollezioni {
    private CopieDiCollezioni() {
    }

    // CONTROESEMPIO: new ArrayList<>(a) non duplica gli elementi
    public static int votoOriginaleDopoModificaViaCopiaArrayList() {
        List<EsameSuperato> a = new ArrayList<>();
        a.add(new EsameSuperato("POO", 27));
        List<EsameSuperato> b = new ArrayList<>(a);
        b.get(0).setVoto(30);
        return a.get(0).voto();
    }

    // CONTROESEMPIO: List.copyOf non è una deep copy
    public static int votoOriginaleDopoModificaViaCopyOf() {
        List<EsameSuperato> a = new ArrayList<>();
        a.add(new EsameSuperato("POO", 27));
        List<EsameSuperato> copia = List.copyOf(a);
        copia.get(0).setVoto(30);
        return a.get(0).voto();
    }

    public static boolean copyOfNonPermetteAggiunte() {
        List<String> copia = List.copyOf(List.of("POO"));
        try {
            copia.add("BASI-DATI");
            return false;
        } catch (UnsupportedOperationException e) {
            return true;
        }
    }

    // [dimensione della vista, dimensione della copia] dopo un'aggiunta alla lista originale
    public static int[] vistaNonModificabileContraCopyOf() {
        List<String> originale = new ArrayList<>();
        originale.add("POO");
        List<String> vista = Collections.unmodifiableList(originale);
        List<String> copia = List.copyOf(originale);
        originale.add("BASI-DATI");
        return new int[] {vista.size(), copia.size()};
    }

    public static int primoValoreOriginaleDopoCloneDiPrimitivi() {
        int[] a = {27, 30, 24};
        int[] b = a.clone();
        b[0] = 18;
        return a[0];
    }

    // CONTROESEMPIO: clone() di un array di riferimenti è superficiale
    public static int votoOriginaleDopoCloneDiRiferimenti() {
        EsameSuperato[] a = {new EsameSuperato("POO", 27)};
        EsameSuperato[] b = a.clone();
        b[0].setVoto(30);
        return a[0].voto();
    }
}
