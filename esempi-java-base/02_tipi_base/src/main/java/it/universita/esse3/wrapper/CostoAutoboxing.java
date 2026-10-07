package it.universita.esse3.wrapper;

public class CostoAutoboxing {
    // CONTROESEMPIO: a ogni iterazione unboxing, addizione e nuovo boxing
    public static long sommaConWrapper(long n) {
        Long somma = 0L;
        for (long i = 0; i < n; i++) {
            somma += i;
        }
        return somma;
    }

    public static long sommaConPrimitivo(long n) {
        long somma = 0L;
        for (long i = 0; i < n; i++) {
            somma += i;
        }
        return somma;
    }
}
