package modeloCasos;

public class ConnectionCaso {
	
	//Variables de la conexion
	private String type;
	private String from;
	private String to;
	
	//Constructor
	public ConnectionCaso(String type, String from, String to) {
		this.type = type;
		this.from = from;
		this.to = to;
	}

	//getters y setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
	
	
}
