package gui;

public class Comentario {
	
	private String comentario;
	private int posX;
	private int posY;
	
	public Comentario(String com, int x, int y){
		comentario = com;
		posX = x;
		posY = y;
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

}
