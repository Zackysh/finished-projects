package mainapp;
/**
 * @author AdriGB
 * @author Enzo
 */
public class Recuiterativo {

	public static void main(String[] args) {
		int[][] matriz = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };
		System.out.println("\n=============================");
		System.out.println("Test fibonacciRecursivo() (4)");
		System.out.println(fibonacciRecursivo(4));
		System.out.println("\n=============================");
		System.out.println("Test fibonacciIterativo() (4)");
		System.out.println(fibonacciIterativo(4));
		System.out.println("\n=============================");
		System.out.println("Test invertirNumRecursivo() (5132)");
		System.out.println(invertirNumRecursivo(5132));
		System.out.println("\n=============================");
		System.out.println("Test invertirNumIterativo() (5132)");
		System.out.println(invertirNumIterativo(5132));
		System.out.println("\n=============================");
		System.out.println("Test potenciaRecursiva() (3, 3)");
		System.out.println(potenciaRecursiva(3, 3));
		System.out.println("\n=============================");
		System.out.println("Test potenciaIterativa() (3, 3)");
		System.out.println(potenciaIterativo(3, 3));
		System.out.println("\n=============================");
		System.out.println("Test sumaDigitosRecursivo() (311)");
		System.out.println(sumaDigitosRecursivo(311));
		System.out.println("\n=============================");
		System.out.println("Test sumaDigitiosIterativo() (311)");
		System.out.println(sumaDigitiosIterativo(311));
		System.out.println("\n=============================");
		System.out.println("Test buscarItemArrayRecursivo() ({1, 2, 3, 4}, target = 3, desde = 0, hasta = 4)");
		System.out.println(buscarItemArrayRecursivo(matriz[0], 3, 0, matriz[0].length - 1));
		System.out.println("\n=============================");
		System.out.println("Test sumaDigitiosIterativo() ({1, 2, 3, 4}, target = 4)");
		System.out.println(buscarItemArrayIterativo(matriz[0], 4));
		System.out.println("\n=============================");
		System.out.println("Test recorrerMatrizRecursivo() ({ {1, 2, 3, 4}, {5, 6, 7, 8} }, 0, 0)");
		recorrerMatrizRecursivo(matriz, 0, 0);
		System.out.println("\n=============================");
		System.out.println("Test recorrerMatrizIterativo() ({ {1, 2, 3, 4}, {5, 6, 7, 8} })");
		recorrerMatrizIterativo(matriz);
	}

	/**
	 * 1
	 * 
	 * @param pos posicion fibonacci
	 * @return valor correspondiente en la serie de fibonacci
	 */
	public static int fibonacciRecursivo(int n) {
		if (n == 1)
			return 0;
		else if (n == 2)
			return 1;
		else
			return fibonacciRecursivo(n - 1) + fibonacciRecursivo(n - 2);
	}

	/**
	 * 2
	 * 
	 * @param pos posicion fibonacci
	 * @return valor correspondiente en la serie de fibonacci
	 */
	public static int fibonacciIterativo(int pos) {
		if (pos == 0)
			return 0;
		if (pos == 2 && pos == 1)
			return 1;
		int a = 1, b = 1, result = 0;
		for (int i = 3; i < pos; i++) {
			result = a + b;
			a = b;
			b = result;
		}
		return result;
	}

	/**
	 * 3
	 * 
	 * @param num
	 * @return numero invertido
	 */
	public static int invertirNumRecursivo(int num) {
		if (num < 10)
			return num;
		else {
			int length = (int) (Math.log10(num) + 1);
			length = (int) Math.pow(10, length - 1);
			return (num % 10) * length + invertirNumRecursivo(num / 10);
		}
	}

	/**
	 * 4
	 * 
	 * @param numero
	 * @return numero invertido
	 */
	public static int invertirNumIterativo(int numero) {
		int num = numero;
		int inverso = 0;
		int numI = 0;
		if (num < 10)
			return num;
		while (num != 0) {
			numI = num % 10;
			inverso = inverso * 10 + numI;
			num = num / 10;
		}
		return inverso;
	}

	/**
	 * 5
	 * 
	 * @param num numero
	 * @param ex  exponente
	 * @return potencia
	 */
	public static double potenciaRecursiva(int num, int ex) {
		if (ex < 0)
			return 1 / potenciaRecursiva(num, ex * -1);
		else if (ex == 0)
			return 1;
		else if (ex == 1)
			return num;
		else
			return num * potenciaRecursiva(num, ex - 1);
	}

	/**
	 * 6
	 * 
	 * @param num numero
	 * @param ex  exponente
	 * @return potencia
	 */
	public static double potenciaIterativo(int num, double ex) {
		double result = num;
		if (ex > 0)
			for (int i = 1; i < ex; i++)
				result = result * num;
		else if (ex < 0) {
			for (int i = -1; i < ex; i++)
				result = result * num;
			result = 1 / (result);
		} else
			result = 1;
		return result;
	}

	/**
	 * 7
	 * 
	 * @param num
	 * @return suma de los digitos
	 */
	public static int sumaDigitosRecursivo(int num) {
		if (num != 0) {
			return sumaDigitosRecursivo(num / 10) + num % 10;
		} else {
			return 0;
		}
	}

	/**
	 * 8
	 * 
	 * @param num
	 * @return suma de los digitos
	 */
	public static int sumaDigitiosIterativo(int num) {
		int resultado = 0;
		while (num > 0) {
			resultado += num % 10;
			num = num / 10;
		}
		return resultado;
	}

	/**
	 * 9
	 * Metodo que dado un array y un valor objetivo, y dos ceros
	 * preferentemente (borde izq y borde derecho / desde y hasta), busca el objetivo.
	 * Para ello comienza revisando los bordes del array, luego los va
	 * reduciendo/estrechando (desde++, hasta--) hasta que se cruzan.
	 * 
	 * @param arr    array
	 * @param target valor a buscar
	 * @param desde  indice menor desde el que empezar a buscar
	 * @param hasta  indice mayor, determina hasta donde se busca
	 * @returns -1 si no se encuentra el valor
	 * @returns indice en el que se encuentra el item (empezando desde 0)
	 */
	static int buscarItemArrayRecursivo(int arr[], int target, int desde, int hasta) {
		if (hasta < desde)
			return -1;
		if (arr[desde] == target)
			return desde;
		if (arr[hasta] == target)
			return hasta;
		return buscarItemArrayRecursivo(arr, target, desde + 1, hasta - 1);
	}

	/**
	 * 10
	 * 
	 * @param arr    array
	 * @param target valor a buscar
	 * @returns -1 si no se encuentra el valor
	 * @returns indice en el que se encuentra el item (empezando desde 0)
	 */
	public static int buscarItemArrayIterativo(int[] arr, int target) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == target)
				return i;
		System.out.println("El array no contiene el elemento buscado.");
		return -1;
	}

	/**
	 * 11 - Al llamar por primera vez a este metodo, pasar dos ceros como i y j
	 * respectivamente. Sino cosas malas podrian pasar.
	 * 
	 * @param m matriz a recorrer
	 * @param i indice 1
	 * @param i indice 2
	 */
	public static void recorrerMatrizRecursivo(int m[][], int i, int j) {
		if (m.length - 1 >= i)
			if (m[i].length - 1 >= j) {
				if (j == 0)
					System.out.print("[");
				System.out.print(" " + m[i][j]);
				if (j == m[i].length - 1)
					System.out.print(" ]");
				if (m[i].length - 1 == j)
					recorrerMatrizRecursivo(m, ++i, 0);
				recorrerMatrizRecursivo(m, i, ++j);
			}
	}

	/**
	 * 12
	 * 
	 * @param m matriz a recorrer
	 */
	public static void recorrerMatrizIterativo(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			System.out.print("[");
			for (int j = 0; j < m[0].length; j++)
				System.out.print(" " + m[i][j] + " ");
			System.out.print("]");
			System.out.println();
		}
	}

}
