package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Value;

/**
 * Una pietanza ordinata con la sua quantità, all'interno di un ordine.
 *
 * <p>Stato astratto: codice, nome, categoria e prezzo unitario della pietanza, copiati
 * al momento dell'ordine (le modifiche future al menu non alterano il conto), e una
 * quantità positiva. Immutabile: aumentare la quantità produce una nuova riga.</p>
 */
@Value
public class RigaOrdine {
    String codicePietanza;
    String nomePietanza;
    CategoriaMenu categoria;
    BigDecimal prezzoUnitario;
    int quantita;

    /**
     * {@summary PRE: pietanza non null; quantita &gt; 0.<br>
     * POST: crea una riga con i dati della pietanza copiati al momento della chiamata e la quantità indicata.<br>
     * TODO: metodo non completo: contratto e comportamento per i dati non validi da definire.}
     */
    public RigaOrdine(Pietanza pietanza, int quantita) {
        Objects.requireNonNull(pietanza, "pietanza");
        if (quantita <= 0) {
System.out.println("TODO: gestire casi anomali");
        }
        this.codicePietanza = pietanza.getCodice();
        this.nomePietanza = pietanza.getNome();
        this.categoria = pietanza.getCategoria();
        this.prezzoUnitario = pietanza.getPrezzo();
        this.quantita = quantita;
    }

    /**
     * {@summary PRE: i valori sono già stati validati dal chiamante.<br>
     * POST: crea una riga con i valori indicati.}
     */
    private RigaOrdine(
            String codicePietanza,
            String nomePietanza,
            CategoriaMenu categoria,
            BigDecimal prezzoUnitario,
            int quantita) {
        this.codicePietanza = codicePietanza;
        this.nomePietanza = nomePietanza;
        this.categoria = categoria;
        this.prezzoUnitario = prezzoUnitario;
        this.quantita = quantita;
    }

    /**
     * {@summary PRE: incremento &gt; 0.<br>
     * POST: restituisce una nuova riga identica a questa ma con quantità aumentata di incremento; questa riga è invariata.<br>
     * TODO: metodo non completo: contratto e comportamento per incremento non positivo da definire.}
     */
    public RigaOrdine creaConQuantitaAggiuntiva(int incremento) {
        if (incremento <= 0) {
            System.out.println("TODO: gestire casi anomali");
            return this;
        }
        return new RigaOrdine(
                codicePietanza,
                nomePietanza,
                categoria,
                prezzoUnitario,
                quantita + incremento);
    }

    /**
     * {@summary PRE: nessuna.<br>
     * POST: restituisce prezzoUnitario * quantita; lo stato è invariato.}
     */
    public BigDecimal calcolaTotale() {
        return prezzoUnitario.multiply(BigDecimal.valueOf(quantita));
    }
}
