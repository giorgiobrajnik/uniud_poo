import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class WaiterTest {
    private Waiter waiter;

    @BeforeEach
    void setUp() {
        waiter = new Waiter("CAM001", "Mario", "Rossi");
    }

    @Test
    void testWaiterCreation() {
        assertEquals("CAM001", waiter.getCode());
        assertEquals("Mario", waiter.getFirstName());
        assertEquals("Rossi", waiter.getLastName());
        assertEquals("Mario Rossi", waiter.getFullName());
        assertTrue(waiter.getAssignedTables().isEmpty());
    }

    @Test
    void testAssignTable() {
        waiter.assignTable(5);

        assertTrue(waiter.isAssignedToTable(5));
        assertEquals(1, waiter.getAssignedTables().size());
        assertTrue(waiter.getAssignedTables().contains(5));
    }

    @Test
    void testAssignMultipleTables() {
        List<Integer> tables = Arrays.asList(1, 3, 5, 7);
        waiter.assignTables(tables);

        assertEquals(4, waiter.getAssignedTables().size());
        for (int table : tables) {
            assertTrue(waiter.isAssignedToTable(table));
        }
    }

    @Test
    void testUnassignTable() {
        waiter.assignTable(5);
        waiter.assignTable(10);

        assertTrue(waiter.isAssignedToTable(5));
        assertTrue(waiter.isAssignedToTable(10));

        waiter.unassignTable(5);

        assertFalse(waiter.isAssignedToTable(5));
        assertTrue(waiter.isAssignedToTable(10));
        assertEquals(1, waiter.getAssignedTables().size());
    }

    @Test
    void testAssignSameTableTwice() {
        waiter.assignTable(5);
        waiter.assignTable(5); // Seconda volta

        assertEquals(1, waiter.getAssignedTables().size());
        assertTrue(waiter.isAssignedToTable(5));
    }

    @Test
    void testIsAssignedToAnyTable() {
        waiter.assignTable(3);
        waiter.assignTable(7);

        List<Integer> testTables1 = Arrays.asList(1, 3, 5); // Include 3
        List<Integer> testTables2 = Arrays.asList(2, 4, 6); // Non include nessuno
        List<Integer> testTables3 = Arrays.asList(7, 8, 9); // Include 7

        assertTrue(waiter.isAssignedToAnyTable(testTables1));
        assertFalse(waiter.isAssignedToAnyTable(testTables2));
        assertTrue(waiter.isAssignedToAnyTable(testTables3));
    }

    @Test
    void testIsAssignedToAnyTableEmptyList() {
        waiter.assignTable(5);

        List<Integer> emptyList = Arrays.asList();
        assertFalse(waiter.isAssignedToAnyTable(emptyList));
    }

    @Test
    void testEqualsAndHashCode() {
        Waiter anotherWaiter = new Waiter("CAM001", "Diverso", "Nome"); // Stesso codice

        assertEquals(waiter, anotherWaiter);
        assertEquals(waiter.hashCode(), anotherWaiter.hashCode());

        Waiter differentWaiter = new Waiter("CAM002", "Mario", "Rossi");
        assertNotEquals(waiter, differentWaiter);
    }

    @Test
    void testUnassignNonExistentTable() {
        waiter.assignTable(5);

        waiter.unassignTable(10); // Tavolo non assegnato

        assertEquals(1, waiter.getAssignedTables().size());
        assertTrue(waiter.isAssignedToTable(5));
    }

    @Test
    void testGetAssignedTablesReturnsNewList() {
        waiter.assignTable(5);
        List<Integer> assignedTables = waiter.getAssignedTables();

        assignedTables.add(10); // Modifica la lista restituita

        // La lista originale non dovrebbe essere modificata
        assertEquals(1, waiter.getAssignedTables().size());
        assertFalse(waiter.isAssignedToTable(10));
    }
}