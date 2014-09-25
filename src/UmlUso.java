import java.util.ArrayList;


public class UmlUso {
	//Nombre del diagrama
	private String nombreDiagrama;
	//Lista de las cosas
	private ArrayList<ActorUso> listaActores = new ArrayList<ActorUso>();
	private ArrayList<UsecaseUso> listaCasos = new ArrayList<UsecaseUso>();
	private ArrayList<ConnectionUso> listaConexiones = new ArrayList<ConnectionUso>();
	
	
	public UmlUso(){
		//Constructor vacio
	}
	
	public void ordenarUserCases(){
		int contador = 30;
		for(UsecaseUso u: listaCasos){
			u.setPosy(contador);
			u.setPosx(500);
			contador +=200;
		}
	}
	
	public void OrdenarActores(){
		
		int contadorp=30,contadors=30;
		
		for(ActorUso u: listaActores){
			
			if(u.getType().equals("primary"))
			{
				u.setPosy(contadorp);
				u.setPosx(10);
				contadorp +=200;
			
			}
			
			else
			{
				u.setPosy(contadors);
				u.setPosx(1100);
				contadors +=200;
				
			}

		}
		
	}
	
	/*
	public void ordenarUserCases()
	{
		for(ConnectionUso c:listaConexiones) //se recorren las conexiones.
		{
			if(c.getType() != "basic") //se buscan sólo las conexiones que son de casos de uso(CU).
			{
				for(UsecaseUso u:listaCasos) //para cada caso de uso.
				{
					if(c.getFrom() == u.getId()) //si la conexión sale desde dicho CU ("from").
						u.addSalen(c.getTo()); //se agrega a la lista "salen".
					
					if(c.getTo() == u.getId()) //si la conexión llega hasta dicho CU ("to").
						u.addEntran(c.getFrom()); //se agrega a la lista "entran".
				}
			}
		} //este foreach asocia a cada User Case una lista de los User Case que salen y entran desde él (viendo las conexiones).
		
		int coordenadaEnX = 100; //posición inicial para el primer CU. REVISAR VALORES
		int coordenadaEnY = 0;
		
		ArrayList<String> siguientes = new ArrayList<String>(); //listas auxiliares para definir los siguientes casos de uso a ubicar.
		ArrayList<String> siguientesAux = new ArrayList<String>();
		
		ArrayList<String> asignados = new ArrayList<String>(); //lista auxiliar para guardar los CU que ya se les han asignado sus coordenadas.
		
		boolean sePuedeAgregar = true; //boolean auxiliar que permite saber si se agrega o no a la lista "siguiente" los CU (para no ponerlos dos veces).
		
		
		for(UsecaseUso u: listaCasos) //para todos los casos de uso (funciona como un for normal en verdad en este caso)
		{
			if(asignados.size() < listaCasos.size()) //si todavía no se ha asignado coordenada a todos los CU.
			{
				//---------------------------------------------------------REVISAR!!!!
				if(u.getId() == "uc1") //si es el primero
				{
					u.setPosx(coordenadaEnX);//setea los valores iniciales.
					u.setPosy(coordenadaEnY);
					
					asignados.add(u.getId());//se agrega a la lista de los "ya asignados con coordenadas"
					
					for(String st: u.getEntran()) //agrega los CU que siguen a la lista "siguientes"
					{
						sePuedeAgregar = ComprobarSiExiste(siguientes, st); //comprueba que no esté repetido en la lista "siguientes"
						if(sePuedeAgregar)
							siguientes.add(st); //agrega los CU que se conectan con él a la lista "siguientes".
					}
					
					for(String s: u.getSalen()) //agrega los CU que siguen a la lista "siguientes".
					{
						sePuedeAgregar = ComprobarSiExiste(siguientes, s);
						if(sePuedeAgregar)
							siguientes.add(s);
					}
					
					coordenadaEnY +=20; //se avanza hacia abajo para los siguientes CU. REVISAR VALOR
				}
				
				for(String str: siguientes) //para todos los CU, se revisa la lista "siguientes".
				{
					for(UsecaseUso uc: listaCasos) //se busca en los CU
					{
						if(str == uc.getId()) //si coincide el id
						{
							uc.setPosy(coordenadaEnY); //se setea el valor de la coordenada Y.
						}
						
						if(siguientes.size() > 1) //si le sigue al CU más de uno.
						{
							coordenadaEnX = 80;// se comienza seteando el primero a la izquierda. REVISAR VALORES.
							uc.setPosx(coordenadaEnX);//se asigna el valor.
							coordenadaEnX += 20;//se avanza hacia la derecha. REVISAR VALOR.
						}
						else //Si es solo uno el que le sigue
						{
							uc.setPosx(coordenadaEnX); //se setea el valor (centrado).
						}
						
						asignados.add(str);  //se agrega a la lista de los "ya asignados con coordenadas".
						
						for(String s: uc.getSalen()) //se agregan a la lista auxiliar los CU que siguen al actual.
						{
							sePuedeAgregar = ComprobarSiExiste(siguientes, s);
							if(sePuedeAgregar)
								siguientes.add(s);
						}
						
						for(String st: uc.getEntran()) //se agregan a la lista auxiliar los CU que siguen al actual.
						{
							sePuedeAgregar = ComprobarSiExiste(siguientes, st); 
							if(sePuedeAgregar)
								siguientes.add(st); 
						}
					}
				}
				
				siguientes.clear();//se limpia la actual.
				
				for(String s: siguientesAux)//se setea la nueva lista con los valores de la auxiliar. DUDA.
					siguientes.add(s);
				
				for(String st: asignados)
				{
					for(String s: siguientes)
					{
						if(st == s)
							siguientes.remove(st); //elimina de la lista "siguiente" los CU que ya han sido asignados.
					}
				}
				
				coordenadaEnY += 20; //se avanza hacia abajo para los siguientes CU. REVISAR VALOR.
				coordenadaEnX = 100; //se setea el valor de la coordenanda X en el centro.
			}
		}//este for busca pone al primer CU "uc1" primero y después va avanzando en la lista "siguientes" que son con los que se conecta dicho CU.
		
	}
	
	public void OrdenarActores()
	{
		for(ConnectionUso c: listaConexiones)//se recorren las conexiones.
		{
			if(c.getType() == "basic") //sólo importan las que unen actores con CU.
			{
				for(ActorUso a: listaActores) //para cada actor.
				{
					if(c.getFrom() == a.getId())//si la conexión sale desde él.
						a.addSalen(c.getTo());//se agregra el CU a "salen".
				}
			}
		}
		
		for(ActorUso a: listaActores)  //para todos los actores.
		{
			if(a.getType() == "primary") //si es de tipo primario.
				a.setPosx(0); //se setea el valor de la coordenada x (REVISAR VALOR).
			else //si es del tipo secundario.
				a.setPosx(300); //se setea el valor (REVISAR VALOR).
		}
		
		int coordenadaEnY = 0; //valor auxiliar para coordenadaY.
		int contador = 0;
		
		for(ActorUso a: listaActores) //para todos los actores.
		{
			for(String s: a.getSalen())//para cada CU al que se conecta dicho actor.
			{
				for(UsecaseUso u: listaCasos)//para todos los CU.
				{
					if(s == u.getId()) //si el CU es igual al que se conecta.
					{
						coordenadaEnY += u.getPosy();//se agrega la coordenada en Y para después sacar el promedio.
						contador++;//se aumenta en uno el contador.
					}
				}
			}
			
			coordenadaEnY =(coordenadaEnY/contador); //la coordenada Y del actor es el promedio de la de sus CU (a los que se conecta).
			
			for(ActorUso act: listaActores) //para todos los actores
			{
				if(act.getPosy() != 0 && act.getId() !=a.getId() && act.getType() == a.getType()) //REVISAR
				{
					boolean sePuede = ComprobarDisponibilidad(coordenadaEnY, act.getPosy()); //se comprueba que no haya otro actor ahí.
					
					while(!sePuede) //mientras no se pueda.
					{
						coordenadaEnY += 20; //se baja el actor en 20 pixeles. REVISAR NUMERO.
						sePuede = ComprobarDisponibilidad(coordenadaEnY, act.getPosy()); //se comprueba que no haya otro actor en la nueva coordenada Y.
					}
				}
			}
			
			a.setPosy(coordenadaEnY); //se le asigna al actor la coordenada Y.
			contador = 0; //se resetean los valores auxiliares.
			coordenadaEnY = 0;
			
		}
	}
	*/
	
	public boolean ComprobarSiExiste(ArrayList<String> lista, String elemento)
	{
		boolean estaRepetido = false;
		for(String s : lista)
		{
			if(s == elemento)
				estaRepetido = true;
		}
		
		return estaRepetido;
	}
	
	public boolean ComprobarDisponibilidad(int coordenadaPrueba, int coordY)
	{
		boolean esPosible = true;
		
		if (coordenadaPrueba + 20 > coordY) //si la coordenanda de prueba más su alto son mayores que la coordY significa que se topan.
			esPosible = false; //REVISAR EL "20".
		
		else if (coordY + 20 > coordenadaPrueba) //si la coordY más su alto son mayores que la coordenada de prueba significa que se topan.
			esPosible = false; //REVISAR EL "20".
		
		return esPosible;
	}

	
	public void addActor(ActorUso act){
		listaActores.add(act);
	}
	public void addCaso(UsecaseUso cas){
		listaCasos.add(cas);
	}
	public void addConexion(ConnectionUso con){
		listaConexiones.add(con);
	}
	
	
	//Getters y setters
	public String getNombreDiagrama() {
		return nombreDiagrama;
	}
	public void setNombreDiagrama(String nombreDiagrama) {
		this.nombreDiagrama = nombreDiagrama;
	}
	public ArrayList<ActorUso> getListaActores() {
		return listaActores;
	}
	public ArrayList<UsecaseUso> getListaCasos() {
		return listaCasos;
	}
	public ArrayList<ConnectionUso> getListaConexiones() {
		return listaConexiones;
	}

}
