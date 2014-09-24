
public class ConnectionUso {
	
	//Variables de la conexion
	private String type;
	private String from;
	private String to;
	//Cajas desde donde y hacia donde va la conexion
	private CajaUso desde;
	private CajaUso hacia;
	
	//Constructor
	public ConnectionUso(String type, String from, String to) {
		this.type = type;
		this.from = from;
		this.to = to;
	}


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
	
	//Setters cajas de uso
	public CajaUso getDesde() {
		return desde;
	}
	public void setDesde(CajaUso desde) {
		this.desde = desde;
	}
	public CajaUso getHacia() {
		return hacia;
	}
	public void setHacia(CajaUso hacia) {
		this.hacia = hacia;
	}

}
