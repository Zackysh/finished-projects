package main;

import java.util.Arrays;
import java.util.Stack;

public class MainApp {

	/**
	 * Dados estas personas: { pipo, pepe, maria, luicia }
	 * 
	 * Crear un hashSet con clave su nombre y valor el dinero en su cuenta bancaria,
	 * el siguiente: - pipo: 100. - pepe: 200. - maria: 3200. - luicia: 190
	 * 
	 * !) Lucia se cambia de banco, tienes que eliminar sus datos o te denuncia.
	 * Ahora.
	 * 
	 * 2) Pepe cobra la lotería, 1 000€.
	 * 
	 * 3) Tu jefe te pide una lista con los nombres de los clientes actuales. O se
	 * la das o te da.
	 * 
	 * 4) Un cliente llamado pipo quiere ver cuánto dinero tiene, díselo.
	 * 
	 * HashMap:
	 * put() - insertar registro
	 * contains() / containsAll()
	 * entrySet() - colección con registros
	 * keySet() - colección con keys
	 * get(key) - objecto con esa key replace(key, value) - sobreescribir valor para la key
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Stack<Integer> s = new Stack<Integer>();
		int[] a = new int[4];
		int[] b;
		a = Arrays.copyOf(a, a.length + 1);
		
		
		
		s.push(1);
		s.push(2);
		s.push(3);
		s.push(4);
		
		s.pop();
		
		s.remove(2);
		
		System.out.println(s);

	}
}
