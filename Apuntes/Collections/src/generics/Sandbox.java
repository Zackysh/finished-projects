package generics;

import java.util.HashMap;

public class Sandbox {
	
	public static void main(String[] args) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		
		map.put(1, "Pollo");
		map.put(2, "Pavo");
		map.put(3, "Ternera");
		map.put(4, "Cerdo");
		
		// map.remove(2);
		map.values().forEach((value) -> System.out.println(value));
	}
	
}
