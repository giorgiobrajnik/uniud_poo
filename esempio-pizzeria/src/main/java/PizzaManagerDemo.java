import java.time.LocalDate;

/**
 * <b>Mission.</b> Programma dimostrativo: conosce lo scenario d'esempio (ordini e pagamenti
 * simulati) e sa eseguirlo contro un {@link PizzaManager}, stampando i risultati.
 * <p>
 * Non è un tipo di dato astratto: è una classe di utilità che non ha istanze né stato
 * (astratto o concreto) e quindi non ha invarianti di rappresentazione; tutto lo stato vive
 * nel {@code PizzaManager} creato da {@code main} e passato ai metodi di supporto.
 */
public class PizzaManagerDemo {
    /**
     * Esegue la demo.
     *
     * @pre args non viene usato (qualsiasi valore, anche vuoto)
     * @post crea un PizzaManager, stampa menu e assegnazioni, simula 5 ordini, mostra ordini
     *       attivi e conto del tavolo 5, simula i pagamenti dei tavoli 12, 8-9 e 3 e stampa il
     *       resoconto di oggi; eventuali eccezioni nella simulazione vengono stampate su
     *       standard error e non interrompono il programma
     */
    public static void main(String[] args) {
        // Crea il sistema di gestione pizzeria
        PizzaManager pizzaManager = new PizzaManager();

        System.out.println("=== DEMO SISTEMA PIZZAMANAGER ===");
        System.out.println();

        // Mostra il menu
        pizzaManager.displayMenu();
        System.out.println();

        // Mostra l'assegnazione dei tavoli
        pizzaManager.displayTableAssignments();
        System.out.println();

        // Simula alcuni ordini
        simulateOrders(pizzaManager);

        // Mostra gli ordini attivi
        System.out.println("=== ORDINI ATTIVI ===");
        pizzaManager.displayActiveOrders();
        System.out.println();

        // Calcola il conto di un tavolo
        System.out.println("=== CONTO TAVOLO 5 ===");
        pizzaManager.displayTableBill(5);
        System.out.println();

        // Simula alcuni pagamenti
        simulatePayments(pizzaManager);

        // Genera il resoconto serale
        System.out.println("=== RESOCONTO SERALE ===");
        pizzaManager.generateDailyReport(LocalDate.now());
    }

    // Precondizione: pizzaManager != null. Postcondizione: aggiunge 5 ordini al sistema.
    private static void simulateOrders(PizzaManager pizzaManager) {
        System.out.println("=== SIMULAZIONE ORDINI ===");

        try {
            // Ordine 1: Tavolo 5
            Order order1 = pizzaManager.createOrder(5);
            order1.addItem(pizzaManager.findMenuItem("P001"), 2); // 2 Margherite
            order1.addItem(pizzaManager.findMenuItem("B001"), 1); // 1 Acqua
            order1.addItem(pizzaManager.findMenuItem("B003"), 2); // 2 Birre
            System.out.println("Creato ordine per tavolo 5");

            // Ordine 2: Tavolo 12
            Order order2 = pizzaManager.createOrder(12);
            order2.addItem(pizzaManager.findMenuItem("P003"), 1); // 1 Quattro Stagioni
            order2.addItem(pizzaManager.findMenuItem("A001"), 1); // 1 Antipasto della casa
            order2.addItem(pizzaManager.findMenuItem("B002"), 1); // 1 Vino rosso
            System.out.println("Creato ordine per tavolo 12");

            // Ordine 3: Tavolo 3 (secondo ordine, stessa serata)
            Order order3 = pizzaManager.createOrder(3);
            order3.addItem(pizzaManager.findMenuItem("PR001"), 2); // 2 Carbonare
            order3.addItem(pizzaManager.findMenuItem("C001"), 2); // 2 Insalate
            order3.addItem(pizzaManager.findMenuItem("B001"), 1); // 1 Acqua
            System.out.println("Creato ordine per tavolo 3");

            // Ordine 4: Tavoli uniti 8 e 9
            Order order4 = pizzaManager.createOrder(java.util.Arrays.asList(8, 9));
            order4.addItem(pizzaManager.findMenuItem("P001"), 3); // 3 Margherite
            order4.addItem(pizzaManager.findMenuItem("P002"), 2); // 2 Marinare
            order4.addItem(pizzaManager.findMenuItem("A002"), 2); // 2 Bruschette
            order4.addItem(pizzaManager.findMenuItem("SC001"), 1); // 1 Tagliata
            order4.addItem(pizzaManager.findMenuItem("B002"), 2); // 2 Vini rossi
            System.out.println("Creato ordine per tavoli uniti 8-9");

            // Ordine 5: Tavolo 5 (secondo ordine per dolci)
            Order order5 = pizzaManager.createOrder(5);
            order5.addItem(pizzaManager.findMenuItem("D001"), 2); // 2 Tiramisù
            System.out.println("Creato secondo ordine per tavolo 5 (dolci)");

        } catch (Exception e) {
            System.err.println("Errore nella simulazione ordini: " + e.getMessage());
        }

        System.out.println("Simulazione ordini completata.");
        System.out.println();
    }

    // Precondizione: pizzaManager != null. Postcondizione: paga gli ordini dei tavoli 12, 8 (e 9 unito) e 3.
    private static void simulatePayments(PizzaManager pizzaManager) {
        System.out.println("=== SIMULAZIONE PAGAMENTI ===");

        try {
            // Paga il tavolo 12 con carta
            pizzaManager.payTableBill(12, Order.PaymentMethod.CARD);

            // Paga i tavoli uniti 8-9 con contanti
            pizzaManager.payTableBill(8, Order.PaymentMethod.CASH);

            // Paga il tavolo 3 con buoni pasto
            pizzaManager.payTableBill(3, Order.PaymentMethod.MEAL_VOUCHER);

            System.out.println("Simulazione pagamenti completata.");

        } catch (Exception e) {
            System.err.println("Errore nella simulazione pagamenti: " + e.getMessage());
        }

        System.out.println();
    }
}