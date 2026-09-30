import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;

public class MenuItemTest {
    private MenuItem pizza;
    private MenuItem specialFish;

    @BeforeEach
    void setUp() {
        pizza = new MenuItem("P001", "Margherita", "Pomodoro, mozzarella, basilico", 7.50, MenuItem.Category.PIZZA);

        Set<MenuItem.DayOfWeek> fishDays = new HashSet<>();
        fishDays.add(MenuItem.DayOfWeek.FRIDAY);
        fishDays.add(MenuItem.DayOfWeek.SATURDAY);
        specialFish = new MenuItem("SP001", "Branzino al sale", "Branzino fresco", 16.00, MenuItem.Category.SECONDO_PESCE, fishDays);
    }

    @Test
    void testMenuItemCreation() {
        assertEquals("P001", pizza.getCode());
        assertEquals("Margherita", pizza.getName());
        assertEquals("Pomodoro, mozzarella, basilico", pizza.getDescription());
        assertEquals(7.50, pizza.getPrice(), 0.01);
        assertEquals(MenuItem.Category.PIZZA, pizza.getCategory());
    }

    @Test
    void testDefaultAvailability() {
        // Pizza dovrebbe essere disponibile tutti i giorni per default
        assertTrue(pizza.isAvailableOn(MenuItem.DayOfWeek.MONDAY));
        assertTrue(pizza.isAvailableOn(MenuItem.DayOfWeek.FRIDAY));
        assertTrue(pizza.isAvailableOn(MenuItem.DayOfWeek.SUNDAY));
        assertTrue(pizza.getAvailableDays().contains(MenuItem.DayOfWeek.ALL_DAYS));
    }

    @Test
    void testSpecificDayAvailability() {
        // Pesce speciale dovrebbe essere disponibile solo venerdì e sabato
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.MONDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.TUESDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.WEDNESDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.THURSDAY));
        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.FRIDAY));
        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.SATURDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.SUNDAY));

        assertFalse(specialFish.getAvailableDays().contains(MenuItem.DayOfWeek.ALL_DAYS));
    }

    @Test
    void testAddAvailableDay() {
        specialFish.addAvailableDay(MenuItem.DayOfWeek.SUNDAY);

        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.FRIDAY));
        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.SATURDAY));
        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.SUNDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.MONDAY));
    }

    @Test
    void testRemoveAvailableDay() {
        specialFish.removeAvailableDay(MenuItem.DayOfWeek.FRIDAY);

        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.FRIDAY));
        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.SATURDAY));
    }

    @Test
    void testSetAvailableDays() {
        Set<MenuItem.DayOfWeek> newDays = new HashSet<>();
        newDays.add(MenuItem.DayOfWeek.MONDAY);
        newDays.add(MenuItem.DayOfWeek.WEDNESDAY);

        specialFish.setAvailableDays(newDays);

        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.MONDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.TUESDAY));
        assertTrue(specialFish.isAvailableOn(MenuItem.DayOfWeek.WEDNESDAY));
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.FRIDAY)); // Non più disponibile
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.SATURDAY)); // Non più disponibile
    }

    @Test
    void testGetDetailedDescription() {
        String description = pizza.getDetailedDescription();

        assertTrue(description.contains("Margherita"));
        assertTrue(description.contains("P001"));
        assertTrue(description.contains("Pomodoro, mozzarella, basilico"));
        assertTrue(description.contains("€7.50"));
        assertTrue(description.contains("Pizze"));
    }

    @Test
    void testGetDetailedDescriptionWithLimitedDays() {
        String description = specialFish.getDetailedDescription();

        assertTrue(description.contains("Branzino al sale"));
        assertTrue(description.contains("SP001"));
        assertTrue(description.contains("€16.00"));
        assertTrue(description.contains("Secondi di pesce"));
        assertTrue(description.contains("Disponibile")); // Dovrebbe mostrare i giorni specifici
    }

    @Test
    void testCategoryDisplayName() {
        assertEquals("Pizze", MenuItem.Category.PIZZA.getDisplayName());
        assertEquals("Secondi di pesce", MenuItem.Category.SECONDO_PESCE.getDisplayName());
        assertEquals("Bevande", MenuItem.Category.BEVANDA.getDisplayName());
        assertEquals("Dolci della casa", MenuItem.Category.DOLCE.getDisplayName());
    }

    @Test
    void testEqualsAndHashCode() {
        MenuItem anotherPizza = new MenuItem("P001", "Nome Diverso", "Descrizione diversa", 10.00, MenuItem.Category.ANTIPASTO);

        assertEquals(pizza, anotherPizza); // Stesso codice
        assertEquals(pizza.hashCode(), anotherPizza.hashCode());

        MenuItem differentItem = new MenuItem("P002", "Marinara", "Altra pizza", 6.00, MenuItem.Category.PIZZA);
        assertNotEquals(pizza, differentItem);
    }

    @Test
    void testGetAvailableDaysReturnsNewSet() {
        Set<MenuItem.DayOfWeek> availableDays = specialFish.getAvailableDays();
        availableDays.add(MenuItem.DayOfWeek.MONDAY); // Modifica il set restituito

        // Il set originale non dovrebbe essere modificato
        assertFalse(specialFish.isAvailableOn(MenuItem.DayOfWeek.MONDAY));
    }

    @Test
    void testAllDaysAvailability() {
        MenuItem alwaysAvailable = new MenuItem("B001", "Acqua", "Sempre disponibile", 2.00, MenuItem.Category.BEVANDA);

        // Verifica che sia disponibile in ogni giorno specifico
        for (MenuItem.DayOfWeek day : MenuItem.DayOfWeek.values()) {
            if (day != MenuItem.DayOfWeek.ALL_DAYS) {
                assertTrue(alwaysAvailable.isAvailableOn(day));
            }
        }
    }
}