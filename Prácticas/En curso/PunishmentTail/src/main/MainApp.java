package main;

import models.PunishmentQueue;

public class MainApp {

	public static void main(String[] args) {
		
		PunishmentQueue<Integer> s = new PunishmentQueue<Integer>((a, b) -> a > b ? 1 : 0);
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(7);
		s.add(8);
		s.add(9);
		s.add(10);
		s.add(10);
		
		
		
		System.out.println(s.size());
		
	}
	
}
