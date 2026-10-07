package it.universita.esse3.visibilita;

import java.time.LocalDate;

/**
 * Visibilita' e modificatori di accesso: campi {@code private}, API pubblica.
 * {@link Appello} mantiene lo stato valido: i posti non scendono sotto zero e uno
 * studente con debiti non puo' prenotarsi, perche' il client usa solo metodi pubblici che
 * controllano le precondizioni. Controesempio: {@link AppelloScadente} ha campi pubblici
 * e nessun controllo, quindi un client puo' impostare {@code postiDisponibili = -1000}
 * senza che il compilatore protesti. {@link CalcoloPostiDisponibili} e' package-private,
 * visibile solo nel proprio package; l'accesso da fuori non compila (vedi i test).
 */
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
