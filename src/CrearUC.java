import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;

public class CrearUC {
	
	private String name;
	private Font font = new Font("Serif", Font.BOLD, 25);
	private BufferedImage bi;
	private Graphics2D pone ;
	private int ancho , alto ;
	private BufferedImage img;
	private ArrayList<ActorUso> actores;
	private ArrayList<UsecaseUso> casos;
	private File actor = new File("actor.PNG");
	private File ovalo = new File("ovalo.PNG");

	//Empiezo a crear la imagen PNG
	public CrearUC(String nombre, UmlUso U) {
		this.name = nombre;
		this.actores=U.getListaActores();
		this.casos=U.getListaCasos();
		
		//Propiedades imagen png
		ancho=1300;
		alto=100+150*casos.size();
		
		System.out.print(alto);
		
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
		
		for(int i=0;i<casos.size();i++)
		{
			//Insertar ovalo
			pone.drawImage(img,casos.get(i).getPosx(), casos.get(i).getPosy(), null);
			pone.drawString(casos.get(i).getName(), casos.get(i).getPosx(), casos.get(i).getPosy());
			//String[] parrafo = casos.get(i).getName().split(delims);
			//Escribir texto
			/*pone.drawString(parrafo[0], casos.get(i).getPosx()+30, casos.get(i).getPosy()+img.getHeight()/3);
			pone.drawString(parrafo[1], casos.get(i).getPosx()+20, casos.get(i).getPosy()+img.getHeight()/2);
			pone.drawString(parrafo[2], casos.get(i).getPosx()+30, casos.get(i).getPosy()+img.getHeight()*2/3);*/
			
		}
	
		
	}
	
	public void CrearConexiones()throws IOException{
		
		//Ojo con los 4 tipos
		pone.setColor(Color.black);
		Stroke stroke = new BasicStroke(6, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 0, new float[] { 1, 0 }, 0);
		pone.setStroke(stroke);
		/*aw = img.getWidth() / 2;
		ah = img.getHeight() / 2;
		pone.drawLine(aw, ah, aw + 300, ah);*/
		
	}

	public void Finalizar() throws IOException{
		ImageIO.write(bi, "PNG", new File(name+".PNG"));
	}

}
