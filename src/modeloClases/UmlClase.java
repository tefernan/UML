package modeloClases;



import java.util.ArrayList;

public class UmlClase {
	
	private String nombreDiagrama;
	private ArrayList<Clase> listaClases;
	private ArrayList<ConnectionClase> listaConexiones;
	
	private int anchomax=0,altomax=0;
	//private ArrayList<String> listaColocadosDC;
	
	public UmlClase(){
		listaClases = new ArrayList<Clase>();
		listaConexiones = new ArrayList<ConnectionClase>();
		//listaColocadosDC = new ArrayList<String>();
	}
	
	public void ordenarDiagramaClases()
	{
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
	public ArrayList<ConnectionClase> getListaConexiones() {
		return listaConexiones;
	}

}
