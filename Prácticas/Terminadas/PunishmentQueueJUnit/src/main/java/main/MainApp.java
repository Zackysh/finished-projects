package main;

import java.util.ArrayList;
import java.util.List;

import models.PunishmentQueue;

public class MainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PunishmentQueue<Integer> p = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 : a < b ? -1 : 0);
		List<Integer> longList = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			longList.add(i);
			p.add(i);
		}
		longList.remove(0);
		
		p.retainAll(longList);
		System.out.println(p);
	}

}
