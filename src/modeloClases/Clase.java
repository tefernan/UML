package modeloClases;

import java.util.ArrayList;

public class Clase {

	private String nombre;
	private String id;
	private ArrayList<String> atributos = new ArrayList<String>();
	private ArrayList<String> metodos = new ArrayList<String>();

	private ArrayList<String> salen = new ArrayList<String>(); // agregado
	private ArrayList<String> entran = new ArrayList<String>(); // agregado
	private ArrayList<String> total = new ArrayList<String>(); // agregado.

	private int ancho = 0;
	private int alto = 0;
	private int posx = 0, posy = 0;

	public Clase(String n, String i) {
		this.nombre = n;
		this.id = i;
		ancho = n.length();
		alto = 40;
	}

	// Agregar Atributos uno por uno
	public void AgregarAtributo(String a) {
		atributos.add(a);
		alto += 20;

		if (ancho < a.length()) {
			ancho = a.length();
		}
	}

	// Agrega Metodos uno por uno
	public void AgregarMetodos(String a) {
		metodos.add(a);
		alto += 20;

		if (ancho < a.length()) {
			ancho = a.length();
		}
	}

	// Obtengo nombre
	public String getNombre() {
		return nombre;
	}

	// Seteo Nombre
	public void setNombre(String nombr) {
		nombre = nombr;
	}

	// Obtengo Lista Atributos
	public ArrayList<String> getAtributos() {
		return atributos;
	}

	// Seteo Lista Atributos
	public void setAtributos(ArrayList<String> atributos) {
		this.atributos = atributos;
	}

	// Obtengo Lista Metodos
	public ArrayList<String> getMetodos() {
		return metodos;
	}

	// Seteo Lista Metodos
	public void setMetodos(ArrayList<String> metodos) {
		this.metodos = metodos;
	}

	// Obtengo Ancho Máximo (Caracteres palabra más larga)
	public int getAncho() {
		return ancho * 9;
	}

	// Seteo Ancho
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	// Obtengo Alto Máximo (Depende de cantidad metodos y atributos)
	public int getAlto() {
		return alto;
	}

	// Seteo Alto
	public void setAlto(int alto) {
		this.alto = alto;
	}

	// Obtengo la Posicion X de la Clase
	public int getPosx() {
		return posx;
	}

	// Seteo la Posicion X de la Clase
	public void setPosx(int posx) {
		this.posx = posx;
	}

	// Obtengo Posicion Y de la Clase
	public int getPosy() {
		return posy;
	}

	// Seteo Posicion Y de la Clase
	public void setPosy(int posy) {
		this.posy = posy;
	}

	// Agregar elementos de entrada y salida a las listas
	public void addTotal(String nuevo) {
		total.add(nuevo);
	}

	public void addSalen(String nuevo) { // agregado
		salen.add(nuevo);
	}

	public void addEntran(String nuevo) { // agregado
		entran.add(nuevo);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> getSalen() {
		return salen;
	}
	
	public void setSalen(ArrayList<String> salen) //nuevo
	{
		this.salen = salen;
	}

	public ArrayList<String> getEntran() {
		return entran;
	}

	public ArrayList<String> getTotal() {
		return total;
	}

}
