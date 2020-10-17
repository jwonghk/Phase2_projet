package persistence;

import model.Category;
import model.Thingy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkThingy(String name, Category category, Thingy thingy) {
        assertEquals(name, thingy.getName());
        assertEquals(category, thingy.getCategory());
    }
}
