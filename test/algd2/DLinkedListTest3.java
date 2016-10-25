package algd2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DLinkedListTest3 {
    private IList<Integer> list1, list2;

    @Before
    public void setUp() throws Exception {
        list1 = new DLinkedList<>();
        list2 = new DLinkedList<>();
        for(int i=1; i <= 3; i++) {
            list1.add(2*i);
            list2.add(2*i);
        }
    }
    
    @Test
    public void testAddListAfterNull() {
        int s = list1.size() + list2.size();
        list1.addAfter(null, list2);
        assertEquals(s, list1.size());
        assertEquals(0, list2.size());
        list1.addAfter(null, list2);
        assertEquals(s, list1.size());
        assertEquals(0, list2.size());
        list2.addAfter(null, list1);
        assertEquals(s, list2.size());
        assertEquals(0, list1.size());
    }
    
    @Test
    public void testAddListBeforeNull() {
        int s = list1.size() + list2.size();
        list1.addBefore(null, list2);
        assertEquals(s, list1.size());
        assertEquals(0, list2.size());
        list1.addBefore(null, list2);
        assertEquals(s, list1.size());
        assertEquals(0, list2.size());
        list2.addBefore(null, list1);
        assertEquals(s, list2.size());
        assertEquals(0, list1.size());
    }
}
