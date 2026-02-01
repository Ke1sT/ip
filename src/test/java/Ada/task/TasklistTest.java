package Ada.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assumptions;

public class TasklistTest {

    @Test
    void testAddGet() {
        TaskList t1 = new TaskList();
        t1.add(new Todo("test"));
        assertEquals("[T][ ] test", t1.get(0).toString());
    }

    @Test
    void testDeleteSize() {
        TaskList t1 = new TaskList();
        t1.add(new Todo("test1"));
        t1.add(new Todo("test2"));
        assertEquals(2, t1.size());
        t1.delete(0);
        assertEquals("[T][ ] test2", t1.get(0).toString());
        assertEquals(1, t1.size());
    }
}
