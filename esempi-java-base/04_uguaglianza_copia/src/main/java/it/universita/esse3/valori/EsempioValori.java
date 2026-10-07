package it.universita.esse3.valori;

import java.util.ArrayList;
import java.util.List;

/**
 * Value object e {@code record}. {@link Importo} e {@link ImportoClassico} hanno la stessa
 * semantica; il record genera costruttore, accessor, {@code equals}, {@code hashCode} e
 * {@code toString}. L'immutabilità è superficiale: {@link PianoStudiSuperficiale} è il
 * controesempio, {@link PianoStudi} fa la copia difensiva nel costruttore compatto.
 * {@link StudenteRecord} mostra che un record non è adatto a un'entità.
 */
public class EsempioValori {
    public static void main(String[] args) {
        System.out.println("--- Record come value object ---");
        Importo a = new Importo(12000, "EUR");
        Importo b = new Importo(12000, "EUR");
        System.out.println("a == b: " + (a == b));
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.somma(b): " + a.somma(b));
        System.out.println("classico: " + new ImportoClassico(12000, "EUR"));

        System.out.println("--- Validazione nel costruttore compatto ---");
        try {
            new Importo(-1, "EUR");
        } catch (IllegalArgumentException e) {
            System.out.println("Importo non valido: " + e.getMessage());
        }

        System.out.println("--- Controesempio: immutabilità superficiale ---");
        List<String> lista = new ArrayList<>();
        lista.add("POO");
        PianoStudiSuperficiale superficiale = new PianoStudiSuperficiale("123456", lista);
        lista.add("BASI-DATI");
        System.out.println("corsi del piano: " + superficiale.corsi());

        System.out.println("--- Copia difensiva nel costruttore compatto ---");
        lista = new ArrayList<>();
        lista.add("POO");
        PianoStudi piano = new PianoStudi("123456", lista);
        lista.add("BASI-DATI");
        System.out.println("corsi del piano: " + piano.corsi());

        System.out.println("--- Controesempio: record per un'entità ---");
        StudenteRecord r1 = new StudenteRecord("123456", "Anna Rossi");
        StudenteRecord r2 = new StudenteRecord("123456", "Anna Maria Rossi");
        System.out.println("r1.equals(r2): " + r1.equals(r2));

        System.out.println("--- Record con equals sulla sola matricola ---");
        System.out.println("equals: " + new StudenteRecordPerMatricola("123456", "Anna Rossi")
                .equals(new StudenteRecordPerMatricola("123456", "Anna Maria Rossi")));
    }
}
