import java.util.List;
import java.util.ArrayList;

/**
 * <b>Mission.</b> Rappresenta un tavolo della pizzeria, singolo oppure risultante
 * dall'unione di più tavoli.
 * <p>
 * <b>Cosa conosce:</b> il proprio numero, la capienza (numero di coperti), se è interno o
 * esterno e i numeri dei tavoli che lo compongono quando è un tavolo unito.
 * <br>
 * <b>Cosa sa fare:</b> fornire tali informazioni, dire se è un tavolo unito, unirsi a un altro
 * tavolo sommando le capienze, descriversi a parole. Due tavoli sono uguali se hanno lo stesso
 * numero.
 *
 * <h2>Tipo di dato astratto</h2>
 * <b>Stato astratto.</b> Una quadrupla (n, c, interno, U) dove n è il numero del tavolo
 * (principale), c &gt; 0 la capienza in coperti, interno un booleano (interno/esterno) e
 * U la sequenza (senza ripetizioni) dei numeri dei tavoli uniti. Se U è vuota o ha un solo
 * elemento il tavolo è semplice; se ha almeno due elementi è un tavolo unito.
 * <p>
 * <b>Invarianti astratti.</b>
 * <ul>
 *   <li>n &ge; 1 e c &ge; 1;</li>
 *   <li>U non contiene duplicati;</li>
 *   <li>se U non è vuota allora n &isin; U;</li>
 *   <li>il tavolo è "unito" sse |U| &gt; 1;</li>
 *   <li>l'identità del tavolo è data dal solo numero n.</li>
 * </ul>
 * <b>Stato concreto.</b> Campi {@code tableNumber} (int), {@code capacity} (int),
 * {@code isIndoor} (boolean) e {@code joinedTables} (un {@code ArrayList<Integer>} privato).
 * <p>
 * <b>Invarianti di rappresentazione.</b>
 * <ul>
 *   <li>{@code joinedTables != null} e non contiene null né duplicati;</li>
 *   <li>{@code joinedTables} è vuota (tavolo creato con il primo costruttore e mai unito)
 *       oppure contiene {@code tableNumber};</li>
 *   <li>{@code tableNumber >= 1}, {@code capacity >= 1};</li>
 *   <li>la lista {@code joinedTables} appartiene solo a questo oggetto (copiata in ingresso
 *       e in uscita);</li>
 *   <li>{@code tableNumber} non cambia dopo la costruzione (stabilità di {@code hashCode}).</li>
 * </ul>
 * Nota: il codice attuale non verifica questi invarianti; è responsabilità del chiamante
 * rispettare le precondizioni.
 */
public class Table {
    private int tableNumber;
    private int capacity;
    private boolean isIndoor;
    private List<Integer> joinedTables; // Lista dei numeri di tavoli uniti a questo

    /**
     * Crea un tavolo semplice.
     *
     * @pre tableNumber &ge; 1, capacity &ge; 1
     * @post getTableNumber()==tableNumber, getCapacity()==capacity, isIndoor()==isIndoor;
     *       getJoinedTables() è vuota; isJoined()==false
     */
    public Table(int tableNumber, int capacity, boolean isIndoor) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.isIndoor = isIndoor;
        this.joinedTables = new ArrayList<>();
    }

    /**
     * Costruttore per tavoli uniti.
     *
     * @pre tableNumbers != null, non vuota, senza null né duplicati; totalCapacity &ge; 1
     * @post getTableNumber()==tableNumbers.get(0); getCapacity()==totalCapacity;
     *       isIndoor()==isIndoor; getJoinedTables() è uguale a tableNumbers (copia);
     *       isJoined() sse tableNumbers.size() &gt; 1
     */
    public Table(List<Integer> tableNumbers, int totalCapacity, boolean isIndoor) {
        this.tableNumber = tableNumbers.get(0); // Il primo tavolo sarà il numero principale
        this.capacity = totalCapacity;
        this.isIndoor = isIndoor;
        this.joinedTables = new ArrayList<>(tableNumbers);
    }

    /**
     * @pre true
     * @post restituisce il numero (principale) del tavolo; non modifica lo stato
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * @pre true
     * @post restituisce il numero di coperti (totale se il tavolo è unito); non modifica lo stato
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @pre true
     * @post restituisce true sse il tavolo è interno; non modifica lo stato
     */
    public boolean isIndoor() {
        return isIndoor;
    }

    /**
     * @pre true
     * @post restituisce una copia della lista dei numeri dei tavoli uniti (vuota per un tavolo
     *       semplice mai unito); modificare la copia non altera lo stato di this
     */
    public List<Integer> getJoinedTables() {
        return new ArrayList<>(joinedTables);
    }

    /**
     * @pre true
     * @post restituisce true sse il tavolo è composto da almeno due tavoli;
     *       non modifica lo stato
     */
    public boolean isJoined() {
        return joinedTables.size() > 1;
    }

    /**
     * Aggiunge un tavolo alla configurazione unita.
     *
     * @pre otherTable != null
     * @post getJoinedTables() contiene sia il numero di this sia quello di otherTable;
     *       se otherTable non era già unito, la capienza aumenta di otherTable.getCapacity(),
     *       altrimenti la capienza è invariata (idempotente);
     *       tableNumber e isIndoor sono invariati; otherTable non viene modificato
     */
    public void joinWith(Table otherTable) {
        if (!joinedTables.contains(tableNumber)) {
            joinedTables.add(tableNumber);
        }
        if (!joinedTables.contains(otherTable.getTableNumber())) {
            joinedTables.add(otherTable.getTableNumber());
            this.capacity += otherTable.getCapacity();
        }
    }

    /**
     * Restituisce una stringa descrittiva del tavolo.
     *
     * @pre true
     * @post se isJoined(): "Tavoli n1, n2, ... (uniti, C coperti)";
     *       altrimenti "Tavolo n (C coperti)"; non modifica lo stato
     */
    public String getTableDescription() {
        if (isJoined()) {
            return "Tavoli " + joinedTables.toString().replace("[", "").replace("]", "") +
                   " (uniti, " + capacity + " coperti)";
        } else {
            return "Tavolo " + tableNumber + " (" + capacity + " coperti)";
        }
    }

    /**
     * @pre true
     * @post restituisce una stringa di debug con tutti i campi; non modifica lo stato
     */
    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", capacity=" + capacity +
                ", isIndoor=" + isIndoor +
                ", joinedTables=" + joinedTables +
                '}';
    }

    /**
     * @pre true
     * @post restituisce true sse obj è un Table con lo stesso numero principale di this
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Table table = (Table) obj;
        return tableNumber == table.tableNumber;
    }

    /**
     * @pre true
     * @post restituisce un valore dipendente solo dal numero del tavolo; coerente con equals
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(tableNumber);
    }
}
