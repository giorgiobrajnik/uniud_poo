package it.universita.esse3.virgolamobile;

public class ValoriSpeciali {
    public static double zeroSuZero() {
        return 0.0 / 0.0;
    }

    public static double unoSuZero() {
        return 1.0 / 0.0;
    }

    public static double menoUnoSuZero() {
        return -1.0 / 0.0;
    }

    // A differenza della virgola mobile, la divisione intera per zero lancia ArithmeticException
    public static int divisioneInteraPerZero(int dividendo, int divisore) {
        return dividendo / divisore;
    }
}
