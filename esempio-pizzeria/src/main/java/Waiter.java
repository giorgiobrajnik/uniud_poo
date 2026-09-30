import java.util.List;
import java.util.ArrayList;

/**
 * <b>Mission.</b> Rappresenta un cameriere della pizzeria.
 * <p>
 * <b>Cosa conosce:</b> il proprio codice, nome e cognome e i numeri dei tavoli di cui è
 * responsabile.
 * <br>
 * <b>Cosa sa fare:</b> fornire tali informazioni, ricevere o perdere l'assegnazione di tavoli,
 * dire se è responsabile di un tavolo o di almeno uno tra un gruppo di tavoli.
 * Due camerieri sono uguali se hanno lo stesso codice.
 *
 * <h2>Tipo di dato astratto</h2>
 * <b>Stato astratto.</b> Una tripla (codice, nome, cognome) più un insieme T di numeri di
 * tavolo assegnati (con ordine di assegnazione conservato). Codice, nome e cognome non
 * cambiano; T varia nel tempo.
 * <p>
 * <b>Invarianti astratti.</b>
 * <ul>
 *   <li>il codice identifica univocamente il cameriere;</li>
 *   <li>T non contiene duplicati: un tavolo è assegnato al più una volta;</li>
 *   <li>il nome completo è "nome cognome".</li>
 * </ul>
 * <b>Stato concreto.</b> Campi {@code code}, {@code firstName}, {@code lastName} (String) e
 * {@code assignedTables} (un {@code ArrayList<Integer>} privato).
 * <p>
 * <b>Invarianti di rappresentazione.</b>
 * <ul>
 *   <li>{@code code, firstName, lastName} != null;</li>
 *   <li>{@code assignedTables != null}, non contiene null né duplicati;</li>
 *   <li>{@code assignedTables} appartiene solo a questo oggetto (copiata in uscita);</li>
 *   <li>{@code code} non cambia dopo la costruzione (stabilità di {@code hashCode}).</li>
 * </ul>
 * Nota: il codice attuale non verifica i null in costruzione; è responsabilità del chiamante.
 * Il cameriere conosce i tavoli solo per numero, non tramite riferimento a {@link Table}.
 */
public class Waiter {
    private String code;
    private String firstName;
    private String lastName;
    private List<Integer> assignedTables; // Lista dei numeri di tavoli assegnati

    /**
     * Crea un cameriere senza tavoli assegnati.
     *
     * @pre code, firstName, lastName != null
     * @post getCode()==code, getFirstName()==firstName, getLastName()==lastName;
     *       getAssignedTables() è vuota
     */
    public Waiter(String code, String firstName, String lastName) {
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.assignedTables = new ArrayList<>();
    }

    /**
     * @pre true
     * @post restituisce il codice del cameriere; non modifica lo stato
     */
    public String getCode() {
        return code;
    }

    /**
     * @pre true
     * @post restituisce il nome di battesimo; non modifica lo stato
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @pre true
     * @post restituisce il cognome; non modifica lo stato
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @pre true
     * @post restituisce la stringa "nome cognome"; non modifica lo stato
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * @pre true
     * @post restituisce una copia della lista dei tavoli assegnati, nell'ordine di assegnazione;
     *       modificare la copia non altera lo stato di this
     */
    public List<Integer> getAssignedTables() {
        return new ArrayList<>(assignedTables);
    }

    /**
     * Assegna un tavolo al cameriere.
     *
     * @pre tableNumber &ge; 1
     * @post isAssignedToTable(tableNumber)==true; se era già assegnato non cambia nulla
     *       (idempotente, nessun duplicato); gli altri tavoli sono invariati
     */
    public void assignTable(int tableNumber) {
        if (!assignedTables.contains(tableNumber)) {
            assignedTables.add(tableNumber);
        }
    }

    /**
     * Assegna una lista di tavoli al cameriere.
     *
     * @pre tableNumbers != null e non contiene null; ogni numero &ge; 1
     * @post per ogni t in tableNumbers, isAssignedToTable(t)==true; nessun duplicato;
     *       i tavoli già assegnati restano assegnati
     */
    public void assignTables(List<Integer> tableNumbers) {
        for (int tableNumber : tableNumbers) {
            assignTable(tableNumber);
        }
    }

    /**
     * Rimuove l'assegnazione di un tavolo.
     *
     * @pre true
     * @post isAssignedToTable(tableNumber)==false; gli altri tavoli sono invariati
     *       (nessun effetto se il tavolo non era assegnato)
     */
    public void unassignTable(int tableNumber) {
        assignedTables.remove(Integer.valueOf(tableNumber));
    }

    /**
     * Verifica se un tavolo è assegnato a questo cameriere.
     *
     * @pre true
     * @post restituisce true sse tableNumber è tra i tavoli assegnati; non modifica lo stato
     */
    public boolean isAssignedToTable(int tableNumber) {
        return assignedTables.contains(tableNumber);
    }

    /**
     * Verifica se almeno uno dei tavoli uniti è assegnato al cameriere.
     *
     * @pre tableNumbers != null e non contiene null
     * @post restituisce true sse esiste t in tableNumbers con isAssignedToTable(t);
     *       false se la lista è vuota; non modifica lo stato
     */
    public boolean isAssignedToAnyTable(List<Integer> tableNumbers) {
        for (int tableNumber : tableNumbers) {
            if (assignedTables.contains(tableNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @pre true
     * @post restituisce una stringa di debug con tutti i campi; non modifica lo stato
     */
    @Override
    public String toString() {
        return "Waiter{" +
                "code='" + code + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", assignedTables=" + assignedTables +
                '}';
    }

    /**
     * @pre true
     * @post restituisce true sse obj è un Waiter con lo stesso codice di this
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Waiter waiter = (Waiter) obj;
        return code.equals(waiter.code);
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
