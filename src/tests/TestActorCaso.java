package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import modeloCasos.ActorCaso;

import org.junit.Test;

public class TestActorCaso {
	
	ActorCaso ac = new ActorCaso("type1", "id1", "nombre1");

	
	@Test
	public void testGetSalen()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		assertEquals(prueba, ac.getSalen());
	}
	
	@Test
	public void testAddSalen()
	{
		ArrayList<String> prueba = new ArrayList<String>();
		prueba.add("nuevo");
		ac.addSalen("nuevo");
		
		assertEquals(prueba, ac.getSalen());
	}
	
	@Test
	public void testGetType()
	{
		assertEquals("type1", ac.getType());
	}
	
	@Test
	public void testSetType()
	{
		ac.setType("type2");
		assertEquals("type2", ac.getType());
	}
	
	@Test
	public void testGetId()
	{
		assertEquals("id1", ac.getId());
	}
	
	@Test
	public void testSetId()
	{
		ac.setId("id2");
		assertEquals("id2", ac.getId());
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("nombre1", ac.getName());
	}
	
	@Test
	public void testSetName()
	{
		ac.setName("nombre2");
		assertEquals("nombre2", ac.getName());
	}
	
	@Test
	public void testGetPosx()
	{
		assertEquals(0, ac.getPosx());
	}
	
	@Test 
	public void testSetPosx()
	{
		ac.setPosx(10);
		assertEquals(10, ac.getPosx());
	}
	
	@Test
	public void testGetPosy()
	{
		assertEquals(0, ac.getPosy());
	}
	
	@Test 
	public void testSetPosy()
	{
		ac.setPosy(10);
		assertEquals(10, ac.getPosy());
	}

}

