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

public class CrearCaso {
	
	private String name;
	private Font font = new Font("Serif", Font.BOLD, 25);
	private BufferedImage bi;
	private Graphics2D pone ;
	private int ancho , alto ;
	private BufferedImage img;
	private ArrayList<ActorCaso> actores;
	private ArrayList<UsecaseCaso> casos;
	private File actor = new File("res/actor.PNG");
	private File ovalo = new File("res/ovalo.PNG");

	//Empiezo a crear la imagen PNG
	public CrearCaso(String nombre, UmlCaso U) {
		this.name = nombre;
		this.actores=U.getListaActores();
		this.casos=U.getListaCasos();
		
		//Propiedades imagen png
		ancho=1300;
		alto=100+150*casos.size();
		
		bi = new BufferedImage(ancho, alto,
				BufferedImage.TYPE_INT_ARGB);
		pone = bi.createGraphics();
		pone.setColor(Color.white);
		pone.fillRect(0, 0, ancho, alto);
		/*pone.setColor(Color.gray);
		pone.fillRect(300, 30, ancho-600, alto-40);*/
		pone.setColor(Color.black);
		pone.setFont(font);
		pone.drawString(nombre,ancho*2/5,25);
		
	}

	//Pongo los usuarios en la imagen, se entrega una lista con los usuarios
	public void CrearUsers() throws IOException {

		img = ImageIO.read(actor);
		font = new Font("Serif", Font.PLAIN, 25);
		
		for(int i = 0; i<actores.size();i++)
		{
			//Primario
			if(actores.get(i).getType()=="primary")
			{
				pone.drawImage(img, actores.get(i).getPosx(), actores.get(i).getPosy(), null);
				pone.setColor(Color.black);
				pone.setFont(font);
				pone.drawString(actores.get(i).getName(), actores.get(i).getPosx(), actores.get(i).getPosy()+img.getHeight()+20);
				
			}
			
			//Secundario no se si es necesario porque se crean las posiciones antes
			else
			{
				pone.drawImage(img, actores.get(i).getPosx(), actores.get(i).getPosy(), null);
				pone.setColor(Color.black);
				pone.setFont(font);
				pone.drawString(actores.get(i).getName(), actores.get(i).getPosx(), actores.get(i).getPosy()+img.getHeight()+20);
				
			}
		}

	}
	
	public void CrearCasos()throws IOException{
		
		//tipo de fuente
		font = new Font("Serif", Font.PLAIN, 20);
		pone.setFont(font);
		//Cargo imagen de ovalo
		img = ImageIO.read(ovalo);

		String delims = "[\n]";
		
		
		//Leo el caso de uso y lo divido por cantidad de caracteres bajando cada 14 caracteres, 30  y 44
		for(int i = 0; i<casos.size();i++)
		{
			String a1, a2;
			if(casos.get(i).getName().length()>14 && casos.get(i).getName().length()<=30)
			{
				a1=casos.get(i).getName().substring(0,14)+"\n";
				a2=a1+casos.get(i).getName().substring(15,casos.get(i).getName().length());
				casos.get(i).setName(a2);
			}
			
			else if(casos.get(i).getName().length()>30)
			{
				a1=casos.get(i).getName().substring(0,14)+"\n"+ casos.get(i).getName().substring(15,30)+"\n";
				a2=a1+casos.get(i).getName().substring(31,casos.get(i).getName().length());
				casos.get(i).setName(a2);
				
			}
			
		}
		
		
		
		for(int i=0;i<casos.size();i++)
		{
			//Insertar ovalo
			pone.drawImage(img,casos.get(i).getPosx(), casos.get(i).getPosy(), null);
			
			//pone.drawString(casos.get(i).getName(),casos.get(i).getPosx()+20,casos.get(i).getPosy()+img.getHeight()/3);
			String[] parrafo = casos.get(i).getName().split(delims);
			
			for(int k = 0; k<parrafo.length;k++)
			{
				if(k==0)
				{
				pone.drawString(parrafo[k], casos.get(i).getPosx()+20, casos.get(i).getPosy()+img.getHeight()/3);
				}
				else if(k==1)
				{
					pone.drawString(parrafo[1], casos.get(i).getPosx()+10, casos.get(i).getPosy()+img.getHeight()/2);
				}
				
				else
				{
					pone.drawString(parrafo[2], casos.get(i).getPosx()+20, casos.get(i).getPosy()+img.getHeight()*2/3);
				}
			}
			
		}
	
		
	}
	
	public void dibujarFlecha(int p1x, int p1y, int p2x, int p2y)
	   {
	     double ang=0.0, angSep=0.0;
	     double tx,ty;
	     int dist=0;
	     Point punto1=null,punto2=null;

	     //defino dos puntos extremos
	     punto1=new Point(p1x,p1y);
	     punto2=new Point(p2x,p2y);

	     //tamaño de la punta de la flecha
	     dist=15;

	     /* (la coordenadas de la ventana es al revez)
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

	     //dale color a la linea
	     pone.setColor (Color.black);
	     // grosor de la linea
	     pone.setStroke (new BasicStroke(3.2f));
	     //dibuja la linea de extremo a extremo
	     pone.drawLine (punto1.x,punto1.y,punto.x,punto.y);
	     //dibujar la punta
	     //pone.drawLine (p1.x,p1.y,punto.x,punto.y);
	     //pone.drawLine (p2.x,p2.y,punto.x,punto.y);    

	   }
	
	public void CrearConexiones()throws IOException{
		
		for(ActorCaso a: actores){
			for(String s: a.getSalen()){
				System.out.println(s);
				int x1 = 0;
				int y1 = 0;
				int x2 = 0;
				int y2 = 0;
				
				int xCaso = 0;
				int yCaso = 0;
				for(UsecaseCaso u: casos){
					if(u.getId().equals(s)){
						xCaso = u.getPosx();
						yCaso = u.getPosy();
					}
				}
				
				x1 = a.getPosx() + 36;
				y1 = a.getPosy() + 63;
				
				if(a.getType().equals("primary")){
					x2 = xCaso;
				}
				else{
					x2 = xCaso + 173;
				}
				y2 = yCaso+63;
				dibujarFlecha(x1, y1, x2, y2);
			}
			
		}
		
	}

	public void Finalizar() throws IOException{
		ImageIO.write(bi, "PNG", new File(name+".PNG"));
	}
	
	public void Finalizar(File dir) throws IOException{
		ImageIO.write(bi, "PNG", dir);
	}
	
	public ImageIcon FinalizarIcon(){
		ImageIcon image = new ImageIcon(bi);
		return image;
		
	}

}
