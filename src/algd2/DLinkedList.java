package algd2;

import java.util.AbstractList;
import java.util.List;


public class DLinkedList<E> extends AbstractList<E> implements IList<E> {

	public class ListItem<E> {
		private DLinkedList<E> parent;
		private ListItem<E> next;
		private ListItem<E> prev;
		private E data;
		private ListItem(E data) {
			this.data = data;
		}
		private ListItem(DLinkedList<E> parent, ListItem<E> next, ListItem<E> prev, E data) {
			this.parent = parent;
			this.next = next;
			this.prev = prev;
			this.data = data;
		}
	}

	private int size;
	private ListItem<E> head;
	private ListItem<E> tail;
	
	public void linkInFront(ListItem<E> item) {
		assert item != null;
		addMember(item);
		if (head != null) {
			head.prev = item;
			item.next = head;
		} else {
			tail = item;
			item.next = null;
		}
		item.prev = null;
		head = item;
		size++;
	}
	
	public void linkInBack(ListItem<E> item) {
		linkInAfter(tail, item);
	}
	
	public void linkInAfter(ListItem<E> prev, ListItem<E> item) {
		assert item != null && prev != null;
		if (!checkMembership(prev)) {
			throw new IllegalArgumentException("Prev must belong to this list.");
		}
		addMember(item);
		if (prev.next != null) {
			prev.next.prev = item;
			item.next = prev.next;
		}
		else {
			item.next = null;
			tail = item;
		}
		item.prev = prev;
		prev.next = item;
		size++;
	}
	
	public ListItem<E> unlink(ListItem<E> item) {
		assert item != null;
		if (!checkMembership(item)) {
			throw new IllegalArgumentException("Item must belong to this list.");
		}
		if (item.prev != null && item.next != null) {
			item.prev.next = item.next;
			item.next.prev = item.prev;
		}
		else if (item.prev != null) {
			item.prev.next = null;
			tail = item.prev;
		}
		else if (item.next != null) {
			item.next.prev = null;
			head = item.next;
		}
		item.parent = null;
		item.prev = null;
		item.next = null;
		size--;
		return item;
	}
	
	private void addMember(ListItem<E> item) {
		if (item.parent == null) {
			item.parent = this;
		}
		else if (item.parent != this) {
			throw new IllegalArgumentException("Cannot assign ListElement of other List to this List. Unlink first.");
		}
	}
	
	@Override
	public boolean checkMembership(ListItem item) {
		return item.parent == this;
	}

	@Override
	public ListItem head() {
		return head; // null is allowed
	}

	@Override
	public ListItem tail() {
		return tail; // null is allowed
	}

	@Override
	public ListItem next(ListItem item) {
		if(item.parent.equals(this)){ // item is in this list
			if(this.tail.equals(item)){
				return null;
			} else {
				return item.next;
			}
		}
		return null;
	}

	@Override
	public ListItem previous(ListItem item) {
		if(item.parent.equals(this)){
			if(this.head.equals(item)){
				return null;
			} else {
				return item.prev;
			}
		}
		return null;
	}

	@Override
	public ListItem cyclicNext(ListItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListItem cyclicPrevious(ListItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListItem delete(ListItem item, boolean next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListItem cyclicDelete(ListItem item, boolean next) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E get(ListItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(ListItem item, E data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove(ListItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListItem addHead(E data) {
		ListItem<E> item = new ListItem<>(data);
		linkInFront(item);
		return item;
	}

	@Override
	public ListItem addTail(E data) {
		ListItem<E> item = new ListItem<>(data);
		linkInBack(item);
		return item;
	}

	@Override
	public ListItem addAfter(ListItem item, E data) {
		ListItem<E> newitem = new ListItem<>(data);
		linkInAfter(item, newitem);
		return newitem;
	}

	@Override
	public ListItem addBefore(ListItem item, E data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveToHead(ListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToTail(ListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(ListItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void swap(ListItem item1, ListItem item2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(ListItem item, List<E> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBefore(ListItem item, List<E> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void conc(List<E> list, boolean after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IList<E> remove(ListItem startInclusive, ListItem endExclusive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		ListItem<E> i = head;
		String s = "[";
		while(i != null){
			s += i.data + ", ";
			i = i.next;
		}
		s += "]";
		return s;
	}

}
