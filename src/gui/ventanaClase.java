package gui;

import javax.swing.JFrame;

public class ventanaClase {
	
	private JFrame frame;
	private String titulo;
	
	public ventanaClase(){
		
		frame = new JFrame(titulo);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(700, 500);
		frame.setVisible(true);
		
	}

}
