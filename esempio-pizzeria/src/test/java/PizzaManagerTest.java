import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

public class PizzaManagerTest {
    private PizzaManager pizzaManager;

    @BeforeEach
    void setUp() {
        pizzaManager = new PizzaManager();
    }

    @Test
    void testPizzeriaInitialization() {
        // Test tabelle
        List<Table> tables = pizzaManager.getTables();
        assertEquals(25, tables.size());

        // Verifica tavoli interni (1-15)
        long indoorTables = tables.stream()
                .filter(Table::isIndoor)
                .count();
        assertEquals(15, indoorTables);

        // Verifica tavoli esterni (16-25)
        long outdoorTables = tables.stream()
                .filter(table -> !table.isIndoor())
                .count();
        assertEquals(10, outdoorTables);

        // Test camerieri
        List<Waiter> waiters = pizzaManager.getWaiters();
        assertEquals(6, waiters.size());

        // Verifica che tutti i camerieri abbiano tavoli assegnati
        for (Waiter waiter : waiters) {
            assertFalse(waiter.getAssignedTables().isEmpty());
        }

        // Test menu
        List<MenuItem> menu = pizzaManager.getMenu();
        assertFalse(menu.isEmpty());
        assertTrue(menu.size() > 10); // Dovrebbe avere molte voci
    }

    @Test
    void testFindTable() {
        Table table5 = pizzaManager.findTable(5);
        assertNotNull(table5);
        assertEquals(5, table5.getTableNumber());
        assertTrue(table5.isIndoor());

        Table table20 = pizzaManager.findTable(20);
        assertNotNull(table20);
        assertEquals(20, table20.getTableNumber());
        assertFalse(table20.isIndoor());

        Table nonExistent = pizzaManager.findTable(999);
        assertNull(nonExistent);
    }

    @Test
    void testFindWaiter() {
        Waiter waiter = pizzaManager.findWaiter("CAM001");
        assertNotNull(waiter);
        assertEquals("CAM001", waiter.getCode());
        assertEquals("Mario", waiter.getFirstName());
        assertEquals("Rossi", waiter.getLastName());

        Waiter nonExistent = pizzaManager.findWaiter("CAM999");
        assertNull(nonExistent);
    }

    @Test
    void testFindMenuItem() {
        MenuItem pizza = pizzaManager.findMenuItem("P001");
        assertNotNull(pizza);
        assertEquals("P001", pizza.getCode());
        assertEquals("Margherita", pizza.getName());

        MenuItem nonExistent = pizzaManager.findMenuItem("XXX999");
        assertNull(nonExistent);
    }

    @Test
    void testFindWaiterForTable() {
        Waiter waiter = pizzaManager.findWaiterForTable(5);
        assertNotNull(waiter);
        assertTrue(waiter.isAssignedToTable(5));

        // Testa un tavolo che dovrebbe esistere
        Waiter waiter20 = pizzaManager.findWaiterForTable(20);
        assertNotNull(waiter20);
        assertTrue(waiter20.isAssignedToTable(20));
    }

    @Test
    void testCreateOrder() {
        Order order = pizzaManager.createOrder(5);

        assertNotNull(order);
        assertEquals(5, order.getTable().getTableNumber());
        assertNotNull(order.getWaiter());
        assertTrue(order.getWaiter().isAssignedToTable(5));
        assertEquals(Order.OrderStatus.IN_PREPARATION, order.getStatus());
        assertTrue(pizzaManager.getOrders().contains(order));
    }

    @Test
    void testCreateOrderWithInvalidTable() {
        assertThrows(IllegalArgumentException.class, () -> {
            pizzaManager.createOrder(999);
        });
    }

    @Test
    void testCreateOrderWithJoinedTables() {
        List<Integer> tableNumbers = Arrays.asList(8, 9);
        Order order = pizzaManager.createOrder(tableNumbers);

        assertNotNull(order);
        assertTrue(order.getTable().isJoined());
        assertEquals(tableNumbers, order.getTable().getJoinedTables());
        assertNotNull(order.getWaiter());
        assertTrue(pizzaManager.getOrders().contains(order));
    }

    @Test
    void testCreateOrderWithEmptyTableList() {
        assertThrows(IllegalArgumentException.class, () -> {
            pizzaManager.createOrder(Arrays.asList());
        });
    }

    @Test
    void testCreateOrderWithInvalidJoinedTables() {
        assertThrows(IllegalArgumentException.class, () -> {
            pizzaManager.createOrder(Arrays.asList(5, 999));
        });
    }

    @Test
    void testGetActiveOrders() {
        // Inizialmente non dovrebbero esserci ordini attivi
        List<Order> activeOrders = pizzaManager.getActiveOrders();
        assertTrue(activeOrders.isEmpty());

        // Crea alcuni ordini
        Order order1 = pizzaManager.createOrder(5);
        Order order2 = pizzaManager.createOrder(10);

        activeOrders = pizzaManager.getActiveOrders();
        assertEquals(2, activeOrders.size());
        assertTrue(activeOrders.contains(order1));
        assertTrue(activeOrders.contains(order2));

        // Paga un ordine
        order1.pay(Order.PaymentMethod.CASH);

        activeOrders = pizzaManager.getActiveOrders();
        assertEquals(1, activeOrders.size());
        assertTrue(activeOrders.contains(order2));
        assertFalse(activeOrders.contains(order1));
    }

    @Test
    void testCalculateTableBill() {
        // Inizialmente il conto dovrebbe essere 0
        double bill = pizzaManager.calculateTableBill(5);
        assertEquals(0.0, bill, 0.01);

        // Crea un ordine e aggiungi elementi
        Order order = pizzaManager.createOrder(5);
        MenuItem pizza = pizzaManager.findMenuItem("P001");
        MenuItem beer = pizzaManager.findMenuItem("B003");

        order.addItem(pizza, 2); // 2 * 7.50 = 15.00
        order.addItem(beer, 1);  // 1 * 4.50 = 4.50

        bill = pizzaManager.calculateTableBill(5);
        assertEquals(19.5, bill, 0.01); // 15.00 + 4.50

        // Aggiungi un secondo ordine allo stesso tavolo
        Order order2 = pizzaManager.createOrder(5);
        MenuItem dessert = pizzaManager.findMenuItem("D001");
        order2.addItem(dessert, 1); // 1 * 5.50 = 5.50

        bill = pizzaManager.calculateTableBill(5);
        assertEquals(25.0, bill, 0.01); // 19.50 + 5.50

        // Paga il primo ordine
        order.pay(Order.PaymentMethod.CARD);

        bill = pizzaManager.calculateTableBill(5);
        assertEquals(5.5, bill, 0.01); // Solo il secondo ordine
    }

    @Test
    void testGetUnpaidOrdersForTable() {
        Order order1 = pizzaManager.createOrder(5);
        Order order2 = pizzaManager.createOrder(5);
        Order order3 = pizzaManager.createOrder(10);

        List<Order> unpaidOrders = pizzaManager.getUnpaidOrdersForTable(5);
        assertEquals(2, unpaidOrders.size());
        assertTrue(unpaidOrders.contains(order1));
        assertTrue(unpaidOrders.contains(order2));

        unpaidOrders = pizzaManager.getUnpaidOrdersForTable(10);
        assertEquals(1, unpaidOrders.size());
        assertTrue(unpaidOrders.contains(order3));

        // Paga un ordine del tavolo 5
        order1.pay(Order.PaymentMethod.CASH);

        unpaidOrders = pizzaManager.getUnpaidOrdersForTable(5);
        assertEquals(1, unpaidOrders.size());
        assertTrue(unpaidOrders.contains(order2));
    }

    @Test
    void testPayTableBill() {
        Order order1 = pizzaManager.createOrder(5);
        Order order2 = pizzaManager.createOrder(5);

        MenuItem pizza = pizzaManager.findMenuItem("P001");
        order1.addItem(pizza, 1);
        order2.addItem(pizza, 1);

        // Verifica che ci siano ordini non pagati
        assertEquals(2, pizzaManager.getUnpaidOrdersForTable(5).size());

        // Paga il conto del tavolo
        pizzaManager.payTableBill(5, Order.PaymentMethod.CARD);

        // Verifica che non ci siano più ordini non pagati
        assertEquals(0, pizzaManager.getUnpaidOrdersForTable(5).size());

        // Verifica che entrambi gli ordini siano stati pagati
        assertEquals(Order.OrderStatus.PAID, order1.getStatus());
        assertEquals(Order.OrderStatus.PAID, order2.getStatus());
        assertEquals(Order.PaymentMethod.CARD, order1.getPaymentMethod());
        assertEquals(Order.PaymentMethod.CARD, order2.getPaymentMethod());
    }

    @Test
    void testPayTableBillWithNoOrders() {
        // Pagare un tavolo senza ordini non dovrebbe causare errori
        assertDoesNotThrow(() -> {
            pizzaManager.payTableBill(5, Order.PaymentMethod.CASH);
        });
    }

    @Test
    void testGenerateDailyReportWithNoOrders() {
        // Il report dovrebbe funzionare anche senza ordini
        assertDoesNotThrow(() -> {
            pizzaManager.generateDailyReport(LocalDate.now());
        });
    }

    @Test
    void testCompleteOrderFlow() {
        // Simula un flusso completo di ordine
        Order order = pizzaManager.createOrder(5);
        MenuItem pizza = pizzaManager.findMenuItem("P001");
        MenuItem beer = pizzaManager.findMenuItem("B003");

        // Aggiungi elementi
        order.addItem(pizza, 2);
        order.addItem(beer, 1);

        // Verifica che l'ordine sia attivo
        assertTrue(pizzaManager.getActiveOrders().contains(order));
        assertEquals(19.5, pizzaManager.calculateTableBill(5), 0.01);

        // Cambia stato
        order.setStatus(Order.OrderStatus.SERVED);
        assertEquals(Order.OrderStatus.SERVED, order.getStatus());
        assertTrue(order.isActive()); // Ancora attivo

        // Paga l'ordine
        pizzaManager.payTableBill(5, Order.PaymentMethod.CASH);

        // Verifica che non sia più attivo
        assertFalse(pizzaManager.getActiveOrders().contains(order));
        assertEquals(0.0, pizzaManager.calculateTableBill(5), 0.01);
        assertEquals(Order.OrderStatus.PAID, order.getStatus());
    }

    @Test
    void testJoinedTablesInOrder() {
        List<Integer> tables = Arrays.asList(8, 9);
        Order order = pizzaManager.createOrder(tables);

        MenuItem pizza = pizzaManager.findMenuItem("P001");
        order.addItem(pizza, 1);

        // Verifica che l'ordine sia associato correttamente ai tavoli uniti
        List<Order> unpaidOrders8 = pizzaManager.getUnpaidOrdersForTable(8);
        List<Order> unpaidOrders9 = pizzaManager.getUnpaidOrdersForTable(9);

        assertEquals(1, unpaidOrders8.size());
        assertEquals(1, unpaidOrders9.size());
        assertEquals(order, unpaidOrders8.get(0));
        assertEquals(order, unpaidOrders9.get(0));

        // Pagare uno dei tavoli dovrebbe pagare l'intero ordine
        pizzaManager.payTableBill(8, Order.PaymentMethod.CASH);

        assertEquals(0, pizzaManager.getUnpaidOrdersForTable(8).size());
        assertEquals(0, pizzaManager.getUnpaidOrdersForTable(9).size());
        assertEquals(Order.OrderStatus.PAID, order.getStatus());
    }

    @Test
    void testAddMenuItemAndFind() {
        MenuItem newItem = new MenuItem("TEST001", "Test Item", "Item di test", 10.00, MenuItem.Category.PIZZA);
        pizzaManager.addMenuItem(newItem);

        MenuItem found = pizzaManager.findMenuItem("TEST001");
        assertNotNull(found);
        assertEquals(newItem, found);
        assertTrue(pizzaManager.getMenu().contains(newItem));
    }

    @Test
    void testTableAssignmentsCompleteness() {
        List<Table> tables = pizzaManager.getTables();
        List<Waiter> waiters = pizzaManager.getWaiters();

        // Verifica che tutti i tavoli siano assegnati a qualche cameriere
        for (Table table : tables) {
            boolean isAssigned = waiters.stream()
                    .anyMatch(waiter -> waiter.isAssignedToTable(table.getTableNumber()));
            assertTrue(isAssigned, "Tavolo " + table.getTableNumber() + " non è assegnato a nessun cameriere");
        }

        // Verifica che non ci siano sovrapposizioni di assegnazione
        for (int i = 0; i < waiters.size(); i++) {
            for (int j = i + 1; j < waiters.size(); j++) {
                Waiter waiter1 = waiters.get(i);
                Waiter waiter2 = waiters.get(j);

                for (int tableNumber : waiter1.getAssignedTables()) {
                    assertFalse(waiter2.isAssignedToTable(tableNumber),
                            "Tavolo " + tableNumber + " è assegnato a più camerieri");
                }
            }
        }
    }
}