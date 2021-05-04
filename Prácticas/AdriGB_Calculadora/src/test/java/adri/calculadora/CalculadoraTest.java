package adri.calculadora;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import models.Calculadora;

public class CalculadoraTest {

  @Test
  public void testSuma() {
    assertEquals(11, Calculadora.suma(5, 6));
  }
  
  @Test
  public void testResta() {
    assertEquals(3, Calculadora.resta(6, 3));
  }
  
  @Test
  public void testMultiplicacion() {
    assertEquals(10, Calculadora.multiplicar(2, 5));
  }
  
  @Test
  public void testDivision() {
    assertEquals(5, Calculadora.dividir(10, 2));
    assertThrows("/ by zero", ArithmeticException.class, () -> Calculadora.dividir(4, 0));
  }
  
  @Test
  public void esPositivo() {
    assertTrue(Calculadora.esPositivo(1));
    assertFalse(Calculadora.esPositivo(-1));
  }

}
