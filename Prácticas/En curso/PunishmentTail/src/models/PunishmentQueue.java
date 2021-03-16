package models;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import exception.QueueExceptions;

public class PunishmentQueue<E> implements Queue<E> {
	
	private Comparator<E> comparator;
	private List<E> queue;
	
	private int limit = 10;
	

	public PunishmentQueue (Comparator<E> comparator) {
		
		this.comparator = comparator;
		
		this.queue = new LinkedList<E>();
		
	}
	
	public void justThrow(String errMessage) throws QueueExceptions {
		throw new QueueExceptions(errMessage);
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return queue.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return queue.iterator();
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO ?
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO ?
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return queue.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return queue.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO ?
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO ?
		return false;
	}

	@Override
	public void clear() {
		// TODO remove all
		
	}

	@Override
	public boolean add(E e) {
		// push (have limit / exception if exceed))
		if(size() == 10) {
			throw new QueueExceptions("Max capacity exceeded");
		}
		return queue.add(e);
	}

	@Override
	public boolean offer(E e) {
		// push (doesn't have limit / no exception if exceed))
		return queue.add(e);
	}

	@Override
	public E remove() {
		// retrieve, remove first (throw exception if empty)
		
		E retrieved = queue.get(0);
		queue.remove(0);
		return retrieved;
	}

	@Override
	public E poll() {
		// TODO retrieve, remove first (return null if empty)
		return null;
	}

	@Override
	public E element() {
		// TODO retrieve first (throw exception if empty)
		return null;
	}

	@Override
	public E peek() {
		// TODO retrieve first (return null if empty)
		return null;
	}

	@Override
	public String toString() {
		return queue.toString();
	}
	
	
	
}
