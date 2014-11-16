package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class mouseEvent extends MouseAdapter {
	
	public void mouseClicked(MouseEvent e) {
		//System.out.println(e);
		//System.out.println("asd que wea");
		int a = e.getX();
		int b = e.getY();
		System.out.println(a+ " - "+b);
	}

}
