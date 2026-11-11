package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Costruisce la pizzeria "Ai Rizzi" con i suoi tavoli, camerieri e menu.
 *
 * <p>Stato astratto: nessuno; ogni metodo costruisce e restituisce oggetti nuovi.</p>
 */
class ConfigurazioneAiRizzi {
    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una nuova pizzeria, senza servizi né ordini, con i tavoli, i camerieri e il menu di Ai Rizzi.}
     */
    Pizzeria crea() {
        return new Pizzeria(creaTavoli(), creaCamerieri(), creaMenu());
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce i tavoli interni e esterni della sala, numerati consecutivamente da 1.}
     */
    private List<Tavolo> creaTavoli() {
        int[] copertiTavoliInterni = {2, 4, 4, 6, 8, 2, 4, 6, 8, 4, 2, 6, 8, 4, 6};
        int[] copertiTavoliEsterni = {2, 4, 6, 4, 2, 6, 4, 6, 2, 4};
        List<Tavolo> tavoli = new ArrayList<>();
        for (int indice = 0; indice < copertiTavoliInterni.length; indice++) {
            tavoli.add(new Tavolo(indice + 1, copertiTavoliInterni[indice], AreaTavolo.INTERNA));
        }
        for (int indice = 0; indice < copertiTavoliEsterni.length; indice++) {
            tavoli.add(new Tavolo(indice + 16, copertiTavoliEsterni[indice], AreaTavolo.ESTERNA));
        }
        return tavoli;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce i camerieri, ciascuno con la sua zona di tavoli.}
     */
    private List<Cameriere> creaCamerieri() {
        return List.of(
                creaCameriere("C1", "Mario", "Rossi", 1, 5),
                creaCameriere("C2", "Anna", "Bianchi", 6, 10),
                creaCameriere("C3", "Luca", "Verdi", 11, 15),
                creaCameriere("C4", "Sara", "Neri", 16, 18),
                creaCameriere("C5", "Paolo", "Gallo", 19, 21),
                creaCameriere("C6", "Elena", "Blu", 22, 25));
    }

    /**
     * {@summary PRE: codice, nome e cognome non vuoti; da e a sono numeri di tavolo con da &lt;= a.<br>
     * POST: restituisce un cameriere responsabile dei tavoli da da a a, estremi inclusi.}
     */
    private Cameriere creaCameriere(
            String codice, String nome, String cognome, int primoTavolo, int ultimoTavolo) {
        Set<Integer> tavoli = new HashSet<>();
        for (int numero = primoTavolo; numero <= ultimoTavolo; numero++) {
            tavoli.add(numero);
        }
        return new Cameriere(codice, nome, cognome, tavoli);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce le pietanze del menu, con codici univoci.}
     */
    private List<Pietanza> creaMenu() {
        Set<DayOfWeek> ogniGiorno = EnumSet.allOf(DayOfWeek.class);
        Set<DayOfWeek> fineSettimana = EnumSet.of(
                DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return List.of(
                creaPietanza("P001", "Margherita", "Pomodoro e mozzarella", "8.50", CategoriaMenu.PIZZE, ogniGiorno),
                creaPietanza("P002", "Friulana", "Montasio e frico", "11.00", CategoriaMenu.PIZZE, fineSettimana),
                creaPietanza("A001", "Antipasto della casa", "Salumi e formaggi", "7.00", CategoriaMenu.ANTIPASTI, ogniGiorno),
                creaPietanza("PR01", "Gnocchi", "Gnocchi al pomodoro", "12.00", CategoriaMenu.PRIMI, ogniGiorno),
                creaPietanza("SC01", "Tagliata", "Tagliata di manzo", "15.00", CategoriaMenu.SECONDI_CARNE, ogniGiorno),
                creaPietanza("SP01", "Orata", "Orata al forno", "16.00", CategoriaMenu.SECONDI_PESCE, ogniGiorno),
                creaPietanza("CO01", "Verdure", "Verdure grigliate", "5.00", CategoriaMenu.CONTORNI, ogniGiorno),
                creaPietanza("D001", "Tiramisù", "Dolce della casa", "6.00", CategoriaMenu.DOLCI, ogniGiorno),
                creaPietanza("B001", "Acqua", "Bottiglia da 75 cl", "3.00", CategoriaMenu.BEVANDE, ogniGiorno),
                creaPietanza("B002", "Birra", "Birra artigianale", "4.50", CategoriaMenu.BEVANDE, ogniGiorno));
    }

    /**
     * {@summary PRE: i valori rispettano le precondizioni del costruttore di Pietanza.<br>
     * POST: restituisce la pietanza con i dati indicati.}
     */
    private Pietanza creaPietanza(
            String codice,
            String nome,
            String descrizione,
            String prezzo,
            CategoriaMenu categoria,
            Set<DayOfWeek> giorni) {
        return new Pietanza(codice, nome, descrizione, new BigDecimal(prezzo), categoria, giorni);
    }
}
