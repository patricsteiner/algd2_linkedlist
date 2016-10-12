package algd2;

import java.util.ListIterator;
import algd2.DLinkedList.ListItem;


/**
 * Interface between list iterators and lists with public list items.
 * 
 * @author Christoph Stamm
 *
 */
public interface IListIterator<E> extends ListIterator<E> {
	public ListItem getVisited();
}
