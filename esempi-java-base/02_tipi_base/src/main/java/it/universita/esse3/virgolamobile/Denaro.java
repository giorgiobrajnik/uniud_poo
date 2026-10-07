package it.universita.esse3.virgolamobile;

import java.math.BigDecimal;

public class Denaro {
    // CONTROESEMPIO: 0.1 non e' rappresentabile esattamente
    public static double sommaDieciVolteConDouble() {
        double totale = 0.0;
        for (int i = 0; i < 10; i++) {
            totale += 0.1;
        }
        return totale;
    }

    // Importi in centesimi con un intero: esatto
    public static long sommaDieciVolteInCentesimi() {
        long totaleCentesimi = 0L;
        for (int i = 0; i < 10; i++) {
            totaleCentesimi += 10L;
        }
        return totaleCentesimi;
    }

    public static BigDecimal sommaDieciVolteConBigDecimal() {
        BigDecimal totale = BigDecimal.ZERO;
        BigDecimal decimo = new BigDecimal("0.1");
        for (int i = 0; i < 10; i++) {
            totale = totale.add(decimo);
        }
        return totale;
    }

    // CONTROESEMPIO: costruire un BigDecimal da un double ne eredita l'approssimazione
    public static BigDecimal bigDecimalDaDouble() {
        return new BigDecimal(0.1);
    }
}
