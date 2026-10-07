package it.universita.esse3.serializzazione;

import java.io.NotSerializableException;
import java.io.UncheckedIOException;

/**
 * Deep copy tramite serializzazione: funziona solo per ciò che è serializzato, non per
 * ciò che il dominio richiede. Copia l'intero grafo raggiungibile, perde i campi
 * {@code transient} e fallisce con oggetti non serializzabili.
 */
public class EsempioSerializzazione {
    public static void main(String[] args) {
        System.out.println("--- Copia per serializzazione ---");
        ReportSerializzabile originale = new ReportSerializzabile("Esami 2026");
        originale.aggiungi("POO");
        originale.calcolaCache();
        ReportSerializzabile copia = CopiaPerSerializzazione.copia(originale);
        copia.aggiungi("BASI-DATI");
        System.out.println("voci dell'originale: " + originale.voci());
        System.out.println("voci della copia: " + copia.voci());

        System.out.println("--- Controesempio: campo transient ---");
        System.out.println("cache originale: " + originale.cache());
        System.out.println("cache della copia: " + copia.cache());

        System.out.println("--- Controesempio: oggetto non serializzabile ---");
        try {
            CopiaPerSerializzazione.copia(new ReportConRisorsa("Esami 2026"));
        } catch (UncheckedIOException e) {
            System.out.println("causa: " + (e.getCause() instanceof NotSerializableException));
        }
    }
}
