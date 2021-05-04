package pk_SuperBuscaMinas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pk_SuperBuscaMinas.BotonMina.Estado;
import pk_SuperBuscaMinas.BotonMina.Valor;
import pk_SuperBuscaMinas.Util.Posicion;

public class MatrizBotonesTest {

  MatrizBotones matriz;
  
  @Before
  public void initialize() {
    matriz = new MatrizBotones(5, 5);
    for (int i = 0; i < 5; i++) {
      matriz.getBoton(0, i).setValor(Valor.MINA);
    }
  }
  
  @Test
  public void constructor() {
    for (int i = 0; i < 5; i++) {
      assertEquals(Valor.MINA, matriz.getBoton(0, i).getValor());      
    }
  }
  
  @Test
  public void hayBoton() {
    // 0, 0:
    assertFalse(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.SUP));
    assertFalse(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.SUP_DER));
    assertTrue(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.DER));
    assertTrue(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.INF_DER));
    assertTrue(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.INF));
    assertFalse(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.INF_IZQ));
    assertFalse(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.IZQ));
    assertFalse(matriz.hayBoton(matriz.getBoton(0, 0), Posicion.SUP_IZQ));
    // 2, 2:
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.SUP));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.SUP_DER));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.DER));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.INF_DER));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.INF));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.INF_IZQ));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.IZQ));
    assertTrue(matriz.hayBoton(matriz.getBoton(2, 2), Posicion.SUP_IZQ));
  }
  
  @Test
  public void muestraMinas() {
    List<Coordenadas> listaMinas = matriz.getListaPosicionMinas();
    for (Coordenadas coordenadas : listaMinas) {
      assertEquals(Estado.MINA, matriz.getBoton(coordenadas).getEstado());
    }
  }

}
