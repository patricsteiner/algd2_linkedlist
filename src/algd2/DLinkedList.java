package algd2;

import java.util.AbstractList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class DLinkedList<E> extends AbstractList<E> implements IList<E> {

	public final class ListItem<T extends E> {
		private DLinkedList<E> parent;
		private ListItem<E> next;
		private ListItem<E> prev;
		private E data;
		private ListItem(DLinkedList<E> parent, E data) {
			this.parent = parent;
			this.data = data;
		}
		private ListItem(DLinkedList<E> parent, ListItem<E> next, ListItem<E> prev, E data) {
			this.parent = parent;
			this.next = next;
			this.prev = prev;
			this.data = data;
		}
		@Override
		public String toString() {
			return data.toString();
		}
	}

	private int size;
	//private long modCount;
	private ListItem<E> head;
	private ListItem<E> tail;
	
	public final void linkInFront(ListItem<E> item) {
		assert checkMembership(item);
		if (head == null) {
			tail = item;
			item.next = null;
		} else {
			head.prev = item;
			item.next = head;
		}
		if (item.prev != null) item.prev.next = null; //make sure item is unlinked in both directions
		item.prev = null;
		head = item;
		size++;
		modCount++;
	}
	
	public final void linkInBack(ListItem<E> item) {
		assert checkMembership(item);
		if (tail == null) {
			head = item;
			item.prev = null;
		} else {
			tail.next = item;
			item.prev = tail;
		}
		if (item.next != null) item.next.prev = null; //make sure item is unlinked in both directions
		item.next = null;
		tail = item;
		size++;
		modCount++;
	}
	
	public final void linkInBefore(ListItem<E> next, ListItem<E> item) {
		assert checkMembership(next) && checkMembership(item);
		if (next == head) {
			linkInFront(item);
		}
		else {
			next.prev.next = item;
			item.prev = next.prev;
			item.next = next;
			next.prev = item;
			size++;
			modCount++;
		}
	}
	
	public final void linkInAfter(ListItem<E> prev, ListItem<E> item) {
		assert checkMembership(prev) && checkMembership(item);
		if (prev == tail) {
			linkInBack(item);
		}
		else {
			prev.next.prev = item;
			item.next = prev.next;
			prev.next = item;
			item.prev = prev;
			size++;
			modCount++;
		}
	}
	
	public final ListItem<E> unlink(ListItem<E> item) {
		assert checkMembership(item);
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
		else {
			head= null;
			tail = null;
		}
		item.parent = null;
		item.prev = null;
		item.next = null;
		size--;
		modCount++;
		return item;
	}
	
	private void makeEmpty() {
		size = 0;
		head = null;
		tail = null;
		modCount++;
	}
	
	private void makeMembers(IList list) {
		ListItem i = (list).head();
		while(i != null) {
			i.parent = this;
			i = i.next;
		}
	}
	
//	private void addMember(ListItem<E> item) {
//		if (item.parent == null) {
//			item.parent = this;
//		}
//		else if (item.parent != this) {
//			throw new IllegalArgumentException("Cannot assign ListElement of other List to this List. Unlink first.");
//		}
//	}
	
//	private void makeSureItemIsMember(ListItem item) {
//		if (!checkMembership(item)) {
//			throw new IllegalArgumentException("Item must belong to this list.");
//		}
//	}
	
	@Override
	public boolean checkMembership(ListItem item) {
		return item != null && item.parent == this;
	}

	@Override
	public ListItem head() {
		return head;
	}

	@Override
	public ListItem tail() {
		return tail;
	}

	@Override
	public ListItem next(ListItem item) {
		assert checkMembership(item);
	    return item.next;
	}

	@Override
	public ListItem previous(ListItem item) {
	    assert checkMembership(item);
	    return item.prev;
	}

	@Override
	public ListItem cyclicNext(ListItem item) {
		assert checkMembership(item);
		if (item == tail) {
			return head;
		}
		else {
			return item.next;
		}
	}

	@Override
	public ListItem cyclicPrevious(ListItem item) {
		assert checkMembership(item);
		if (item == head) {
			return tail;
		}
		else {
			return item.prev;
		}
	}

	@Override
	public ListItem delete(ListItem item, boolean next) {
		assert checkMembership(item);
		ListItem ret = next ? item.next : item.prev;
		unlink(item);
		return ret;
	}

	@Override
	public ListItem cyclicDelete(ListItem item, boolean next) {
		assert checkMembership(item);
		ListItem ret = next ? cyclicNext(item) : cyclicPrevious(item);
		unlink(item);
		return size == 0 ? null : ret;
	}

	@Override
	public E get(ListItem item) {
		assert checkMembership(item);
		return (E) item.data;
	}

	@Override
	public void set(ListItem item, E data) {
		assert checkMembership(item);
		item.data = data;
	}

	@Override
	public E remove(ListItem item) {
		assert checkMembership(item);
		unlink(item);
		return (E) item.data;
	}

	@Override
	public ListItem addHead(E data) {
		ListItem<E> item = new ListItem<>(this, data);
		linkInFront(item);
		return item;
	}

	@Override
	public ListItem addTail(E data) {
		ListItem<E> item = new ListItem<>(this, data);
		linkInBack(item);
		return item;
	}

	@Override
	public ListItem addAfter(ListItem item, E data) {
		ListItem<E> newitem = new ListItem<>(this, data);
		if (item == null) linkInFront(newitem);
		else linkInAfter(item, newitem);
		return newitem;
	}

	@Override
	public ListItem addBefore(ListItem item, E data) {
		ListItem<E> newitem = new ListItem<>(this, data);
		if (item == null) linkInBack(newitem);
		else linkInBefore(item, newitem);
		return newitem;
	}

	@Override
	public void moveToHead(ListItem item) {
		unlink(item);
		item.parent = this;
		linkInFront(item);
	}

	@Override
	public void moveToTail(ListItem item) {
		unlink(item);
		item.parent = this;
		linkInBack(item);
	}

	@Override
	public void rotate(ListItem item) {
		assert checkMembership(item);
		if (item.prev == null) return; //if it's already head: no need to do anything
		item.prev.next = null;
		item.prev = null;
		tail.next = head;
		head.prev = tail;
		tail = head;
		head = item;
	}

	@Override
	public void swap(ListItem item1, ListItem item2) {
		if (checkMembership(item1) && checkMembership(item2) && !item1.equals(item2)) {
			if (item1 == head) head = item2;
			else if (item2 == head) head = item1;
			if (item1 == tail) tail = item2;
			else if (item2 == tail) tail = item1;
			ListItem tmpPrev = item1.prev;
			ListItem tmpNext = item1.next;
			item1.prev = item2.prev;
			item1.next = item2.next;
			item2.prev = tmpPrev;
			item2.next = tmpNext;
		}
	}

	@Override
	public void reverse() {
		//linkInFront(tail);
		//TODO
	}

	@Override
	public void addAfter(ListItem item, List<E> list) {
		//shouldn't the second parameter be of type IList?
		assert item == null || item.parent == this && list != null && list != this;
		if (item == null || item == tail) {
			conc(list, true);
		}
		else if (list instanceof IList) {
			makeMembers((IList) list);
			item.next.prev = ((IList) list).tail();
			((IList) list).tail().next = item.next;
			item.next = ((IList) list).head();
			((IList) list).head().prev = item;
			size += list.size();
			((DLinkedList<E>) list).makeEmpty();
			modCount++;
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void addBefore(ListItem item, List<E> list) {
		assert item == null || item.parent == this && list != null && list != this;
		if (item == null || item == head) {
			conc(list, false);
		}
		else if (list instanceof IList) {
			makeMembers((IList) list);
			item.prev.next = ((IList) list).head();
			((IList) list).head().prev = item.prev;
			((IList) list).tail().next = item;
			item.prev = ((IList) list).tail();
			size += list.size();
			((DLinkedList<E>) list).makeEmpty();
			modCount++;
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void conc(List<E> list, boolean after) {
		assert list != null && list != this;
		if (list instanceof IList) {
			makeMembers((IList) list);
			if (after) {
				tail.next = ((IList) list).head();
				((IList) list).head().prev = tail;
				tail = ((IList) list).tail();
			}
			else {
				((IList) list).tail().next = head;
				head.prev = ((IList) list).tail();
				head = ((IList) list).head();
			}
			size += list.size();
			((DLinkedList<E>) list).makeEmpty();
			modCount++;
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public IList<E> remove(ListItem startInclusive, ListItem endExclusive) {
		assert checkMembership(startInclusive) && checkMembership(endExclusive);
		ListItem<E> i = startInclusive;
		DLinkedList<E> removedItems = new DLinkedList<>();
		if (startInclusive == endExclusive) {
			removedItems.size = size;
			removedItems.head = head;
			removedItems.tail = tail;
			i = removedItems.head;
			while (i != null) {
				i.parent = removedItems;
				i = i.next;
			}
			makeEmpty();
		} else {
			while (i != endExclusive) {
				ListItem tmp = i;
				i = cyclicDelete(i, true);
				tmp.parent = removedItems;
				removedItems.linkInBack(tmp);
			}
		}
		return removedItems;
	}

	@Override
	public IListIterator<E> iterator() {
		return listIterator();
	}
	
	@Override
	public IListIterator<E> listIterator() {
		return new DLinkedListIterator<>();
	}

	@Override
	public IListIterator<E> listIterator(int index) {
		return new DLinkedListIterator<>(index);
	}

	@Override
	public E get(int index) {
		int i = 0;
		ListItem<E> item = head;
		while (item.next != null && i < index) {
			item = item.next;
			index++;
		}
		return item.data;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
    public boolean add(E element) {
		return addTail(element) != null;
    }
	
	
	@Override
	public String toString() {
		ListItem<E> i = head;
		StringBuilder sb = new StringBuilder("[");
		while(i != null) {
			sb.append(i.data).append(", ");
			i = i.next;
		}
		if (sb.length() > 1) sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		return sb.toString();
	}
	
	private class DLinkedListIterator<T extends E> implements IListIterator<E> {

		private int index;
		private ListItem<E> next;
		private ListItem<E> returned;
		private long curModCount;
		
		private DLinkedListIterator() {
			curModCount = modCount;
			next = head;
		}
		
		private DLinkedListIterator(int index) {
			this();
			int i = 0;
			while (hasNext() && i < index) {
				returned = next;
				next = returned.next;
				i++;
			}
			this.index = index;
		}
		
		@Override
		public boolean hasNext() {
			return next != null;
		}
		
		@Override
		public E next() {
			if (hasNext()) {
				E data = next.data;
				returned = next;
				next = returned.next;
				index++;
				return data;
			}
			return null;
		}
		
		@Override
		public boolean hasPrevious() {
			return next.prev != null;
		}
		
		@Override
		public E previous() {
			if (hasPrevious()) {
				E data = next.prev.data;
				returned = next.prev;
				next = returned;
				index--;
				return data;
			}
			return null;
		}
		
		@Override
		public int nextIndex() {
			return index + 1;
		}
		
		@Override
		public int previousIndex() {
			return index - 1;
		}
		
		@Override
		public void remove() {
			if (curModCount != modCount) throw new ConcurrentModificationException();
			if (returned == null) {
				throw new IllegalStateException();
			}
			else {
				if (returned == next) {
					next = returned.next;
			} else {
					index--;
				}
				DLinkedList.this.remove(returned);
				returned = null;
				curModCount++;
			}
		}
		
		@Override
		public void set(E e) {
			if (returned == null) {
				throw new IllegalStateException();
			}
			else {
				returned.data = e;
			}
		}
		
		@Override
		public void add(E e) {
			addBefore(next, e);
			//TODO curmod check
		}
		
		@Override
		public ListItem getVisited() {
			return returned;
		}
	}
}
