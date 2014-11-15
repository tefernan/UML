package modeloClases;

import java.util.ArrayList;
import java.awt.*;
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
	private Font font;
	private Graphics2D pone ;
	private Clase auxiliar;
	private int posx,posy, auxy;
	
	private ArrayList<Point> de = new ArrayList<Point>();
	private ArrayList<Point> a = new ArrayList<Point>();

	//Recibe las clases a crear y las conexiones entre ella
	public CrearDC(UmlClase u)
	{
		
		//Que se cree dependiendo del mas abajo!ArrayList<Clase> a
	
		this.clases=u.getListaClases();
		name = u.getNombreDiagrama();

		this.conexiones=u.getListaConexiones();

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
			pone.drawLine(posx, posy+30, posx+auxiliar.getAncho()+10, posy+30);
			
			//Pongo los atributos
			CrearAtributos();

			//Dibujo la linea 2
			pone.setColor(Color.black);
			pone.drawLine(posx, auxy+30, posx+auxiliar.getAncho()+10, auxy+30);
			
			
			//Pongo los metodos
			CrearMetodos();
			
		}

	}
	
	public void dibujarUnion(int p1x, int p1y, int p2x, int p2y, String tipo)
	   {
	     double ang=0.0, angSep=0.0;
	     double tx,ty;
	     int dist=0;
	     Point punto1=null,punto2=null;

	     //defino dos puntos extremos
	     punto1=new Point(p1x,p1y);
	     punto2=new Point(p2x,p2y);

	     //tamaño de la punta de la flecha
	     dist=20;

	     /* (la coordenadas de la ventana es al reves)
	         calculo de la variacion de "x" y "y" para hallar el angulo
	      **/

	     ty=-(punto1.y-punto2.y)*1.0;
	     tx=(punto1.x-punto2.x)*1.0;
	     //angulo
	     ang=Math.atan (ty/tx);

	     if(tx<0)
	     {// si tx es negativo aumentar 180 grados
	        ang+=Math.PI;
	     }

	     //puntos de control para la punta
	     //p1 y p2 son los puntos de salida
	     Point p1=new Point(),p2=new Point(),punto=punto2;

	     //angulo de separacion
	     angSep=25.0;
	    
	     p1.x=(int)(punto.x+dist*Math.cos (ang-Math.toRadians (angSep)));
	     p1.y=(int)(punto.y-dist*Math.sin (ang-Math.toRadians (angSep)));
	     p2.x=(int)(punto.x+dist*Math.cos (ang+Math.toRadians (angSep)));
	     p2.y=(int)(punto.y-dist*Math.sin (ang+Math.toRadians (angSep)));
	   

	     if(tipo.equals("basic"))
	     {
	    	 //dale color a la linea
		     pone.setColor (Color.black);
		     // grosor de la linea
		     pone.setStroke (new BasicStroke(3.2f));
		     //dibuja la linea de extremo a extremo
		     pone.drawLine (punto1.x,punto1.y,punto.x,punto.y);
		     
		     de.add(punto1);
		     a.add(punto);

	     }
	     
	     //Escribir sobre la linea  <<extend>>
	     else if(tipo.equals("extend"))
	     {
	    	 //dale color a la linea
		     pone.setColor (Color.red);
		     float[] style = {5,5};
		     pone.setStroke( new BasicStroke( 
		             3.2f,
		             BasicStroke.CAP_BUTT,
		             BasicStroke.JOIN_MITER,
		             10.0f,
		             style,
		             0));

		     //dibuja la linea de extremo a extremo
		     pone.drawLine (punto1.x,punto1.y,punto.x,punto.y);
		     
		     //dibujar la punta
		     pone.setStroke (new BasicStroke(3.2f));
		     pone.drawLine (p1.x,p1.y,punto.x,punto.y);
		     
		     pone.drawLine (p2.x,p2.y,punto.x,punto.y);
		     
		     
		     pone.rotate((int)ang);
		     pone.drawString("<<extend>>",p1.x,30);
		     
		     pone.rotate(-(int)ang);
	
	     }
	     
	   //Escribir sobre la linea  <<include>>
	     else if(tipo.equals("include"))
	     {
	    	 //dale color a la linea
	    	 pone.setColor (Color.gray);
		     float[] style = {5,5};
		     pone.setStroke( new BasicStroke( 
		             3.2f,
		             BasicStroke.CAP_BUTT,
		             BasicStroke.JOIN_MITER,
		             10.0f,
		             style,
		             0));

		     //dibuja la linea de extremo a extremo
		     pone.drawLine (punto1.x,punto1.y,punto.x,punto.y);
		     //dibujar la punta
		     pone.setStroke (new BasicStroke(3.2f));
		     pone.drawLine (p1.x,p1.y,punto.x,punto.y);
		     pone.drawLine (p2.x,p2.y,punto.x,punto.y);
		  
	    	 
	     }
	     
	     else
	     {
	    	 //dale color a la linea
		     pone.setColor (Color.black);
		     // grosor de la linea
		     pone.setStroke (new BasicStroke(3.2f));
		     //dibuja la linea de extremo a extremo
		     pone.drawLine (punto1.x,punto1.y,punto.x,punto.y);
		     //dibujar la punta
		     pone.drawLine (p1.x,p1.y,punto.x,punto.y);
		     pone.drawLine (p2.x,p2.y,punto.x,punto.y);
		     pone.drawLine(p1.x, p1.y, p2.x, p2.y);
	     
	     }     
	     

	   }
	
	public void CrearConexiones()throws IOException{
		
		String a1,a2,tipo,atipo="";
		
		for(ConnectionClase a: conexiones)
		{
			a1=a.getFrom();
			a2=a.getTo();
			tipo=a.getType();
			int x1=0,y1=0,x2=0,y2=0;
			
			
			//Para unir basic
			if(tipo.equals("basic"))
			{
	
				for(int i=0;i<clases.size();i++ )
				{
					
					if(clases.get(i).getId().equals(a1))
					{

						x1=clases.get(i).getPosx() + 36;
						y1=clases.get(i).getPosy() + 63;
						
					}
				}
				
				for(Clase c: clases)
				{
					if(c.getId().equals(a2))
					{

						x2=c.getPosx();
						y2=c.getPosy()+63;
					}
				}
				
			}
			
			
			
			dibujarUnion(x1, y1, x2, y2,tipo);
			
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
