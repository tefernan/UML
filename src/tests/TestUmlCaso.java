package tests;

import static org.junit.Assert.*;
import gui.Comentario;

import java.util.ArrayList;

import modeloCasos.ActorCaso;
import modeloCasos.ConnectionCaso;
import modeloCasos.UmlCaso;
import modeloCasos.UsecaseCaso;

import org.junit.Test;

public class TestUmlCaso{

	UmlCaso u = new UmlCaso();
	
	@Test
	public void testLimpiarRepetidos() 
	{	
		ArrayList<String> listaConRepetidos = new ArrayList<String>();
		
		listaConRepetidos.add("a");
		listaConRepetidos.add("a");
		listaConRepetidos.add("b");
		listaConRepetidos.add("b");
		listaConRepetidos.add("c");
		listaConRepetidos.add("c");
		
		ArrayList<String> lista = new ArrayList<String>();
		
		lista.add("a");
		lista.add("b");
		lista.add("c");
		
		assertEquals(lista, u.LimpiarRepetidos(listaConRepetidos));
	}
	
	@Test
	public void testComprobarSiExiste()
	{
		ArrayList<String> lista1 = new ArrayList<String>();
		
		lista1.add("a");
		lista1.add("b");
		
		ArrayList<String> lista2 = new ArrayList<String>();
		
		lista2.add("c");
		lista2.add("d");
		
		assertTrue(u.ComprobarSiExiste(lista1, lista2, "e"));
	}
	
	@Test
	public void testOrdenarUserCases()
	{
		UsecaseCaso uc = new UsecaseCaso("uc1", "prueba");
		ArrayList<UsecaseCaso> listaCasos = new ArrayList<UsecaseCaso>();
		listaCasos.add(uc);
		
		u.setListaCasos(listaCasos);
		u.ordenarUserCases();
		
		assertEquals(500, uc.getPosx());
		assertEquals(75, uc.getPosy());
	}
	
	@Test
	public void testOrdenarActores()
	{
		ActorCaso a = new ActorCaso("primary", "id1", "prueba1");
		ArrayList<ActorCaso> listaActores = new ArrayList<ActorCaso>();
		listaActores.add(a);
		
		u.setListaActores(listaActores);
		u.OrdenarActores();
		
		assertEquals(10, a.getPosx());
		assertEquals(30, a.getPosy());		
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
	public void testGetListaActores()
	{
		ArrayList<ActorCaso> prueba = new ArrayList<ActorCaso>();
		assertEquals(prueba, u.getListaActores());
	}
	
	@Test
	public void testSetListaActores()
	{
		ArrayList<ActorCaso> prueba = new ArrayList<ActorCaso>();
		ActorCaso a1 = new ActorCaso("type1", "id1", "nombre1");
		prueba.add(a1);
		u.setListaActores(prueba);
		
		assertEquals(prueba, u.getListaActores());
	}
	
	@Test
	public void testGetListaCasos()
	{
		ArrayList<UsecaseCaso> prueba = new ArrayList<UsecaseCaso>();
		assertEquals(prueba, u.getListaCasos());
	}
	
	@Test
	public void testSetListaCasos()
	{
		ArrayList<UsecaseCaso> prueba = new ArrayList<UsecaseCaso>();
		UsecaseCaso u1 = new UsecaseCaso("id1", "nombre1");
		prueba.add(u1);
		u.setListaCasos(prueba);
		
		assertEquals(prueba, u.getListaCasos());
	}
	
	@Test
	public void testGetListaConexiones()
	{
		ArrayList<ConnectionCaso> prueba = new ArrayList<ConnectionCaso>();
		assertEquals(prueba, u.getListaConexiones());
	}
	
	@Test
	public void testGetListaComentarios()
	{
		ArrayList<Comentario> prueba = new ArrayList<Comentario>();
		assertEquals(prueba, u.getListaComentarios());
	}
	
	@Test
	public void testSetListaComentarios()
	{
		ArrayList<Comentario> prueba = new ArrayList<Comentario>();
		Comentario c1 = new Comentario("Comentario", 0 ,0);
		prueba.add(c1);
		u.setListaComentarios(prueba);
		
		assertEquals(prueba, u.getListaComentarios());
	}
	
	@Test
	public void testAddActor()
	{
		ArrayList<ActorCaso> prueba = new ArrayList<ActorCaso>();
		ActorCaso a1 = new ActorCaso("type1", "id1", "nombre1");
		prueba.add(a1);
		u.addActor(a1);
		
		assertEquals(prueba, u.getListaActores());
	}
	
	@Test
	public void testAddCaso()
	{
		ArrayList<UsecaseCaso> prueba = new ArrayList<UsecaseCaso>();
		UsecaseCaso u1 = new UsecaseCaso("id1", "nombre1");
		prueba.add(u1);
		u.addCaso(u1);
		
		assertEquals(prueba, u.getListaCasos());
	}
	
	@Test
	public void testAddConexion()
	{
		ArrayList<ConnectionCaso> prueba = new ArrayList<ConnectionCaso>();
		ConnectionCaso cc1 = new ConnectionCaso("type1", "from1", "to1");
		prueba.add(cc1);
		u.addConexion(cc1);
		
		assertEquals(prueba, u.getListaConexiones());
	}
	
	@Test
	public void testAddComentario()
	{
		ArrayList<Comentario> prueba = new ArrayList<Comentario>();
		Comentario c1 = new Comentario("Comentario", 0 ,0);
		prueba.add(c1);
		u.addComentario(c1);
		
		assertEquals(prueba, u.getListaComentarios());
	}
	

}

