package it.universita.esse3.virgolamobile;

public class Approssimazione {
    public static double zeroUnoPiuZeroDue() {
        return 0.1 + 0.2;
    }

    // CONTROESEMPIO: confrontare double con == non e' affidabile
    public static boolean sommaUgualeATreDecimi() {
        return 0.1 + 0.2 == 0.3;
    }

    public static boolean sommaVicinaATreDecimi(double tolleranza) {
        return Math.abs(0.1 + 0.2 - 0.3) < tolleranza;
    }
}
