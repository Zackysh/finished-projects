package pk_SuperBuscaMinas;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pk_SuperBuscaMinas.BotonMina.Estado;

public class BotonMinaTests {

  // BANDERA, BOTON, PULSADO, MINA, NUMERO

  BotonMina[][] matriz;
  
  @BeforeClass
  public static void inicio() {
    System.out.println("=========== Inicio Prueba BotonMina ===========");
  }
  
  @AfterClass
  public static void fin() {
    System.out.println("=========== Fin Prueba BotonMina ===========");
  }

  @Before
  public void initialize() {
    matriz = new BotonMina[2][2];

    for (int i = 0; i < matriz.length; i++) {
      for (int j = 0; j < matriz[0].length; j++) {
        matriz[i][j] = new BotonMina();
      }
    }

  }

  @Test
  public void cambiarAspecto() {
    // 0, 0: estado inicial
    assertEquals(Estado.BOTON, matriz[0][0].getEstado());
    // 0, 0: a bandera
    System.out.println("Boton Inicial: " + matriz[0][0].getEstado());
    matriz[0][0].setEstado(Estado.BANDERA);
    System.out.println("Nuevo estado:  " + Estado.BANDERA);
    assertEquals(Estado.BANDERA, matriz[0][0].getEstado());
    System.out.println("Boton final:   " + matriz[0][0].getEstado() + "\n");
    // 0, 0: a boton
    System.out.println("Boton Inicial: " + matriz[0][0].getEstado());
    matriz[0][0].setEstado(Estado.BOTON);
    System.out.println("Nuevo estado:  " + Estado.BOTON);
    assertEquals(Estado.BOTON, matriz[0][0].getEstado());
    System.out.println("Boton final:   " + matriz[0][0].getEstado() + "\n");
    // 0, 0: a pulsado
    System.out.println("Boton Inicial: " + matriz[0][0].getEstado());
    matriz[0][0].setEstado(Estado.PULSADO);
    System.out.println("Nuevo estado:  " + Estado.PULSADO);
    assertEquals(Estado.PULSADO, matriz[0][0].getEstado());
    System.out.println("Boton final:   " + matriz[0][0].getEstado() + "\n");
    // 0, 0: a mina
    System.out.println("Boton Inicial: " + matriz[0][0].getEstado());
    matriz[0][0].setEstado(Estado.MINA);
    System.out.println("Nuevo estado:  " + Estado.MINA);
    assertEquals(Estado.MINA, matriz[0][0].getEstado());
    System.out.println("Boton final:   " + matriz[0][0].getEstado() + "\n");
    // 0, 0: a numero
    System.out.println("Boton Inicial: " + matriz[0][0].getEstado());
    matriz[0][0].setEstado(Estado.NUMERO);
    System.out.println("Nuevo estado:  " + Estado.NUMERO);
    assertEquals(Estado.NUMERO, matriz[0][0].getEstado()); 
    System.out.println("Boton final:   " + matriz[0][0].getEstado() + "\n");
  }

}
