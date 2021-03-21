package models;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import exceptions.ElementBlockedException;
import exceptions.LlevateTuNullDeAquiException;
import exceptions.QueueExceededSizeException;

/**
 * Class that implement Queue interface, therefore it has almost
 * the same behavior. There are various differences: <br>
 *  - It doesn't accept null elements. <br>
 *  - It can't be empty once the first element has been inserted. <br>
 *  - It can't store more than 10 elements. <br>
 *  - It is sorted every time an element is added or removed. <br>
 *  
 * @author AdriGB
 *
 * @param <E> Generic type
 */
public class PunishmentQueue<E> implements Queue<E> {

	private Comparator<E> comparator;
	private List<E> queue;

	private int limit = 10;

	/**
	 * Since it is a generic class and must be able to use sort method correctly,
	 * it must go next to a comparator.
	 *  
	 * @param comparator Suitable for the specified generic type
	 */
	public PunishmentQueue(Comparator<E> comparator) {

		this.comparator = comparator;

		this.queue = new LinkedList<E>();

	}

	/**
	 * Sort this queue.
	 */
	public void sort() { // tested
		queue.sort(comparator);
	}

	@Override
	public boolean remove(Object o) throws ElementBlockedException { //  tested
		if (queue.size() == 1)
			throw new ElementBlockedException();
		boolean result = queue.remove(o);
		sort();
		return result;
	}
	
	@Override
	public boolean add(E e) throws LlevateTuNullDeAquiException { //  tested
		// push (have limit / exception if exceed))
		if (e == null)
			throw new LlevateTuNullDeAquiException();
		if (size() == 10) {
			try { throw new QueueExceededSizeException(); }
			catch (QueueExceededSizeException er) { er.fillInStackTrace(); }
			return false;
		}
		boolean result = queue.add(e);
		sort();
		return result;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) throws LlevateTuNullDeAquiException { //  tested
		for (E e : c)
			if (e == null)
				throw new LlevateTuNullDeAquiException();
		if (queue.size() + c.size() > limit) {
			try { throw new QueueExceededSizeException(); }
			catch(QueueExceededSizeException er) { System.err.println(er); }
			return false;
		}
		// we handle the error here, because Queue interface doesn't
		// throws any exception in this method.
		// besides, we avoid using RuntimeException and at last
		// providing exception tolerance. This practice is common and
		// suggested as best-coding practice.
		boolean result = queue.addAll(c);
		sort();
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) throws ElementBlockedException { //  tested
		if (containsAll(c) && queue.size() == c.size())
			throw new ElementBlockedException();
		boolean result = queue.removeAll(c);
		sort();
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) throws ElementBlockedException { //  tested
		// lets avoid empty our queue (there is multiple critical situations)
		if (queue.isEmpty())
			return false;
		boolean atLeastOne = false;
		for (Object object : queue)
			if (c.contains(object))
				atLeastOne = true;
		if (!atLeastOne)
			throw new ElementBlockedException();
		
		boolean result = queue.retainAll(c);
		sort();
		return result;
	}

	@Override
	public void clear() { //  tested
		if(queue.size() == 1)
			throw new ElementBlockedException();
		if (!queue.isEmpty())
			queue.subList(1, queue.size()).clear();
	}

	@Override
	public boolean offer(E e) throws LlevateTuNullDeAquiException { //  tested (similar behavior to add)
		// push (doesn't have limit / no exception if exceed))
		if (e == null)
			throw new LlevateTuNullDeAquiException();
		boolean result = queue.add(e);
		sort();
		return result;
	}

	@Override
	public E remove() throws ElementBlockedException { //  tested
		// retrieve, remove first (throw exception if empty)
		if (queue.size() == 1)
			throw new ElementBlockedException();
		E retrieved = queue.get(0);
		// it can throw an exception at this point when queue is empty,
		// at start, when queue is empty
		queue.remove(0);
		sort();
		return retrieved;
	}

	@Override
	public E poll() { // tested (similar behavior to remove)
		// retrieve, remove first (return null if empty)
		if (queue.isEmpty()) // can happen at start, when queue is empty
			return null;
		E retrieved = queue.get(0);
		queue.remove(0);
		sort();
		return retrieved;
	}

	@Override
	public E element() { // not tested
		// retrieve first (throw exception if empty)
		return queue.get(0);
	}

	@Override
	public E peek() { // not tested (similar behavior to element)
		// retrieve first (return null if empty)
		return queue.isEmpty() ? null : queue.get(0);
	}
	
	@Override
	public int size() { // not tested
		return queue.size();
	}

	@Override
	public boolean isEmpty() { // not tested
		return queue.isEmpty();
	}

	@Override
	public boolean contains(Object o) { // not tested
		return queue.contains(o);
	}

	@Override
	public Iterator<E> iterator() { // not tested
		return queue.iterator();
	}

	@Override
	public Object[] toArray() { // not tested
		return queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) { // not tested
		return queue.toArray(a);
	}

	@Override
	public boolean containsAll(Collection<?> c) { //  not tested
		return queue.containsAll(c);
	}

	@Override
	public String toString() {
		return queue.toString();
	}

}