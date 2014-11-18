package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import modeloClases.Clase;

import org.junit.Test;

public class TestClase {
	
	Clase c = new Clase("prueba", "id");

	@Test
	public void testAgregarAtributo()
	{	
		ArrayList<String> atributos = new ArrayList<String>();
		atributos.add("atributo1");
		
		c.AgregarAtributo("atributo1");
		
		assertEquals(atributos, c.getAtributos());
	}
	
	@Test
	public void testAgregarMetodo()
	{	
		ArrayList<String> metodos = new ArrayList<String>();
		metodos.add("metodo1");
		
		c.AgregarMetodos("metodo1");
		
		assertEquals(metodos, c.getMetodos());
	}

	@Test
	public void testGetNombre()
	{
		assertEquals("prueba", c.getNombre());
	}
	
	@Test
	public void testSetNombre()
	{
		c.setNombre("prueba2");
		assertEquals("prueba2", c.getNombre());
	}
	
	@Test
	public void testGetAtributos()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		
		assertEquals(prueba, c.getAtributos());
	}
	
	@Test
	public void testSetAtributos()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		c.setAtributos(prueba);
		
		assertEquals(prueba, c.getAtributos());
	}
	
	@Test
	public void testGetMetodos()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		
		assertEquals(prueba, c.getMetodos());
	}
	
	@Test
	public void testSetMetodos()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		c.setMetodos(prueba);
		
		assertEquals(prueba, c.getMetodos());
	}
	
	@Test
	public void testGetAncho()
	{
		int numero = 6*9;
		assertEquals(numero, c.getAncho());
	}
	
	@Test
	public void testSetAncho()
	{
		c.setAncho(5);
		assertEquals(5*9, c.getAncho());
	}
	
	@Test
	public void testGetAlto()
	{
		assertEquals(40, c.getAlto());
	}
	
	@Test
	public void testSetAlto()
	{
		c.setAlto(5);
		assertEquals(5, c.getAlto());
	}
	
	@Test
	public void testGetPosx()
	{
		assertEquals(0, c.getPosx());
	}
	
	@Test
	public void testSetPosx()
	{
		c.setPosx(10);
		assertEquals(10, c.getPosx());
	}
	
	@Test
	public void testGetPosy()
	{
		assertEquals(0, c.getPosy());
	}
	
	@Test
	public void testSetPosy()
	{
		c.setPosy(10);
		assertEquals(10, c.getPosy());
	}
	
	@Test
	public void testGetId()
	{
		assertEquals("id", c.getId());
	}
	
	@Test
	public void testSetId()
	{
		c.setId("id2");
		assertEquals("id2", c.getId());
	}
	
	@Test
	public void testGetSalen()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		
		assertEquals(prueba, c.getSalen());
	}
	
	@Test
	public void testSetSalen()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		c.setSalen(prueba);
		
		assertEquals(prueba, c.getSalen());
	}
	
	@Test
	public void testGetEntran()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		
		assertEquals(prueba, c.getEntran());
	}
	
	@Test
	public void testGetConexionesTotales()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		
		assertEquals(prueba, c.getTotal());
	}
	
	@Test
	public void testAddTotal()
	{
		c.addTotal("nuevo");
		
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		
		assertEquals(prueba, c.getTotal());
	}
	
	@Test
	public void testAddSalen()
	{
		c.addSalen("nuevo");
		
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		
		assertEquals(prueba, c.getSalen());
	}
	
	@Test
	public void testAddEntran()
	{
		c.addEntran("nuevo");
		
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		
		assertEquals(prueba, c.getEntran());
	}
	
	
}
