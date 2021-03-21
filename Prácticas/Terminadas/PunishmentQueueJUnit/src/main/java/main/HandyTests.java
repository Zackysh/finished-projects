package main;

import java.util.LinkedList;
import java.util.List;

import models.PunishmentQueue;

public class HandyTests {
	
	/**
	 * Possible scenarios:
	 *  - Empty queue
	 *  - Not empty queue
	 */
	public static void testClear() { // done
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		System.out.println("==================================");
		System.out.println("          CLEAR TESTS");
		System.out.println("==================================");
		System.out.println("When queue is empty:");
		System.out.println("Initial state -> " + p);
		p.clear();
		System.out.println("Final state: " + p + ", in this case I have chosen not to throw any exception");
		p.add(1); p.add(2); p.add(3); p.add(4); p.add(5);
		System.out.println("-----------------------------------");
		System.out.println("When queue is not empty:");
		System.out.println("Initial state -> " + p);
		p.clear();
		System.out.println("Final state -> " + p + ", removes all but first");
	}
	
	/**
	 * Possible scenarios:
	 *  - Correct parameter, no exceed limit.
	 *  - Correct parameter, exceed limit.
	 *  - Incorrect parameter (checked first).
	 */
	public static void testAdd() { // exceptions remaining
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		System.out.println("==================================");
		System.out.println("           ADD TESTS");
		System.out.println("==================================");
		System.out.println("When parameter is correct and limit isn't exceeded:");
		System.out.println("Initial state: " + p);
		p.add(5);
		System.out.println("Final state: " + p);
	}
	
//	public static void testOffer() {
//		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
//		p.add(1); p.add(2); p.add(3); p.add(4);
//		System.out.println("==================================");
//		System.out.println("Test offer(5):");
//		System.out.println("Initial state: " + p);
//		p.offer(5);
//		System.out.println("Final state: " + p);
//	}
	
	public static void testRemove() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		System.out.println("==================================");
		System.out.println("Test remove();");
		System.out.println("Initial state: " + p);
		Integer a = p.remove();
		System.out.println("Final state: " + p + ", removed item (retrieved): " + a);
		System.out.println("==================================");
		System.out.println("Test remove(2);");
		System.out.println("Initial state: " + p);
		boolean result = p.remove(2);
		System.out.println("Result (if an element was removed): " + result);
	}
	
	public static void testElement() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		System.out.println("==================================");
		System.out.println("Test element():");
		System.out.println("Initial state: " + p);
		Integer a = p.element();
		System.out.println("Retrieved item: " + a);
	}
	
	public static void testRetainAll() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		List<Integer> list = new LinkedList<Integer>();
		list.add(2);
		System.out.println("==================================");
		System.out.println("Test retainAll():");
		System.out.println("Initial state: queue ->" + p + ", list -> " + list);
		p.retainAll(list);
		System.out.println("Final state: " + p);
	}
	
	public static void testRemoveAll() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		List<Integer> list = new LinkedList<Integer>();
		list.add(2);
		System.out.println("==================================");
		System.out.println("Test removeAll():");
		System.out.println("Initial state: queue ->" + p + ", list -> " + list);
		p.removeAll(list);
		System.out.println("Final state: " + p);
	}
	
	public static void testAddAll() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		List<Integer> list = new LinkedList<Integer>();
		list.add(2);
		System.out.println("==================================");
		System.out.println("Test addAll():");
		System.out.println("Initial state: queue ->" + p + ", list -> " + list);
		p.addAll(list);
		System.out.println("Final state: " + p);
	}
	
	public static void testContainsAll() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		List<Integer> list = new LinkedList<Integer>();
		list.add(2);
		System.out.println("==================================");
		System.out.println("Test containsAll(list):");
		System.out.println("Initial state: queue ->" + p + ", list -> " + list);
		boolean result = p.containsAll(list);
		System.out.println("Result: " + result);
		System.out.println("==================================");
		System.out.println("Test containsAll(list):");
		list.add(-1);
		System.out.println("Initial state: queue ->" + p + ", list -> " + list);
		result = p.containsAll(list);
		System.out.println("Result: " + result);
	}
	
	public static void testContains() {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 :a < b ? -1 : 0);
		p.add(1); p.add(2); p.add(3); p.add(4);
		System.out.println("==================================");
		System.out.println("Test contains(2) && contains(-1):");
		System.out.println("Initial state: queue ->" + p);
		boolean result = p.contains(2);
		boolean result1 = p.contains(-1);
		System.out.println("Result 1: " + result + " Result 2: " + result1);
	}

}
