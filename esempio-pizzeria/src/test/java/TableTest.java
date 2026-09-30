import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class TableTest {
    private Table indoorTable;
    private Table outdoorTable;

    @BeforeEach
    void setUp() {
        indoorTable = new Table(1, 4, true);
        outdoorTable = new Table(16, 6, false);
    }

    @Test
    void testTableCreation() {
        assertEquals(1, indoorTable.getTableNumber());
        assertEquals(4, indoorTable.getCapacity());
        assertTrue(indoorTable.isIndoor());
        assertFalse(indoorTable.isJoined());
    }

    @Test
    void testOutdoorTable() {
        assertEquals(16, outdoorTable.getTableNumber());
        assertEquals(6, outdoorTable.getCapacity());
        assertFalse(outdoorTable.isIndoor());
        assertFalse(outdoorTable.isJoined());
    }

    @Test
    void testJoinTables() {
        Table table2 = new Table(2, 6, true);
        indoorTable.joinWith(table2);

        assertTrue(indoorTable.isJoined());
        assertEquals(10, indoorTable.getCapacity()); // 4 + 6
        assertTrue(indoorTable.getJoinedTables().contains(1));
        assertTrue(indoorTable.getJoinedTables().contains(2));
    }

    @Test
    void testJoinedTableConstructor() {
        List<Integer> tableNumbers = Arrays.asList(3, 4, 5);
        Table joinedTable = new Table(tableNumbers, 12, true);

        assertEquals(3, joinedTable.getTableNumber()); // Primo della lista
        assertEquals(12, joinedTable.getCapacity());
        assertTrue(joinedTable.isIndoor());
        assertTrue(joinedTable.isJoined());
        assertEquals(tableNumbers, joinedTable.getJoinedTables());
    }

    @Test
    void testTableDescription() {
        assertEquals("Tavolo 1 (4 coperti)", indoorTable.getTableDescription());

        Table table2 = new Table(2, 6, true);
        indoorTable.joinWith(table2);

        assertTrue(indoorTable.getTableDescription().contains("Tavoli"));
        assertTrue(indoorTable.getTableDescription().contains("uniti"));
        assertTrue(indoorTable.getTableDescription().contains("10 coperti"));
    }

    @Test
    void testEqualsAndHashCode() {
        Table anotherTable1 = new Table(1, 8, false); // Stesso numero, caratteristiche diverse

        assertEquals(indoorTable, anotherTable1);
        assertEquals(indoorTable.hashCode(), anotherTable1.hashCode());

        assertNotEquals(indoorTable, outdoorTable);
    }

    @Test
    void testJoinMultipleTables() {
        Table table2 = new Table(2, 4, true);
        Table table3 = new Table(3, 6, true);

        indoorTable.joinWith(table2);
        indoorTable.joinWith(table3);

        assertEquals(14, indoorTable.getCapacity()); // 4 + 4 + 6
        assertEquals(3, indoorTable.getJoinedTables().size());
    }

    @Test
    void testJoinSameTableTwice() {
        Table table2 = new Table(2, 4, true);

        indoorTable.joinWith(table2);
        int capacityAfterFirstJoin = indoorTable.getCapacity();

        indoorTable.joinWith(table2); // Seconda volta

        assertEquals(capacityAfterFirstJoin, indoorTable.getCapacity()); // Non dovrebbe cambiare
        assertEquals(2, indoorTable.getJoinedTables().size()); // Dovrebbe rimanere 2
    }
}