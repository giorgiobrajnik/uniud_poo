package it.universita.esse3.omonimie;

import it.universita.esse3.omonimie.report.ReportEsami;
import java.time.LocalDate;

/**
 * Package e import: lo stesso nome semplice in package diversi indica tipi
 * diversi.
 * {@code Esame} esiste in {@code omonimie.esami} (attivita' didattica) e in
 * {@code omonimie.appelli} (prova da sostenere); {@code Stato} esiste in tre
 * package con
 * significati diversi. {@code ReportEsami} importa un solo {@code Esame} e
 * qualifica per
 * esteso l'altro (nome completamente qualificato). Controesempio: importare
 * entrambi i
 * tipi con lo stesso nome semplice non compila (vedi i sorgenti
 * {@code .java.txt} nei
 * test). I package evitano sia i conflitti di nome sia la confusione di
 * significato.
 */
public class EsempioOmonimie {
    public static void main(String[] args) {
        var esameDidattico = new it.universita.esse3.omonimie.esami.Esame("POO",
                "Programmazione orientata agli oggetti", 6);
        var esameAppello = new it.universita.esse3.omonimie.appelli.Esame("POO", LocalDate.of(2026, 2, 10), "Aula 5");

        System.out.println("--- Stesso nome semplice, tipi diversi ---");
        // esami invece di appelli
        System.out.println(esameDidattico.getClass().getName());
        System.out.println(esameAppello.getClass().getName());

        System.out.println("--- Costanti Stato in tre package ---");
        System.out.println(it.universita.esse3.omonimie.carriere.Stato.ISCRITTO);
        System.out.println(it.universita.esse3.omonimie.appelli.Stato.CONFERMATA);
        System.out.println(it.universita.esse3.omonimie.tasse.Stato.PAGATA);

        System.out.println("--- Nome completamente qualificato in ReportEsami ---");
        System.out.println(new ReportEsami().generaReport(esameAppello));

        System.out.println("--- Controesempio: due import con lo stesso nome non compilano ---");
        System.out.println("vedi src/test/resources/non-compilabili/DueImportStessoNome.java.txt");
    }
}
