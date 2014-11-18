package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import modeloCasos.UsecaseCaso;

import org.junit.Test;

public class TestUsecaseCaso {
	
	UsecaseCaso u = new UsecaseCaso("id1", "nombre1");

	@Test
	public void testGetId()
	{
		assertEquals("id1", u.getId());
	}
	
	@Test
	public void testSetId()
	{
		u.setId("id2");
		assertEquals("id2", u.getId());
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("nombre1", u.getName());
	}
	
	@Test
	public void testSetName()
	{
		u.setName("nombre2");
		assertEquals("nombre2", u.getName());
	}
	
	@Test
	public void testGetPosx()
	{
		assertEquals(0, u.getPosx());
	}
	
	@Test
	public void testSetPosx()
	{
		u.setPosx(10);
		assertEquals(10, u.getPosx());
	}
	
	@Test
	public void testGetPosy()
	{
		assertEquals(0, u.getPosy());
	}
	
	@Test
	public void testSetPosy()
	{
		u.setPosy(10);
		assertEquals(10, u.getPosy());
	}
	
	@Test
	public void testGetSalen()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		assertEquals(prueba, u.getSalen());
	}
	
	@Test
	public void testGetEntran()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		assertEquals(prueba, u.getEntran());
	}
	
	@Test
	public void testGetUniones()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		assertEquals(prueba, u.getUniones());
	}
	
	@Test
	public void testAddSalen()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		u.addSalen("nuevo");
		
		assertEquals(prueba, u.getSalen());
	}
	
	@Test
	public void testAddEntran()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		u.addEntran("nuevo");
		
		assertEquals(prueba, u.getEntran());
	}
	
	@Test
	public void testAddUniones()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		u.addUniones("nuevo");
		
		assertEquals(prueba, u.getUniones());	
	}
}
