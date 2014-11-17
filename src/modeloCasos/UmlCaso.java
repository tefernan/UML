package modeloCasos;

import gui.Comentario;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class UmlCaso {

	//Nombre del diagrama
		private String nombreDiagrama;
		//Lista de las cosas
		private ArrayList<ActorCaso> listaActores = new ArrayList<ActorCaso>();
		private ArrayList<UsecaseCaso> listaCasos = new ArrayList<UsecaseCaso>();
		private ArrayList<ConnectionCaso> listaConexiones = new ArrayList<ConnectionCaso>();
		
		private ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();
		
		
		public UmlCaso(){
			//Constructor vacio
		}
			

		public void OrdenarActores(){
			
			int contadorp=30,contadors=30;
			
			for(ActorCaso u: listaActores){
				
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
		

		public void ordenarUserCases()
		{
			
			int coordenadaEnX = 500; //posición inicial para el primer CU. 
			int coordenadaEnY = 75;
			
			ArrayList<String> siguientes = new ArrayList<String>(); //listas auxiliares para definir los siguientes casos de uso a ubicar.
			ArrayList<String> siguientesAux = new ArrayList<String>();
			
			ArrayList<String> asignados = new ArrayList<String>(); //lista auxiliar para guardar los CU que ya se les han asignado sus coordenadas.

			
			boolean sePuedeAgregar = true; //boolean auxiliar que permite saber si se agrega o no a la lista "siguiente" los CU (para no ponerlos dos veces).
			

			for(UsecaseCaso u: listaCasos) //para todos los casos de uso (funciona como un for normal en verdad en este caso)
			{
				if(asignados.size() < listaCasos.size()) //si todavía no se ha asignado coordenada a todos los CU.
				{
					if(u.getId().equals("uc1")) //si es el primero
					{
						u.setPosx(coordenadaEnX);//setea los valores iniciales.
						u.setPosy(coordenadaEnY);
						
						asignados.add(u.getId());//se agrega a la lista de los "ya asignados con coordenadas"
						
						if(u.getEntran().size() > 0) //por si la lista está vacía.
						{
							for(String st: u.getEntran()) //agrega los CU que siguen a la lista "siguientes"
							{
								sePuedeAgregar = ComprobarSiExiste(siguientes, asignados, st); //comprueba que no esté repetido en la lista "siguientes" o "asignados"
								if(sePuedeAgregar)
									siguientes.add(st); //agrega los CU que se conectan con él a la lista "siguientes".
							}
						}
						
						if(u.getSalen().size() > 0) //por si la lista está vacía.
						{
							for(String s: u.getSalen()) //agrega los CU que siguen a la lista "siguientes".
							{
								sePuedeAgregar = ComprobarSiExiste(siguientes, asignados, s); //comprueba que no esté repetido en la lista "siguientes" o "asignados"
								if(sePuedeAgregar)
									siguientes.add(s); //agrega los CU que se conectan con él a la lista "siguientes".
							}
						}
						
						coordenadaEnY += 200; //se avanza hacia abajo para los siguientes CU. REVISAR VALOR
					}

					
					int contadorUC = 0; //cuenta los UC por "nivel" 
					
					for(String str: siguientes) //para todos los CU, se revisa la lista "siguientes". 
					{
						for(UsecaseCaso uc: listaCasos) //se busca en los CU. 
						{
							if(str.equals(uc.getId())) //si coincide el id
							{
								uc.setPosy(coordenadaEnY); //se setea el valor de la coordenada Y.
							
								if(siguientes.size() > 1) //si le sigue al CU más de uno.
								{
									coordenadaEnX = 370; 
									int coordenadaDefinitivaX = coordenadaEnX + 250 * contadorUC; //se desplaza hacia la derecha dependiendo de cuantos UC hayan en la fila.
									
									uc.setPosx(coordenadaDefinitivaX);//se asigna el valor.		
								}
								
								else //Si es solo uno el que le sigue
								{
									uc.setPosx(coordenadaEnX); //se setea el valor (centrado).
								}
							
								asignados.add(str);  //se agrega a la lista de los "ya asignados con coordenadas".
							
								if(uc.getSalen().size() > 0) //por si la lista está vacía.
								{
									for(String s: uc.getSalen()) //se agregan a la lista auxiliar los CU que siguen al actual.
									{
										sePuedeAgregar = ComprobarSiExiste(siguientes, asignados, s);
										if(sePuedeAgregar)
											siguientesAux.add(s);
									}	
								}
							
								if(uc.getEntran().size() > 0) //por si la lista está vacía.
								{
									for(String st: uc.getEntran()) //se agregan a la lista auxiliar los CU que siguen al actual.
									{
										sePuedeAgregar = ComprobarSiExiste(siguientes, asignados, st); 
										if(sePuedeAgregar)
											siguientesAux.add(st); 
									}
								}
								
								siguientesAux = LimpiarRepetidos(siguientesAux); //se limpian los repetidos (dos UC podrían conectarse al mismo UC que sigue un nivel más abajo).
								
								contadorUC ++; //se aumenta el contador de UC para este nivel.
								
							}
						}
					}
					
					siguientes.clear();//se limpia la actual.
					
					for(String s: siguientesAux)//se setea la nueva lista con los valores de la lista auxiliar. 
						siguientes.add(s);
					
					siguientesAux.clear(); //se limpia la lista auxiliar para el siguient for.
					
					
					for(String st: asignados)
					{
						for(String s: siguientes)
						{
							if(st.equals(s))
								siguientes.remove(st); //elimina de la lista "siguiente" los CU que ya han sido asignados.
						}
					}
					
					coordenadaEnY += 200; //se avanza hacia abajo para los siguientes CU. REVISAR VALOR.
					coordenadaEnX = 500; //se setea el valor de la coordenanda X en el centro.
				}
			}//este for busca pone al primer CU "uc1" primero y después va avanzando en la lista "siguientes" que son con los que se conecta dicho CU.
			
		}

		
		public ArrayList<String> LimpiarRepetidos(ArrayList<String> lista) //método que elimina elementos repetidos de una lista.
		{
			if(lista.size() > 1)
			{
				Set<String> s = new LinkedHashSet<String>(lista);
				lista.clear();
				lista.addAll(s);
				return lista;	
			}
			
			return lista;
		}
		
		public boolean ComprobarSiExiste(ArrayList<String> lista1, ArrayList<String> lista2, String elemento) //método que comprueba que un elemento no esté en otras dos listas.
		{
			boolean NoEstaRepetido = true;
			for(String s : lista1)
			{
				if(s.equals(elemento))
					NoEstaRepetido = false;
			}
			
			for(String s : lista2)
			{
				if(s.equals(elemento))
					NoEstaRepetido = false;
			}
			
			return NoEstaRepetido;
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

		
		public void addActor(ActorCaso act){
			listaActores.add(act);
		}
		public void addCaso(UsecaseCaso cas){
			listaCasos.add(cas);
		}
		public void addConexion(ConnectionCaso con){
			listaConexiones.add(con);
		}
		public void addComentario(Comentario com){
			listaComentarios.add(com);
		}
		
		
		//Getters y setters
		public String getNombreDiagrama() {
			return nombreDiagrama;
		}
		public void setNombreDiagrama(String nombreDiagrama) {
			this.nombreDiagrama = nombreDiagrama;
		}
		public ArrayList<ActorCaso> getListaActores() {
			return listaActores;
		}
		public ArrayList<UsecaseCaso> getListaCasos() {
			return listaCasos;
		}
		
		public void setListaActores(ArrayList<ActorCaso> a) {
			this.listaActores=a;
		}
		public void setListaCasos(ArrayList<UsecaseCaso> a) {
			this.listaCasos=a;
		}
		
		public ArrayList<ConnectionCaso> getListaConexiones() {
			return listaConexiones;
		}


		public ArrayList<Comentario> getListaComentarios() {
			return listaComentarios;
		}
		public void setListaComentarios(ArrayList<Comentario> listaComentarios) {
			this.listaComentarios = listaComentarios;
		}
		
		

	
}
