package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import modeloCasos.ActorCaso;
import modeloCasos.CrearCaso;
import modeloCasos.UmlCaso;
import modeloCasos.UsecaseCaso;

public class ventanaCaso {

	private enum GBC {
		LABELCAJA, LABELX, LABELY, POSX, POSY, BOTONACTUALIZAR, BOTONGUARDAR, IMAGEN
	};

	private JFrame frame;
	private String titulo;
	private String texto;

	private UmlCaso uml;

	// Barra de herramientas
	private JLabel cajaSeleccionada;
	private JLabel labelX;
	private JLabel labelY;

	private JTextField posX;
	private JTextField posY;

	private UsecaseCaso casoElegido;
	private ActorCaso actorElegido;

	
	private JPanel panelImagen;
	

	private lectorXML lector;

	private ArrayList<UsecaseCaso> listaCasos = new ArrayList<UsecaseCaso>();
	private ArrayList<ActorCaso> listaActores = new ArrayList<ActorCaso>();
	
	JScrollPane scrollImagen=null;
	
	JScrollPane scr2=null;

	public ventanaCaso(String t) {

		frame = new JFrame(titulo);
		frame.setLayout(new GridBagLayout());
		lector = new lectorXML();
		texto = t;
		texto = texto.replace(" ", " ");

		cargarHerramientas();
		uml = lector.leerXMLCaso(texto);
		uml.OrdenarActores();
		uml.ordenarUserCases();
		generarCaso();

		frame.setSize(700, 500);
		frame.setVisible(true);
	}

	public void cargarHerramientas() {

		cajaSeleccionada = new JLabel("Caja: ");
		// cajaSeleccionada.setBackground(Color.BLUE);
		// cajaSeleccionada.setSize(200, 500);

		labelX = new JLabel("Coordenada X: ");
		labelY = new JLabel("Coordenada Y: ");

		posX = new JTextField(4);
		posY = new JTextField(4);

		KeyAdapter ka = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c == KeyEvent.VK_BACK_SPACE)
						|| (c == KeyEvent.VK_DELETE) || (Character.isDigit(c)))) {
					e.consume();
				}
			}
		};
		posX.addKeyListener(ka);
		posY.addKeyListener(ka);

		casoElegido = null;
		actorElegido = null;

		//BOTONES
		ImageIcon iconoGuardar = new ImageIcon("res/guardar.png");
		ImageIcon iconoActualizar = new ImageIcon("res/actualizar.png");
		JButton bGuardar = new JButton(iconoGuardar);
		JButton bActualizar = new JButton(iconoActualizar);
		bGuardar.addActionListener(listenerBotonGuardar());
		bActualizar.addActionListener(listenerBotonActualizar());
		bGuardar.setFocusable(false);
		bActualizar.setFocusable(false);

		// herramientas.add(opcionCaja);
		frame.add(cajaSeleccionada, getGbc(GBC.LABELCAJA));
		frame.add(labelX, getGbc(GBC.LABELX));
		frame.add(posX, getGbc(GBC.POSX));
		frame.add(labelY, getGbc(GBC.LABELY));
		frame.add(posY, getGbc(GBC.POSY));
		frame.add(bActualizar, getGbc(GBC.BOTONACTUALIZAR));
		frame.add(bGuardar, getGbc(GBC.BOTONGUARDAR));

		posX.setSize(50, 18);
		posX.setMinimumSize(new Dimension(50, 18));
		posX.setMaximumSize(new Dimension(50, 18));

		posY.setSize(50, 18);
		posY.setMinimumSize(new Dimension(50, 18));
		posY.setMaximumSize(new Dimension(50, 18));

	}

	public void generarCaso() {

		try {
			CrearCaso diag = new CrearCaso(uml.getNombreDiagrama(), uml);
			diag.CrearUsers();
			diag.CrearCasos();
			diag.CrearConexiones();
			ImageIcon iconTab = diag.FinalizarIcon();
			JLabel picLabel = new JLabel(iconTab);
			scrollImagen = new JScrollPane(picLabel,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

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
							System.out.println(c);
							uml.addComentario(new Comentario(c, a, b));
							
						}
						
					}
					
				}

			};
			// scrollImagen.addMouseListener(ma);
			picLabel.addMouseListener(ma);
			//----
			panelImagen = new JPanel(new FlowLayout());
			panelImagen.add(scrollImagen);
			//----

			scr2 = new JScrollPane(panelImagen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			frame.add(scr2,getGbc(GBC.IMAGEN));
		

			listaCasos = uml.getListaCasos();
			listaActores = uml.getListaActores();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ActionListener listenerBotonActualizar() {
		// TODO Auto-generated method stub

		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					if (casoElegido != null){
						
						String nombre = casoElegido.getName();
						int px = Integer.parseInt(posX.getText());
						int py = Integer.parseInt(posY.getText());
						for (int i=0; i<listaCasos.size();i++) {
							if (listaCasos.get(i).getName().equals(nombre)) 
							{
								listaCasos.get(i).setPosx(px);
								listaCasos.get(i).setPosy(py);
								break;
							}
						}
						uml.setListaCasos(listaCasos);
					}				
					
					
					if(actorElegido!=null)
					{
						String nombre = actorElegido.getName();
						int px = Integer.parseInt(posX.getText());
						int py = Integer.parseInt(posY.getText());
						for (int i=0; i<listaActores.size();i++) {
							if (listaActores.get(i).getName().equals(nombre)) 
							{
								listaActores.get(i).setPosx(px);
								listaActores.get(i).setPosy(py);
								break;
							}
						}
						uml.setListaActores(listaActores);
						
					}
					
					frame.remove(panelImagen);
					frame.remove(scr2);
					frame.repaint();
					generarCaso();
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

				try {
					CrearCaso diag = new CrearCaso(uml.getNombreDiagrama(), uml);
					diag.CrearUsers();
					diag.CrearCasos();
					diag.CrearConexiones();

					JFileChooser fc = new JFileChooser();
					fc.setSelectedFile(new File("diagramaCaso.png"));
					if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(null)) {
						File ubicacion = fc.getSelectedFile();
						diag.Finalizar(ubicacion);
					}

					// diag.Finalizar();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}

	public void buscarCaja(int A, int B) {
		System.out.println(A + " - " + B);
		UsecaseCaso selCaso = null;
		ActorCaso selActor = null;
		int X = 0, Y = 0;
		int xAncho, yAlto;
		for (UsecaseCaso caso : listaCasos) {
			X = caso.getPosx();
			Y = caso.getPosy();
			xAncho = X + 173;
			yAlto = Y + 127;
			if ((X < A && A < xAncho) && (Y < B && B < yAlto)) {
				selCaso = caso;
				casoElegido = selCaso;
				actorElegido = null;

				break;
			}
		}
		if (selCaso == null) {
			for (ActorCaso actor : listaActores) {
				X = actor.getPosx();
				Y = actor.getPosy();
				xAncho = X + 73;
				yAlto = Y + 150;
				if ((X < A && A < xAncho) && (Y < B && B < yAlto)) {
					selActor = actor;

					actorElegido = selActor;
					casoElegido = null;
					break;
				}
			}
		}
		if (selCaso != null) {
			String id = selCaso.getId();
			id = selCaso.getName();
			cajaSeleccionada.setText("Caso: " + id);
			posX.setText(Integer.toString(X));
			posY.setText(Integer.toString(Y));

		} else {
			if (selActor != null) {
				String id = selActor.getId();
				id = selActor.getName();
				cajaSeleccionada.setText("Actor: " + id);
				posX.setText(Integer.toString(X));
				posY.setText(Integer.toString(Y));
			}
		}

	}

	// metodo que retorna las posiciones de los elementos graficos
	public GridBagConstraints getGbc(GBC tipo) {
		// El GBC que retornara
		GridBagConstraints gbc = new GridBagConstraints();
		int gridx = 0;
		int gridy = 0;
		int gridwidth = 0;
		int gridheight = 0;
		float weightx = 0;
		float weighty = 0;
		int fill = GridBagConstraints.NONE;
		Insets insets = new Insets(0, 0, 0, 0);
		switch (tipo) {
		case LABELCAJA:
			gridx = 0;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 1;
			weighty = 0;
			fill = GridBagConstraints.HORIZONTAL;
			insets = new Insets(5, 5, 5, 5);
			break;
		case LABELX:
			gridx = 1;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 0;
			weighty = 0;
			fill = GridBagConstraints.NONE;
			insets = new Insets(5, 5, 5, 5);
			break;
		case POSX:
			gridx = 2;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 0;
			weighty = 0;
			fill = GridBagConstraints.NONE;
			insets = new Insets(5, 5, 5, 5);
			break;
		case LABELY:
			gridx = 3;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 0;
			weighty = 0;
			fill = GridBagConstraints.NONE;
			insets = new Insets(5, 5, 5, 5);
			break;
		case POSY:
			gridx = 4;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 0;
			weighty = 0;
			fill = GridBagConstraints.NONE;
			insets = new Insets(5, 5, 5, 5);
			break;
		case BOTONACTUALIZAR:
			gridx = 5;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 0;
			weighty = 0;
			fill = GridBagConstraints.NONE;
			insets = new Insets(5, 5, 5, 5);
			break;
		case BOTONGUARDAR:
			gridx = 6;
			gridy = 0;
			gridwidth = 1;
			gridheight = 1;
			weightx = 0;
			weighty = 0;
			fill = GridBagConstraints.NONE;
			insets = new Insets(5, 5, 5, 5);
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
