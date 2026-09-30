import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * <b>Mission.</b> Rappresenta un ordine effettuato da un tavolo e servito da un cameriere.
 * <p>
 * <b>Cosa conosce:</b> un identificativo univoco, l'istante di creazione, il tavolo, il
 * cameriere, le righe d'ordine (pietanza + quantità), lo stato di avanzamento
 * (in preparazione, servito, pagato), il metodo di pagamento (dopo il pagamento) e il totale.
 * <br>
 * <b>Cosa sa fare:</b> aggiungere, rimuovere e modificare righe d'ordine, calcolare il totale,
 * cambiare stato, registrare il pagamento, dire se è ancora attivo, raggruppare le righe per
 * categoria e produrre riassunti testuali. Due ordini sono uguali se hanno lo stesso id.
 *
 * <h2>Tipo di dato astratto</h2>
 * <b>Stato astratto.</b> Una tupla (id, istante, tavolo, cameriere, R, stato, metodo) dove
 * R è una collezione di coppie (pietanza, quantità) con al più una coppia per pietanza;
 * stato &isin; {IN_PREPARATION, SERVED, PAID}; metodo è il metodo di pagamento oppure
 * "nessuno". Il totale è una quantità derivata: somma, su R, di prezzo &times; quantità.
 * <p>
 * <b>Invarianti astratti.</b>
 * <ul>
 *   <li>id univoco tra tutti gli ordini creati e immutabile;</li>
 *   <li>tavolo e cameriere non null e immutabili;</li>
 *   <li>in R ogni pietanza compare al più una volta, con quantità &gt; 0 se inserita tramite
 *       {@code addItem} con quantità positiva e {@code updateItemQuantity};</li>
 *   <li>totale = &Sigma; prezzo(p) &times; q(p) per (p,q) in R; totale &ge; 0;</li>
 *   <li>l'ordine è attivo sse stato != PAID;</li>
 *   <li>un ordine creato ha stato IN_PREPARATION, nessun metodo di pagamento e totale 0;</li>
 *   <li>dopo {@code pay(m)}: stato == PAID e metodo == m.</li>
 * </ul>
 * <b>Stato concreto.</b> Campi {@code orderId} (int), {@code timestamp} (LocalDateTime),
 * {@code table} (Table), {@code waiter} (Waiter), {@code items} (un
 * {@code ArrayList<OrderItem>}), {@code status}, {@code paymentMethod}, {@code totalAmount}
 * (double, cache del totale) e il contatore statico di classe {@code nextOrderId}.
 * <p>
 * <b>Invarianti di rappresentazione.</b>
 * <ul>
 *   <li>{@code nextOrderId} &gt; orderId di ogni ordine creato (il contatore è strettamente
 *       crescente, quindi gli id sono distinti);</li>
 *   <li>{@code timestamp, table, waiter, items, status} != null;</li>
 *   <li>{@code items} non contiene null e non contiene due righe con la stessa
 *       {@code MenuItem} (secondo {@code equals});</li>
 *   <li>{@code items} appartiene solo a questo oggetto (le righe {@code OrderItem} sono però
 *       condivise con le copie restituite da {@link #getItems()}, che le espone per
 *       riferimento);</li>
 *   <li>{@code totalAmount} è una cache: dopo ogni operazione pubblica che modifica
 *       {@code items} vale la somma dei subtotali; {@link #getTotalAmount()} la ricalcola
 *       comunque prima di restituirla;</li>
 *   <li>{@code paymentMethod == null} finché {@code pay} non viene invocato. Nota:
 *       {@code setStatus} non aggiorna {@code paymentMethod}, quindi è possibile avere
 *       {@code status == PAID} con {@code paymentMethod == null} (e, dopo un {@code pay},
 *       riportare lo stato indietro mantenendo il metodo); chi usa l'ordine dovrebbe
 *       passare da {@code pay} per chiudere un ordine.</li>
 * </ul>
 * Il codice non verifica i null né la positività delle quantità; è responsabilità del chiamante
 * rispettare le precondizioni.
 */
public class Order {
    /**
     * Stato di avanzamento di un ordine, con nome da mostrare. Tipo enumerato immutabile.
     */
    public enum OrderStatus {
        IN_PREPARATION("In preparazione"),
        SERVED("Servito"),
        PAID("Pagato");

        private final String displayName;

        OrderStatus(String displayName) {
            this.displayName = displayName;
        }

        /**
         * @pre true
         * @post restituisce il nome non null da mostrare per lo stato
         */
        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * Modalità di pagamento di un ordine, con nome da mostrare. Tipo enumerato immutabile.
     */
    public enum PaymentMethod {
        CASH("Contanti"),
        CARD("Carta"),
        MEAL_VOUCHER("Buoni pasto");

        private final String displayName;

        PaymentMethod(String displayName) {
            this.displayName = displayName;
        }

        /**
         * @pre true
         * @post restituisce il nome non null da mostrare per il metodo di pagamento
         */
        public String getDisplayName() {
            return displayName;
        }
    }

    /**
     * <b>Mission.</b> Riga di un ordine: conosce una pietanza e la quantità ordinata, e sa
     * calcolare il proprio subtotale.
     *
     * <h2>Tipo di dato astratto</h2>
     * <b>Stato astratto.</b> Una coppia (pietanza, quantità).
     * <br>
     * <b>Invarianti astratti.</b> pietanza non null; quantità &ge; 0 (normalmente &gt; 0);
     * subtotale = prezzo della pietanza &times; quantità.
     * <br>
     * <b>Stato concreto.</b> Campi {@code menuItem} (MenuItem) e {@code quantity} (int).
     * <br>
     * <b>Invarianti di rappresentazione.</b> {@code menuItem != null} e non cambia dopo la
     * costruzione; {@code quantity} è modificabile solo tramite {@link #setQuantity(int)};
     * il codice non impone {@code quantity > 0}, quindi è l'{@link Order} a rimuovere le righe
     * con quantità non positiva in {@code updateItemQuantity}.
     */
    public static class OrderItem {
        private MenuItem menuItem;
        private int quantity;

        /**
         * @pre menuItem != null; quantity &gt; 0
         * @post getMenuItem()==menuItem; getQuantity()==quantity
         */
        public OrderItem(MenuItem menuItem, int quantity) {
            this.menuItem = menuItem;
            this.quantity = quantity;
        }

        /**
         * @pre true
         * @post restituisce la pietanza della riga; non modifica lo stato
         */
        public MenuItem getMenuItem() {
            return menuItem;
        }

        /**
         * @pre true
         * @post restituisce la quantità; non modifica lo stato
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * @pre quantity &gt; 0
         * @post getQuantity()==quantity; la pietanza è invariata.
         *       Attenzione: l'eventuale {@link Order} che contiene la riga non aggiorna la
         *       cache del totale finché non viene richiesto {@code getTotalAmount()}.
         */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        /**
         * @pre true
         * @post restituisce menuItem.getPrice() * quantity; non modifica lo stato
         */
        public double getSubtotal() {
            return menuItem.getPrice() * quantity;
        }

        /**
         * @pre true
         * @post restituisce una stringa "Nx nome - €subtotale" (2 decimali);
         *       non modifica lo stato
         */
        @Override
        public String toString() {
            return quantity + "x " + menuItem.getName() + " - €" + String.format("%.2f", getSubtotal());
        }
    }

    private static int nextOrderId = 1;

    private int orderId;
    private LocalDateTime timestamp;
    private Table table;
    private Waiter waiter;
    private List<OrderItem> items;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private double totalAmount;

    /**
     * Crea un nuovo ordine vuoto.
     *
     * @pre table != null; waiter != null
     * @post getOrderId() è un id mai assegnato prima ad altri ordini;
     *       getTimestamp() è l'istante corrente; getTable()==table; getWaiter()==waiter;
     *       getItems() è vuota; getStatus()==IN_PREPARATION; getPaymentMethod()==null;
     *       getTotalAmount()==0; isActive()==true
     */
    public Order(Table table, Waiter waiter) {
        this.orderId = nextOrderId++;
        this.timestamp = LocalDateTime.now();
        this.table = table;
        this.waiter = waiter;
        this.items = new ArrayList<>();
        this.status = OrderStatus.IN_PREPARATION;
        this.paymentMethod = null; // Sarà impostato al momento del pagamento
        this.totalAmount = 0.0;
    }

    /**
     * @pre true
     * @post restituisce l'id univoco dell'ordine; non modifica lo stato
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @pre true
     * @post restituisce l'istante di creazione dell'ordine; non modifica lo stato
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @pre true
     * @post restituisce il tavolo dell'ordine; non modifica lo stato
     */
    public Table getTable() {
        return table;
    }

    /**
     * @pre true
     * @post restituisce il cameriere dell'ordine; non modifica lo stato
     */
    public Waiter getWaiter() {
        return waiter;
    }

    /**
     * @pre true
     * @post restituisce una nuova lista con le righe d'ordine nell'ordine di inserimento;
     *       aggiungere/togliere elementi dalla lista non altera l'ordine, ma le righe
     *       {@code OrderItem} restituite sono le stesse dell'ordine
     */
    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * @pre true
     * @post restituisce lo stato corrente; non modifica lo stato dell'ordine
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * @pre true
     * @post restituisce il metodo di pagamento, oppure null se l'ordine non è stato pagato
     *       tramite {@code pay}
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @pre true
     * @post restituisce la somma dei subtotali delle righe (0 se non ci sono righe);
     *       il risultato è &ge; 0
     */
    public double getTotalAmount() {
        calculateTotal();
        return totalAmount;
    }

    /**
     * Aggiunge un elemento all'ordine; se la pietanza è già presente ne aumenta la quantità.
     *
     * @pre menuItem != null; quantity &gt; 0
     * @post se menuItem era presente, la sua quantità è aumentata di quantity; altrimenti
     *       esiste una nuova riga (menuItem, quantity) in coda; le altre righe sono invariate;
     *       getTotalAmount() è aumentato di menuItem.getPrice() * quantity
     */
    public void addItem(MenuItem menuItem, int quantity) {
        // Controlla se l'elemento esiste già e aggiorna la quantità
        for (OrderItem item : items) {
            if (item.getMenuItem().equals(menuItem)) {
                item.setQuantity(item.getQuantity() + quantity);
                calculateTotal();
                return;
            }
        }
        // Se non esiste, aggiunge un nuovo elemento
        items.add(new OrderItem(menuItem, quantity));
        calculateTotal();
    }

    /**
     * Rimuove un elemento dall'ordine.
     *
     * @pre true
     * @post nessuna riga dell'ordine ha pietanza uguale a menuItem; le altre righe sono
     *       invariate (nessun effetto se la pietanza non era presente); il totale è aggiornato
     */
    public void removeItem(MenuItem menuItem) {
        items.removeIf(item -> item.getMenuItem().equals(menuItem));
        calculateTotal();
    }

    /**
     * Aggiorna la quantità di un elemento.
     *
     * @pre true
     * @post se menuItem non è presente: nessun cambiamento;
     *       se è presente e newQuantity &gt; 0: la sua quantità è newQuantity;
     *       se è presente e newQuantity &le; 0: la riga è rimossa;
     *       le altre righe sono invariate; il totale è aggiornato
     */
    public void updateItemQuantity(MenuItem menuItem, int newQuantity) {
        for (OrderItem item : items) {
            if (item.getMenuItem().equals(menuItem)) {
                if (newQuantity <= 0) {
                    removeItem(menuItem);
                } else {
                    item.setQuantity(newQuantity);
                }
                calculateTotal();
                return;
            }
        }
    }

    // Calcola il totale dell'ordine
    private void calculateTotal() {
        totalAmount = items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    /**
     * Cambia lo stato dell'ordine.
     *
     * @pre status != null
     * @post getStatus()==status; le righe, il tavolo, il cameriere e il metodo di pagamento
     *       sono invariati (per pagare usare {@link #pay(PaymentMethod)})
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Imposta il metodo di pagamento e cambia lo stato a pagato.
     *
     * @pre paymentMethod != null
     * @post getPaymentMethod()==paymentMethod; getStatus()==PAID; isActive()==false;
     *       le righe sono invariate
     */
    public void pay(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.status = OrderStatus.PAID;
    }

    /**
     * Verifica se l'ordine è attivo (non ancora pagato).
     *
     * @pre true
     * @post restituisce true sse getStatus() != PAID; non modifica lo stato
     */
    public boolean isActive() {
        return status != OrderStatus.PAID;
    }

    /**
     * Restituisce un riassunto dettagliato dell'ordine.
     *
     * @pre true
     * @post restituisce una stringa multi-riga con id, data/ora, tavolo, cameriere, stato,
     *       metodo di pagamento (solo se presente), righe d'ordine e totale;
     *       non modifica lo stato osservabile
     */
    public String getDetailedSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ORDINE #").append(orderId).append(" ===\n");
        sb.append("Data/Ora: ").append(timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        sb.append("Tavolo: ").append(table.getTableDescription()).append("\n");
        sb.append("Cameriere: ").append(waiter.getFullName()).append(" (").append(waiter.getCode()).append(")\n");
        sb.append("Stato: ").append(status.getDisplayName()).append("\n");

        if (paymentMethod != null) {
            sb.append("Pagamento: ").append(paymentMethod.getDisplayName()).append("\n");
        }

        sb.append("\n--- DETTAGLIO ORDINE ---\n");
        for (OrderItem item : items) {
            sb.append(item.toString()).append("\n");
        }

        sb.append("\nTOTALE: €").append(String.format("%.2f", getTotalAmount()));

        return sb.toString();
    }

    /**
     * Restituisce un riassunto breve per la visualizzazione.
     *
     * @pre true
     * @post restituisce una riga "Ordine #id - descrizione tavolo - €totale (stato)";
     *       non modifica lo stato osservabile
     */
    public String getBriefSummary() {
        return String.format("Ordine #%d - %s - €%.2f (%s)",
                orderId,
                table.getTableDescription(),
                getTotalAmount(),
                status.getDisplayName());
    }

    /**
     * Raggruppa gli elementi per categoria.
     *
     * @pre true
     * @post restituisce una nuova mappa in cui ogni chiave è una categoria presente tra le
     *       righe e il valore è la lista (non vuota) delle righe di quella categoria, nell'ordine
     *       di inserimento; la mappa è vuota se l'ordine non ha righe;
     *       l'unione dei valori coincide con getItems(); non modifica lo stato dell'ordine
     */
    public Map<MenuItem.Category, List<OrderItem>> getItemsByCategory() {
        Map<MenuItem.Category, List<OrderItem>> itemsByCategory = new HashMap<>();

        for (OrderItem item : items) {
            MenuItem.Category category = item.getMenuItem().getCategory();
            itemsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
        }

        return itemsByCategory;
    }

    /**
     * @pre true
     * @post restituisce una stringa di debug con id, istante, tavolo, cameriere, stato e totale
     *       (quest'ultimo è il valore in cache); non modifica lo stato
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", timestamp=" + timestamp +
                ", table=" + table.getTableDescription() +
                ", waiter=" + waiter.getFullName() +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                '}';
    }

    /**
     * @pre true
     * @post restituisce true sse obj è un Order con lo stesso id di this
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return orderId == order.orderId;
    }

    /**
     * @pre true
     * @post restituisce un valore dipendente solo dall'id; coerente con equals
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(orderId);
    }
}
