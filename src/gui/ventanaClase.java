package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import modeloCasos.CrearCaso;
import modeloClases.Clase;
import modeloClases.CrearDC;
import modeloClases.UmlClase;

public class ventanaClase {
	
	private enum GBC{
		LABELCAJA, LABELX, LABELY,
		POSX,POSY,
		BOTONACTUALIZAR,BOTONGUARDAR,
		IMAGEN
	};
	
	private JFrame frame;
	private String titulo;
	private String texto;
	
	private UmlClase uml;
	
	//Barra de herramientas
	private JLabel cajaSeleccionada;
	private JLabel labelX;
	private JLabel labelY;
	private JTextField posX;
	private JTextField posY;
	
	private JPanel panelImagen;
	JScrollPane scrollImagen;
	
	private Clase claseElegida = null;
	
	JScrollPane scr2=null;
	
	private lectorXML lector;
	
	private ArrayList<Clase> listaClases = new ArrayList<Clase>();
	
	public ventanaClase(String t){
		frame = new JFrame(titulo);
		frame.setLayout(new GridBagLayout());
		lector = new lectorXML();
		texto = t;
		texto = texto.replace(" ", " ");
		
		uml = lector.leerXMLClase(texto);
		uml.ordenarDiagramaClases();
		
		cargarHerramientas();
		generarClase();
		
		frame.setSize(700, 500);
		frame.setVisible(true);
	}
	
	private void generarClase() {
		// TODO Auto-generated method stub

	
	try{
		CrearDC diag = new CrearDC(uml);
		diag.CrearClase();
		ImageIcon iconTab = diag.FinalizarIcon();
		JLabel picLabel = new JLabel(iconTab);
		
		//JScrollPane scrollImagen = new JScrollPane(picLabel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollImagen = new JScrollPane(picLabel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		MouseAdapter ma = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)){
					int a = e.getX();
					int b = e.getY();
					buscarCaja(a, b);
				}
				else if(SwingUtilities.isRightMouseButton(e)){
					int a = e.getX();
					int b = e.getY();
					String c = JOptionPane.showInputDialog(frame,"Ingrese comentario","Soy un comentario",JOptionPane.QUESTION_MESSAGE);
					if(c != null){
						uml.addComentario(new Comentario(c, a, b));
						frame.remove(panelImagen);
						frame.remove(scr2);
						frame.repaint();
						generarClase();
						frame.setVisible(true);
						
					}
					
				}
			}
		};
		picLabel.addMouseListener(ma);

		//----
		panelImagen = new JPanel(new FlowLayout());
		panelImagen.add(scrollImagen);
		//----
		scr2 = new JScrollPane(panelImagen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		frame.add(scr2,getGbc(GBC.IMAGEN));
		
		listaClases = uml.getListaClases();
		
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	public void cargarHerramientas(){
		
		cajaSeleccionada = new JLabel("Clase: ");
		//cajaSeleccionada.setBackground(Color.BLUE);
		//cajaSeleccionada.setSize(200, 500);
		
		
		
		labelX = new JLabel("Coordenada X: ");
		labelY = new JLabel("Coordenada Y: ");
		
		posX = new JTextField(4);
		posY = new JTextField(4);
		KeyAdapter ka = new KeyAdapter() {
			public void keyTyped(KeyEvent e){
				char  c = e.getKeyChar();
				if(! ((c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (Character.isDigit(c)) )){
					e.consume();
				}
			}
		};
		posX.addKeyListener(ka);
		posY.addKeyListener(ka);
		
		
		claseElegida = null;
		
		
		//BOTONES
		ImageIcon iconoGuardar = new ImageIcon("res/guardar.png");
		ImageIcon iconoActualizar = new ImageIcon("res/actualizar.png");
		JButton bGuardar = new JButton(iconoGuardar);
		JButton bActualizar = new JButton(iconoActualizar);
		bGuardar.addActionListener(
				listenerBotonGuardar()
			);
		bActualizar.addActionListener(
				listenerBotonActualizar()
			);
		bGuardar.setFocusable(false);
		bActualizar.setFocusable(false);
		
		//herramientas.add(opcionCaja);
		frame.add(cajaSeleccionada,getGbc(GBC.LABELCAJA));
		frame.add(labelX,getGbc(GBC.LABELX));
		frame.add(posX, getGbc(GBC.POSX));
		frame.add(labelY,getGbc(GBC.LABELY));
		frame.add(posY, getGbc(GBC.POSY));
		frame.add(bActualizar, getGbc(GBC.BOTONACTUALIZAR));
		frame.add(bGuardar, getGbc(GBC.BOTONGUARDAR));
		
		posX.setSize(50,18);
		posX.setMinimumSize(new Dimension(50,18));
		posX.setMaximumSize(new Dimension(50,18));
		
		posY.setSize(50,18);
		posY.setMinimumSize(new Dimension(50,18));
		posY.setMaximumSize(new Dimension(50,18));
		
		
	}

	public void buscarCaja(int A, int B){
		
		Clase selClase = null;
		int X = 0,  Y = 0;
		int xAncho, yAlto;
		for(Clase clase: listaClases){
			X = clase.getPosx();
			Y = clase.getPosy();
			xAncho = X + clase.getAncho();
			yAlto = Y + clase.getAlto();
			if((X < A && A < xAncho) && (Y < B && B < yAlto)){
				selClase = clase;
				claseElegida = selClase;
				break;
			}
		}
		
		if(selClase != null){
			String id = selClase.getId();
			id = selClase.getNombre();
			cajaSeleccionada.setText("Caso: "+id);
			posX.setText(Integer.toString(X));
			posY.setText(Integer.toString(Y));
		}
	}

	private ActionListener listenerBotonActualizar() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					if (claseElegida != null){
						String nombre = claseElegida.getNombre();
						int px = Integer.parseInt(posX.getText());
						int py = Integer.parseInt(posY.getText());
						for (int i=0; i<listaClases.size();i++) {
							if (listaClases.get(i).getNombre().equals(nombre)) 
							{
								listaClases.get(i).setPosx(px);
								listaClases.get(i).setPosy(py);
								break;
							}
						}
						uml.setListaClases(listaClases);
					}				

					frame.remove(panelImagen);
					frame.remove(scr2);
					frame.repaint();
					generarClase();
					frame.setVisible(true);
					


				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}

	private ActionListener listenerBotonGuardar() {
	// TODO Auto-generated method stub
	
		return new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				try{
					CrearDC diag = new CrearDC(uml);
					diag.CrearClase();
					
					JFileChooser fc = new JFileChooser();
					fc.setSelectedFile(new File("diagramaClases.png"));
					if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)){
						File ubicacion = fc.getSelectedFile();
						diag.Finalizar(ubicacion);
					}
					
				}catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}


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
			case LABELCAJA:
				gridx = 0;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 1;
				weighty = 0;
				fill = GridBagConstraints.HORIZONTAL;
				insets = new Insets(5,5,5,5);
				break;
			case LABELX:
				gridx = 1;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(5,5,5,5);
				break;
			case POSX:
				gridx = 2;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(5,5,5,5);
				break;
			case LABELY:
				gridx = 3;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(5,5,5,5);
				break;
			case POSY:
				gridx = 4;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(5,5,5,5);
				break;
			case BOTONACTUALIZAR:
				gridx = 5;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(5,5,5,5);
				break;
			case BOTONGUARDAR:
				gridx = 6;
				gridy = 0;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				fill = GridBagConstraints.NONE;
				insets = new Insets(5,5,5,5);
				break;
			case IMAGEN:
				gridx = 0;
				gridy = 2;
				gridwidth = 7;
				gridheight = 1;
				weightx = 1;
				weighty = 1;
				fill = GridBagConstraints.BOTH;
				insets = new Insets(10, 10, 10, 10);
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
}
