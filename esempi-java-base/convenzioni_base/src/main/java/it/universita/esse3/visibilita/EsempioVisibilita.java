package it.universita.esse3.visibilita;

import java.time.LocalDate;

public class EsempioVisibilita {
    public static void main(String[] args) {
        Studente regolare = new Studente("123", 60, false);
        Studente conDebiti = new Studente("456", 60, true);

        System.out.println("--- Esempio: API pubblica, campi privati ---");
        Appello appello = new Appello("POO", LocalDate.of(2026, 2, 10), 1);
        appello.prenotaStudente(conDebiti);
        System.out.println("Dopo studente con debiti, posti: " + appello.getPostiDisponibili());
        appello.prenotaStudente(regolare);
        System.out.println("Dopo studente regolare, posti: " + appello.getPostiDisponibili());
        appello.prenotaStudente(regolare);
        System.out.println("Appello pieno, posti: " + appello.getPostiDisponibili());

        System.out.println("--- Controesempio: campi pubblici ---");
        AppelloScadente scadente = new AppelloScadente();
        scadente.postiDisponibili = -1000;
        scadente.prenotaStudente(regolare);
        System.out.println("Posti: " + scadente.postiDisponibili);

        System.out.println("--- Controesempi che non compilano ---");
        System.out.println(
                "vedi AccessoCampoPrivato e AccessoClassePackagePrivate in src/test/resources/non-compilabili");
    }
}
