package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class numEvent extends KeyAdapter {
	
	public void keyTyped(KeyEvent thisEvent) {

		char c=thisEvent.getKeyChar();
		if(! ((c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (Character.isDigit(c)) )){
			thisEvent.consume();
		}
		/*
		if ((c > 47 && c < 58) || c == 8){
			//myInput.append(c);
			thisEvent.consume();
		}*/

	}

}
