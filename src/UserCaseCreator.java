import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class UserCaseCreator {
	
	//URL de donde esta guardado el archivo XML de Casos de Uso
	String urlArchivo;
	File archivo;
	
	//Constructor, que le pasa la url
	public UserCaseCreator(File dir){
		archivo = dir;
	}
	
	//Metodo que lee el XML, lo va parseando y creando los objetos del diagrama
	public UmlUso parseXML(){
		UmlUso diag = new UmlUso();
		
		try{
			//Crea un documento con el XML
			File fXmlFile = archivo;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			//Imprime la raiz
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
						
						//-----------------------
						String tipoCaja = "actor";
						String id = actorElement.getAttribute("id").replaceAll("\\s","");
						String name = actorElement.getAttribute("name");
						String type = actorElement.getAttribute("type").replaceAll("\\s","");
						ActorUso actor = new ActorUso(tipoCaja, id, name, type);
						diag.addActor(actor);
						
						//-----------------------
						/*
						System.out.println("---------ACTOR---------");
						System.out.println("Tipo de Actor :" + actor.getAttribute("type"));
						System.out.println("Nombre :" + actor.getAttribute("name"));
						System.out.println("id :" + actor.getAttribute("id"));
						*/
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
						
						//-----------------------
						String tipoCaja = "caso";
						String id = useCaseElement.getAttribute("id").replaceAll("\\s","");
						String name = useCaseElement.getAttribute("name");
						UsecaseUso caso = new UsecaseUso(tipoCaja, id, name);
						diag.addCaso(caso);
						
						//-----------------------
						
						/*
						System.out.println("---------Use Case---------");
						System.out.println("Nombre del caso :" + useCase.getAttribute("name"));
						System.out.println("id :" + useCase.getAttribute("id"));
						*/
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
						
						//-----------------------
						String type = useCaseElement.getAttribute("type").replaceAll("\\s","");
						String from = useCaseElement.getAttribute("from").replaceAll("\\s","");
						String to = useCaseElement.getAttribute("to").replaceAll("\\s","");
						
						ConnectionUso conexion = new ConnectionUso(type, from, to);
						diag.addConexion(conexion);
						
						//-----------------------
						
						/*
						System.out.println("---------Connection---------");
						System.out.println("Tipo de conexion :" + useCase.getAttribute("type"));
						System.out.println("Desde :" + useCase.getAttribute("from"));
						System.out.println("Hacia :" + useCase.getAttribute("to"));
						*/
					}
				}
				
			}
			
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		diag = asignarEntradasSalidas(diag);
		return diag;
		
		
	}
	
	public UmlUso asignarEntradasSalidas(UmlUso d){
		
		UmlUso diag = d;
		for(ActorUso a: diag.getListaActores()){
			for(ConnectionUso c: diag.getListaConexiones()){
				if(a.getId().equals(c.getFrom())){
					a.addSalen(c.getTo());
				}
			}
		}
		
		for(UsecaseUso u: diag.getListaCasos()){
			for(ConnectionUso c: diag.getListaConexiones()){
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
	
	

}
