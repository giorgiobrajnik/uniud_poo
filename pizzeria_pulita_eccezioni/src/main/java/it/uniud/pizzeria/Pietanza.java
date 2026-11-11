package it.uniud.pizzeria;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Voce del menu.
 *
 * <p>Stato astratto: un codice che la identifica (due pietanze con lo stesso codice
 * sono uguali), nome e descrizione non vuoti, un prezzo positivo con al più due
 * decimali, una categoria e un insieme non vuoto di giorni della settimana in cui è
 * disponibile. Immutabile.</p>
 */
@Getter
@ToString
@EqualsAndHashCode(of = "codice")
public class Pietanza {
    private final String codice;
    private final String nome;
    private final String descrizione;
    private final BigDecimal prezzo;
    private final CategoriaMenu categoria;
    private final Set<DayOfWeek> giorniDisponibili;

    /**
     * {@summary PRE: codice, nome e descrizione non null né vuoti; prezzo non null, positivo e con al più due decimali; categoria e giorniDisponibili non null; giorniDisponibili non vuoto.<br>
     * POST: crea una pietanza con i dati indicati; il prezzo ha esattamente due decimali e i giorni sono una copia dell'insieme ricevuto.<br>
     * TODO: metodo non completo: contratto e comportamento per i dati non validi da definire.}
     */
    public Pietanza(
            String codice,
            String nome,
            String descrizione,
            BigDecimal prezzo,
            CategoriaMenu categoria,
            Set<DayOfWeek> giorniDisponibili) {
        if (codice == null || codice.isBlank()) {
System.out.println("TODO: gestire casi anomali");
        }
        if (nome == null || nome.isBlank()) {
System.out.println("TODO: gestire casi anomali");
        }
        if (descrizione == null || descrizione.isBlank()) {
System.out.println("TODO: gestire casi anomali");
        }
        if (prezzo == null || prezzo.signum() <= 0) {
            System.out.println("TODO: gestire casi anomali");
        }
        if (prezzo != null && prezzo.stripTrailingZeros().scale() > 2) {
            System.out.println("TODO: gestire casi anomali");
        }
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo == null ? null : prezzo.setScale(2, RoundingMode.HALF_UP);
        this.categoria = categoria;
        if (giorniDisponibili == null || giorniDisponibili.isEmpty()) {
            System.out.println("TODO: gestire casi anomali");
        }
        this.giorniDisponibili = giorniDisponibili == null ? Set.of() : Set.copyOf(giorniDisponibili);
    }

    /**
     * {@summary PRE: istante non null.<br>
     * POST: restituisce true se e solo se il giorno della settimana di istante è tra quelli disponibili; lo stato è invariato.}
     */
    public boolean isDisponibile(LocalDateTime istante) {
        Objects.requireNonNull(istante, "istante");
        return giorniDisponibili.contains(istante.getDayOfWeek());
    }
}
