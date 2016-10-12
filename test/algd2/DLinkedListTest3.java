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
		for (int i = 9; i >= 0; i--) {
			l1.addHead(i);
			l2.addHead(i+10);
		}
	}

	@Test
	public void testLinkInFront() {
		
		l1.linkInAfter(l1.head(), l2.unlink(l2.head()));
		l1.linkInAfter(l1.head(), l2.unlink(l2.head()));
		l1.linkInAfter(l1.head(), l2.unlink(l2.head()));
		
		System.out.println(l1);
		System.out.println(l2);
		
		
	}

}
