
public class ActorUso extends CajaUso {
	
	//Typo de actor (primario o secundario)
	private String type;
	
	//Constructor, llama al super y le agrega el tipo de actor
	public ActorUso(String tipoCaja, String id, String name, String type) {
		super(tipoCaja, id, name);
		this.type = type;
	}

	
	//Getters y Setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
