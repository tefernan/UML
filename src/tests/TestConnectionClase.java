package tests;

import static org.junit.Assert.*;
import modeloClases.ConnectionClase;

import org.junit.Test;

public class TestConnectionClase {
	
	ConnectionClase cc = new ConnectionClase("type1", "from1", "to1");

	@Test
	public void testGetType() {
		
		assertEquals("type1", cc.getType());
	}
	
	@Test
	public void testSetType()
	{
		cc.setType("type2");
		assertEquals("type2", cc.getType());
	}
	
	@Test
	public void testGetFrom()
	{
		assertEquals("from1", cc.getFrom());
	}
	
	@Test
	public void testSetFrom()
	{
		cc.setFrom("from2");
		assertEquals("from2", cc.getFrom());
	}
	
	@Test
	public void testGetTo()
	{
		assertEquals("to1", cc.getTo());
	}
	
	@Test
	public void testSetTo()
	{
		cc.setTo("to2");
		assertEquals("to2", cc.getTo());
	}

}
