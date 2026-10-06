package it.universita.esse3.wrapper;

public class EsempioWrapper {
    public static void main(String[] args) {
        System.out.println("--- Boxing, autoboxing, unboxing ---");
        System.out.println("Integer.valueOf(6) = " + Boxing.boxingEsplicito(6));
        System.out.println("autoboxing 6       = " + Boxing.autoboxing(6));
        System.out.println("unboxing di 30     = " + Boxing.unboxing(30));
        System.out.println("List<Integer>      = " + Boxing.votiConAutoboxing());

        System.out.println("--- Controesempio: null e unboxing ---");
        try {
            Boxing.votoNumerico(null);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException");
        }
        System.out.println("Con valore di default: " + Boxing.votoNumericoODefault(null, 0));

        System.out.println("--- Costo dell'autoboxing (i tempi variano) ---");
        long n = 100_000_000L;
        long inizioWrapper = System.currentTimeMillis();
        CostoAutoboxing.sommaConWrapper(n);
        long tempoWrapper = System.currentTimeMillis() - inizioWrapper;

        long inizioPrimitivo = System.currentTimeMillis();
        CostoAutoboxing.sommaConPrimitivo(n);
        long tempoPrimitivo = System.currentTimeMillis() - inizioPrimitivo;

        System.out.println("Long (wrapper):   " + tempoWrapper + " ms");
        System.out.println("long (primitivo): " + tempoPrimitivo + " ms");
    }
}
