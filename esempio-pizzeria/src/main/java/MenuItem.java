import java.util.Set;
import java.util.HashSet;

/**
 * <b>Mission.</b> Rappresenta una pietanza (o bevanda) del menu della pizzeria.
 * <p>
 * <b>Cosa conosce:</b> codice identificativo, nome, descrizione, prezzo unitario,
 * categoria e giorni della settimana in cui la pietanza è disponibile.
 * <br>
 * <b>Cosa sa fare:</b> fornire le proprie informazioni, dire se è disponibile in un dato giorno,
 * modificare l'insieme dei giorni di disponibilità, produrre una descrizione dettagliata di sé.
 * Due pietanze sono considerate uguali se hanno lo stesso codice.
 *
 * <h2>Tipo di dato astratto</h2>
 * <b>Stato astratto.</b> Una quadrupla/quintupla (codice, nome, descrizione, prezzo, categoria)
 * più un insieme di giorni di disponibilità D ⊆ {MONDAY, ..., SUNDAY, ALL_DAYS}.
 * Il codice identifica la pietanza ed è immutabile di fatto (non esistono modificatori);
 * l'unica parte mutabile dello stato astratto è D.
 * <p>
 * <b>Invarianti astratti.</b>
 * <ul>
 *   <li>codice, nome, descrizione e categoria non sono null;</li>
 *   <li>prezzo &ge; 0;</li>
 *   <li>D non è null;</li>
 *   <li>la pietanza è disponibile nel giorno g sse ALL_DAYS &isin; D oppure g &isin; D;</li>
 *   <li>l'identità di una pietanza è data dal solo codice (uguaglianza coerente con hashCode).</li>
 * </ul>
 * <b>Stato concreto.</b> Campi {@code code}, {@code name}, {@code description} (String),
 * {@code price} (double), {@code category} (Category) e {@code availableDays}
 * (un {@code HashSet<DayOfWeek>} privato).
 * <p>
 * <b>Invarianti di rappresentazione.</b>
 * <ul>
 *   <li>{@code code, name, description, category, availableDays} != null;</li>
 *   <li>{@code price >= 0} e non NaN;</li>
 *   <li>{@code availableDays} è un insieme posseduto esclusivamente da questo oggetto
 *       (nessun alias verso l'esterno: viene copiato in ingresso e in uscita);</li>
 *   <li>i campi {@code code}, {@code name}, {@code description}, {@code price}, {@code category}
 *       non cambiano dopo la costruzione (il codice, in particolare, garantisce la stabilità di
 *       {@code hashCode});</li>
 *   <li>{@code availableDays} può essere vuoto (pietanza momentaneamente non disponibile
 *       in alcun giorno).</li>
 * </ul>
 * Nota: il codice attuale non verifica questi invarianti in costruzione; è responsabilità
 * del chiamante rispettare le precondizioni dei costruttori.
 */
public class MenuItem {
    /**
     * Categoria merceologica di una pietanza; ogni categoria ha un nome da mostrare al cliente.
     * Tipo enumerato immutabile: stato astratto = stato concreto = (costante, displayName non null).
     */
    public enum Category {
        PIZZA("Pizze"),
        ANTIPASTO("Antipasti"),
        PRIMO("Primi piatti"),
        SECONDO_CARNE("Secondi di carne"),
        SECONDO_PESCE("Secondi di pesce"),
        CONTORNO("Contorni"),
        DOLCE("Dolci della casa"),
        BEVANDA("Bevande");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }

        /**
         * @pre true
         * @post restituisce il nome non null e non vuoto da mostrare in menu per la categoria
         */
        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Giorno della settimana, più il valore speciale {@code ALL_DAYS} che significa
     * "tutti i giorni". Tipo enumerato senza stato.
     */
    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, ALL_DAYS
    }

    private String code;
    private String name;
    private String description;
    private double price;
    private Category category;
    private Set<DayOfWeek> availableDays; // Giorni in cui la pietanza è disponibile

    /**
     * Crea una pietanza disponibile tutti i giorni.
     *
     * @pre code, name, description, category != null; price &ge; 0
     * @post getCode()==code, getName()==name, getDescription()==description,
     *       getPrice()==price, getCategory()==category;
     *       getAvailableDays() == {ALL_DAYS}
     */
    public MenuItem(String code, String name, String description, double price, Category category) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.availableDays = new HashSet<>();
        this.availableDays.add(DayOfWeek.ALL_DAYS); // Di default disponibile tutti i giorni
    }

    /**
     * Crea una pietanza disponibile solo nei giorni indicati.
     *
     * @pre code, name, description, category, availableDays != null; price &ge; 0;
     *      availableDays non contiene null
     * @post getCode()==code, getName()==name, getDescription()==description,
     *       getPrice()==price, getCategory()==category;
     *       getAvailableDays() è uguale (come insieme) a availableDays;
     *       l'insieme passato viene copiato: modifiche successive ad esso non influenzano this
     */
    public MenuItem(String code, String name, String description, double price, Category category, Set<DayOfWeek> availableDays) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.availableDays = new HashSet<>(availableDays);
    }

    /**
     * @pre true
     * @post restituisce il codice della pietanza (non null); non modifica lo stato
     */
    public String getCode() {
        return code;
    }

    /**
     * @pre true
     * @post restituisce il nome della pietanza; non modifica lo stato
     */
    public String getName() {
        return name;
    }

    /**
     * @pre true
     * @post restituisce la descrizione della pietanza; non modifica lo stato
     */
    public String getDescription() {
        return description;
    }

    /**
     * @pre true
     * @post restituisce il prezzo unitario (&ge; 0); non modifica lo stato
     */
    public double getPrice() {
        return price;
    }

    /**
     * @pre true
     * @post restituisce la categoria della pietanza; non modifica lo stato
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @pre true
     * @post restituisce una copia dell'insieme dei giorni di disponibilità;
     *       modificare la copia non altera lo stato di this
     */
    public Set<DayOfWeek> getAvailableDays() {
        return new HashSet<>(availableDays);
    }

    /**
     * Verifica se la pietanza è disponibile in un determinato giorno.
     *
     * @pre true (se day è null il risultato è true solo se ALL_DAYS è presente)
     * @post restituisce true sse l'insieme dei giorni contiene ALL_DAYS oppure day;
     *       non modifica lo stato
     */
    public boolean isAvailableOn(DayOfWeek day) {
        return availableDays.contains(DayOfWeek.ALL_DAYS) || availableDays.contains(day);
    }

    /**
     * Aggiunge un giorno di disponibilità.
     *
     * @pre day != null
     * @post getAvailableDays() contiene day; gli altri giorni sono invariati (idempotente)
     */
    public void addAvailableDay(DayOfWeek day) {
        availableDays.add(day);
    }

    /**
     * Rimuove un giorno di disponibilità.
     *
     * @pre true
     * @post getAvailableDays() non contiene day; gli altri giorni sono invariati.
     *       Nota: rimuovere un giorno specifico quando è presente ALL_DAYS non ha effetto
     *       sulla disponibilità (ALL_DAYS resta nell'insieme).
     */
    public void removeAvailableDay(DayOfWeek day) {
        availableDays.remove(day);
    }

    /**
     * Imposta la disponibilità solo per giorni specifici.
     *
     * @pre days != null e non contiene null
     * @post getAvailableDays() è uguale (come insieme) a days; l'insieme viene copiato
     */
    public void setAvailableDays(Set<DayOfWeek> days) {
        this.availableDays = new HashSet<>(days);
    }

    /**
     * Restituisce una rappresentazione dettagliata della pietanza.
     *
     * @pre true
     * @post restituisce una stringa multi-riga con nome, codice, descrizione, prezzo
     *       (2 decimali) e categoria; se ALL_DAYS non è tra i giorni, aggiunge anche
     *       l'elenco dei giorni di disponibilità; non modifica lo stato
     */
    public String getDetailedDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(code).append(")");
        sb.append("\n").append(description);
        sb.append("\nPrezzo: €").append(String.format("%.2f", price));
        sb.append("\nCategoria: ").append(category.getDisplayName());

        if (!availableDays.contains(DayOfWeek.ALL_DAYS)) {
            sb.append("\nDisponibile: ");
            for (DayOfWeek day : availableDays) {
                sb.append(day.name()).append(" ");
            }
        }

        return sb.toString();
    }

    /**
     * @pre true
     * @post restituisce una stringa di debug con codice, nome, prezzo, categoria e giorni;
     *       non modifica lo stato
     */
    @Override
    public String toString() {
        return "MenuItem{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", availableDays=" + availableDays +
                '}';
    }

    /**
     * @pre true
     * @post restituisce true sse obj è una MenuItem con lo stesso codice di this
     *       (relazione di equivalenza; dipende solo dal codice)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MenuItem menuItem = (MenuItem) obj;
        return code.equals(menuItem.code);
    }

    /**
     * @pre true
     * @post restituisce un valore dipendente solo dal codice; coerente con equals
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
