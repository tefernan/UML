import java.util.ArrayList;

public class UmlUso2 {
	
	//Nombre del diagrama
		private String nombreDiagrama;
		//Lista de las cosas
		private ArrayList<ActorUso> listaActores = new ArrayList<ActorUso>();
		private ArrayList<UsecaseUso> listaCasos = new ArrayList<UsecaseUso>();
		private ArrayList<ConnectionUso> listaConexiones = new ArrayList<ConnectionUso>();
		
		public UmlUso2(){
			//Constructor vacio
		}
		
		
		
		public void ordenarCasos(){
			int contador = 0;
			for(UsecaseUso u: listaCasos){
				u.setPosy(contador);
				contador +=200;
			}
		}
		
		//Agregar elementos a las listas
		public void addActor(ActorUso act){
			listaActores.add(act);
		}
		public void addCaso(UsecaseUso cas){
			listaCasos.add(cas);
		}
		public void addConexion(ConnectionUso con){
			listaConexiones.add(con);
		}
		
		//Getters y setters
		public String getNombreDiagrama() {
			return nombreDiagrama;
		}
		public void setNombreDiagrama(String nombreDiagrama) {
			this.nombreDiagrama = nombreDiagrama;
		}
		public ArrayList<ActorUso> getListaActores() {
			return listaActores;
		}
		public ArrayList<UsecaseUso> getListaCasos() {
			return listaCasos;
		}
		public ArrayList<ConnectionUso> getListaConexiones() {
			return listaConexiones;
		}

}
