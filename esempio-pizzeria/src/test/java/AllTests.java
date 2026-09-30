import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    TableTest.class,
    WaiterTest.class,
    MenuItemTest.class,
    OrderTest.class,
    PizzaManagerTest.class
})
public class AllTests {
    // Questa classe serve per eseguire tutti i test insieme
}