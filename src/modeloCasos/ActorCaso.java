package modeloCasos;

import java.util.ArrayList;

public class ActorCaso {
	
	//Tipo de actor, id y nombre
	private String type;
	private String id;
	private String name;
	//Conexiones que salen
	private ArrayList<String> salen;
	
	//Coordenadas de la caja del actor
	private int posx = 0;
	private int posy = 0;
	
	//Ancho y alto
	private int ancho;
	private int alto;
	
	//Constructor del actor
	public ActorCaso(String type, String id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;
		
		salen = new ArrayList<String>();
	}
	
	//Agregar salidas desde el actor
	public void addSalen(String nuevo){
		salen.add(nuevo);
	}

	//Getter de la lista
	public ArrayList<String> getSalen() {
		return salen;
	}
	
	//getters y setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPosx() {
		return posx;
	}
	public void setPosx(int posx) {
		this.posx = posx;
	}
	public int getPosy() {
		return posy;
	}
	public void setPosy(int posy) {
		this.posy = posy;
	}

}
