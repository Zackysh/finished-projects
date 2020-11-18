package simulacro;

public class Main {
	
	public static void main(String []args) {
		
		int[] naturales = {-1, 1, 2};
		
		int[][] parImpar = Autoevaluacion.separarPares(naturales);
		
		for (int i = 0; i < parImpar.length; i++) {
			for (int j = 0; j < parImpar[i].length; j++) {
				System.out.print(parImpar[i][j] + " ");
			}
			System.out.println();
		}
		
		
	}
}
