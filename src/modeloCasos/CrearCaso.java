package modeloCasos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import gui.*;

public class CrearCaso {

	private String name;
	private Font font = new Font("Serif", Font.BOLD, 25);
	private BufferedImage bi;
	private Graphics2D pone;
	private int ancho, alto;
	private BufferedImage img;
	private ArrayList<ActorCaso> actores;
	private ArrayList<UsecaseCaso> casos;
	private ArrayList<Comentario> comentarios;
	private ArrayList<ConnectionCaso> conexiones;
	private File actor = new File("res/actor.PNG");
	private File ovalo = new File("res/ovalo.PNG");

	// Empiezo a crear la imagen PNG
	public CrearCaso(String nombre, UmlCaso U) throws IOException {
		this.name = nombre;
		this.actores = U.getListaActores();
		this.casos = U.getListaCasos();
		this.conexiones = U.getListaConexiones();
		this.comentarios = U.getListaComentarios();

		int au = 0;
		// Propiedades imagen png
		for (ActorCaso a : actores) 
		{
			if(au<a.getPosx()+a.getAncho())
			{
				au=a.getPosx()+a.getAncho();
			}
		
		}
		
		for (UsecaseCaso a : casos) 
		{
			if(au<a.getPosx()+a.getAncho())
			{
				au=a.getPosx()+a.getAncho();
			}
		
		}
		
		for (Comentario a : comentarios) 
		{
			if(au<a.getPosX()+a.getAncho())
			{
				au=a.getPosX()+a.getAncho();
			}
		
		}

		ancho = au +30;
		
		int aa = 0;
		// Obtener alto
		for (ActorCaso a : actores) {
			if (aa < a.getPosy()) {
				aa = a.getPosy() + 179;
			}
		}

		for (UsecaseCaso a : casos) {
			if (aa < a.getPosy()) {
				aa = a.getPosy() + 179;
			}

		}
		
		for(Comentario a: comentarios)
		{
			if (aa < a.getPosY()) {
				aa = a.getPosY() + a.getAlto();
			}
		}

		alto = aa + 50;

		bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		pone = bi.createGraphics();
		pone.setColor(Color.white);
		pone.fillRect(0, 0, ancho, alto);
		pone.setColor(Color.black);
		pone.setFont(font);
		pone.drawString(nombre, ancho * 2 / 5, 25);

	}

	// Pongo los usuarios en la imagen, se entrega una lista con los usuarios
	public void CrearUsers() throws IOException {

		img = ImageIO.read(actor);
		font = new Font("Serif", Font.PLAIN, 25);

		for (int i = 0; i < actores.size(); i++) {
			pone.drawImage(img, actores.get(i).getPosx(), actores.get(i)
					.getPosy(), null);
			pone.setColor(Color.black);
			pone.setFont(font);
			pone.drawString(actores.get(i).getName(), actores.get(i).getPosx(),
					actores.get(i).getPosy() + img.getHeight() + 20);
		}

	}

	public void CrearCasos() throws IOException {

		// tipo de fuente
		font = new Font("Serif", Font.PLAIN, 20);
		pone.setFont(font);
		// Cargo imagen de ovalo
		img = ImageIO.read(ovalo);

		String del = "[ ]";

		ArrayList<String> texto;
		ArrayList<ArrayList<String>> nombres = new ArrayList<ArrayList<String>>();

		// Leo el caso de uso y lo divido por cantidad de caracteres bajando
		// cada 14 caracteres, 30 y 44
		for (int i = 0; i < casos.size(); i++) {
			String a1 = "", a2 = "", aux = "";
			texto = new ArrayList<String>();

			String par[] = casos.get(i).getName().split(del);

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
			nombres.add(texto);

		}

		for (int i = 0; i < casos.size(); i++) {
			// Insertar ovalo
			pone.drawImage(img, casos.get(i).getPosx(), casos.get(i).getPosy(),
					null);
			// pone.drawString(casos.get(i).getName(),casos.get(i).getPosx()+20,casos.get(i).getPosy()+img.getHeight()/3);

			int p = 0;

			for (String b : nombres.get(i)) {
				if (p == 0) {
					pone.drawString(b, casos.get(i).getPosx() + 20, casos
							.get(i).getPosy() + 40);
				}

				else if (p == 1) {
					pone.drawString(b, casos.get(i).getPosx() + 10, casos
							.get(i).getPosy() + 60);
				}

				else if (p == 2) {
					pone.drawString(b, casos.get(i).getPosx() + 20, casos
							.get(i).getPosy() + 80);
				}

				else if (p == 3) {
					pone.drawString(b, casos.get(i).getPosx() + 27, casos
							.get(i).getPosy() + 100);
				}

				p++;
			}

		}

	}

	public void dibujarUnion(int p1x, int p1y, int p2x, int p2y, String tipo) {
		double ang = 0.0, angSep = 0.0;
		double tx, ty;
		int dist = 0;
		Point punto1 = null, punto2 = null;

		// defino dos puntos extremos
		punto1 = new Point(p1x, p1y);
		punto2 = new Point(p2x, p2y);

		// tama�o de la punta de la flecha
		dist = 20;

		/*
		 * (la coordenadas de la ventana es al reves) calculo de la variacion de
		 * "x" y "y" para hallar el angulo
		 */

		ty = -(punto1.y - punto2.y) * 1.0;
		tx = (punto1.x - punto2.x) * 1.0;
		// angulo
		ang = Math.atan(ty / tx);

		if (tx < 0) {// si tx es negativo aumentar 180 grados
			ang += Math.PI;
		}

		// puntos de control para la punta
		// p1 y p2 son los puntos de salida
		Point p1 = new Point(), p2 = new Point(), punto = punto2;

		// angulo de separacion
		angSep = 25.0;

		p1.x = (int) (punto.x + dist * Math.cos(ang - Math.toRadians(angSep)));
		p1.y = (int) (punto.y - dist * Math.sin(ang - Math.toRadians(angSep)));
		p2.x = (int) (punto.x + dist * Math.cos(ang + Math.toRadians(angSep)));
		p2.y = (int) (punto.y - dist * Math.sin(ang + Math.toRadians(angSep)));

		if (tipo.equals("basic")) {
			// dale color a la linea
			pone.setColor(Color.black);
			// grosor de la linea
			pone.setStroke(new BasicStroke(3.2f));
			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);

		}

		// Escribir sobre la linea <<extend>>
		else if (tipo.equals("extend")) {
			// dale color a la linea
			pone.setColor(Color.red);
			float[] style = { 5, 5 };
			pone.setStroke(new BasicStroke(3.2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, style, 0));

			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);

			// dibujar la punta
			pone.setStroke(new BasicStroke(3.2f));
			pone.drawLine(p1.x, p1.y, punto.x, punto.y);
			pone.drawLine(p2.x, p2.y, punto.x, punto.y);

			pone.drawString("<<extend>>", punto1.x, punto1.y);

		}

		// Escribir sobre la linea <<include>>
		else if (tipo.equals("include")) {
			// dale color a la linea
			pone.setColor(Color.gray);
			float[] style = { 5, 5 };
			pone.setStroke(new BasicStroke(3.2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, style, 0));

			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);
			// dibujar la punta
			pone.setStroke(new BasicStroke(3.2f));
			pone.drawLine(p1.x, p1.y, punto.x, punto.y);
			pone.drawLine(p2.x, p2.y, punto.x, punto.y);

			pone.drawString("<<include>>", punto1.x, punto1.y);

		}

		else {
			// dale color a la linea
			pone.setColor(Color.blue);
			// grosor de la linea
			pone.setStroke(new BasicStroke(3.2f));
			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);
			// dibujar la punta
			pone.drawLine(p1.x, p1.y, punto.x, punto.y);
			pone.drawLine(p2.x, p2.y, punto.x, punto.y);
			pone.drawLine(p1.x, p1.y, p2.x, p2.y);

		}

	}

	public void CrearConexiones(){

		String a1, a2, tipo, atipo = "";

		for (ConnectionCaso a : conexiones) {
			a1 = a.getFrom();
			a2 = a.getTo();
			tipo = a.getType();
			int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

			// Para unir casos basic
			if (tipo.equals("basic")) {

				for (int i = 0; i < actores.size(); i++) {

					if (actores.get(i).getId().equals(a1)) {
						x1 = actores.get(i).getPosx() + 36;
						y1 = actores.get(i).getPosy() + 63;
						atipo = actores.get(i).getType();
					}
				}

				for (UsecaseCaso c : casos) {
					if (c.getId().equals(a2)) {

						x2 = c.getPosx();
						if (atipo.equals("secondary")) {
							x2 = c.getPosx() + 173;
						}

						y2 = c.getPosy() + 63;
					}
				}
			}

			else {
				for (UsecaseCaso c : casos) {
					if (c.getId().equals(a1)) {
						x1 = c.getPosx();
						y1 = c.getPosy();
					}
				}

				for (UsecaseCaso c : casos) {
					if (c.getId().equals(a2)) {

						x2 = c.getPosx();
						y2 = c.getPosy();
					}
				}

				if (y1 < y2) {

					x1 += 173 / 2;
					y1 += 127;
					x2 += 127 / 2;
				}

				// Conecto p1 por arriba y p2 por abajo
				if (y2 < y1) {
					x1 += 127 / 2;
					x2 += 127 / 2;
					y2 += 127;
				}

				// Conecto p1 por derecha y p2 por izquierda
				if (x1 < x2 && y1 == y2) {

					x1 += 127;
					y1 += 127 / 2;
					y2 += 127 / 2;
				}

				// Conecto p2 por derecha y p1 por izquierda
				if (x2 < x1 && y1 == y2) {
					y1 += 127 / 2;
					x2 += 127;
					y2 += 127 / 2;
				}
			}

			dibujarUnion(x1, y1, x2, y2, tipo);

		}
	}

	
	public void CrearComentarios() {
		
		int ax=0,ay=0;
		for (int i=0 ; i<comentarios.size();i++) 
		{
			
			ArrayList<String> escribir= comentarios.get(i).getLineas();
			pone.setColor(Color.YELLOW);
			ax=comentarios.get(i).getPosX();
			ay=comentarios.get(i).getPosY();
			pone.fillRect(ax, ay,120,escribir.size()*20);
			font = new Font("Serif", Font.PLAIN, 20);
			pone.setColor(Color.BLACK);
			
			for(String b :escribir)
			{
				pone.drawString(b, ax+3, ay+17);
				ay+=15;
			}
			
		}
	}

	public void Finalizar() throws IOException {
		ImageIO.write(bi, "PNG", new File(name + ".PNG"));
	}

	public void Finalizar(File dir) throws IOException {
		ImageIO.write(bi, "PNG", dir);
	}

	public ImageIcon FinalizarIcon() {
		ImageIcon image = new ImageIcon(bi);
		return image;

	}

}
