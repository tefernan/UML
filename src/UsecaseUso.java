import java.util.ArrayList;


public class UsecaseUso extends CajaUso {

	private ArrayList<String> salen;
	private ArrayList<String> entran;
	private ArrayList<String> uniones;
	
	//Constructor, llama al super
	public UsecaseUso(String tipoCaja, String id, String name) {
		super(tipoCaja, id, name);
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
	
	

}
