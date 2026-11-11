package it.uniud.pizzeria;

import lombok.Value;

/**
 * Tavolo fisico della sala.
 *
 * <p>Stato astratto: un numero positivo, un'area (interna o esterna) e il numero di
 * coperti disponibili, compreso tra 2 e la capienza massima dell'area. Immutabile;
 * non sa se è occupato, perché l'occupazione dipende dai servizi.</p>
 */
@Value
public class Tavolo {
    int numero;
    int coperti;
    AreaTavolo area;

    /**
     * {@summary PRE: numero &gt; 0; area non null; 2 &lt;= coperti &lt;= area.getCapienzaMassima().<br>
     * POST: crea un tavolo con i dati indicati.<br>
     * TODO: metodo non completo: contratto e comportamento per i dati non validi da definire.}
     */
    public Tavolo(int numero, int coperti, AreaTavolo area) {
        if (numero <= 0) {
System.out.println("TODO: gestire casi anomali");
        }
        this.numero = numero;
        this.area = area;
        if (coperti < 2 || coperti > area.getCapienzaMassima()) {
System.out.println("TODO: gestire casi anomali");
        }
        this.coperti = coperti;
    }
}
