import java.util.ArrayList;


public class ActorUso extends CajaUso {
	
	//Typo de actor (primario o secundario)
	private String type;
	//Conexiones que salen
	private ArrayList<String> salen;
	
	//Constructor, llama al super y le agrega el tipo de actor
	public ActorUso(String tipoCaja, String id, String name, String type) {
		super(tipoCaja, id, name);
		this.type = type;
	}
	
	//Agregar salidas desde el actor
	public void addSalen(String nuevo){
		salen.add(nuevo);
	}
	
	//Getter de la lista
	public ArrayList<String> getSalen() {
		return salen;
	}
	

	
	//Getters y Setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	

}
