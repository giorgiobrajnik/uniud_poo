import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

public class OrderTest {
    private Order order;
    private Table table;
    private Waiter waiter;
    private MenuItem pizza;
    private MenuItem beer;
    private MenuItem pasta;

    @BeforeEach
    void setUp() {
        table = new Table(5, 4, true);
        waiter = new Waiter("CAM001", "Mario", "Rossi");
        order = new Order(table, waiter);

        pizza = new MenuItem("P001", "Margherita", "Pizza classica", 7.50, MenuItem.Category.PIZZA);
        beer = new MenuItem("B001", "Birra media", "Birra alla spina", 4.50, MenuItem.Category.BEVANDA);
        pasta = new MenuItem("PR001", "Carbonara", "Pasta all'uovo", 9.00, MenuItem.Category.PRIMO);
    }

    @Test
    void testOrderCreation() {
        assertTrue(order.getOrderId() > 0);
        assertNotNull(order.getTimestamp());
        assertEquals(table, order.getTable());
        assertEquals(waiter, order.getWaiter());
        assertEquals(Order.OrderStatus.IN_PREPARATION, order.getStatus());
        assertTrue(order.getItems().isEmpty());
        assertEquals(0.0, order.getTotalAmount(), 0.01);
        assertNull(order.getPaymentMethod());
        assertTrue(order.isActive());
    }

    @Test
    void testAddItem() {
        order.addItem(pizza, 2);

        assertEquals(1, order.getItems().size());
        Order.OrderItem item = order.getItems().get(0);
        assertEquals(pizza, item.getMenuItem());
        assertEquals(2, item.getQuantity());
        assertEquals(15.0, item.getSubtotal(), 0.01); // 7.50 * 2
        assertEquals(15.0, order.getTotalAmount(), 0.01);
    }

    @Test
    void testAddMultipleItems() {
        order.addItem(pizza, 1);
        order.addItem(beer, 2);

        assertEquals(2, order.getItems().size());
        assertEquals(16.5, order.getTotalAmount(), 0.01); // 7.50 + (4.50 * 2)
    }

    @Test
    void testAddSameItemTwice() {
        order.addItem(pizza, 1);
        order.addItem(pizza, 2); // Dovrebbe aggiornare la quantità

        assertEquals(1, order.getItems().size());
        Order.OrderItem item = order.getItems().get(0);
        assertEquals(3, item.getQuantity()); // 1 + 2
        assertEquals(22.5, order.getTotalAmount(), 0.01); // 7.50 * 3
    }

    @Test
    void testRemoveItem() {
        order.addItem(pizza, 2);
        order.addItem(beer, 1);

        order.removeItem(pizza);

        assertEquals(1, order.getItems().size());
        assertEquals(4.5, order.getTotalAmount(), 0.01); // Solo la birra
    }

    @Test
    void testUpdateItemQuantity() {
        order.addItem(pizza, 2);

        order.updateItemQuantity(pizza, 5);

        Order.OrderItem item = order.getItems().get(0);
        assertEquals(5, item.getQuantity());
        assertEquals(37.5, order.getTotalAmount(), 0.01); // 7.50 * 5
    }

    @Test
    void testUpdateItemQuantityToZero() {
        order.addItem(pizza, 2);
        order.addItem(beer, 1);

        order.updateItemQuantity(pizza, 0); // Dovrebbe rimuovere l'elemento

        assertEquals(1, order.getItems().size());
        assertEquals(4.5, order.getTotalAmount(), 0.01); // Solo la birra
    }

    @Test
    void testSetStatus() {
        order.setStatus(Order.OrderStatus.SERVED);
        assertEquals(Order.OrderStatus.SERVED, order.getStatus());
        assertTrue(order.isActive()); // Ancora attivo finché non è pagato
    }

    @Test
    void testPay() {
        order.addItem(pizza, 1);
        order.pay(Order.PaymentMethod.CARD);

        assertEquals(Order.OrderStatus.PAID, order.getStatus());
        assertEquals(Order.PaymentMethod.CARD, order.getPaymentMethod());
        assertFalse(order.isActive()); // Non più attivo dopo il pagamento
    }

    @Test
    void testGetItemsByCategory() {
        order.addItem(pizza, 2);
        order.addItem(beer, 1);
        order.addItem(pasta, 1);

        Map<MenuItem.Category, List<Order.OrderItem>> itemsByCategory = order.getItemsByCategory();

        assertEquals(3, itemsByCategory.size());
        assertTrue(itemsByCategory.containsKey(MenuItem.Category.PIZZA));
        assertTrue(itemsByCategory.containsKey(MenuItem.Category.BEVANDA));
        assertTrue(itemsByCategory.containsKey(MenuItem.Category.PRIMO));

        assertEquals(1, itemsByCategory.get(MenuItem.Category.PIZZA).size());
        assertEquals(1, itemsByCategory.get(MenuItem.Category.BEVANDA).size());
        assertEquals(1, itemsByCategory.get(MenuItem.Category.PRIMO).size());
    }

    @Test
    void testOrderItemSubtotal() {
        Order.OrderItem item = new Order.OrderItem(pizza, 3);

        assertEquals(pizza, item.getMenuItem());
        assertEquals(3, item.getQuantity());
        assertEquals(22.5, item.getSubtotal(), 0.01); // 7.50 * 3
    }

    @Test
    void testOrderItemSetQuantity() {
        Order.OrderItem item = new Order.OrderItem(pizza, 2);
        item.setQuantity(5);

        assertEquals(5, item.getQuantity());
        assertEquals(37.5, item.getSubtotal(), 0.01); // 7.50 * 5
    }

    @Test
    void testGetDetailedSummary() {
        order.addItem(pizza, 1);
        order.addItem(beer, 2);

        String summary = order.getDetailedSummary();

        assertTrue(summary.contains("ORDINE #" + order.getOrderId()));
        assertTrue(summary.contains("Tavolo 5"));
        assertTrue(summary.contains("Mario Rossi"));
        assertTrue(summary.contains("CAM001"));
        assertTrue(summary.contains("Margherita"));
        assertTrue(summary.contains("Birra media"));
        assertTrue(summary.contains("€16.50")); // Totale
    }

    @Test
    void testGetBriefSummary() {
        order.addItem(pizza, 1);

        String summary = order.getBriefSummary();

        assertTrue(summary.contains("Ordine #" + order.getOrderId()));
        assertTrue(summary.contains("Tavolo 5"));
        assertTrue(summary.contains("€7.50"));
        assertTrue(summary.contains("In preparazione"));
    }

    @Test
    void testEqualsAndHashCode() {
        Order anotherOrder = new Order(table, waiter);

        assertNotEquals(order, anotherOrder); // ID diversi
        assertNotEquals(order.hashCode(), anotherOrder.hashCode());

        assertEquals(order, order); // Stesso oggetto
        assertEquals(order.hashCode(), order.hashCode());
    }

    @Test
    void testTimestampIsRecent() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime orderTime = order.getTimestamp();

        // L'ordine dovrebbe essere stato creato negli ultimi secondi
        assertTrue(orderTime.isAfter(now.minusSeconds(5)));
        assertTrue(orderTime.isBefore(now.plusSeconds(1)));
    }

    @Test
    void testPaymentMethodDisplayNames() {
        assertEquals("Contanti", Order.PaymentMethod.CASH.getDisplayName());
        assertEquals("Carta", Order.PaymentMethod.CARD.getDisplayName());
        assertEquals("Buoni pasto", Order.PaymentMethod.MEAL_VOUCHER.getDisplayName());
    }

    @Test
    void testOrderStatusDisplayNames() {
        assertEquals("In preparazione", Order.OrderStatus.IN_PREPARATION.getDisplayName());
        assertEquals("Servito", Order.OrderStatus.SERVED.getDisplayName());
        assertEquals("Pagato", Order.OrderStatus.PAID.getDisplayName());
    }

    @Test
    void testUpdateNonExistentItem() {
        order.addItem(pizza, 1);

        // Tentativo di aggiornare un elemento non presente
        order.updateItemQuantity(beer, 5);

        // Non dovrebbe accadere nulla
        assertEquals(1, order.getItems().size());
        assertEquals(7.5, order.getTotalAmount(), 0.01);
    }

    @Test
    void testRemoveNonExistentItem() {
        order.addItem(pizza, 1);

        // Tentativo di rimuovere un elemento non presente
        order.removeItem(beer);

        // Non dovrebbe accadere nulla
        assertEquals(1, order.getItems().size());
        assertEquals(7.5, order.getTotalAmount(), 0.01);
    }
}