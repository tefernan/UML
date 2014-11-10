package modeloClases;

import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CrearDC {
	
	private String name;
	private BufferedImage bi;
	private ArrayList<Clase> clases;
	private ArrayList<ConnectionClase> conexion;
	private Font font;
	private Graphics2D pone ;
	private Clase auxiliar;
	private int posx,posy, auxy;

	//Recibe las clases a crear y las conexiones entre ella
	public CrearDC(UmlClase u)
	{
		
		//Que se cree dependiendo del mas abajo!ArrayList<Clase> a
	
		this.clases=u.getListaClases();
		name = u.getNombreDiagrama();


		bi = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		pone = bi.createGraphics();
	

		pone.setColor(Color.white);
		pone.fillRect(0, 0, 1000, 1000);
		pone.setStroke (new BasicStroke(3.2f));
		
		pone.setColor(Color.black);
		font = new Font("Serif", Font.BOLD, 30);
		pone.setFont(font);
		pone.drawString(name,500,20);

	}
	
	public void CrearNombre()
	{
		//Le pongo el nombre
		pone.setColor(Color.black);
		font = new Font("Serif", Font.BOLD, 20);
		pone.setFont(font);
		pone.drawString(auxiliar.getNombre(),posx+3,posy+20);
	
	}
	
	public void CrearAtributos()
	{
		font = new Font("Serif", Font.PLAIN, 20);
		pone.setFont(font);
		for(int k = 0; k < auxiliar.getAtributos().size(); k++)
		{
			pone.drawString(auxiliar.getAtributos().get(k),posx+3,posy+50+k*25);
			auxy=posy+50+k*25;
		}
		
	}
		
	public void CrearMetodos()
	{
		font = new Font("Serif", Font.PLAIN, 20);
		pone.setFont(font);
		for(int k = 0; k < auxiliar.getMetodos().size(); k++)
		{
			pone.drawString(auxiliar.getMetodos().get(k),posx+3,auxy+55+k*25);
			auxy=posy+50+k*25;				
		}
	}
	
	
	public void CrearClase()
	{
		//Itero por las clases
		for(int i = 0; i<clases.size();i++)
		{
			auxy=0;
			auxiliar= clases.get(i);
			posx=auxiliar.getPosx();
			posy= auxiliar.getPosy();
		
			//Creo el Rectangulo
			pone.setColor(Color.BLACK);
			pone.drawRect(posx, posy, auxiliar.getAncho()+10, auxiliar.getAlto());


			CrearNombre();
			
			//Dibujo la linea 1
			pone.setColor(Color.black);
			pone.drawLine(posx, posy+30, posx+auxiliar.getAncho(), posy+30);
			
			//Pongo los atributos
			CrearAtributos();

			//Dibujo la linea 2
			pone.setColor(Color.black);
			pone.drawLine(posx, auxy+30, posx+auxiliar.getAncho(), auxy+30);
			
			
			//Pongo los metodos
			CrearMetodos();
			
			
		}

	}
	

	public void Finalizar() throws IOException{
		ImageIO.write(bi, "PNG", new File("DiagramaClases.PNG"));
	}
	
	public void Finalizar(File dir) throws IOException{
		ImageIO.write(bi, "PNG", dir);
	}
	
	public ImageIcon FinalizarIcon(){
		ImageIcon image = new ImageIcon(bi);
		return image;
		
	}

}
