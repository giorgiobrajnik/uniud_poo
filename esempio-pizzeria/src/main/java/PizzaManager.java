import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <b>Mission.</b> Facciata (punto di ingresso) del sistema di gestione della pizzeria.
 * <p>
 * <b>Cosa conosce:</b> i tavoli, i camerieri (con i tavoli loro assegnati), il menu e tutti
 * gli ordini registrati.
 * <br>
 * <b>Cosa sa fare:</b> cercare tavoli, camerieri e pietanze; creare ordini per un tavolo o per
 * più tavoli uniti, assegnando il cameriere responsabile; elencare gli ordini attivi; calcolare,
 * mostrare e incassare il conto di un tavolo; produrre il resoconto giornaliero; mostrare il
 * menu e l'assegnazione dei tavoli. All'atto della creazione popola la pizzeria con 25 tavoli,
 * 6 camerieri e un menu di esempio.
 *
 * <h2>Tipo di dato astratto</h2>
 * <b>Stato astratto.</b> Una quadrupla (T, W, M, O) dove T è l'insieme dei tavoli fisici della
 * pizzeria, W l'insieme dei camerieri, M il menu (insieme di pietanze) e O la sequenza
 * cronologica degli ordini creati. L'assegnazione tavolo&rarr;cameriere è parte dello stato dei
 * camerieri.
 * <p>
 * <b>Invarianti astratti.</b>
 * <ul>
 *   <li>i tavoli hanno numeri distinti; i camerieri hanno codici distinti;</li>
 *   <li>ogni tavolo fisico è assegnato ad almeno un cameriere subito dopo la creazione del
 *       sistema (e in tal caso {@code createOrder} ha successo per quel tavolo);</li>
 *   <li>ogni ordine in O si riferisce a tavoli esistenti in T e a un cameriere in W, e ha un id
 *       distinto dagli altri;</li>
 *   <li>gli ordini non vengono mai eliminati; un ordine pagato non torna attivo tramite questa
 *       classe;</li>
 *   <li>il conto di un tavolo è la somma dei totali degli ordini attivi che lo riguardano
 *       (direttamente o come parte di un tavolo unito).</li>
 * </ul>
 * <b>Stato concreto.</b> Quattro {@code ArrayList} privati: {@code tables}, {@code waiters},
 * {@code menu}, {@code orders}.
 * <p>
 * <b>Invarianti di rappresentazione.</b>
 * <ul>
 *   <li>le quattro liste sono != null e non contengono null;</li>
 *   <li>{@code tables} non contiene due tavoli con lo stesso numero (equals su numero);</li>
 *   <li>{@code waiters} non contiene due camerieri con lo stesso codice;</li>
 *   <li>{@code menu} non contiene due pietanze con lo stesso codice
 *       (non imposto da {@code addMenuItem}: va garantito dal chiamante);</li>
 *   <li>{@code orders} è ordinata per istante di creazione (append-only);</li>
 *   <li>gli oggetti {@code Table} contenuti in {@code tables} non sono unioni: i tavoli uniti
 *       esistono solo dentro gli ordini (creati da {@code createOrder(List)});</li>
 *   <li>le liste non sono mai esposte per riferimento (si restituiscono copie), ma gli elementi
 *       contenuti sono condivisi con i chiamanti.</li>
 * </ul>
 */
public class PizzaManager {
    private List<Table> tables;
    private List<Waiter> waiters;
    private List<MenuItem> menu;
    private List<Order> orders;

    /**
     * Crea il sistema con la configurazione predefinita della pizzeria.
     *
     * @pre true
     * @post getTables() contiene 25 tavoli (1-15 interni, 16-25 esterni); getWaiters() contiene
     *       6 camerieri, ciascuno con i propri tavoli, e ogni tavolo ha un cameriere;
     *       getMenu() contiene il menu di esempio; getOrders() è vuota
     */
    public PizzaManager() {
        this.tables = new ArrayList<>();
        this.waiters = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
        initializePizzeria();
    }

    // Inizializza la pizzeria con tavoli e camerieri predefiniti
    private void initializePizzeria() {
        // Creazione tavoli interni (15 tavoli, da 2 a 8 coperti)
        for (int i = 1; i <= 15; i++) {
            int capacity = 2 + (i % 7); // Variazione da 2 a 8 coperti
            tables.add(new Table(i, capacity, true));
        }

        // Creazione tavoli esterni (10 tavoli, da 2 a 6 coperti)
        for (int i = 16; i <= 25; i++) {
            int capacity = 2 + ((i - 16) % 5); // Variazione da 2 a 6 coperti
            tables.add(new Table(i, capacity, false));
        }

        // Creazione camerieri (6 camerieri)
        waiters.add(new Waiter("CAM001", "Mario", "Rossi"));
        waiters.add(new Waiter("CAM002", "Giulia", "Bianchi"));
        waiters.add(new Waiter("CAM003", "Luca", "Verdi"));
        waiters.add(new Waiter("CAM004", "Anna", "Neri"));
        waiters.add(new Waiter("CAM005", "Paolo", "Gialli"));
        waiters.add(new Waiter("CAM006", "Sara", "Blu"));

        // Assegnazione tavoli ai camerieri (circa 4-5 tavoli per cameriere)
        assignTablesToWaiters();

        // Inizializzazione menu di esempio
        initializeMenu();
    }

    // Assegna i tavoli ai camerieri
    private void assignTablesToWaiters() {
        int tablesPerWaiter = tables.size() / waiters.size();
        int extraTables = tables.size() % waiters.size();

        int tableIndex = 0;
        for (int i = 0; i < waiters.size(); i++) {
            Waiter waiter = waiters.get(i);
            int tablesToAssign = tablesPerWaiter + (i < extraTables ? 1 : 0);

            for (int j = 0; j < tablesToAssign && tableIndex < tables.size(); j++) {
                waiter.assignTable(tables.get(tableIndex).getTableNumber());
                tableIndex++;
            }
        }
    }

    // Inizializza il menu con alcuni esempi
    private void initializeMenu() {
        // Pizze
        menu.add(new MenuItem("P001", "Margherita", "Pomodoro, mozzarella, basilico", 7.50, MenuItem.Category.PIZZA));
        menu.add(new MenuItem("P002", "Marinara", "Pomodoro, aglio, origano", 6.00, MenuItem.Category.PIZZA));
        menu.add(new MenuItem("P003", "Quattro Stagioni", "Pomodoro, mozzarella, prosciutto, funghi, carciofi, olive", 9.50, MenuItem.Category.PIZZA));

        // Antipasti
        menu.add(new MenuItem("A001", "Antipasto della casa", "Selezione di salumi e formaggi locali", 12.00, MenuItem.Category.ANTIPASTO));
        menu.add(new MenuItem("A002", "Bruschette", "Pane tostato con pomodoro e basilico", 6.50, MenuItem.Category.ANTIPASTO));

        // Primi piatti
        menu.add(new MenuItem("PR001", "Spaghetti alla carbonara", "Spaghetti con uova, guanciale e pecorino", 9.00, MenuItem.Category.PRIMO));
        menu.add(new MenuItem("PR002", "Risotto ai porcini", "Risotto con funghi porcini freschi", 11.00, MenuItem.Category.PRIMO));

        // Secondi di carne
        menu.add(new MenuItem("SC001", "Tagliata di manzo", "Tagliata di manzo con rucola e grana", 18.00, MenuItem.Category.SECONDO_CARNE));

        // Secondi di pesce
        Set<MenuItem.DayOfWeek> fishDays = new HashSet<>();
        fishDays.add(MenuItem.DayOfWeek.FRIDAY);
        fishDays.add(MenuItem.DayOfWeek.SATURDAY);
        menu.add(new MenuItem("SP001", "Branzino al sale", "Branzino fresco cotto al sale", 16.00, MenuItem.Category.SECONDO_PESCE, fishDays));

        // Contorni
        menu.add(new MenuItem("C001", "Insalata mista", "Insalata di stagione", 4.50, MenuItem.Category.CONTORNO));
        menu.add(new MenuItem("C002", "Patate al forno", "Patate arrosto con rosmarino", 5.00, MenuItem.Category.CONTORNO));

        // Dolci
        menu.add(new MenuItem("D001", "Tiramisù", "Tiramisù della casa", 5.50, MenuItem.Category.DOLCE));

        // Bevande
        menu.add(new MenuItem("B001", "Acqua naturale", "Acqua naturale 1L", 2.00, MenuItem.Category.BEVANDA));
        menu.add(new MenuItem("B002", "Vino della casa rosso", "Vino della casa (bottiglia)", 15.00, MenuItem.Category.BEVANDA));
        menu.add(new MenuItem("B003", "Birra media", "Birra alla spina 40cl", 4.50, MenuItem.Category.BEVANDA));
    }

    // Metodi getter

    /**
     * @pre true
     * @post restituisce una nuova lista con tutti i tavoli; modificare la lista non altera
     *       il sistema
     */
    public List<Table> getTables() {
        return new ArrayList<>(tables);
    }

    /**
     * @pre true
     * @post restituisce una nuova lista con tutti i camerieri; modificare la lista non altera
     *       il sistema
     */
    public List<Waiter> getWaiters() {
        return new ArrayList<>(waiters);
    }

    /**
     * @pre true
     * @post restituisce una nuova lista con tutte le pietanze del menu; modificare la lista non
     *       altera il sistema
     */
    public List<MenuItem> getMenu() {
        return new ArrayList<>(menu);
    }

    /**
     * @pre true
     * @post restituisce una nuova lista con tutti gli ordini creati, in ordine cronologico;
     *       modificare la lista non altera il sistema
     */
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Trova un tavolo per numero.
     *
     * @pre true
     * @post restituisce il tavolo il cui numero è tableNumber, oppure null se non esiste;
     *       non modifica lo stato
     */
    public Table findTable(int tableNumber) {
        return tables.stream()
                .filter(table -> table.getTableNumber() == tableNumber)
                .findFirst()
                .orElse(null);
    }

    /**
     * Trova un cameriere per codice.
     *
     * @pre waiterCode != null
     * @post restituisce il cameriere con quel codice, oppure null se non esiste;
     *       non modifica lo stato
     */
    public Waiter findWaiter(String waiterCode) {
        return waiters.stream()
                .filter(waiter -> waiter.getCode().equals(waiterCode))
                .findFirst()
                .orElse(null);
    }

    /**
     * Trova una pietanza per codice.
     *
     * @pre itemCode != null
     * @post restituisce la pietanza con quel codice, oppure null se non esiste;
     *       non modifica lo stato
     */
    public MenuItem findMenuItem(String itemCode) {
        return menu.stream()
                .filter(item -> item.getCode().equals(itemCode))
                .findFirst()
                .orElse(null);
    }

    /**
     * Trova il cameriere responsabile di un tavolo.
     *
     * @pre true
     * @post restituisce il primo cameriere (nell'ordine di getWaiters()) a cui il tavolo è
     *       assegnato, oppure null se nessuno; non modifica lo stato
     */
    public Waiter findWaiterForTable(int tableNumber) {
        return waiters.stream()
                .filter(waiter -> waiter.isAssignedToTable(tableNumber))
                .findFirst()
                .orElse(null);
    }

    /**
     * Crea un nuovo ordine per un tavolo.
     *
     * @pre esiste un tavolo con numero tableNumber e almeno un cameriere gli è assegnato
     * @post restituisce un nuovo ordine vuoto, in preparazione, associato al tavolo e al suo
     *       cameriere; l'ordine è aggiunto in coda a getOrders().
     * @throws IllegalArgumentException se il tavolo non esiste o non ha cameriere assegnato;
     *         in tal caso lo stato non cambia
     */
    public Order createOrder(int tableNumber) {
        Table table = findTable(tableNumber);
        if (table == null) {
            throw new IllegalArgumentException("Tavolo non trovato: " + tableNumber);
        }

        Waiter waiter = findWaiterForTable(tableNumber);
        if (waiter == null) {
            throw new IllegalArgumentException("Nessun cameriere assegnato al tavolo: " + tableNumber);
        }

        Order order = new Order(table, waiter);
        orders.add(order);
        return order;
    }

    /**
     * Crea un ordine per tavoli uniti.
     *
     * @pre tableNumbers != null e non vuota; ogni numero corrisponde a un tavolo esistente;
     *      almeno uno dei tavoli è assegnato a un cameriere; i numeri sono distinti
     * @post restituisce un nuovo ordine vuoto il cui tavolo è un tavolo unito (numero principale
     *       = primo numero della lista, capienza = somma delle capienze, interno sse tutti i
     *       tavoli sono interni); il cameriere è il primo di getWaiters() responsabile di almeno
     *       uno dei tavoli; l'ordine è aggiunto in coda a getOrders(); i tavoli in getTables()
     *       non sono modificati
     * @throws IllegalArgumentException se la lista è vuota, se un tavolo non esiste o se nessun
     *         cameriere è assegnato ad alcuno dei tavoli; in tal caso lo stato non cambia
     */
    public Order createOrder(List<Integer> tableNumbers) {
        if (tableNumbers.isEmpty()) {
            throw new IllegalArgumentException("Lista tavoli vuota");
        }

        // Verifica che tutti i tavoli esistano
        List<Table> tablesToJoin = new ArrayList<>();
        int totalCapacity = 0;
        boolean allIndoor = true;

        for (int tableNumber : tableNumbers) {
            Table table = findTable(tableNumber);
            if (table == null) {
                throw new IllegalArgumentException("Tavolo non trovato: " + tableNumber);
            }
            tablesToJoin.add(table);
            totalCapacity += table.getCapacity();
            if (!table.isIndoor()) {
                allIndoor = false;
            }
        }

        // Trova un cameriere che sia responsabile di almeno uno dei tavoli
        Waiter waiter = waiters.stream()
                .filter(w -> w.isAssignedToAnyTable(tableNumbers))
                .findFirst()
                .orElse(null);

        if (waiter == null) {
            throw new IllegalArgumentException("Nessun cameriere assegnato ai tavoli: " + tableNumbers);
        }

        // Crea un tavolo unito
        Table joinedTable = new Table(tableNumbers, totalCapacity, allIndoor);

        Order order = new Order(joinedTable, waiter);
        orders.add(order);
        return order;
    }

    // 1. VISUALIZZARE GLI ORDINI ATTIVI IN TEMPO REALE

    /**
     * @pre true
     * @post restituisce una nuova lista degli ordini non ancora pagati, dal più vecchio al più
     *       recente; non modifica lo stato
     */
    public List<Order> getActiveOrders() {
        return orders.stream()
                .filter(Order::isActive)
                .sorted(Comparator.comparing(Order::getTimestamp))
                .collect(Collectors.toList());
    }

    /**
     * Stampa su standard output gli ordini attivi.
     *
     * @pre true
     * @post stampa "NESSUN ORDINE ATTIVO" se non ce ne sono, altrimenti l'istante di
     *       aggiornamento e il riassunto dettagliato di ogni ordine attivo; lo stato del sistema
     *       non cambia
     */
    public void displayActiveOrders() {
        List<Order> activeOrders = getActiveOrders();

        if (activeOrders.isEmpty()) {
            System.out.println("=== NESSUN ORDINE ATTIVO ===");
            return;
        }

        System.out.println("=== ORDINI ATTIVI ===");
        System.out.println("Aggiornamento: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println();

        for (Order order : activeOrders) {
            System.out.println(order.getDetailedSummary());
            System.out.println("-".repeat(50));
        }
    }

    // 2. CALCOLARE IL CONTO DI UN TAVOLO

    /**
     * @pre true
     * @post restituisce la somma dei totali degli ordini attivi relativi al tavolo
     *       (vedi {@link #getUnpaidOrdersForTable(int)}); 0 se non ce ne sono;
     *       non modifica lo stato
     */
    public double calculateTableBill(int tableNumber) {
        List<Order> tableOrders = getUnpaidOrdersForTable(tableNumber);
        return tableOrders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }

    /**
     * @pre true
     * @post restituisce una nuova lista degli ordini attivi il cui tavolo ha numero
     *       tableNumber oppure include tableNumber tra i tavoli uniti, in ordine di creazione;
     *       non modifica lo stato
     */
    public List<Order> getUnpaidOrdersForTable(int tableNumber) {
        return orders.stream()
                .filter(order -> order.isActive())
                .filter(order -> {
                    Table orderTable = order.getTable();
                    return orderTable.getTableNumber() == tableNumber ||
                           orderTable.getJoinedTables().contains(tableNumber);
                })
                .collect(Collectors.toList());
    }

    /**
     * Stampa il conto di un tavolo.
     *
     * @pre true
     * @post stampa "NESSUN CONTO DA PAGARE" se non ci sono ordini attivi per il tavolo;
     *       altrimenti stampa, per ogni ordine, le righe e il subtotale, poi il totale da
     *       pagare; lo stato del sistema non cambia
     */
    public void displayTableBill(int tableNumber) {
        List<Order> tableOrders = getUnpaidOrdersForTable(tableNumber);

        if (tableOrders.isEmpty()) {
            System.out.println("=== NESSUN CONTO DA PAGARE ===");
            System.out.println("Tavolo: " + tableNumber);
            return;
        }

        System.out.println("=== CONTO TAVOLO " + tableNumber + " ===");
        System.out.println("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println();

        double totalBill = 0.0;
        Map<MenuItem.Category, Double> categoryTotals = new HashMap<>();

        for (Order order : tableOrders) {
            System.out.println("Ordine #" + order.getOrderId() + " - " +
                    order.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")));

            for (Order.OrderItem item : order.getItems()) {
                System.out.printf("  %dx %-30s €%.2f%n",
                        item.getQuantity(),
                        item.getMenuItem().getName(),
                        item.getSubtotal());

                // Accumula per categoria
                MenuItem.Category category = item.getMenuItem().getCategory();
                categoryTotals.merge(category, item.getSubtotal(), Double::sum);
            }

            totalBill += order.getTotalAmount();
            System.out.printf("  Subtotale ordine: €%.2f%n%n", order.getTotalAmount());
        }

        System.out.println("-".repeat(50));
        System.out.printf("TOTALE DA PAGARE: €%.2f%n", totalBill);
        System.out.println("=".repeat(50));
    }

    /**
     * Paga tutti gli ordini di un tavolo.
     *
     * @pre paymentMethod != null
     * @post tutti gli ordini che erano attivi per il tavolo (vedi
     *       {@link #getUnpaidOrdersForTable(int)}) sono PAID con metodo paymentMethod; dopo la
     *       chiamata calculateTableBill(tableNumber)==0; gli altri ordini sono invariati; stampa
     *       una conferma (anche se non c'erano ordini da pagare)
     */
    public void payTableBill(int tableNumber, Order.PaymentMethod paymentMethod) {
        List<Order> tableOrders = getUnpaidOrdersForTable(tableNumber);

        for (Order order : tableOrders) {
            order.pay(paymentMethod);
        }

        System.out.printf("Pagamento completato per tavolo %d con %s%n",
                tableNumber, paymentMethod.getDisplayName());
    }

    // 3. GENERARE IL RESOCONTO SERALE

    /**
     * Stampa il resoconto delle vendite di una giornata.
     *
     * @pre date != null; ogni ordine pagato ha un metodo di pagamento non null
     *      (garantito se si è pagato tramite {@code pay}/{@code payTableBill})
     * @post considera solo gli ordini pagati creati nel giorno date; se non ce ne sono stampa
     *       "NESSUNA VENDITA REGISTRATA", altrimenti stampa statistiche per cameriere (tavoli
     *       serviti, coperti, incasso), ripartizione per categoria e per modalità di pagamento
     *       (con percentuali sull'incasso totale), incasso totale e numero di ordini;
     *       lo stato del sistema non cambia
     */
    public void generateDailyReport(LocalDate date) {
        List<Order> dailyPaidOrders = orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.PAID)
                .filter(order -> order.getTimestamp().toLocalDate().equals(date))
                .collect(Collectors.toList());

        if (dailyPaidOrders.isEmpty()) {
            System.out.println("=== NESSUNA VENDITA REGISTRATA ===");
            System.out.println("Data: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            return;
        }

        System.out.println("=== RESOCONTO SERALE ===");
        System.out.println("Data: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println();

        // Calcoli per cameriere
        Map<Waiter, WaiterStats> waiterStatsMap = new HashMap<>();
        Map<MenuItem.Category, Double> categoryTotals = new HashMap<>();
        Map<Order.PaymentMethod, Double> paymentTotals = new HashMap<>();

        for (Order order : dailyPaidOrders) {
            Waiter waiter = order.getWaiter();
            WaiterStats stats = waiterStatsMap.computeIfAbsent(waiter, k -> new WaiterStats());

            stats.ordersServed++;
            stats.tablesServed.add(order.getTable().getTableNumber());
            stats.totalRevenue += order.getTotalAmount();
            stats.totalCovers += order.getTable().getCapacity();

            // Accumula per categoria
            Map<MenuItem.Category, List<Order.OrderItem>> itemsByCategory = order.getItemsByCategory();
            for (Map.Entry<MenuItem.Category, List<Order.OrderItem>> entry : itemsByCategory.entrySet()) {
                double categoryTotal = entry.getValue().stream()
                        .mapToDouble(Order.OrderItem::getSubtotal)
                        .sum();
                categoryTotals.merge(entry.getKey(), categoryTotal, Double::sum);
            }

            // Accumula per metodo di pagamento
            paymentTotals.merge(order.getPaymentMethod(), order.getTotalAmount(), Double::sum);
        }

        // Stampa statistiche per cameriere
        System.out.println("--- STATISTICHE PER CAMERIERE ---");
        double totalRevenue = 0.0;
        for (Map.Entry<Waiter, WaiterStats> entry : waiterStatsMap.entrySet()) {
            Waiter waiter = entry.getKey();
            WaiterStats stats = entry.getValue();

            System.out.printf("Cameriere: %s (%s)%n", waiter.getFullName(), waiter.getCode());
            System.out.printf("  Tavoli serviti: %d%n", stats.tablesServed.size());
            System.out.printf("  Coperti totali: %d%n", stats.totalCovers);
            System.out.printf("  Incasso lordo: €%.2f%n", stats.totalRevenue);
            System.out.println();

            totalRevenue += stats.totalRevenue;
        }

        // Stampa ripartizione per categoria
        System.out.println("--- RIPARTIZIONE PER CATEGORIA ---");
        for (Map.Entry<MenuItem.Category, Double> entry : categoryTotals.entrySet()) {
            double percentage = (entry.getValue() / totalRevenue) * 100;
            System.out.printf("%-20s: €%8.2f (%5.1f%%)%n",
                    entry.getKey().getDisplayName(),
                    entry.getValue(),
                    percentage);
        }
        System.out.println();

        // Stampa modalità di pagamento
        System.out.println("--- MODALITÀ DI PAGAMENTO ---");
        for (Map.Entry<Order.PaymentMethod, Double> entry : paymentTotals.entrySet()) {
            double percentage = (entry.getValue() / totalRevenue) * 100;
            System.out.printf("%-15s: €%8.2f (%5.1f%%)%n",
                    entry.getKey().getDisplayName(),
                    entry.getValue(),
                    percentage);
        }
        System.out.println();

        System.out.println("=".repeat(50));
        System.out.printf("INCASSO TOTALE GIORNALIERO: €%.2f%n", totalRevenue);
        System.out.printf("NUMERO TOTALE ORDINI: %d%n", dailyPaidOrders.size());
        System.out.println("=".repeat(50));
    }

    /**
     * <b>Mission.</b> Accumulatore di supporto (privato) per le statistiche di un cameriere nel
     * resoconto: conosce ordini serviti, tavoli distinti serviti, coperti e incasso.
     * <p>
     * <b>Stato astratto.</b> (n. ordini, insieme dei numeri di tavolo serviti, coperti, incasso).
     * <b>Invarianti astratti.</b> tutti i contatori &ge; 0; il n. di tavoli distinti &le;
     * n. ordini.
     * <br>
     * <b>Stato concreto.</b> Campi {@code ordersServed}, {@code tablesServed} ({@code HashSet}),
     * {@code totalCovers}, {@code totalRevenue}.
     * <b>Invarianti di rappresentazione.</b> {@code tablesServed != null};
     * i campi sono modificati solo da {@code generateDailyReport}, che li incrementa ad ogni
     * ordine considerato; la classe non è visibile all'esterno.
     */
    // Classe di supporto per le statistiche del cameriere
    private static class WaiterStats {
        int ordersServed = 0;
        Set<Integer> tablesServed = new HashSet<>();
        int totalCovers = 0;
        double totalRevenue = 0.0;
    }

    // Metodi di utilità per testing

    /**
     * Aggiunge una pietanza al menu.
     *
     * @pre item != null; nel menu non esiste già una pietanza con lo stesso codice
     * @post getMenu() contiene item in coda; le altre pietanze sono invariate
     */
    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    /**
     * Stampa il menu raggruppato per categoria.
     *
     * @pre true
     * @post stampa su standard output, per ogni categoria non vuota (nell'ordine
     *       dell'enumerazione), codice, nome, prezzo e descrizione (se non vuota) delle pietanze;
     *       lo stato non cambia
     */
    public void displayMenu() {
        Map<MenuItem.Category, List<MenuItem>> menuByCategory = menu.stream()
                .collect(Collectors.groupingBy(MenuItem::getCategory));

        System.out.println("=== MENU PIZZERIA AI RIZZI ===");
        System.out.println();

        for (MenuItem.Category category : MenuItem.Category.values()) {
            List<MenuItem> items = menuByCategory.get(category);
            if (items != null && !items.isEmpty()) {
                System.out.println("--- " + category.getDisplayName().toUpperCase() + " ---");
                for (MenuItem item : items) {
                    System.out.printf("%-8s %-25s €%.2f%n",
                            item.getCode(),
                            item.getName(),
                            item.getPrice());
                    if (!item.getDescription().isEmpty()) {
                        System.out.printf("         %s%n", item.getDescription());
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Stampa l'assegnazione dei tavoli ai camerieri.
     *
     * @pre true
     * @post stampa su standard output, per ogni cameriere, nome, codice e tavoli assegnati;
     *       lo stato non cambia
     */
    public void displayTableAssignments() {
        System.out.println("=== ASSEGNAZIONE TAVOLI ===");
        System.out.println();

        for (Waiter waiter : waiters) {
            System.out.printf("Cameriere: %s (%s)%n", waiter.getFullName(), waiter.getCode());
            System.out.print("Tavoli assegnati: ");
            System.out.println(waiter.getAssignedTables().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", ")));
            System.out.println();
        }
    }
}
