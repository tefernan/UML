package modeloClases;

import gui.Comentario;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import modeloCasos.ConnectionCaso;
import modeloCasos.UsecaseCaso;

public class CrearDC {

	private String name;
	private BufferedImage bi;
	private ArrayList<Clase> clases;
	private ArrayList<ConnectionClase> conexiones;
	private ArrayList<Comentario> comentarios;
	private Font font;
	private Graphics2D pone;
	private Clase auxiliar;
	private int posx, posy, auxy, ancho, alto;

	private ArrayList<Point> de = new ArrayList<Point>();
	private ArrayList<Point> a = new ArrayList<Point>();

	// Recibe las clases a crear y las conexiones entre ella
	public CrearDC(UmlClase u) {

		this.clases = u.getListaClases();
		name = u.getNombreDiagrama();
		this.conexiones = u.getListaConexiones();
		this.comentarios=u.getListaComentarios();

		// Auxiliares para crear dimensión imagen
		int ancho = 0, alto = 0;
		for (Clase c : clases) {
			if (ancho< c.getPosx() + c.getAncho()) {
				ancho = c.getPosx() + c.getAncho();
			}

			if (alto < c.getPosy() + c.getAlto()) {
				alto = c.getPosy() + c.getAlto();
			}

		}
		
		for (Comentario c : comentarios) {
			if (ancho < c.getPosX() + c.getAncho()) {
				ancho = c.getPosX() + c.getAncho();
			}

			if (alto < c.getPosY() + c.getAlto()) {
				alto = c.getPosY() + c.getAlto();
			}

		}
		
		ancho += 20;
		alto += 20;

		bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		pone = bi.createGraphics();

		pone.setColor(Color.white);
		pone.fillRect(0, 0, bi.getWidth(), bi.getHeight());
		pone.setStroke(new BasicStroke(3.2f));

		pone.setColor(Color.black);
		font = new Font("Serif", Font.BOLD, 30);
		pone.setFont(font);
		pone.drawString(name, bi.getWidth() / 3, 30);

	}

	public void CrearNombre() {
		// Le pongo el nombre
		pone.setColor(Color.black);
		font = new Font("Serif", Font.BOLD, 20);
		pone.setFont(font);
		pone.drawString(auxiliar.getNombre(), posx + 3, posy + 20);
		auxy += 25;
	}

	public void CrearAtributos() {
		font = new Font("Serif", Font.PLAIN, 20);
		pone.setFont(font);
		for (int k = 0; k < auxiliar.getAtributos().size(); k++) {
			auxy += 20;
			pone.drawString(auxiliar.getAtributos().get(k), posx + 3, auxy);
		}

		auxy += 10;

	}

	public void CrearMetodos() {
		font = new Font("Serif", Font.PLAIN, 20);
		pone.setFont(font);
		for (int k = 0; k < auxiliar.getMetodos().size(); k++) {
			auxy += 20;
			pone.drawString(auxiliar.getMetodos().get(k), posx + 3, auxy);
		}
	}

	public void CrearClase() {
		// Itero por las clases
		for (int i = 0; i < clases.size(); i++) {

			auxiliar = clases.get(i);
			posx = auxiliar.getPosx();
			posy = auxiliar.getPosy();
			ancho = auxiliar.getAncho();
			alto = auxiliar.getAlto();
			auxy = posy;

			
			// Creo el Rectangulo
			pone.setColor(Color.BLACK);
			pone.drawRect(posx, posy, ancho, alto);

			CrearNombre();

			// Dibujo la linea 1
			pone.setColor(Color.BLACK);
			pone.drawLine(posx, auxy, posx + ancho, auxy);

			// Pongo los atributos
			CrearAtributos();

			// Dibujo la linea 2
			pone.setColor(Color.BLACK);
			pone.drawLine(posx, auxy, posx + ancho, auxy);

			// Pongo los metodos
			CrearMetodos();
			
			// Creo las conexiones
			CrearConexiones();
			
			//Crea los comentarios
			CrearComentarios();

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

		// tamaño de la punta de la flecha
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

		if (tipo.equals("association")) {
			// dale color a la linea
			pone.setColor(Color.BLACK);
			// grosor de la linea
			pone.setStroke(new BasicStroke(3.2f));
			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);

			de.add(punto1);
			a.add(punto);

		}

		else if (tipo.equals("dependency")) {
			// dale color a la linea
			pone.setColor(Color.BLACK);
			float[] style = { 5, 5 };
			pone.setStroke(new BasicStroke(3.2f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, style, 0));

			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);

			// dibujar la punta
			pone.setStroke(new BasicStroke(3.2f));
			pone.drawLine(p1.x, p1.y, punto.x, punto.y);
			pone.drawLine(p2.x, p2.y, punto.x, punto.y);

		}

		else if (tipo.equals("inheritance")) {
			// dale color a la linea
			pone.setColor(Color.BLACK);
			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);
			// dibujar la punta
			pone.setStroke(new BasicStroke(3.2f));
			pone.drawLine(p1.x, p1.y, punto.x, punto.y);
			pone.drawLine(p2.x, p2.y, punto.x, punto.y);
			pone.drawLine(p1.x, p1.y, p2.x, p2.y);

		}

		// Negro
		else if (tipo.equals("composition")) {
			// dale color a la linea
			pone.setColor(Color.BLACK);
			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);
			// dibujar rombo

			//AffineTransform oa = pone.getTransform();
			//pone.rotate(Math.toRadians(ang), ancho, alto);
			pone.setColor(Color.BLACK);
			pone.fillRect(punto2.x-5, punto2.y, 11, 11);
			//pone.setTransform(oa);

		}

		// Blanco
		else if (tipo.equals("aggregation")) {
			// dale color a la linea
			pone.setColor(Color.BLACK);
			// dibuja la linea de extremo a extremo
			pone.drawLine(punto1.x, punto1.y, punto.x, punto.y);
			// dibujar rombo

			//AffineTransform old = pone.getTransform();
			//pone.rotate(Math.toRadians(ang), ancho, alto);

			pone.drawRect(punto2.x-5, punto2.y, 11, 11);
			pone.setColor(Color.WHITE);
			pone.fillRect(punto2.x-5, punto2.y, 10, 10);
			//pone.setTransform(old);

		}

	}

	public void CrearConexiones(){

		String a1, a2, tipo;
		int p1 = 0, p2 = 0;

		for (ConnectionClase a : conexiones) {
			a1 = a.getFrom();
			a2 = a.getTo();
			tipo = a.getType();

			int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

			for (int i = 0; i < clases.size(); i++) {
				if (clases.get(i).getId().equals(a1)) {
					p1 = i;
					x1 = clases.get(i).getPosx();
					y1 = clases.get(i).getPosy();

				}

				if (clases.get(i).getId().equals(a2)) {
					p2 = i;
					x2 = clases.get(i).getPosx();
					y2 = clases.get(i).getPosy();

				}
			}

			// Como conecto!

			/*
			if (y1 + clases.get(p1).getAlto() < y2 && ((x1<x2 && x2<x1+clases.get(p1).getAncho())||(x2<x1 && x1<x2+clases.get(p2).getAncho()))) {
				x1 += clases.get(p1).getAncho() / 2;
				y1 += clases.get(p1).getAlto();
				x2 += clases.get(p2).getAncho() / 2;
			}
			*/
			

			
			  //Conecto p1 por abajo y p2 por arriba 
			if(y1<y2 && y1<y2 && y2>y1+clases.get(p1).getAlto()) {
			  x1+=clases.get(p1).getAncho()/2; y1+=clases.get(p1).getAlto();
			  x2+=clases.get(p2).getAncho()/2; }
			  
			  //Conecto p1 por arriba y p2 por abajo 
			if(y2<y1) {
			  x1+=clases.get(p1).getAncho()/2; x2+=clases.get(p2).getAncho()/2;
			  y2+=clases.get(p2).getAlto(); }
			  
			  if(y2<y1 && y1<y2 && y2<y1+clases.get(p1).getAlto()) {
			  }
			  
			  //Conecto p1 por derecha y p2 por izquierda 
			  if(x1<x2 && y1==y2) {
			  
			  
			  x1+=clases.get(p1).getAncho(); y1+=clases.get(p1).getAlto()/2;
			  y2+=clases.get(p2).getAlto()/2; }
			  
			  //Conecto p2 por derecha y p1 por izquierda 
			  if(x2<x1 && y1==y2) {
			  y1+=clases.get(p1).getAlto()/2; x2+=clases.get(p2).getAncho();
			  y2+=clases.get(p2).getAlto()/2; }
			 

			dibujarUnion(x1, y1, x2, y2, tipo);

		}

	}

	public void CrearComentarios() {
		
		ArrayList<String> texto;
		String del = "[ ]";
		
		for (int i = 0; i < comentarios.size(); i++) {
			String a1 = "", a2 = "", aux = "";
			texto = new ArrayList<String>();

			String par[] = comentarios.get(i).getComentario().split(del);
			

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
			comentarios.get(i).setLineas(texto);
		}
		
	
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
		ImageIO.write(bi, "PNG", new File("DiagramaClases.PNG"));
	}

	public void Finalizar(File dir) throws IOException {
		ImageIO.write(bi, "PNG", dir);
	}

	public ImageIcon FinalizarIcon() {
		ImageIcon image = new ImageIcon(bi);
		return image;

	}

}
