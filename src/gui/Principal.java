package gui;


import javax.swing.UIManager;



public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		


		GUI2 gui = new GUI2("Editor de UML");
		
	}

}
