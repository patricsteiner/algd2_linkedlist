package algd2;

import static org.junit.Assert.*;
import algd2.DLinkedList.ListItem;


import org.junit.Before;
import org.junit.Test;
import algd2.DLinkedList.ListItem;

public class DLinkedListTest3 {

	DLinkedList<Integer> l1;
	DLinkedList<Integer> l2;
	
	@Before
	public void setUp() throws Exception {
		l1 = new DLinkedList<>();
		l2 = new DLinkedList<>();
		for (int i = 0; i < 10; i++) {
			l1.add(i);
		}
	}

	@Test
	public void testLinkInFront() {
		l1.linkInFront(l2.head());
		
	}

}
