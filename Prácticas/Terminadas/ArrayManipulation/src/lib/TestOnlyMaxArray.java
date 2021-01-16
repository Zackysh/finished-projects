package lib;

import lib.Compute;

public class TestOnlyMaxArray {
	
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

	public static void main(String[] args) {
		
		
		System.out.println("\nTesting: \"public static int[] maxArray(int[][] m)\"\n");
		
		System.out.println("Base matrix:");
		int[][] matrix1 = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
		
		printMatrix(matrix1);
		
		System.out.println("Reuslt");
		
		int[] max1 = Compute.maxArray(matrix1);
		
		printArray(max1);
		
		System.out.println("\nBase matrix:");
		int[][] matrix2 = {{2,0,1},{0,0,0},{2,0,3}};
		
		printMatrix(matrix2);
		
		System.out.println("Reuslt");
		
		int[] max2 = Compute.maxArray(matrix2);
		
		printArray(max2);
		
		System.out.println("\nBase matrix:");
		int[][] matrix3 = {{2,0,1},{0,0,0},{2,9,3},{2,9,3},{2,0,3}};
		
		printMatrix(matrix3);
		
		System.out.println("Reuslt");
		
		int[] max3 = Compute.maxArray(matrix3);
		
		printArray(max3);
		
	}
}
