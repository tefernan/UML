package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gui.Comentario;

import org.junit.Test;

public class TestComentario {

	Comentario c1 = new Comentario("[comentario]", 0, 0);
	
	@Test
	public void testGetComentario()
	{
		assertEquals("[comentario]", c1.getComentario());
	}
	
	@Test
	public void testSetComentario()
	{
		c1.setComentario("[comentario2]");
		assertEquals("[comentario2]", c1.getComentario());
	}
	
	@Test
	public void testGetPosx()
	{
		assertEquals(0, c1.getPosX());
	}

	@Test
	public void testSetPosx()
	{
		c1.setPosX(10);
		assertEquals(10, c1.getPosX());
	}
	
	@Test
	public void testGetPosy()
	{
		assertEquals(0, c1.getPosY());
	}

	@Test
	public void testSetPosy()
	{
		c1.setPosY(10);
		assertEquals(10, c1.getPosY());
	}
	
	@Test
	public void testGetLineas()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("[comentario] ");
		assertEquals(prueba, c1.getLineas());
	}
	@Test
	public void testSetLineas()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		c1.setLineas(prueba); //se setea.
		
		assertEquals(prueba, c1.getLineas()); //se devuelve.
	}
	
	@Test
	public void testGetAlto()
	{
		assertEquals(20, c1.getAlto());
	}
	
	@Test
	public void testGetAncho()
	{
		assertEquals(120, c1.getAncho());
	}
}
