package punishmentQueeueTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.ElementBlockedException;
import exceptions.LlevateTuNullDeAquiException;
import models.PunishmentQueue;

/**
 * Punishment Queue tests.
 * 
 * @author AdriGB
 */
public class PQUnitTests {

	private static PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 : a < b ? -1 : 0);
	private static List<Integer> shortList = new ArrayList<Integer>(); // used to normal test
	private static List<Integer> longList = new ArrayList<Integer>(); // used to exceed queue limit
	private static List<Integer> emptyList = new ArrayList<Integer>();
	
	private boolean checkSortedList(PunishmentQueue<Integer> p) {
		int aux = p.element();
		for (Integer i : p) {
			if(!(aux <= i)) return false;
			aux = i;
		}
		return true;
	}
	
	@BeforeEach // reset list
	void fillList() {
		longList.clear();
		for (int i = 0; i < 9; i++) longList.add(i);
		shortList.clear();
		for (int i = 0; i < 4; i++) shortList.add(i);
	}
	
	@AfterEach // reset queue
	void resetQueue() {
		p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 : a < b ? -1 : 0);
		emptyList.clear();
	}
	
	@Test
	void add() { // done
		assertTrue(p.add(1));
		assertThrows(LlevateTuNullDeAquiException.class, () -> p.add(null));
		assertFalse(() -> {
			p.addAll(longList); // longList has 10 element beforeEach test
			return p.add(11);
		});
		// p is not restarted before test finalize, so we can check if its sorted after testing add
		assertTrue(checkSortedList(p));
	}
	
	@Test
	void offer() { // done
		assertTrue(p.offer(1));
		assertThrows(LlevateTuNullDeAquiException.class, () -> p.offer(null));
		assertTrue(() -> {
			p.addAll(longList);
			return p.offer(11); // offer ignores limit
		});
		assertTrue(checkSortedList(p));
	}
	
	@Test
	void addAll() {
		p.add(1);
		int initialSize = p.size();
		int first = p.element();
		// test addAll() - empty list
		assertAll(() -> {
			assertFalse(p.addAll(emptyList));
			assertTrue(p.size() == initialSize && p.element() == first);
		});
		
		// test addAll() - null element in list
		emptyList.add(-15); emptyList.add(null); emptyList.add(15);
		assertThrows(LlevateTuNullDeAquiException.class, () -> p.addAll(emptyList));
		
		// test addAll() - proper list
		assertAll(() -> {
			assertFalse(p.containsAll(shortList));
			assertTrue(p.addAll(shortList));
			assertTrue(p.containsAll(shortList));
		});
		
		// test addAll() - list exceed capacity
		assertAll(() -> {
			// exception is handled inside addAll() (CHECK CONSOLE), tests will continue (no errors)
			assertFalse(p.addAll(longList));
			assertFalse(p.containsAll(longList));
		});
	}

	@Test
	void sort() { // done
		assertTrue(() -> {
			p.add(3); p.add(1); p.add(-1); p.add(5);
			p.sort();
			return checkSortedList(p);
		});
	}
	
	@Test
	void clear() {
		// test clear() - queue is empty
		assertDoesNotThrow(() -> p.clear());
		
		// test clear() - queue has one element
		assertAll(() -> {
			p.add(1);
			assertThrows(ElementBlockedException.class, () -> p.clear());
			assertTrue(p.contains(1));
		});
		
		// test clear() - queue has >1 element
		assertAll(() -> {
			p.add(2);
			assertDoesNotThrow(() -> p.clear());
			assertFalse(p.contains(2));
		});
	}
	
	@Test
	void removeAll() {
		// test removeAll - proper list
		p.add(1); p.add(2); p.add(3); p.add(4);
		emptyList.add(1); emptyList.add(2);
		assertTrue(p.contains(1) && p.contains(2));
		assertTrue(p.removeAll(emptyList));
		assertFalse(p.contains(1) && p.contains(2));
		
		// test removeAll - incorrect list
		emptyList.clear(); emptyList.add(3); emptyList.add(4);
		assertThrows(ElementBlockedException.class, () -> p.removeAll(emptyList));
	}
	
	@Test
	void retainAll() {
		// test retainAll() - empty queue
		assertFalse(p.retainAll(shortList));
		
		// test retainAll() - empty list
		p.add(99);
		assertThrows(ElementBlockedException.class, () -> p.retainAll(emptyList));
		
		// test retainAll() - no matches between queue and list
		// (normally it should empty queue, not in this case)
		assertThrows(ElementBlockedException.class, () -> p.retainAll(shortList));
	}
	
	@Test
	void remove() {
		// test remove() - queue is empty
		assertThrows(IndexOutOfBoundsException.class, () -> p.remove());
		// test remove(Object) - queue is empty
		assertFalse(p.remove(2));
		
		p.add(1);
		// test remove() - queue has 1 element
		assertThrows(ElementBlockedException.class, () -> p.remove());
		
		// test remove(Object) - queue has 1 element
		assertThrows(ElementBlockedException.class, () -> p.remove(1));
		
		
		p.addAll(shortList);
		// test remove() - queue size > 1
		assertAll(() -> {
			int initialSize = p.size();
			int first = p.element();
			assertTrue(p.remove() == first);
			assertTrue(initialSize > p.size());
			assertTrue(p.element() != first);
		});
		
		// test remove(Object) - queue size > 1
		assertAll(() -> {
			int initialSize = p.size();
			assertTrue(p.remove(3));
			assertTrue(initialSize > p.size());
			assertFalse(p.contains(3));
		});
	}
	
	@Test
	void poll() {
		// test poll() - queue is empty
		assertNull(p.poll());
		
		// test poll() - queue is not empty
		int inserted = 23;
		p.add(inserted);
		assertTrue(p.poll() == inserted);
	}
	
	@Test
	void element() {
		// test element() - queue is empty
		assertThrows(IndexOutOfBoundsException.class, () -> p.element());
		
		// test element() - queue is not empty
		p.add(1);
		assertTrue(p.element() == 1);
	}
	
	@Test
	void peek() {
		// test element() - queue is empty
		assertNull(p.peek());
		
		// test element() - queue is not empty
		p.add(1);
		assertTrue(p.peek() == 1);
	}
}
