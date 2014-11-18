package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.text.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import modeloCasos.CrearCaso;
import modeloCasos.UmlCaso;
import modeloClases.Clase;
import modeloClases.ConnectionClase;
import modeloClases.CrearDC;
import modeloClases.UmlClase;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class GUI2 {
	
	//Frame principal y el titulo del frame
	private JFrame frame;
	private String titulo;
	
	//Pestañas
	private JTabbedPane paneles;
	private JPanel tabCodigo;
	private JPanel tabImagen;
	private JComboBox opciones;
	
	//Editor de texto
	private JTextPane texto;
	private JScrollPane scroll;
	private JToolBar herramientas;
	
	private boolean estaGuardado;
	private File archivoGuardado;
	
	private boolean teclaControl=false;
	private boolean teclaEspacio=false;
	
	//lector de xml
	private lectorXML lector;

	public GUI2(String titulo){
		//inicializamos ventana y menu
		this.titulo = titulo;
		frame = new JFrame(titulo);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		archivoGuardado = null;
		estaGuardado = false;
		
		lector = new lectorXML();
		
		cargarHerramientas();
		cargarTabs();
		cargarEditorTexto();
		listenerTecla();

		
		herramientas.setFloatable(false);
		
		frame.setSize(700, 500);
		//frame.setExtendedState(frame.MAXIMIZED_BOTH);  
		frame.setVisible(true);
	}


	private void listenerTecla() {
		// TODO Auto-generated method stub
		texto.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_CONTROL){
					teclaControl = false;
				}
			}
			
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_CONTROL){
					teclaControl = true;
				}
				else{
					if(teclaControl && e.getKeyCode() == KeyEvent.VK_SPACE){
						teclaControl = false;
						int index = opciones.getSelectedIndex();
						if(index == 0){
							completarClase();
						}
						else{
							completarCaso();
						}
						
						
					}
				}
				
			}
		});
	}
	private void completarClase(){
		int pos = texto.getCaretPosition();
		String text = texto.getText();
		text = text.replaceAll("\r", "");
		text = text.replace(" ", " ");
		int posA = pos - 1;
        while (posA >= 0) {
        	//System.out.println(posA);
        	//System.out.println(text.length());
            if (String.valueOf(text.charAt(posA)).matches("<")) {
                break;
            }
            else if (String.valueOf(text.charAt(posA)).matches("\n")) {
            	posA = -2;
                break;
            }
            posA--;
        }
        posA++;
        
        int posB = pos ;
        while (posB < text.length()) {
            if (String.valueOf(text.charAt(posB)).matches("\n")) {
            	//posB--;
                break;
            }
            posB++;
        }
        
        
        //System.out.println(posA + "/" + posB);
        if(posA >= 0 && posB >= 0){
        	String linea = text.substring(posA, posB);
        	
        	String lineaOriginal = linea;
        	linea = linea.replaceAll("\\s", "");
        	//System.out.println(linea);
        	//System.out.println(lineaOriginal);
        	if(linea.length()>1){
        		String p1 = "att";
        		String p2 = "method";
        		String p3 = "param";
        		String p4 = "connection";
        		String p5 = "ClassDiagram";
        		StyleContext cont = StyleContext.getDefaultStyleContext();
				AttributeSet attrNegro = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
				javax.swing.text.Document doc = texto.getDocument();
        		
        		if(p1.contains(linea)){
        			try {
        				doc.remove(posA, lineaOriginal.length());
						doc.insertString(posA, "att name = \"\" type = \"\" visibility = \"\" />", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p2.contains(linea)){
        			try {
        				doc.remove(posA, lineaOriginal.length());
						doc.insertString(posA, "method name = \"\" type = \"\" >\n</method>", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p3.contains(linea)){
        			try {
        				doc.remove(posA, lineaOriginal.length());
        				doc.insertString(posA, "param name = \"\" type = \"\" />", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p4.contains(linea)){
        			try {
        				doc.remove(posA, lineaOriginal.length());
        				doc.insertString(posA, "connection type = \"\" from = \"\" to = \"\" />", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p5.contains(linea)){
        			try {
        				doc.remove(posA, lineaOriginal.length());
        				doc.insertString(posA, "ClassDiagram name = \"\" >\n</ClassDiagram>", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        }
	}
	
	
	private void completarCaso(){
		
		int pos = texto.getCaretPosition();
		String text = texto.getText();
		text = text.replaceAll("\r", "");
		text = text.replaceAll("\r", "");
		int posA = pos - 1;
        while (posA >= 0) {
            if (String.valueOf(text.charAt(posA)).matches("<")) {
                break;
            }
            else if (String.valueOf(text.charAt(posA)).matches("\n")) {
            	posA = -2;
                break;
            }
            posA--;
        }
        posA++;
        
        int posB = pos ;
        while (posB < text.length()) {
            if (String.valueOf(text.charAt(posB)).matches("\n")) {
            	//posB--;
                break;
            }
            posB++;
        }
        
        
        //System.out.println(posA + "/" + posB);
        if(posA >= 0 && posB >= 0){
        	String linea = text.substring(posA, posB);
        	
        	String lineaOriginal = linea;
        	linea = linea.replaceAll("\\s", "");
        	//System.out.println(linea);
        	//System.out.println(lineaOriginal);
        	if(linea.length()>1){
        		String p1 = "actor";
        		String p2 = "usecase";
        		String p3 = "connection";
        		String p4 = "UseCaseDiagram";
        		StyleContext cont = StyleContext.getDefaultStyleContext();
				AttributeSet attrNegro = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        		
        		if(p1.contains(linea)){
        			javax.swing.text.Document doc = texto.getDocument();
        			try {
        				doc.remove(posA, lineaOriginal.length());
						doc.insertString(posA, "actor type = \"\" id = \"\" name = \"\" />", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p2.contains(linea)){
        			javax.swing.text.Document doc = texto.getDocument();
        			try {
        				doc.remove(posA, lineaOriginal.length());
						doc.insertString(posA, "usecase id = \"\" name = \"\" />", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p3.contains(linea)){
        			javax.swing.text.Document doc = texto.getDocument();
        			try {
        				doc.remove(posA, lineaOriginal.length());
        				doc.insertString(posA, "connection type = \"\" from = \"\" to = \"\" />", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		else if(p4.contains(linea)){
        			javax.swing.text.Document doc = texto.getDocument();
        			try {
        				doc.remove(posA, lineaOriginal.length());
        				doc.insertString(posA, "UseCaseDiagram name = \"\" >\n</UseCaseDiagram>", attrNegro);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        }
        
	}


	private void cargarTabs() {
		//inicializa panel
		paneles = new JTabbedPane();
		
		tabCodigo = new JPanel();
		//tabImagen = new JPanel();
        //ImageIcon iconoDiagrama = new ImageIcon("res/diagrama.png");
        ImageIcon iconoCodigo = new ImageIcon("res/codigo.png");
        
        //tabCodigo.setLayout(new BorderLayout());
        tabCodigo.setLayout(new GridBagLayout());
        //tabImagen.setLayout(new GridBagLayout());
        
        tabCodigo.setFocusable(false);
        //tabImagen.setFocusable(false);
        paneles.setFocusable(false);
        
        paneles.addTab("XML", iconoCodigo, tabCodigo, "Pestaña de codigo XML");
        //paneles.addTab("Diagrama", iconoDiagrama, tabImagen, "Pestaña de diagrama UML");
        frame.add(paneles);
        
        /*
        ChangeListener changeListener = new ChangeListener() {
        	public void stateChanged(ChangeEvent changeEvent) {
        		JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
        		int index = sourceTabbedPane.getSelectedIndex();
        		//System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
        		//System.out.println(index);
        		int index2 = opciones.getSelectedIndex();

        		if(index ==1 && index2==0){//elige clase
        			tabImagen.removeAll();
        			//generarDiagramaClase();
        			generarClaseEnTab();
        		}
        		else if(index==1 && index2==1){//elige caso
        			tabImagen.removeAll();       
        			try {
						generarCasoEnTab();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	}
        };
        
        paneles.addChangeListener(changeListener);
	*/
	}
	

	private void cargarEditorTexto() {
		//texto = new JTextPane(resaltado());
		texto = new JTextPane(sintaxis());
		scroll = new JScrollPane(texto);
		texto.setFont(new Font(Font.MONOSPACED, 0, 12));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;	
		gbc.insets = new Insets(5,5,5,2);
		
		tabCodigo.add(scroll,gbc);
		
		texto.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				estaGuardado = false;
			}
			
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				estaGuardado = false;
			}
			
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				estaGuardado = false;
			}
		});
		
	}

	private void cargarHerramientas() {
		
		herramientas = new JToolBar();
		//Carga los iconos
		ImageIcon iconoGuardar = new ImageIcon("res/guardar.png");
		ImageIcon iconoGuardarComo = new ImageIcon("res/guardarComo.png");
		ImageIcon iconoAbrir = new ImageIcon("res/abrir.png");
		ImageIcon iconoImagen = new ImageIcon("res/imagen.png");
		
		//Crea botones con los iconos
		JButton bGuardar = new JButton(iconoGuardar);
		JButton bGuardarComo = new JButton(iconoGuardarComo);
		JButton bAbrir = new JButton(iconoAbrir);
		JButton bImagen = new JButton(iconoImagen);
		
		//Implementamos los listener
		bGuardar.addActionListener(
			listenerBotonGuardar()
		);
		bGuardarComo.addActionListener(
			listenerBotonGuardarComo()
		);
		bAbrir.addActionListener(
			listenerBotonAbrir()
		);
		bImagen.addActionListener(
			listenerbotonImagen()
		);
		
		
		//Setea los tooltip
		bGuardar.setToolTipText("Guardar");
		bGuardarComo.setToolTipText("Guardar Como");
		bAbrir.setToolTipText("Abrir archivo");
		bImagen.setToolTipText("Crear imagen");
		
		//Setea los margenes entre los botones
		bGuardar.setMargin(new Insets(6,6,6,6));
		bGuardarComo.setMargin(new Insets(6,6,6,6));
		bAbrir.setMargin(new Insets(6,6,6,6));
		bImagen.setMargin(new Insets(6,6,6,6));
		
		//Quita el focus
		bGuardar.setFocusable(false);
		bGuardarComo.setFocusable(false);
		bAbrir.setFocusable(false);
		bImagen.setFocusable(false);
		
		//Crea el combobox
		String[] opt = {"Diagrama de clases", "Diagrama de caso"};
		opciones = new JComboBox(opt);
		opciones.setMaximumSize(new Dimension(150, 22));
		opciones.setFocusable(false);
		
		herramientas.add(bGuardar);
		herramientas.add(bGuardarComo);
		herramientas.add(bAbrir);
		herramientas.add(bImagen);
		
		herramientas.add(opciones);
		

		frame.add(herramientas, BorderLayout.PAGE_START);
		
		
	}

	
	private ActionListener listenerbotonImagen() {
		return new ActionListener() {
			
			//@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int index = opciones.getSelectedIndex();
				if(index == 0){
					//generarDiagramaClase();
					ventanaClase VC = new ventanaClase(texto.getText());
				}
				else if(index == 1){
					ventanaCaso VC = new ventanaCaso(texto.getText());
				}
				
				
			}
		};
		
	}



	private ActionListener listenerBotonAbrir() {
		return new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {
				//Filechooser, para elegir el archivo
				try{
					JFileChooser fc = new JFileChooser();
					File archivo = null;
					//Si acepta un archivo, lo carga en un stream
					if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
						/*
			        	archivo= fc.getSelectedFile();
			        	String ruta = archivo.getAbsolutePath();
		        		FileReader lector = new FileReader(ruta);
		        		BufferedReader br = new BufferedReader(lector);
		        		//añade el contenido al textarea, borrando lo anterior
		        		texto.read(br, null);
		        		texto.setText(texto.getText().replace("Â", " ")); //reemplazar caracter raro (ascii 194)
		        		texto.setText(texto.getText().replace(" ", " ")); //reemplazar caracter raro (ascii 160)
		        		br.close();
		        		lector.close();
		        		*/
						archivo= fc.getSelectedFile();
						String text = new Scanner(archivo).useDelimiter("\\A").next();
						text = text.replace("Â", " ");
						text = text.replace(" ", " ");
						//System.out.println(text);
						
						javax.swing.text.Document doc = texto.getDocument();
						try {
							doc.remove(0, doc.getLength());
							doc.insertString(0, text, null);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	
					}else {
						System.out.println("No selecciono archivo");
					}
				}
				catch(Exception er){
	    			er.printStackTrace();
	    		}
			}
		};
	}

	private ActionListener listenerBotonGuardarComo() {
		return new ActionListener() {
			//@Override
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc = new JFileChooser();
					if(archivoGuardado != null){
						//me recomienda la ultima ubicacion guardada
						fc.setSelectedFile(archivoGuardado);
					}
					else{
						fc.setSelectedFile(new File("archivo.xml"));
					}
					//archivoGuardado = null;
					
					if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)){
					//Si selecciona donde guardar
						archivoGuardado = fc.getSelectedFile();
						if(archivoGuardado.exists()){
						//si el archivo ya existia, pregunta si lo sobre escribe
							int respuesta = JOptionPane.NO_OPTION;
							String msje = archivoGuardado.getName()+" ya existe.\n¿Desea reemplazarlo?";
							respuesta = JOptionPane.showConfirmDialog(null, msje, "Archivo existente", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
							if(respuesta == JOptionPane.YES_OPTION){
							//Si decide que si, lo sobreescribe
								FileWriter fw = new FileWriter(archivoGuardado);
								BufferedWriter bw = new BufferedWriter(fw);
								texto.write(bw);
								fw.close();
								bw.close();
								estaGuardado = true;
							}
							else if(respuesta == JOptionPane.NO_OPTION || respuesta == JOptionPane.CLOSED_OPTION){
							//Si elige que no o cierra la ventanita, reinicia archivoGuardado
								archivoGuardado = null;
							}
						}
						else{
						//si no existia, escribe donde eligio el usuario
							FileWriter fw = new FileWriter(archivoGuardado);
							BufferedWriter bw = new BufferedWriter(fw);
							texto.write(bw);
							fw.close();
							bw.close();
							estaGuardado = true;
						}
					}
					else {
						System.out.println("No selecciono donde guardar");
					}
	
				}
				catch(Exception er){
	    			er.printStackTrace();
	    		}
			}
		};
	}

	private ActionListener listenerBotonGuardar() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(archivoGuardado == null){
						//Si el archivo no estaba guardado, pregunta donde guardarlo
						JFileChooser fc = new JFileChooser();
						fc.setSelectedFile(new File("archivo.xml"));
						archivoGuardado = null;
						
						if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)){
						//Si selecciona donde guardar
							archivoGuardado = fc.getSelectedFile();
							if(archivoGuardado.exists()){
							//si el archivo ya existia, pregunta si lo sobre escribe
								int respuesta = JOptionPane.NO_OPTION;
								String msje = archivoGuardado.getName()+" ya existe.\n¿Desea reemplazarlo?";
								respuesta = JOptionPane.showConfirmDialog(null, msje, "Archivo existente", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
								if(respuesta == JOptionPane.YES_OPTION){
								//Si decide que si, lo sobreescribe
									FileWriter fw = new FileWriter(archivoGuardado);
									BufferedWriter bw = new BufferedWriter(fw);
									texto.write(bw);
									fw.close();
									bw.close();
									estaGuardado = true;
								}
								else if(respuesta == JOptionPane.NO_OPTION || respuesta == JOptionPane.CLOSED_OPTION){
								//Si elige que no o cierra la ventanita, reinicia archivoGuardado
									archivoGuardado = null;
								}
							}
							else{
							//si no existia, escribe donde eligio el usuario
								FileWriter fw = new FileWriter(archivoGuardado);
								BufferedWriter bw = new BufferedWriter(fw);
								texto.write(bw);
								fw.close();
								bw.close();
								estaGuardado = true;
							}
						}
						else {
							System.out.println("No selecciono donde guardar");
						}
					}
					else if(archivoGuardado != null){
						//Si el archivo ya estaba guardado, sobreescribe
						FileWriter fw = new FileWriter(archivoGuardado);
						BufferedWriter bw = new BufferedWriter(fw);
						texto.write(bw);
						fw.close();
						bw.close();
						estaGuardado = true;
					}
	
				}
				catch(Exception er){
	    			er.printStackTrace();
	    		}
				
			}
		};
	}

	/*
	
	public void generarDiagramaClase(){
		String t = texto.getText();
		t = t.replace(" ", " "); //reemplazar caracter raro (ascii 160)
		UmlClase uml = lector.leerXMLClase(t);
		
		uml.ordenarDiagramaClases();
		
		try{
			CrearDC diag = new CrearDC(uml);
			diag.CrearClase();
			
			JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("diagramaClases.png"));
			if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)){
				File ubicacion = fc.getSelectedFile();
				diag.Finalizar(ubicacion);
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void generarClaseEnTab(){
		String t = texto.getText();
		t = t.replace(" ", " "); //reemplazar caracter raro (ascii 160)
		UmlClase uml = lector.leerXMLClase(t);
		
		uml.ordenarDiagramaClases();

		CrearDC diag = new CrearDC(uml);
		
		
		try{
			diag.CrearClase();
			diag.CrearConexiones();
			ImageIcon iconTab = diag.FinalizarIcon();
			JLabel picLabel = new JLabel(iconTab);
			
			JScrollPane scrollImagen = new JScrollPane(picLabel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.gridheight = 2;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.fill = GridBagConstraints.BOTH;	
			gbc.insets = new Insets(5,5,5,2);
			
			
			tabImagen.add(scrollImagen,gbc);
			
			tabImagen.setBackground(Color.white);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void generarDiagramaCaso() throws IOException{
		String t = texto.getText();
		t = t.replace(" ", " "); //reemplazar caracter raro (ascii 160)
		UmlCaso uml = lector.leerXMLCaso(t);
		
		uml.OrdenarActores();
		uml.ordenarUserCases();
		
		CrearCaso diag = new CrearCaso(uml.getNombreDiagrama(), uml);
		
		try {
			diag.CrearUsers();
			diag.CrearCasos();
			diag.CrearConexiones();
			
			JFileChooser fc = new JFileChooser();
			fc.setSelectedFile(new File("diagramaCaso.png"));
			if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)){
				File ubicacion = fc.getSelectedFile();
				diag.Finalizar(ubicacion);
			}
			
			//diag.Finalizar();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public void generarCasoEnTab() throws IOException{
		String t = texto.getText();
		t = t.replace(" ", " "); //reemplazar caracter raro (ascii 160)
		UmlCaso uml = lector.leerXMLCaso(t);
		
		uml.OrdenarActores();
		uml.ordenarUserCases();
		
		CrearCaso diag = new CrearCaso(uml.getNombreDiagrama(), uml);
		
		try {
			diag.CrearCasos();
			diag.CrearUsers();
			diag.CrearConexiones();
			ImageIcon iconTab = diag.FinalizarIcon();
			JLabel picLabel = new JLabel(iconTab);
			
			JScrollPane scrollImagen = new JScrollPane(picLabel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.gridheight = 2;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.fill = GridBagConstraints.BOTH;	
			gbc.insets = new Insets(5,5,5,2);
			
			
			tabImagen.add(scrollImagen,gbc);
			
			tabImagen.setBackground(Color.white);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	*/
	
	private DefaultStyledDocument sintaxis(){
		StyleContext cont = StyleContext.getDefaultStyleContext();
		final AttributeSet attrRed    = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
		final AttributeSet attrBlack  = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
		final AttributeSet attrBlue   = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
		
		DefaultStyledDocument doc = new DefaultStyledDocument() {
			public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
				
				String asd = str.replaceAll("\r", "");
				
                super.insertString(offset, asd, a);
                
                String text = getText(0, getLength());
                int largo = getLength();
                setCharacterAttributes(0, largo, attrBlack, false);
                
                int index = opciones.getSelectedIndex();
				if(index == 0){
					//Resalta en clases
					resaltar("ClassDiagram",text,attrBlue);
	                resaltarSymbol("<",text,attrBlue);
	                resaltarSymbol(">",text,attrBlue);
	                
	                resaltar("class",text,attrBlue);
	                resaltar("attributes",text,attrBlue);
	                resaltar("att",text,attrBlue);
	                
	                resaltar("methods",text,attrBlue);
	                resaltar("method",text,attrBlue);
	                resaltar("param",text,attrBlue);
	                resaltar("connection",text,attrBlue);
	                resaltar("connections",text,attrBlue);
	                
	                resaltar("id",text,attrRed);
	                resaltar("name",text,attrRed);
	                resaltar("type",text,attrRed);
	                resaltar("to",text,attrRed);
	                resaltar("from",text,attrRed);
	                resaltar("visibility",text,attrRed);
					
				}
				else if(index == 1){
					//Resalta casos
					resaltar("UseCaseDiagram",text,attrBlue);
	                resaltarSymbol("<",text,attrBlue);
	                resaltarSymbol(">",text,attrBlue);
	                
	                resaltar("usecases",text,attrBlue);
	                resaltar("actors",text,attrBlue);
	                resaltar("connections",text,attrBlue);
	                
	                resaltar("usecase",text,attrBlue);
	                resaltar("actor",text,attrBlue);
	                resaltar("connection",text,attrBlue);
	                
	                resaltar("id",text,attrRed);
	                resaltar("name",text,attrRed);
	                resaltar("type",text,attrRed);
	                resaltar("to",text,attrRed);
	                resaltar("from",text,attrRed);
	                
	                
				}
   
			}
			public void remove (int offs, int len) throws BadLocationException {
				
                super.remove(offs, len);

                String text = getText(0, getLength());
                int largo = getLength();
                setCharacterAttributes(0, largo, attrBlack, false);
                
                int index = opciones.getSelectedIndex();
				if(index == 0){
					//Resalta en clases
					resaltar("ClassDiagram",text,attrBlue);
	                resaltarSymbol("<",text,attrBlue);
	                resaltarSymbol(">",text,attrBlue);
	                
	                resaltar("class",text,attrBlue);
	                resaltar("attributes",text,attrBlue);
	                resaltar("att",text,attrBlue);
	                
	                resaltar("methods",text,attrBlue);
	                resaltar("method",text,attrBlue);
	                resaltar("param",text,attrBlue);
	                resaltar("connection",text,attrBlue);
	                resaltar("connections",text,attrBlue);
	                
	                resaltar("id",text,attrRed);
	                resaltar("name",text,attrRed);
	                resaltar("type",text,attrRed);
	                resaltar("to",text,attrRed);
	                resaltar("from",text,attrRed);
	                resaltar("visibility",text,attrRed);
					
				}
				else if(index == 1){
					//Resalta casos
					resaltar("UseCaseDiagram",text,attrBlue);
	                resaltarSymbol("<",text,attrBlue);
	                resaltarSymbol(">",text,attrBlue);
	                
	                resaltar("usecases",text,attrBlue);
	                resaltar("actors",text,attrBlue);
	                resaltar("connections",text,attrBlue);
	                
	                resaltar("usecase",text,attrBlue);
	                resaltar("actor",text,attrBlue);
	                resaltar("connection",text,attrBlue);
	                
	                resaltar("id",text,attrRed);
	                resaltar("name",text,attrRed);
	                resaltar("type",text,attrRed);
	                resaltar("to",text,attrRed);
	                resaltar("from",text,attrRed);
	                
				}
                
            }

			private void resaltar(String input, String text, AttributeSet color) {
				String palabra = input; 
                int pos = text.indexOf(palabra);            
                while(pos >= 0){
                	if((pos-1>=0) && (pos+palabra.length() < text.length())){
                		if(String.valueOf(text.charAt(pos-1)).matches("/") && String.valueOf(text.charAt(pos+palabra.length() )).matches("\\W")){
                			setCharacterAttributes(pos, palabra.length(), color, false);
                		}
                		else if (String.valueOf(text.charAt(pos-1)).matches("\\W") && String.valueOf(text.charAt(pos+palabra.length() )).matches("\\W")){
                			setCharacterAttributes(pos, palabra.length(), color, false);
                		}

                	}
                	pos = text.indexOf(palabra,pos+1);
                }
			}
			private void resaltarSymbol(String input, String text, AttributeSet color) {
				String palabra = input; 
                int pos = text.indexOf(palabra);            
                while(pos >= 0){
                	setCharacterAttributes(pos, palabra.length(), color, false);
                	pos = text.indexOf(palabra,pos+1);
                }
			}
		};
		
		return doc;
	}
}

