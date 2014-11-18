package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import modeloClases.Clase;
import modeloClases.ConnectionClase;
import modeloClases.UmlClase;

import org.junit.Test;

public class TestUmlClase {

	UmlClase u = new UmlClase();
	
	
	@Test
	public void testOrdenarDiagramaClases() 
	{
		Clase c1 = new Clase("prueba1", "id1");
		ArrayList<String> salen1 = new ArrayList<String>();
		salen1.add("id3");
		c1.setSalen(salen1);
		
		Clase c2 = new Clase("prueba2", "id2");
		ArrayList<String> salen2 = new ArrayList<String>();
		salen2.add("id1");
		salen2.add("id3");
		c2.setSalen(salen2);
		
		Clase c3 = new Clase("prueba3", "id3");
		ArrayList<String> salen3 = new ArrayList<String>();
		salen3.add("id1");
		c3.setSalen(salen3);
		
		Clase c4 = new Clase("prueba4", "id4");
		ArrayList<String> salen4 = new ArrayList<String>();
		salen4.add("id2");
		c4.setSalen(salen4);
		
		ArrayList<Clase> listaClase = new ArrayList<Clase>();
		listaClase.add(c1);
		listaClase.add(c2);
		listaClase.add(c3);
		listaClase.add(c4);
		
		u.setListaClases(listaClase);
		u.ordenarDiagramaClases();
		
		assertEquals(70, c1.getPosx());
		assertEquals(50, c1.getPosy());
	}
	
	@Test
	public void testEntregarClaseConMasConexiones() 
	{
		Clase c1 = new Clase("prueba1", "id1");
		ArrayList<String> salen1 = new ArrayList<String>();
		salen1.add("c1");
		c1.setSalen(salen1);
		
		Clase c2 = new Clase("prueba2", "id2");
		ArrayList<String> salen2 = new ArrayList<String>();
		salen2.add("c2");
		salen2.add("c3");
		c2.setSalen(salen2);
		
		ArrayList<Clase> listaClase = new ArrayList<Clase>();
		listaClase.add(c1);
		listaClase.add(c2);
		
		assertEquals(c2.getId(), u.entregarClaseConMasConexiones(listaClase));
	}
	
	@Test
	public void testEntregarClasesOrdenadas()
	{
		Clase c1 = new Clase("prueba1", "id1");
		ArrayList<String> salen1 = new ArrayList<String>();
		salen1.add("id2");
		c1.setSalen(salen1);
		
		Clase c2 = new Clase("prueba2", "id2");
		ArrayList<String> salen2 = new ArrayList<String>();
		salen2.add("id1");
		c2.setSalen(salen2);
		
		Clase c3 = new Clase("prueba3", "id3");
		ArrayList<String> salen3 = new ArrayList<String>();
		c3.setSalen(salen3);
		
		ArrayList<Clase> prueba = new ArrayList<Clase>();
		prueba.add(c3);
		prueba.add(c1);
		prueba.add(c2);
		
		ArrayList<Clase> esperado = new ArrayList<Clase>();
		esperado.add(c1);
		esperado.add(c2);
		esperado.add(c3);
		
		assertEquals(esperado, u.entregarClasesOrdenadas(prueba));
	}
	
	@Test
	public void testObtenerMayorAlto()
	{
		Clase c1 = new Clase("prueba1", "id1");
		c1.setAlto(20);
		
		Clase c2 = new Clase("prueba2", "id2");
		c2.setAlto(10);
		
		ArrayList<Clase> prueba = new ArrayList<Clase>();
		prueba.add(c1);
		prueba.add(c2);
		
		assertEquals(20, u.obtenerMayorAlto(prueba));
	}
	
	@Test
	public void testGetNombreDiagrama()
	{
		String nombre = null;
		assertEquals(nombre, u.getNombreDiagrama());
	}
	
	@Test
	public void testSetNombreDiagrama()
	{
		u.setNombreDiagrama("nombre");
		assertEquals("nombre", u.getNombreDiagrama());
	}
	
	@Test
	public void testGetListaClases()
	{
		ArrayList<Clase> prueba = new ArrayList<Clase>();
		assertEquals(prueba, u.getListaClases());
	}
	
	@Test
	public void testSetListaClases()
	{
		ArrayList<Clase> prueba = new ArrayList<Clase>();
		Clase c1 = new Clase("nombre1", "id1");
		prueba.add(c1);
		u.setListaClases(prueba);
		
		assertEquals(prueba, u.getListaClases());
	}
	
	@Test
	public void testGetListaConexiones()
	{
		ArrayList<ConnectionClase> prueba = new ArrayList<ConnectionClase>();
		assertEquals(prueba, u.getListaConexiones());
	}
	
	@Test
	public void testAddClases()
	{
		ArrayList<Clase> prueba = new ArrayList<Clase>();
		Clase c1 = new Clase("nombre1", "id1");
		prueba.add(c1);
		u.addClases(c1);
		
		assertEquals(prueba, u.getListaClases());
	}
	
	@Test
	public void testAddConexion()
	{
		ArrayList<ConnectionClase> prueba = new ArrayList<ConnectionClase>();
		ConnectionClase cc1 = new ConnectionClase("type1", "from1", "to1");
		prueba.add(cc1);
		u.addConexion(cc1);
		
		assertEquals(prueba, u.getListaConexiones());
	}
	
	

}
