package lib;

/**
 * This class is experimental.
 * There is important info in Compute class description.
 * 
 * @author Adrián Garrido Blanco
 *
 */
public class Main {
	
	public static void printMatrix(int[][]m) {
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				System.out.print(" " + m[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void printArray(int[] array) {
		System.out.print("{");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if(i != array.length - 1) System.out.print(", ");
		}
		System.out.print("}\n");
	}
	
	public static void printArray(double[] array) {
		System.out.print("{");
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if(i != array.length - 1) System.out.print(", ");
		}
		System.out.print("}\n");
	}
	
	public static void main(String[] args) {

		System.out.println(
				  "==================\n"
				+ "|  TEST METHOD   |\n"
				+ "==================\n");
		
		
		System.out.println("Testing: \"public static int[] diagonal()\":\n"); //>>>>>>>>>> FIRST TEST <<<<<<<<<<<<
		
		int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}; //create matrix
		
		System.out.println("Matrix:");
		
		printMatrix(matrix);
		
		int[] diagonal = Compute.diagonal(matrix); //get its main diagonal
		
		System.out.print("Main diagonal: ");
		
		printArray(diagonal);		
		
		diagonal = Compute.diagonal(matrix, true); //get its secondary diagonal
		
		System.out.print("Secondary diagonal: ");
		
		printArray(diagonal);
		
		System.out.print("If matrix is Non-square: ");
		
		int[][] nonSquare = {{1, 2, 3, 4}, {5, 6, 7, 8}};
		
		diagonal = Compute.diagonal(nonSquare);
		if(diagonal != null)
		printArray(diagonal);
		else System.out.println("It's not possible to print a null array.");
		
		System.out.println("\nTesting: \"public static int[] invertir(int[] array)\":\n"); //>>>>>>>>>> SECOND TEST <<<<<<<<<<<<
		
		int[] array = {1, 2, 3, 4};
		
		System.out.print("Array: ");
		
		printArray(array);
		
		array = Compute.invertir(array);
		
		System.out.print("Inverted array: ");
		
		printArray(array);
		
		System.out.println("\nTesting: \"public static int[] getFila(int[][] m, int index)\":\n");  //>>>>>>>>>> THIRD TEST <<<<<<<<<<<<
		
		System.out.println("Matrix:");
		
		printMatrix(matrix);
		
		int[] row = Compute.getFila(matrix, 2);
		
		System.out.print("Correct row index 2: ");
		
		printArray(row);
		
		row = Compute.getFila(matrix, 3);
		
		System.out.print("Incorrect row index 3:");
		
		printArray(row);
		
		System.out.println("\nTesting: \"public static int[] getColumna(int m[][], int index)\"\n"); //>>>>>>>>>> FOURTH TEST <<<<<<<<<<<<
		
		System.out.println("Matrix:");
		
		printMatrix(matrix);
		
		int[] col = Compute.getColumna(matrix, 2);
		
		System.out.print("Correct column index 2: ");
		
		printArray(col);
		
		col = Compute.getColumna(matrix, 3);
		
		System.out.print("Incorrect column index 3: ");
		
		printArray(col);
		
		System.out.println("\nTesting: \"public static int[][] submatriz(int[][] m, int[] chossenRows, int[] chossenCols)\"\n"); //>>>>>>>>>> FIFTH TEST <
		
		int[][] superMatrix = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}};
		
		System.out.println("Super-Matrix:");
		
		printMatrix(superMatrix);
		
		System.out.println("Sub-Matrix (rows: 1, 2, 3; cols: 0, 2, 3):");
		
		int[] rows = {1, 2, 3};
		int[] cols = {0, 2, 3};
		
		int[][] subMatrix = Compute.submatriz(superMatrix, rows, cols);
		
		printMatrix(subMatrix);
		
		System.out.println("Incorrect row/col index: It returns null. But it's not possible tu print a null array."); 
		
		System.out.println("\nTesting: \"public static int[][] multiplica(int[][] m1, int[][] m2)\"\n"); //>>>>>>>>>> SIXTH TEST <<<<<<<<<<<<
		
		int[][] A = matrix;
		int[][] B = {{1, 2}, {3, 4}, {5, 6}};
		
		System.out.println("Matrix A:");
		
		printMatrix(A);
		
		System.out.println("Matrix B:");
		
		printMatrix(B);
		
		System.out.println("A * B equals to:");
		
		int[][] resultado = Compute.multiplica(A, B);
		
		printMatrix(resultado);
		
		System.out.println("If the dimensions of matrix are not compatible: It returns 0-filled matrix.");
		
		resultado = Compute.multiplica(subMatrix, superMatrix);
		
		printMatrix(resultado);
		
		System.out.println("\nTesting: \"public static double[] extraerEstadisticas(int[][] m)\"\n"); //>>>>>>>>>> SEVENTH TEST <<<<<<<<<<<<
		
		double[] stats = Compute.extraerEstadisticas(matrix);
		
		System.out.println("Matrix to analyze:");
		
		printMatrix(matrix);
		
		System.out.println("{MinValue, MaxValue, Mean, VarianceOfPopulation, StandardDeviation}");
		
		printArray(stats);
		
		double varianceOfSample = Compute.computeVarianceOfSample(matrix);
		
		System.out.print("\nVariance Of Sample: " + varianceOfSample);
		
		System.out.println("\n\n"
				+ "======================\n"
				+ "|      EXTRA!!       |\n"
				+ "======================\n"
				);
		
		System.out.println("\nTesting: \"public static int[][] rotate90Clockwise(int[][] m)\""); //>>>>>>>>>> EIGHTH TEST <<<<<<<<<<<<
		System.out.println("Testing: \"public static int[][] rotated90CounterClockwise(int[][] m)\"\n");
		
		System.out.println("Initial Matrix:");
		
		printMatrix(matrix);
		
		System.out.println("Rotated 90° Clock Wise:");
		
		int[][] rotatedMatri90xClockWise = Compute.rotate90Clockwise(matrix);
		
		printMatrix(rotatedMatri90xClockWise);
		
		System.out.println("Rotated 90° Counter Clock Wise:");
		
		int[][] rotatedMatrix90CounterClockWise = Compute.rotated90CounterClockwise(matrix);
		
		printMatrix(rotatedMatrix90CounterClockWise);
		
		System.out.println("\nTesting: \"public static void reemplazarFilaConArray(int m[][], int[] a, int indice)\"\n"); //>>>>>>>>>> NINETH TEST <<<<<<
		
		int rowIndex = 2;
		int colIndex = 2;
		int[] desiredRow = {3, 7, 1};
		int[] desiredCol = {9, 2, 3};
		
		System.out.println("Initial Matrix:");
		
		printMatrix(matrix);
		
		System.out.print("Index: " + rowIndex + " Desired row: ");
		
		printArray(desiredRow);
		
		System.out.println("Final Matrix:");
		
		Compute.reemplazarFilaConArray(matrix, desiredRow, rowIndex);
		
		printMatrix(matrix);
		
		System.out.println("\nTesting: \"public static void reemplazarColumnaConArray(int m[][], int[] a, int indice)\"\n");
		
		System.out.println("Initial Matrix:");
		
		printMatrix(matrix);
		
		System.out.print("Index: " + colIndex + " Desired col: ");
		
		printArray(desiredCol);
		
		System.out.println("Final Matrix:");
		
		Compute.reemplazarColumnaConArray(matrix, desiredCol, colIndex);

		printMatrix(matrix);
		
		System.out.println("\nTesting: \"public static void insertRow(int[][] m, int[] array, int index)\"\n");
		
		System.out.println("Matrix");
		
		printMatrix(matrix);
		
		System.out.println("Desired index row: " + rowIndex);
		
		System.out.print("New row: ");
		
		int[] newRow = {9, 2, 6};
		
		printArray(newRow);
		
		Compute.insertRow(matrix, newRow, rowIndex);
		
		System.out.println("Final matrix:");
		
		printMatrix(matrix);
		
		System.out.println("\nTesting: \"public static void insertCol(int[][] m, int[] array, int index)\"\n");
		
		int[] newCol = {1, 6, 3};
		
		System.out.println("Current matrix:");
		
		printMatrix(matrix);
		
		System.out.println("Desired col index: " + colIndex);
		
		System.out.println("New col: (up - down)");
		
		printArray(newCol);
		
		Compute.insertCol(matrix, newCol, colIndex);
		
		System.out.println("Final matrix: ");
		
		printMatrix(matrix);
		
		System.out.println("\nTesting: \"public static int[] maxArray(int[][] m)\"\n");
		
		System.out.println("Base matrix:");
		
		printMatrix(matrix);
		
		System.out.println("Reuslt");
		
		int[] max = Compute.maxArray(matrix);
		
		printArray(max);
		
	}
}
