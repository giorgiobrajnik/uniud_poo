package it.uniud.pizzeria;

/** Zona della sala; a ogni area è associata la capienza massima dei suoi tavoli. */
public enum AreaTavolo {
    INTERNA(8),
    ESTERNA(6);

    private final int capienzaMassima;

    AreaTavolo(int capienzaMassima) {
        this.capienzaMassima = capienzaMassima;
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce la capienza massima dei tavoli dell'area ; lo stato è invariato.}
     */
    public int getCapienzaMassima() {
        return capienzaMassima;
    }
}
