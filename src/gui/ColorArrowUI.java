package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;

//Esta clase es solo para el estilo del combobox. ignorar
public class ColorArrowUI extends BasicComboBoxUI {
	public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowUI();
    }

    @Override protected JButton createArrowButton() {
    	return new BasicArrowButton(BasicArrowButton.SOUTH, new Color(61, 61, 61), new Color(61, 61, 61), Color.GRAY, new Color(61, 61, 61));
    	//return new BasicArrowButton(BasicArrowButton.SOUTH);
    	//new BasicArrowButton(direction, background, shadow, darkShadow, highlight)
    	/*
        return new BasicArrowButton(
            BasicArrowButton.NORTH,
            Color.cyan, Color.magenta,
            Color.yellow, Color.blue);
            */
    }
}
