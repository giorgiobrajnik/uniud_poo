package it.uniud.pizzeria;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Catalogo delle pietanze offerte.
 *
 * <p>Stato astratto: un insieme di pietanze identificate da un codice univoco.
 * Immutabile dopo la costruzione.</p>
 */
class Menu {
    private final Map<String, Pietanza> pietanzePerCodice;

    /**
     * {@summary PRE: pietanze non null.<br>
     * POST: crea un menu con le pietanze ricevute, nello stesso ordine. <br>
     * TODO: metodo non completo: contratto e comportamento per i codici duplicati da definire.}
     */
    Menu(List<Pietanza> pietanze) {
        Map<String, Pietanza> risultato = new LinkedHashMap<>();
        for (Pietanza pietanza : pietanze) {
            if (risultato.putIfAbsent(pietanza.getCodice(), pietanza) != null) {
System.out.println("TODO: gestire casi anomali");
            }
        }
        this.pietanzePerCodice = Collections.unmodifiableMap(risultato);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce una copia non modificabile delle pietanze; lo stato è invariato.}
     */
    List<Pietanza> getPietanze() {
        return List.copyOf(pietanzePerCodice.values());
    }

    /**
     * {@summary PRE: nessuna (codice può essere null).<br>
     * POST: restituisce true se esiste una pietanza con quel codice; lo stato è invariato.}
     */
    boolean contiene(String codice) {
        return codice != null && pietanzePerCodice.containsKey(codice);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la pietanza con quel codice.<br>
     * TODO: metodo non completo: contratto e comportamento per la pietanza inesistente da definire.}
     */
    Pietanza richiedi(String codice) {
        if (!contiene(codice)) {
            System.out.println("TODO: gestire casi anomali");
            return null;
        }
        return pietanzePerCodice.get(codice);
    }
}
