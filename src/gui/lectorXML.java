package gui;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import modeloCasos.ActorCaso;
import modeloCasos.ConnectionCaso;
import modeloCasos.UmlCaso;
import modeloCasos.UsecaseCaso;
import modeloClases.Clase;
import modeloClases.ConnectionClase;
import modeloClases.UmlClase;



public class lectorXML {
	
	public lectorXML(){
		
	}
	
	public UmlClase leerXMLClase(String texto){
		UmlClase diag = new UmlClase();
		try {
			//carga el texto en un documento
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(new InputSource(new StringReader(texto)));
			
			doc.getDocumentElement().normalize();
			String nombre = doc.getDocumentElement().getNodeName();
			
			diag.setNombreDiagrama(nombre);
			
			
			//empieza a leer el xml. 
			//CLASES-----
			NodeList nListClases= doc.getElementsByTagName("class");
			for(int temp = 0; temp<nListClases.getLength(); temp++){
				Node nodoClase = nListClases.item(temp);
				if(nodoClase.getNodeType() == Node.ELEMENT_NODE){
					Element claseElement = (Element) nodoClase;
					String idClase = claseElement.getAttribute("id").replaceAll("\\s",""); //borra espacio de los id
					String nameClase = claseElement.getAttribute("name");
					
					Clase clase = new Clase(nameClase, idClase);//clase que se va a agregar
					
					//Atributos
					NodeList attributes = claseElement.getElementsByTagName("attributes");
					for(int temp2 = 0; temp2 < attributes.getLength(); temp2++){
						NodeList listaAtt = ((Element)attributes.item(temp2)).getElementsByTagName("att");
						
						for(int temp3 = 0; temp3 < listaAtt.getLength(); temp3++){
							Element att = (Element) listaAtt.item(temp3);
							String nameAtt = att.getAttribute("name");
							String typeAtt = att.getAttribute("type").replaceAll("\\s","");
							String visibilityAtt = att.getAttribute("visibility").replaceAll("\\s","");
							
							String atributo = visibilityAtt + nameAtt + ": " + typeAtt;
							clase.AgregarAtributo(atributo);
						}
					}
					
					//metodos
					NodeList methods = claseElement.getElementsByTagName("methods");
					for(int temp2 = 0; temp2 < methods.getLength(); temp2++){
						NodeList listaMethods = ((Element)methods.item(temp2)).getElementsByTagName("method");
						
						for(int temp3 = 0; temp3 < listaMethods.getLength(); temp3++){
							Element method = (Element) listaMethods.item(temp3);
							String nameMet = method.getAttribute("name");
							String typeMet = method.getAttribute("type").replaceAll("\\s","");
							String visibilityMet = method.getAttribute("visibility").replaceAll("\\s","");
							
							String metodo = visibilityMet + nameMet + "(";
							
							NodeList params = method.getElementsByTagName("param");
							for(int temp4 = 0; temp4<params.getLength(); temp4++){
								Element param = (Element) params.item(temp4);
								String nameParam = param.getAttribute("name");
								String typeParam = param.getAttribute("type").replaceAll("\\s","");
								metodo += nameParam + ":" +typeParam;
								if(temp4 <params.getLength()-1){
									metodo += ",";
								}
							}
							
							metodo += "):" + typeMet;
							
							//System.out.println(metodo);
							clase.AgregarMetodos(metodo);
						}
					}
					

					
					diag.addClases(clase);
				}
			}
			
			/*
			
			for(Clase c:diag.getListaClases()){
				System.out.println(c.getNombre());
				for(String b:c.getAtributos()){
					System.out.println(b);
				}
				for(String b:c.getMetodos()){
					System.out.println(b);
				}
			}
			
			Thread.sleep(1000);
			*/
			
			//CONEXIONES ------
			NodeList nListConnections = doc.getElementsByTagName("connections");
			
			for(int temp = 0; temp<nListConnections.getLength(); temp++){
				Node nNode = nListConnections.item(temp);
				NodeList listaConnection = ((Element)nNode).getElementsByTagName("connection");
				
				for(int temp2 = 0; temp2<listaConnection.getLength(); temp2++){
					Node nodoConnection = listaConnection.item(temp2);
					
					if(nodoConnection.getNodeType() == Node.ELEMENT_NODE){
						Element conex = (Element) nodoConnection;
						
						//-----------------------
						String type = conex.getAttribute("type").replaceAll("\\s","");
						String from = conex.getAttribute("from").replaceAll("\\s","");
						String to = conex.getAttribute("to").replaceAll("\\s","");
						
						ConnectionClase conexion = new ConnectionClase(type, from, to);
						diag.addConexion(conexion);
					}
				}
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		diag = asignarEntradasSalidasClases(diag);
		
		/*
		for(Clase c:diag.getListaClases()){
			System.out.println(c.getNombre());
			for(String b:c.getAtributos()){
				System.out.println(b);
			}
			for(String b:c.getMetodos()){
				System.out.println(b);
			}
		}
		
		for(ConnectionClase s:diag.getListaConexiones()){
			System.out.println(s.getFrom());
			System.out.println(s.getTo());
			System.out.println(s.getType());
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		return diag;
		
	}

	public UmlCaso leerXMLCaso(String texto){
		UmlCaso diag = new UmlCaso();
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(new InputSource(new StringReader(texto)));
			
			doc.getDocumentElement().normalize();
			String nombre = doc.getDocumentElement().getNodeName();
			
			diag.setNombreDiagrama(nombre);
			//System.out.println("Elemento raiz: "+  diag.getNombreDiagrama());
			
			
			//-----PROCESADO DE ACTORES
			//Obtiene una lista de Actors (no Actor). Deberia ser de largo uno
			NodeList nListActors = doc.getElementsByTagName("actors");
			
			for(int temp = 0; temp<nListActors.getLength(); temp++){
				//Obtiene listas de actor
				Node nNode = nListActors.item(temp);
				NodeList listaActor = nNode.getChildNodes();
				
				//Para cada actor, lee sus datos y lo imprime
				for(int temp2 = 0; temp2<listaActor.getLength(); temp2++){
					Node nodoActor = listaActor.item(temp2);
					
					if(nodoActor.getNodeType() == Node.ELEMENT_NODE){
						Element actorElement = (Element) nodoActor;
						
						String id = actorElement.getAttribute("id").replaceAll("\\s","");
						String name = actorElement.getAttribute("name");
						String type = actorElement.getAttribute("type").replaceAll("\\s","");
						ActorCaso actor = new ActorCaso(type, id, name);
						diag.addActor(actor);

					}
				}
				
			}
			
			//-----PROCESADO DE CASOS DE USO
			//Obtiene una lista de Usecases (no Usecase). Deberia ser de largo uno
			NodeList nListUsecases = doc.getElementsByTagName("usecases");
			
			for(int temp = 0; temp<nListUsecases.getLength(); temp++){
				//Obtiene listas de actor
				Node nNode = nListUsecases.item(temp);
				NodeList listaUseCase = nNode.getChildNodes();
				
				//Para cada actor, lee sus datos y lo imprime
				for(int temp2 = 0; temp2<listaUseCase.getLength(); temp2++){
					Node nodoUseCase = listaUseCase.item(temp2);
					
					if(nodoUseCase.getNodeType() == Node.ELEMENT_NODE){
						Element useCaseElement = (Element) nodoUseCase;
						
						String id = useCaseElement.getAttribute("id").replaceAll("\\s","");
						String name = useCaseElement.getAttribute("name");
						UsecaseCaso caso = new UsecaseCaso(id, name);
						diag.addCaso(caso);
					}
				}
				
			}
			
			//-----PROCESADO DE CONEXIONES
			//Obtiene una lista de Connections (no Connection). Deberia ser de largo uno
			NodeList nListConnections = doc.getElementsByTagName("connections");
			
			for(int temp = 0; temp<nListConnections.getLength(); temp++){
				//Obtiene listas de actor
				Node nNode = nListConnections.item(temp);
				NodeList listaConnection = nNode.getChildNodes();
				
				//Para cada actor, lee sus datos y lo imprime
				for(int temp2 = 0; temp2<listaConnection.getLength(); temp2++){
					Node nodoConnection = listaConnection.item(temp2);
					
					if(nodoConnection.getNodeType() == Node.ELEMENT_NODE){
						Element useCaseElement = (Element) nodoConnection;

						String type = useCaseElement.getAttribute("type").replaceAll("\\s","");
						String from = useCaseElement.getAttribute("from").replaceAll("\\s","");
						String to = useCaseElement.getAttribute("to").replaceAll("\\s","");
						
						ConnectionCaso conexion = new ConnectionCaso(type, from, to);
						diag.addConexion(conexion);
						
					}
				}
				
			}
			
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		diag = asignarEntradasSalidasCaso(diag);
		return diag;
	}

	public UmlCaso asignarEntradasSalidasCaso(UmlCaso d){
		
		UmlCaso diag = d;
		for(ActorCaso a: diag.getListaActores()){
			for(ConnectionCaso c: diag.getListaConexiones()){
				if(a.getId().equals(c.getFrom())){
					a.addSalen(c.getTo());
				}
			}
		}
		
		for(UsecaseCaso u: diag.getListaCasos()){
			for(ConnectionCaso c: diag.getListaConexiones()){
				if(u.getId().equals(c.getFrom())){
					u.addSalen(c.getTo());
					u.addUniones(c.getTo());
				}
				if(u.getId().equals(c.getTo())){
					u.addEntran(c.getFrom());
					u.addUniones(c.getFrom());
				}
			}
		}

		return diag;
	}
	
	public UmlClase asignarEntradasSalidasClases(UmlClase d){
		UmlClase diag = d;
		for(Clase c: diag.getListaClases()){
			for(ConnectionClase a: diag.getListaConexiones()){
				if(c.getId().equals(a.getFrom())){
					c.addSalen(a.getTo());
					c.addTotales(a.getTo());
				}
				if(c.getId().equals(a.getTo())){
					c.addEntran(a.getFrom());
					c.addTotales(a.getFrom());
				}
			}
		}
		
		return diag;
	}
}
