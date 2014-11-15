package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Lienzo extends JPanel {
	
	private ArrayList<Point> a= new ArrayList<Point>();
	private ArrayList<Point> b= new ArrayList<Point>();
	private File actor = new File("res/actor.PNG");
	private File ovalo = new File("res/ovalo.PNG");
	private JPanel actual;
	private Graphics g;
	
	public Lienzo()
	{
		
	}
	
	public JPanel Panel ()
	{
		return actual;
	}
	
	public void Lineas(ArrayList<Point>a,ArrayList<Point>b)
	{
		this.a=a;
		this.b=b;
	}
	
	public void Botones(ArrayList<Point> botones, ArrayList<String> tipo, JPanel a) throws IOException
	{
		JButton act;
		actual= a;
		System.out.print("boton");
		for(int i =0;i<botones.size();i++)
		{
		
			act= new JButton();
			
			if(tipo.get(i).equals("Caso"))
			{
			ImageIcon icon = new ImageIcon(ImageIO.read(ovalo));
			act.setIcon(icon);
			act.setBorderPainted(true); 
	        act.setContentAreaFilled(true); 
	        act.setFocusPainted(true); 
	        act.setOpaque(true);
	        act.setBounds(botones.get(i).x, botones.get(i).y, 173, 127);
			}
			
			else
			{
				ImageIcon icon = new ImageIcon(ImageIO.read(actor));
				act.setIcon(icon);
				act.setBorderPainted(false); 
		        act.setContentAreaFilled(false); 
		        act.setFocusPainted(false); 
		        act.setOpaque(false);
		        act.setBounds(botones.get(i).x, botones.get(i).y, 79, 129);
				
			}
			
			 actual.add(act);

		}
		
		
	}
	
	public void paints() throws InterruptedException
	{
		g=actual.getGraphics();

		g.setColor(Color.black);
		
		for(int i =0;i<a.size();i++)
		{
			System.out.print("flecha");
			g.drawLine(a.get(i).x, a.get(i).y, b.get(i).x, b.get(i).y);
			
		}
		
		
	}
	

}
