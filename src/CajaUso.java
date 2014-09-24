import java.util.ArrayList;


public abstract class CajaUso {
	
	//Variables en comun entre casos y actores (el id, el nombre, y si es Uso o Actor)
	protected String tipoCaja;
	protected String id;
	protected String name;
	
	//Coordenadas de la caja
	protected int posx = 0;
	protected int posy = 0;
	
	//Lista de las conexiones de cada caja
	protected ArrayList<ConnectionUso> conexiones = new ArrayList<ConnectionUso>();
	
	//Constructor
	public CajaUso(String tipoCaja, String id, String name) {
		this.tipoCaja = tipoCaja;
		this.id = id;
		this.name = name;
	}
	
	//Metodo para agregar conexion
	public void addConnection(ConnectionUso nuevaConexion){
		conexiones.add(nuevaConexion);
	}
	
	//Getters y Setters
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
	public String getTipoCaja() {
		return tipoCaja;
	}
	public void setTipoCaja(String tipoCaja) {
		this.tipoCaja = tipoCaja;
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
