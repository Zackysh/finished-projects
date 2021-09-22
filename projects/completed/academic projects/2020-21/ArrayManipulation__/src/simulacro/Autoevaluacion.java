package simulacro;

public class Autoevaluacion {

	/**
	 * Devuelve el número de ocurrencias que hay de un determinado elemento n dentro
	 * de una matriz.
	 * 
	 * @param matriz donde buscar
	 * @param n      elemento a buscar dentro de la matriz
	 * @return la cuenta total de elementos que hay dentro de la matriz
	 */
	public static int contarElemento(int[][] matriz, int n) {
		
		int cont = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				
				if(matriz[i][j] == n) cont++;
				
			}
		}
		
		return cont;
	}

	/**
	 * Determina si todos los números de un array son positivos (o 0) o no. El 0
	 * también es natural.
	 * 
	 * @param array a analizar
	 * @return true si todos son naturales y false en caso contrario
	 */
	public static boolean sonTodosNaturales(int[] array) {
		
		boolean isNatural = false;
		for (int i = 0; i < array.length; i++) {
			if(array[i] >= 0) isNatural = true;
			else {
				isNatural = false;
				return isNatural;
			}
		}
		
		return isNatural;
	}

	/**
	 * Separa los números pares de los impares en una nueva matriz de dos arrays en
	 * la cual el primer array serán los números pares y el segundo array serán los
	 * impares.
	 * 
	 * @param array a analizar
	 * @return una matriz de 2 filas donde cada fila será un array. El primer array
	 *         serán los números pares y el segundo array serán los impares.
	 */
	public static int[][] separarPares(int[] array) {
		int par = 0;
		int impar = 0;
		for (int i = 0; i < array.length; i++) {
			if(array[i]%2 == 0) par++;
			else impar++;
		}
		
		int it = 0;
		int[] totalPares = new int[par];

		for (int i = 0; i < array.length; i++) {
			if(array[i]%2 == 0) totalPares[it++] = array[i];
		}
		it = 0;
		int[] totalImpares = new int [impar];
		for (int i = 0; i < array.length; i++) {
			if(array[i]%2 != 0) totalImpares[it++] = array[i];
		}
		
		int[][] parImpar = {totalPares, totalImpares};
		
		return parImpar;
	}

	/**
	 * Sea una matriz (a b c) 
	 *                (d e f)
	 * 
	 * @param matriz a dar la vuelta (flipped o volteada) sobre el eje vertical.
	 * @return la matriz invertida:
	 * 
	 *         (c b a) 
	 *         (f e d)
	 */
	public static int[][] flip(int[][] matriz) {
		
		int[][] matrizInvertida = new int[matriz.length][matriz[0].length];
		
		int col = matriz[0].length - 1; // Original array iterator
		int row = 0;
		for (int i = 0; i < matrizInvertida.length; i++) {
			for (int j = 0; j < matrizInvertida[0].length; j++) {
				matrizInvertida[i][j] = matriz[row][col--];
			}
			col = matriz[0].length - 1;
			row++;
		}		
		
		return matrizInvertida;
	}

	public static void main(String[] args) {
		double nota = 0;

		try {
			// Para probar contarElemento
			int[][] arrayContar = { { 1, 5, 3 }, { 3, 2, 1 }, { 1, 11, 3 } };
			assert contarElemento(arrayContar, 1) == 3 : "contarElemento: no cuenta correctamente elementos existentes";
			nota += 1;
			assert contarElemento(arrayContar,
					22) == 0 : "contarElemento: no cuenta correctamente elementos que no existen";
			nota += 1;

			// Para probar sonTodosNaturales
			int[] arrayNatural = { 1, 0, 2, 4 };
			int[] arrayNoNatural = { 1, 0, -2, 4 };
			assert sonTodosNaturales(arrayNatural) : "sonTodosNaturales: no encuentra bien todos los naturales";
			nota += 1;
			assert !sonTodosNaturales(arrayNoNatural) : "sonTodosNaturales: falla al encontrar NO naturales.";
			nota += 1;
			
			// Para probar separarPares
			int[] arrayPares = { 1, 6, 3, 2, 6, 7, 4 };
			assert matrizSeparada(separarPares(arrayPares)) : "La separación de las matriz no es correcta...";
			nota += 3;

			// Para probar flip
			int[][] flipM = { { 1, 2, 3 }, { 4, 5, 6 } };
			assert matrizFlipped(flip(flipM)) : "No has volteado bien la matriz...";
			nota += 3;
		} catch (AssertionError e) {
			System.out.println(e);
			System.out.println("Tu nota es: " + nota);
			System.exit(-1);
		}

		System.out.println("Tienes un 10!");
	}

	// NO TOCAR ESTOS MÉTODOS, SON DE PROPÓSITO DE PRUEBAS
	private static boolean matrizSeparada(int[][] matrizOrigen) {
		int[][] matriz = { { 6, 2, 6, 4 }, { 1, 3, 7 } };
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] != matrizOrigen[i][j])
					return false;
			}
		}
		return true;
	}

	private static boolean matrizFlipped(int[][] matrizOrigen) {
		int[][] matriz = { { 3, 2, 1 }, { 6, 5, 4 } };
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] != matrizOrigen[i][j])
					return false;
			}
		}
		return true;
	}

}
