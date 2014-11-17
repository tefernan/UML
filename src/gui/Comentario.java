package gui;

import java.util.ArrayList;

public class Comentario {

	private String comentario;
	private int posX;
	private int posY;
	private ArrayList<String> lineas;
	private int ancho = 120;
	private int alto;

	public Comentario(String com, int x, int y) {
		comentario = com;
		posX = x;
		posY = y;

		ArrayList<String> texto;
		String del = "[ ]";

		String a1 = "", a2 = "", aux = "";
		texto = new ArrayList<String>();
		String par[] = comentario.split(del);

		for (String a : par) {
			if (aux.length() < 14) {
				a2 = aux;
				aux += a + " ";
				a1 = a;

				if (aux.length() > 14) {
					texto.add(a2);
					aux = a1 + " ";
				}

			}
		}
		texto.add(aux);
		this.lineas = texto;
		alto=lineas.size()*20;

	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public ArrayList<String> getLineas() {
		return lineas;
	}

	public void setLineas(ArrayList<String> lineas) {
		this.lineas = lineas;
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

}
