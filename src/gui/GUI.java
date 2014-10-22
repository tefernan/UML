package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;


public class GUI {
	
	private enum GBC{
		BOTONCARGAR, BOTONGUARDAR, BOTONIMAGEN,
		OPCIONES,
		ETIQUETA,
		TEXTO
	};
	
	private JFrame frame;
	private String titulo;
	
	
	//Barra de herramientas
	private JButton botonGuardar;
	private JButton botonCargar;
	private JButton botonImagen;//salto el boton 3 por si hay que agregar algo al medio despues(etiqueta por mientras)
	private JLabel etiqueta;
	private JComboBox opciones;
	
	//Editor de texto
	private JTextPane texto;
	private JScrollPane scroll;
	
	public GUI(String titulo){
		
		this.titulo = titulo;
		//Crea la ventana y usamos un grid bag layout
		frame = new JFrame(titulo);
		frame.setLayout(new GridBagLayout());	
		
		//Se cierra si se hace click en exit
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Creamos botones e interfaz
		crearBotones();
		crearElementosInterfaz();
		
		//Añadimos la barra de herramientas
		frame.add(botonGuardar,getGbc(GBC.BOTONGUARDAR));
		frame.add(botonCargar,getGbc(GBC.BOTONCARGAR));
		frame.add(opciones, getGbc(GBC.OPCIONES));
		frame.add(etiqueta,getGbc(GBC.ETIQUETA));
		frame.add(botonImagen,getGbc(GBC.BOTONIMAGEN));
		frame.add(scroll,getGbc(GBC.TEXTO));
		
		frame.setSize(700, 500);
		frame.getContentPane().setBackground(new Color(61, 61, 61));
		frame.setVisible(true);
	
	}
	
	//Metodo para crear los botones de la barra de herramientas
	public void crearBotones(){
		//Crea los botones
		ImageIcon iconoGuardar = new ImageIcon("diskette.png");
		ImageIcon iconoCargar = new ImageIcon("file.png");
		ImageIcon iconoDibujar = new ImageIcon("pencil.png");
		
		botonGuardar = new JButton(iconoGuardar);
		botonCargar = new JButton(iconoCargar);
		botonImagen = new JButton(iconoDibujar);
		
		
		//Implementamos los listener
		botonGuardar.addActionListener(
			listenerBotonGuardar()
		);
				
		botonCargar.addActionListener(
				listenerBotonCargar()
		);
		botonImagen.addActionListener(
				listenerBotonImagen()
		);
		
		//Convierte los botones en transparentes
		botonGuardar.setOpaque(false);
		botonGuardar.setContentAreaFilled(false);
		botonGuardar.setBorderPainted(false);
		
		botonCargar.setOpaque(false);
		botonCargar.setContentAreaFilled(false);
		botonCargar.setBorderPainted(false);
		
		botonImagen.setOpaque(false);
		botonImagen.setContentAreaFilled(false);
		botonImagen.setBorderPainted(false);
		
		//Quita el focus
		botonGuardar.setFocusable(false);
		botonCargar.setFocusable(false);
		botonImagen.setFocusable(false);
	}
	
	@SuppressWarnings("unchecked")
	public void crearElementosInterfaz(){
		//crea el label vacio para dar espacio
		etiqueta = new JLabel("");
		
		//creamos el menu el el combobox
		String[] opt = {"Diagrama de clases", "Diagrama de caso"};
		opciones = new JComboBox(opt);
		opciones.setUI(ColorArrowUI.createUI(opciones));

		opciones.setRenderer(new DefaultListCellRenderer(){
			public void paint(Graphics g) {
		        setBackground(new Color(61, 61, 61));
		        setForeground(Color.WHITE);
		        super.paint(g);
		    }
		});
		

		//Creamos el editor de texto
		texto = new JTextPane();
		//scroll = new JScrollPane(texto);
		scroll = new JScrollPane(texto);
		
		//Color y letras del texto
		texto.setBackground(new Color(39, 40, 34));
		texto.setFont(new Font(Font.MONOSPACED, 0, 12));
		texto.setForeground(Color.WHITE);
		
		
	}
	// metodo que retorna las posiciones de los elementos graficos
	public GridBagConstraints getGbc(GBC tipo){
		//El GBC que retornara
		GridBagConstraints gbc = new GridBagConstraints();
		int gridx = 0;
		int gridy = 0;
		int gridwidth = 0;
		int gridheight = 0;
		float weightx = 0;
		float weighty = 0;
		int fill = GridBagConstraints.NONE;
		Insets insets = new Insets(0, 0, 0, 0);
		
		switch(tipo){
			case BOTONGUARDAR:
				gridx = 0;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(4, 6, 4, 0);
				break;
			case BOTONCARGAR:
				gridx = 1;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(4, 0, 4, 3);
				break;
			case BOTONIMAGEN:
				gridx = 4;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(4, 6, 4, 6);
				break;
			case OPCIONES:
				gridx = 2;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(4, 6, 4, 6);
				break;
			case ETIQUETA:
				gridx = 3;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 1;
				weighty = 0;
				fill = GridBagConstraints.HORIZONTAL;
				insets = new Insets(4, 6, 4, 6);
				break;
			case TEXTO:
				gridx = 0;
				gridy = 1;
				gridwidth = 5;
				gridheight = 1;
				weightx = 1;
				weighty = 1;
				fill = GridBagConstraints.BOTH;
				insets = new Insets(0,10,10,10);
				break;
		}
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.fill = fill;	
		gbc.insets = insets;
		
		return gbc;
	}
	
	//Lo que hace el boton guardar. Guarda el texto en un archivo
	public ActionListener listenerBotonGuardar(){
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc = new JFileChooser();
					fc.setSelectedFile(new File("archivo.xml"));
					File archivo = null;
					if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)){
						archivo = fc.getSelectedFile();
						System.out.println(archivo.toString());
						FileWriter fw = new FileWriter(archivo);
						BufferedWriter bw = new BufferedWriter(fw);
						texto.write(bw);
						fw.close();
						bw.close();
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
	//lo que hace el boton cargar. Carga un xml en el textarea
	public ActionListener listenerBotonCargar(){
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//Filechooser, para elegir el archivo
				try{
					JFileChooser fc = new JFileChooser();
					File archivo = null;
					//Si acepta un archivo, lo carga en un stream
					if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
			        	archivo= fc.getSelectedFile();
			        	String ruta = archivo.getAbsolutePath();
		        		FileReader lector = new FileReader(ruta);
		        		BufferedReader br = new BufferedReader(lector);
		        		//añade el contenido al textarea, borrando lo anterior
		        		texto.read(br, null);
		        		br.close();
		        		lector.close();
		        	
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
	//Lo que hace el boton 4
	public ActionListener listenerBotonImagen(){
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//JOptionPane.showMessageDialog(frame, "Wasaaaap");
				
				//Aca lo que hace si es diagrama de clases
				if(opciones.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(frame, "diagrama de clases");
				}
				//Aca lo que hace si es diagrama de casos
				else if(opciones.getSelectedIndex() == 1){
					JOptionPane.showMessageDialog(frame, "diagrama de caso");
				}
				
				
			}
		};
	}

}
