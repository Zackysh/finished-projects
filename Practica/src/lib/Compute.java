package lib;

import java.util.Arrays;

/**
 * This class contains various methods for manipulating and compute analytics
 * from unidimensional and bidimensional Arrays.
 * 
 * <p>
 * Methods in this class all will not throw Exceptions if the specified array
 * reference is null. Instead, null will be returned.
 * 
 * <p>
 * In addition, I have built boards where some specific methods are represented
 * graphically. Here's where you can find source files:
 * 
 * https://drive.google.com/drive/folders/1E9DA6kZ3TSBCH0jcSkj4m1y8CLEgXmSb?usp=sharing
 * 
 * @author Adrián Garrido Blanco
 *
 */
public class Compute {

	/**
	 * Method that extracts the main diagonal from a square matrix.
	 * <p>
	 * If the condition is not met (matrix must be square), an empty matrix is
	 * returned.
	 * 
	 * @param m Matrix from which its main diagonal will be obtained.
	 * 
	 * @return diagonal If m is square.
	 */
	public static int[] diagonal(int[][] m) {

		int diagonal[] = new int[m.length];

		if (m.length == m[0].length) { // check if m is square
			for (int i = 0; i < diagonal.length; i++) {
				diagonal[i] = m[i][i]; // get m Main Diagonal
			}
			return diagonal;
		}

		return null;
	}

	/**
	 * Method that extracts a secondary diagonal from a square matrix.
	 * <p>
	 * If the condition is not met (matrix must be square), an empty matrix is
	 * returned.
	 * 
	 * @param m            Matrix from which its secondary diagonal will be
	 *                     obtained.
	 * @param esSecundaria Boolean that manage if diagonal is secondary or not.
	 * 
	 * @return diagonal If m is square.
	 */
	public static int[] diagonal(int[][] m, boolean esSecundaria) {

		int diagonal[] = new int[m.length];

		if (m.length == m[0].length && esSecundaria == true) { // check if m is square
			for (int i = 0; i < diagonal.length; i++) {
				diagonal[i] = m[i][m.length - 1 - i]; // get m Secondary Diagonal
			}
		}
		return diagonal;
	}

	/**
	 * Method that receives an Array A [] and copies its content in reverse to
	 * another Array B [].
	 * 
	 * @param array Initial Array.
	 * 
	 * @return aux Final inverted Array.
	 */
	public static int[] invertir(int[] array) {

		int[] aux = new int[array.length]; // Array in which inverted outcome will be stored
		int cont = 0; // Original array iterator
		for (int i = aux.length - 1; i >= 0; i--) { // Traverse new array in reverse
			aux[i] = array[cont++]; // Traverse original array and assign its value to new array
		}
		return aux;
	}

	/**
	 * Given an array m, it will return, whenever possible, the chosen row from the
	 * array indicated by the index parameter.
	 * <p>
	 * If the condition is not met (index in bounds of m.length), an empty array is
	 * returned.
	 * 
	 * @param m     Matrix you want to get the row from.
	 * @param index Index used to locate target row.
	 * 
	 * @return aux Array that contains desired row.
	 */
	public static int[] getFila(int[][] m, int index) {

		int aux[] = new int[m[0].length]; // Array in which target row will be stored
		if (index < m.length) {
			aux = Arrays.copyOf(m[index], m[index].length); // Store row into array
		}

		return aux;
	}

	/**
	 * Given an array m, it will return, whenever possible, the chosen column from
	 * the array indicated by the index parameter.
	 * <p>
	 * If condition is not met (index in bounds of m[0]-length), an empty array is
	 * returned.
	 * 
	 * @param m     Matrix you want to get the column from.
	 * @param index Index used to locate target column.
	 *
	 * @return column Array that contains desired column.
	 */
	public static int[] getColumna(int m[][], int index) {

		int column[] = new int[m.length];

		if (index < m[0].length) {
			for (int i = 0; i < column.length; i++) {
				column[i] = m[i][index]; // Store column into array
			}
		}
		return column;
	}

	/**
	 * Method that extracts a subMatrix from a given matrix.
	 * 
	 * @param m           Matrix you want to get a subMatrix.
	 * @param chossenRows Array that contains target row index.
	 * @param chossenCols Array that contains target col index.
	 * 
	 * @returns subMatrix SubMatrix from original matrix.
	 * @returns null If any of chossenRows or chossenCols are out of bounds of m.
	 */
	public static int[][] submatriz(int[][] m, int[] chossenRows, int[] chossenCols) {
		int[][] subMatrix = new int[chossenRows.length][chossenCols.length];

		int rowIt = 0;
		int colIt = 0;

		for (int i = 0; i < subMatrix.length; i++) {
			for (int j = 0; j < subMatrix[0].length; j++) { // fill subMatrix with:

				if (rowIt < chossenRows.length && colIt < chossenCols.length) { // check if external iterators are in
																				// bounds

					if (chossenRows[rowIt] < m.length && chossenCols[colIt] < m[0].length) //
						subMatrix[i][j] = m[chossenRows[rowIt]][chossenCols[colIt++]];

					else {

						if (chossenRows[rowIt] >= m.length && chossenCols[colIt] < m[0].length)
							System.out.println(chossenRows[rowIt] + " row index is not on bounds.");
						if (chossenRows[rowIt] < m.length && chossenCols[colIt] >= m[0].length)
							System.out.println(chossenCols[colIt] + " col index is not on bounds.");

						return null;
					}
				}
			}
			rowIt++;
			colIt = 0;
		}
		return subMatrix;
	}

	/**
	 * Method that multiply two matrix if they are compatible.
	 * <p>
	 * If the condition is not met (matrix dimensions must be compatible), an empty
	 * matrix is returned.
	 * 
	 * @param m1 First matrix.
	 * @param m2 Second matrix.
	 * @return sum Multiplication outcome.
	 */
	public static int[][] multiplica(int[][] m1, int[][] m2) {

		int[][] sum = new int[m1.length][m2[0].length];

		if (m1[0].length == m2.length) {

			for (int i = 0; i < sum.length; i++) {
				for (int j = 0; j < sum[0].length; j++) {
					for (int k = 0; k < m1[0].length; k++) {

						sum[i][j] += m1[i][k] * m2[k][j];
					}
				}
			}
		}
		return sum;
	}

	/**
	 * Method that compute MinValue, MaxValue, Mean, StandardDeviation and Variance
	 * from a matrix.
	 * 
	 * @param m matrix you want to know its stats.
	 * @return stats Compound of stats from m.
	 */
	public static double[] extraerEstadisticas(int[][] m) {

		double[] stats = { computeMin(m), computeMax(m), computeMean(m), computeVarianceOfPopulation(m),
				computeStandardDeviation(m) };

		return stats;
	}

	/**
	 * Method that replaces any specified row of an matrix by a given array that
	 * fits the target matrix.
	 * 
	 * @param m      Original matrix.
	 * @param a      Desired row.
	 * @param indice Target index to change.
	 */
	public static void reemplazarFilaConArray(int m[][], int[] a, int indice) {

		if (indice < m.length && a.length == m[0].length) // Check if index is in bounds of m.length and if
															// a.length fits m[0].length
			for (int col = 0; col < a.length; col++) {
				m[indice][col] = a[col];
			}

	}

	/**
	 * Method that replaces any specified column of an matrix by a given array that
	 * fits the target matrix.
	 * 
	 * @param m
	 * @param a
	 * @param indice
	 */
	public static void reemplazarColumnaConArray(int m[][], int[] a, int indice) {

		if (indice < m[0].length && a.length == m.length) { // Check if index is in bounds of m[0].length
															// and if a.length fits m.length
			for (int row = 0; row < m.length; row++) {
				m[row][indice] = a[row];
			}
		}

	}

	/**
	 * Private method wich compute MaxValue of a matrix.
	 * 
	 * @param m Matrix of which you want to know its maximum value.
	 * @return max MaxValue of m.
	 */
	private static int computeMax(int[][] m) {

		int max = 0;
		// Compute max value.
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {

				if (m[i][j] > max)
					max = m[i][j];
			}
		}
		return max;
	}

	/**
	 * Private method which compute MinValue of a matrix.
	 * 
	 * @param m Matrix of which you want to know its minimum value.
	 * @return min MinValue of m.
	 */
	private static int computeMin(int[][] m) {

		int min = m[0][0];
		
		// Compute min value.
		for (int i = 0; i < m.length; i++) {
			
			for (int j = 0; j < m[0].length; j++) {

				if (m[i][j] < min)
					min = m[i][j];
			}
		}
		return min;
	}

	/**
	 * Method that compute StandardDeviation of a matrix. Note that in this case the
	 * data is assumed to come from a population.
	 * 
	 * If you want to work with data from a sample, you can look at its formula at
	 * this method comments. Or you can switch {@code computeVariancePopulation} to
	 * {@code computeVarianceSample}.
	 * 
	 * @param Matrix of which you want to know its StandardDeviation.
	 * @return ValueOf StandardDeviation of m.
	 */
	private static double computeStandardDeviation(int[][] m) {
		// StandardDeviation equals to square root of Variance.
		return Math.sqrt(computeVarianceOfPopulation(m));
	}

	/**
	 * Method that compute Variance of a matrix. Note that data is assumed to come
	 * from a population, not a sample.
	 * 
	 * 
	 * @param m Matrix of which you want to know its Variance.
	 * @return ValueOf Variance of m.
	 */
	private static double computeVarianceOfPopulation(int[][] m) {

		double mean = computeMean(m), squaredDiff = 0;

		// 1 - Sum squared differences with mean.
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				squaredDiff += (m[i][j] - mean) * (m[i][j] - mean);
			}
		}

		// 2 - Variance equals to squared diff slash number of elements.
		return squaredDiff / 9;
	}

	/**
	 * Method that compute Variance of a matrix. Note that data is assumed to come
	 * from a sample, not a population.
	 * 
	 * @param m Matrix of which you want to know its Variance.
	 * @return ValueOf Variance of m.
	 */

	public static double computeVarianceOfSample(int[][] m) {

		double mean = computeMean(m), squaredDiff = 0;

		// 1 - Sum squared differences with mean.
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				squaredDiff += (m[i][j] - mean) * (m[i][j] - mean);
			}
		}

		// 2 - Variance equals to squared diff slash number of elements.
		return squaredDiff / (m.length * m[0].length - 1);
	}

	/**
	 * Method that compute Mean of a matrix.
	 * 
	 * @param m Matrix which you want to know its Mean.
	 * @return mean Mean of m.
	 */
	private static double computeMean(int[][] m) {

		double mean;
		int aux = 0;

		// Compute mean (average) - sum of elements slash number of elements.
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				aux += m[i][j];
			}
		}
		mean = (double) aux / (m.length * m[0].length);
		return mean;
	}

	/**
	 * Method that rotate a matrix clockwise by 90 degrees.
	 * 
	 * @param m Original Matrix.
	 * @return rotatedMatrix Rotated Matrix clockwise by 90°.
	 */
	public static int[][] rotate90Clockwise(int[][] m) {

		int row = m[0].length; // Total columns of Original Matrix
		int col = m.length; // Total rows of Original Matrix

		int[][] rotatedMatrix = new int[row][col];

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				rotatedMatrix[j][(col - 1) - i] = m[i][j];
			}
		}
		return rotatedMatrix;
	}

	/**
	 * Method that rotate a matrix counter clockwise by 90 degrees.
	 * 
	 * @param m Original Matrix.
	 * 
	 * @return rotatedMatrix Rotated Matrix counter clockwise by 90°.
	 */
	public static int[][] rotated90CounterClockwise(int[][] m) {

		// Rotated Matrix row = Original Matrix col and viceversa.
		int row = m[0].length;
		int col = m.length; // Since the variable is only used once, it would be optimal to apply its value
							// directly instead of saving it to a variable.
							// However, since this is a didactic class, I will not change it.

		int[][] rotatedMatrix = new int[row][col]; // rotated matrix with rotated dimensions

		for (int i = 0; i < m.length; i++) { // traverse original matrix to pick up values
			for (int j = 0; j < m[0].length; j++) {
				// fill rotated matrix in necessary positions so that it is correctly rotated
				// row - 1: value of rotated matrix row

				rotatedMatrix[(row - 1) - j][i] = m[i][j];
			}
		}
		return rotatedMatrix;
	}
	
	
	/**
	 * Replace row into matrix.
	 * 
	 * @param m
	 * @param array
	 * @param index
	 */
	public static void insertRow(int[][] m, int[] array, int index) {
		
		if(index < m.length && index >= 0 && array.length == m[0].length) {
			
			for (int i = 0; i < m[0].length; i++) {
				m[index][i] = array[i];
			}
			
		}
	}
	
	/**
	 * Replace col into matrix.
	 * 
	 * @param m
	 * @param array
	 * @param index
	 */
	public static void insertCol(int[][] m, int[] array, int index) {
		
		if(index < m[0].length && index >= 0 && array.length == m.length) {
			
			for (int i = 0; i < m.length; i++) {
				m[i][index] = array[i];
			}
		}
		
	}
	
	public static int[] maxArray(int[][] m) {
		// Declarations
		int[] diagonalP = new int[m.length];
		int[] diagonalS = new int[m.length];
		int[] maxArray = new int[m.length];
		
		int maxSumAux = 0; // not set to 0
		int matrixSumAux = 0;
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				matrixSumAux += m[i][j];
			}
			
			if(matrixSumAux > maxSumAux) maxSumAux = matrixSumAux;
			matrixSumAux = 0;
		}
		
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				matrixSumAux += m[i][j];
			}
			
			if (matrixSumAux == maxSumAux) {
				for (int j = 0; j < m.length; j++) {
					maxArray[i] = m[i][j];
				}
			}
		}
		
		if(m.length == m[0].length) { // get max sum between diagonals (if exist)
			diagonalP = diagonal(m);
			diagonalS = diagonal(m, true);
			int sumDP = 0;
			int sumDS = 0;
			for (int i = 0; i < m.length; i++) { // get main diagonal sum
				
				sumDP += diagonalP[i];
				
			}
			
			for (int i = 0; i < diagonalS.length; i++) { // get secondary diagonal sum
				sumDS += diagonalS[i];
			}
			
			if(sumDP > sumDS && sumDP > maxSumAux) maxSumAux = sumDP;			
			else if(sumDS > sumDP && sumDS > maxSumAux) maxSumAux = sumDS;
			
			
		}
		
		return maxArray;
	}
}