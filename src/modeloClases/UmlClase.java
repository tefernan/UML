package modeloClases;

import gui.Comentario;

import java.util.ArrayList;

public class UmlClase {
	
	private String nombreDiagrama;
	private ArrayList<Clase> listaClases;
	private ArrayList<ConnectionClase> listaConexiones;
	private ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();
	
	private int anchomax=0,altomax=0;
	//private ArrayList<String> listaColocadosDC;
	
	public UmlClase(){
		listaClases = new ArrayList<Clase>();
		listaConexiones = new ArrayList<ConnectionClase>();
		//listaColocadosDC = new ArrayList<String>();
	}
	
	public void ordenarDiagramaClases()
	{
		try
		{
		String primero = entregarClaseConMasConexiones(listaClases);
		ArrayList<Clase> clasesAuxiliar = new ArrayList<Clase>();
		
		for(Clase c: listaClases)
		{ clasesAuxiliar.add(c);}
		
		for(Clase c: listaClases) 
		{
			if(primero.equals(c.getId())) //ordena el primero.
			{
				int posX = (1000 - c.getAncho())/2;
				int posY = (1000 - c.getAlto())/2;
				c.setPosx(posX); //REVISAR (tiene que quedar al medio).
				c.setPosy(posY);
				//listaColocadosDC.add(c.getId());
				clasesAuxiliar.remove(c);
			}
		}
		
		ArrayList<Clase> clasesOrdenada = new ArrayList<Clase>(); 
		clasesOrdenada = entregarClasesOrdenadas(clasesAuxiliar); //se ordenan las clases de acuerdo a sus conexiones.
		
		int totalPorLado = clasesOrdenada.size()/4 + 1;
		ArrayList<Clase> derecha = new ArrayList<Clase>();
		ArrayList<Clase> izquierda = new ArrayList<Clase>();
		ArrayList<Clase> arriba = new ArrayList<Clase>();
		ArrayList<Clase> abajo = new ArrayList<Clase>();
		int contador = 0;
		
		for(Clase c: clasesOrdenada)
		{
				if(contador > 3*totalPorLado - 1) //ejemplo: 11
				{
					izquierda.add(c);
					contador++;
				}
				
				if(contador > 2*totalPorLado - 1) //ejemplo: 7
				{
					if(contador < 3*totalPorLado) //ejemplo: 12
					{
						abajo.add(c);
						contador++;
					}
				}
				
				if(contador > totalPorLado - 1) //ejemplo: 3
				{
					if(contador < 2*totalPorLado) //ejemplo: 8
					{
						derecha.add(c);
						contador++;
					}
				}
				
				if(contador < totalPorLado) //ejemplo: 4
				{
					arriba.add(c);
					contador++;
				}
		}
		
		/*
		for(Clase c: clasesOrdenada)
		{
			int resto = contador % 4;
			
			if (resto == 1)
			{ derecha.add(c); }
			
			if (resto == 2)
			{ abajo.add(c);	}
			
			if (resto == 3)
			{ izquierda.add(c);	}
			
			if (resto == 0)
			{ arriba.add(c); }
			
			contador++;
		}
		*/
		
		int coordx_arriba = 20;
		int coordy_arriba = 50;
		
		int coordx_izquierda = 50;
		int coordy_izquierda = 800;
		int contadorIzquierda = 0;
		
		if(izquierda.size() != 0)
		{ coordy_izquierda = 970 - (obtenerMayorAlto(clasesOrdenada)+50); }
		
		int coordx_abajo = 800;
		int coordy_abajo = 800;
		int contadorAbajo = 0;
		
		if(abajo.size() != 0){
		coordx_abajo = 970 - abajo.get(0).getAncho();
		coordy_abajo = 970 - abajo.get(0).getAlto();}
		
		int coordx_derecha = 800;
		if(derecha.size() != 0)
		{ coordx_derecha = 970 - (derecha.get(0).getAncho()+50); }
		int coordy_derecha = 50 + (obtenerMayorAlto(clasesOrdenada)+50);
		
		
		for(Clase c: arriba)
		{
			c.setPosx(coordx_arriba);
			c.setPosy(coordy_arriba);
			//coordx_arriba += delta_arriba;
			coordx_arriba += (c.getAncho() +50);
		}
		
		for(Clase c: izquierda)
		{
			c.setPosx(coordx_izquierda);
			if(contadorIzquierda != 0)
			{ coordy_izquierda -= (c.getAlto() +50); }
			c.setPosy(coordy_izquierda);
			contadorIzquierda = 1;	
		}
		
		for(Clase c: abajo)
		{
			coordy_abajo = 970 - c.getAlto();
			if(contadorAbajo != 0)
			{ coordx_abajo -= (c.getAncho() +50); }
			c.setPosx(coordx_abajo);
			c.setPosy(coordy_abajo);
			contadorAbajo = 1;
		}
		
		for(Clase c: derecha)
		{
			coordx_derecha = 970 - c.getAncho();
			c.setPosx(coordx_derecha);
			c.setPosy(coordy_derecha);
			coordy_derecha += (c.getAlto() +50);
		}	
		
		// nuevo (arregla las posiciones negativas).
		int deltaX = 0; 
		int deltaY = 0;
		
		for(Clase c: listaClases)
		{
			if(c.getPosx() < 0)
			{
				int negativo = 0 - c.getPosx();
				if( negativo > deltaX)
				{ deltaX = negativo; }
			}
			
			if(c.getPosy() < 0)
			{
				int negativo = 0 - c.getPosy();
				if ( negativo > deltaY)
				{ deltaY = negativo; }	
			}
		}
		
		for (Clase c: listaClases)
		{
			int posx = c.getPosx();
			posx += (deltaX +50);
			int posy = c.getPosy();
			posy += deltaY;
			
			c.setPosx(posx);
			c.setPosy(posy);
		}
		
		
		}
		
		catch(Exception e)
		{
			int cordX = 250;
			int cordY = 50;
			
			for(Clase c: listaClases)
			{
				c.setPosy(cordY);
				c.setPosx(cordX);
				int alto = c.getAlto() + 40;
				cordY += alto;
			}
		}
		
		//AQUI PARTE EL ANTIGUO METODO
		/*
		String primero = entregarClaseConMasConexiones(listaClases);
		ArrayList<Clase> clasesAuxiliar = new ArrayList<Clase>();
		
		for(Clase c: listaClases)
		{ clasesAuxiliar.add(c);}
		
		for(Clase c: listaClases) 
		{
			if(primero.equals(c.getId())) //ordena el primero.
			{
				c.setPosx(400); //REVISAR (tiene que quedar al medio).
				c.setPosy(400);
				//listaColocadosDC.add(c.getId());
				clasesAuxiliar.remove(c);
			}
		}
		
		int totalPorLado = clasesAuxiliar.size()/4 + 1;
		ArrayList<Clase> derecha = new ArrayList<Clase>();
		ArrayList<Clase> izquierda = new ArrayList<Clase>();
		ArrayList<Clase> arriba = new ArrayList<Clase>();
		ArrayList<Clase> abajo = new ArrayList<Clase>();
		int contador = 0;
		
		for(Clase c: clasesAuxiliar)
		{
				if(contador > 3*totalPorLado - 1) //ejemplo: 11
				{
					derecha.add(c);
					contador++;
				}
				
				if(contador > 2*totalPorLado - 1) //ejemplo: 7
				{
					if(contador < 3*totalPorLado) //ejemplo: 12
					{
						abajo.add(c);
						contador++;
					}
				}
				
				if(contador > totalPorLado - 1) //ejemplo: 3
				{
					if(contador < 2*totalPorLado) //ejemplo: 8
					{
						izquierda.add(c);
						contador++;
					}
				}
				
				if(contador < totalPorLado) //ejemplo: 4
				{
					arriba.add(c);
					contador++;
				}
		}
		
		int coordx_arriba = 10;
		int coordy_arriba = 40;
		for(Clase c: arriba)
		{
			c.setPosx(coordx_arriba);
			c.setPosy(coordy_arriba);
			coordx_arriba += anchomax+30;
		}
		
		int coordx_izquierda = 10;
		int coordy_izquierda = altomax+30;
		for(Clase c: izquierda)
		{
			c.setPosx(coordx_izquierda);
			c.setPosy(coordy_izquierda);
			coordy_izquierda += altomax+30;
		}
		
		int coordx_abajo = altomax+30;
		int coordy_abajo = anchomax+30;
		for(Clase c: abajo)
		{
			c.setPosx(coordx_abajo);
			c.setPosy(coordy_abajo);
			coordx_abajo += anchomax;
		}
		
		int coordx_derecha = anchomax+30;
		int coordy_derecha = altomax+30;
		
		for(Clase c: derecha)
		{
			c.setPosx(coordx_derecha);
			c.setPosy(coordy_derecha);
			coordy_derecha += altomax+30;
		}	
		*/
	}
	
	
	public String entregarClaseConMasConexiones(ArrayList<Clase> listaClase)
	{
		int contadorConexiones = 0;
		
		String claseMasConexiones = "";
		
		for(Clase c: listaClase)
		{				
			for(String conexion: c.getSalen())
			{
				c.addTotal(conexion);
			}
			for(String conexion: c.getEntran())
			{
				c.addTotal(conexion);
			}	
		}
		
		for(Clase c: listaClase)
		{
			int temporal = 0;
			for(String conexion: c.getTotal())
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
	
	public ArrayList<Clase> entregarClasesOrdenadas(ArrayList<Clase> listaClase) //nuevo
	{
		ArrayList<Clase> listaOrdenada = new ArrayList<Clase>();
		ArrayList<Clase> listaAuxiliar = new ArrayList<Clase>();
		
		for(Clase c: listaClase)
		{ listaAuxiliar.add(c);	}
		
		for(Clase c1: listaClase)
		{
			boolean yaElegiUno1 = false;
			boolean yaElegiUno2 = false;
			boolean yaElegiUno3 = false;
			
			for(String s: c1.getSalen())
			{
				for(Clase c2: listaClase)
				{
					if(c2.getId().equals(s) && !yaElegiUno1)
					{
						yaElegiUno1 = true;
						yaElegiUno2 = true;
						
						boolean sePuedeC1 = true;
						boolean sePuedeC2 = true;
						for(Clase cl: listaOrdenada)
						{
							if(c1.equals(cl))
							{ sePuedeC1 = false; }
							
							if(c2.equals(cl))
							{ sePuedeC2 = false; }
						}
						
						if(sePuedeC1)
						{
							listaOrdenada.add(c1);
							listaAuxiliar.remove(c1);
						}
						
						if(sePuedeC2)
						{
							listaOrdenada.add(c2);
							listaAuxiliar.remove(c2);
						}
					}
				}
			}
				
			if(!yaElegiUno2)
			{
				for(String s1: c1.getEntran())
				{
					for(Clase c2: listaClase)
					{
						if(c2.getId().equals(s1) && !yaElegiUno3)
						{
							yaElegiUno3 = true;
							
							boolean sePuedeC1 = true;
							boolean sePuedeC2 = true;
							for(Clase cl: listaOrdenada)
							{
								if(c1.equals(cl))
								{ sePuedeC1 = false; }
								
								if(c2.equals(cl))
								{ sePuedeC2 = false; }
							}
							
							if(sePuedeC1)
							{
								listaOrdenada.add(c1);
								listaAuxiliar.remove(c1);
							}
							
							if(sePuedeC2)
							{
								listaOrdenada.add(c2);
								listaAuxiliar.remove(c2);
							}
						}
					}
				}
			}	
		}
		
		for(Clase c: listaAuxiliar)
		{
			listaOrdenada.add(c);
		}
		
		return listaOrdenada;
	}
	
	public int obtenerMayorAlto(ArrayList<Clase> lista) //nuevo
	{
		int mayorAlto = 0;
		for(Clase c: lista)
		{
			if(c.getAlto() > mayorAlto)
			{ mayorAlto = c.getAlto(); }
		}
		
		return mayorAlto;
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
		if(altomax<c.getAlto())
		{
			altomax=c.getAlto();
		}
		
		if(anchomax<c.getAncho())
		{
			anchomax=c.getAncho();
		}
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
	
	public void setListaClases(ArrayList<Clase> a){
		this.listaClases=a;
	}
	
	
	public ArrayList<ConnectionClase> getListaConexiones() {
		return listaConexiones;
	}
	
	public void addComentario(Comentario com){
		listaComentarios.add(com);
	}

	public ArrayList<Comentario> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(ArrayList<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}

}
