package modeloCasos;

import java.util.ArrayList;

public class UsecaseCaso {
	
	//id y nombre del caso de uso
	private String id;
	private String name;
	//Conexiones 
	private ArrayList<String> salen;
	private ArrayList<String> entran;
	private ArrayList<String> uniones;
	
	//Coordenadas de la caja del caso de uso
	private int posx = 0;
	private int posy = 0;
	
	//constructor
	public UsecaseCaso(String id, String name) {
		this.id = id;
		this.name = name;
		
		salen = new ArrayList<String>();
		entran = new ArrayList<String>();
		uniones = new ArrayList<String>();
	}
	
	//Agregar salidas y entradas a las listas
	public void addSalen(String nuevo){
		salen.add(nuevo);
	}
	public void addEntran(String nuevo){
		entran.add(nuevo);
	}
	public void addUniones(String nuevo){
		uniones.add(nuevo);
		System.out.println(nuevo);
	}
	
	//Getters de la lista
	public ArrayList<String> getSalen() {
		return salen;
	}
	public ArrayList<String> getEntran() {
		return entran;
	}
	public ArrayList<String> getUniones() {
		return uniones;
	}

	//setters y getters
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
