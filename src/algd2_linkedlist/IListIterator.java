package algd2_linkedlist;

import java.util.ListIterator;

/**
 * Interface between list iterators and lists with public list items.
 * 
 * @author Christoph Stamm
 *
 */
public interface IListIterator<E> extends ListIterator<E> {
	public ListItem getVisited();
}
