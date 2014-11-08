package modeloClases;

import java.util.ArrayList;

public class UmlClase {
	
	private String nombreDiagrama;
	private ArrayList<Clase> listaClases;
	private ArrayList<ConnectionClase> listaConexiones;
	private ArrayList<String> listaColocadosDC;
	
	public UmlClase(){
		listaClases = new ArrayList<Clase>();
		listaConexiones = new ArrayList<ConnectionClase>();
		listaColocadosDC = new ArrayList<String>();
	}
	
	public void ordenarDiagramaClases()
	{
		int cordX = 100;
		int cordY = 60;
		
		for(Clase c: listaClases){
			c.setPosy(cordY);
			c.setPosx(cordX);
			int alto = c.getAlto() + 20;
			cordY += alto;
		}
		
		/*
		String primero = entregarClaseConMasConexiones(listaClases);
		ArrayList<Clase> clasesAuxiliar = new ArrayList<Clase>();
		
		for(Clase c: listaClases)
		{ clasesAuxiliar.add(c);}
		int indexBorrar = 0;
		for(Clase c: clasesAuxiliar) 
		{
			if(primero.equals(c.getId())) //ordena el primero.
			{
				c.setPosx(250); //REVISAR (tiene que quedar al medio).
				c.setPosy(250);
				listaColocadosDC.add(c.getId());
				indexBorrar = clasesAuxiliar.indexOf(c);
				//clasesAuxiliar.remove(c);
			}
		}
		
		clasesAuxiliar.remove(indexBorrar);
		
		//int totalPorLado = clasesAuxiliar.size()/4;
		
		double sizeDec = ((double)clasesAuxiliar.size())/(4);
		int totalPorLado = (int)Math.ceil(sizeDec);
		
		System.out.println(totalPorLado);
		
		ArrayList<Clase> derecha = new ArrayList<Clase>();
		ArrayList<Clase> izquierda = new ArrayList<Clase>();
		ArrayList<Clase> arriba = new ArrayList<Clase>();
		ArrayList<Clase> abajo = new ArrayList<Clase>();
		int contador = 0;
		
		for(Clase c: clasesAuxiliar)
		{
			if(contador < totalPorLado) //ejemplo: 4
			{
				arriba.add(c);
				contador++; 
			}
			
			else if(contador > totalPorLado - 1) //ejemplo: 3
			{
				if(contador < 2*totalPorLado) //ejemplo: 8
				{
					izquierda.add(c);
					contador++;
				}
			}
			
			else if(contador > 2*totalPorLado - 1) //ejemplo: 7
			{
				if(contador < 3*totalPorLado) //ejemplo: 12
				{
					abajo.add(c);
					contador++;
				}
			}
			
			else if(contador > 3*totalPorLado - 1) //ejemplo: 11
			{
				derecha.add(c);
				contador++;
			}
			
			if(contador == clasesAuxiliar.size())
			{return;}
		}
		
		System.out.println("cosas");
		for(Clase c:arriba){
			System.out.println("arriba");
			System.out.println(c.getNombre());
		}
		for(Clase c:derecha){
			System.out.println("derecha");
			System.out.println(c.getNombre());
		}
		for(Clase c:izquierda){
			System.out.println("izquierda");
			System.out.println(c.getNombre());
		}
		for(Clase c:abajo){
			System.out.println("abajo");
			System.out.println(c.getNombre());
		}
		
		
		int coordx_arriba = 0;
		int coordy_arriba = 0;
		for(Clase c: arriba)
		{
			c.setPosx(coordx_arriba);
			c.setPosy(coordy_arriba);
			coordx_arriba += c.getAncho() + 20;
		}
		
		int coordx_izquierda = 0;
		int coordy_izquierda = 0;
		for(Clase c: izquierda)
		{
			c.setPosx(coordx_izquierda);
			c.setPosy(coordy_izquierda);
			coordy_arriba += c.getAlto() + 20;
		}
		
		int coordx_abajo = 0;
		int coordy_abajo = 400;
		for(Clase c: abajo)
		{
			c.setPosx(coordx_arriba);
			c.setPosy(coordy_arriba);
			coordx_abajo += c.getAncho() + 20;
		}
		
		int coordx_derecha = 400;
		int coordy_derecha = 0;
		for(Clase c: derecha)
		{
			c.setPosx(coordx_derecha);
			c.setPosy(coordy_derecha);
			coordy_derecha += c.getAlto() + 20;
		}	*/
	}
	
	public String entregarClaseConMasConexiones(ArrayList<Clase> listaClases)
	{
		int contadorConexiones = 0;
		
		String claseMasConexiones = "";
		
		for(Clase c: listaClases)
		{				
			for(String conexion: c.getSalen())
			{
				c.addTotales(conexion);
			}
			for(String conexion: c.getEntran())
			{
				c.addTotales(conexion);
			}	
		}
		
		for(Clase c: listaClases)
		{
			int temporal = 0;
			for(String conexion: c.getConexionesTotales())
			{
				temporal++;
				if(temporal > contadorConexiones)
				{
					contadorConexiones = temporal;
					claseMasConexiones = c.getId();
				}
			}
		}
		
		return claseMasConexiones;
	}
	
	public boolean sePuedeColocar(ArrayList<Clase> lista, int x, int y)
	{
		boolean sePuede = false;
		
		for(Clase c: lista)
		{
			if(x + c.getAncho() < c.getPosx())
			{ 
				if(y + c.getAlto() < c.getPosy())
				{ sePuede = true; }
				
				if(y > c.getPosy() + c.getAlto())
				{ sePuede = true; }
				
			}
			if(x > c.getAncho() + c.getPosx())
			{
				if(y + c.getAlto() < c.getPosy())
				{ sePuede = true; }
				
				if(y > c.getPosy() + c.getAlto())
				{ sePuede = true; }
			}
		}
		
		return sePuede;
	}
	
	
	//añadir elementos a las listas
	public void addClases(Clase c){
		listaClases.add(c);
	}
	public void addConexion(ConnectionClase c){
		listaConexiones.add(c);
	}
	

	//getters y seterrs
	public String getNombreDiagrama() {
		return nombreDiagrama;
	}
	public void setNombreDiagrama(String nombreDiagrama) {
		this.nombreDiagrama = nombreDiagrama;
	}
	public ArrayList<Clase> getListaClases() {
		return listaClases;
	}
	public ArrayList<ConnectionClase> getListaConexiones() {
		return listaConexiones;
	}

}
