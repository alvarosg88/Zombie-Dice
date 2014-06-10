package dadosZombies;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.IOException;

public class DZGame extends JFrame{

	private JPanel contentPane;
	
	//Variables auxiliares
	private int jugadorActual = 0;
	private int tipo, tirada, numCerebros = 0, numDisparos = 0, nj = 0;
	private int imgD = 0, imgC = 0, cuentaCerebros = 0, cuentaHuellas = 0, cuentaDisparos = 0;
	private String numjugadores = null;
	private String imagenDado = null;
	private String[] valores = null;

	/**Expresión regular para los nombres de los jugadores*/
	private String validaUsuario = "^[a-zA-Z0-9_]{3,16}$";
	/**Array de jugadores*/
	private Jugador[] listaJugadores = null;
	
	//Arrays de JLabel
	private JLabel[] zonaDisparos = new JLabel[5];
	private JLabel[] zonaHuellas = new JLabel[3];
	private JLabel[] zonaCerebros = new JLabel[15];
	
	//JLabel de datos de Jugador
	private JLabel lblP = null;
	private JLabel lblJugador = null;
	private  JLabel lblNJ = null;
	
	private JLabel label_23 = null;
	
	private JLabel lblNumCerebros = null;
	private JLabel lblNumDisparos = null;
	
	//Audio
	private Clip sonido1 = null;
	private Clip sonido2 = null;
	
	/** Icono */
	public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("/img/icono0.png"));


        return retValue;
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DZGame frame = new DZGame();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public DZGame() throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {

		final JLabel lblNewLabel_5 = new JLabel(""); //Ocultamos los botones de juego, al principio sólo se podrá
		final JLabel label_22 = new JLabel("");		 //pulsar en "Nueva partida"
		lblNewLabel_5.setVisible(false);
		label_22.setVisible(false);
		
		sonido1 = AudioSystem.getClip(); //Reproducción de música
		sonido1.open(AudioSystem.getAudioInputStream(getClass().getResource(
				"audio/musica.wav")));

		setBackground(Color.DARK_GRAY);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DZGame.class.getResource("/img/icono0.png")));
		setTitle("Dados Zombies v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image icon = new ImageIcon(getClass().getResource("/img/icono0.png")).getImage(); //Carga el icono
		setIconImage(icon);
		setBounds(100, 100, 625, 568);

		//MENU
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAyuda = new JMenu("Menu");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmSobreZombieDice = new JMenuItem("Reglas de juego");
		mntmSobreZombieDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //Con la clase AbrirURL podemos asignar la apertura de enlaces con eventos
				AbrirURL.openURL("http://www.sjgames.com/dice/zombiedice/demo.html");
			}
		});
		
		//Elemento "Nueva Partida del Menu"
		JMenuItem mntmNewMenuItem = new JMenuItem("Nueva partida");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nuevaPartida();
			}
		});
		mnAyuda.add(mntmNewMenuItem);
		mnAyuda.add(mntmSobreZombieDice);

		JMenuItem mntmManual = new JMenuItem("Tutorial");
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbrirURL.openURL("http://www.youtube.com/watch?v=CjmSiplP68U");
			}
		});
		mnAyuda.add(mntmManual);

		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Dados Zombies 1.0\n--------------------------------" +
						"\nAutor: Álvaro Serrano García\nContacto: alvarosg88@gmail.com" +
						"\nZombie Dice© es propiedad de Steve Jackson Games© 2010");
			}
		});
		mnAyuda.add(mntmAcercaDe);
		
		//Botón Salir
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnAyuda.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//ELEMENTOS GRÁFICOS DEL INTERFAZ
		
				lblNumDisparos = new JLabel("0");
				lblNumDisparos.setBounds(140, 458, 19, 50);
				contentPane.add(lblNumDisparos);
				lblNumDisparos.setForeground(Color.RED);
				lblNumDisparos.setFont(new Font("Gulim", Font.BOLD, 16));
		
				JLabel label_21 = new JLabel("");
				label_21.setBounds(95, 451, 40, 57);
				contentPane.add(label_21);
				label_21.setIcon(new ImageIcon(DZGame.class
						.getResource("/img/numDisparos.png")));
		
				lblNumCerebros = new JLabel("0");
				lblNumCerebros.setBounds(45, 458, 19, 50);
				contentPane.add(lblNumCerebros);
				lblNumCerebros.setForeground(Color.ORANGE);
				lblNumCerebros.setFont(new Font("Gulim", Font.BOLD, 16));
		
				JLabel lblNewLabel_4 = new JLabel("");
				lblNewLabel_4.setBounds(10, 451, 33, 57);
				contentPane.add(lblNewLabel_4);
				lblNewLabel_4.setIcon(new ImageIcon(DZGame.class
						.getResource("/img/numCerebros.png")));

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 623, 524);
		contentPane.add(panel);
		panel.setLayout(null);
		
		//Imagen con funcionalidad de botón para realizar una tirada
		lblNewLabel_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) { //Al entrar o salir el cursos de la imagen, ésta cambia
				lblNewLabel_5.setIcon(new ImageIcon(DZGame.class.getResource("/img/tirar2.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_5.setIcon(new ImageIcon(DZGame.class.getResource("/img/tirar1.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				sonidoBoton("audio/dado.wav"); //Reproduce sonido y llama a método al hacer click
				tirada();
			}
		});
		
		//Imagen con funciones de botón "Nueva partida"
		label_23 = new JLabel("");
		label_23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label_23.setIcon(new ImageIcon(DZGame.class.getResource("/img/nuevo02.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_23.setIcon(new ImageIcon(DZGame.class.getResource("/img/nuevo01.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				sonidoBoton("audio/horda2.wav");
				nuevaPartida();
				lblNewLabel_5.setVisible(true); //Al empezar una partida, los botones de Tirar y Siguiente turno 
				label_22.setVisible(true);		// se hacen visibles
			}
		});
		label_23.setIcon(new ImageIcon(DZGame.class.getResource("/img/nuevo01.png")));
		label_23.setBounds(202, 139, 214, 216);
		panel.add(label_23);
		
		//Imagen con funciones de botón "Siguiente turno"
		lblNewLabel_5.setIcon(new ImageIcon(DZGame.class.getResource("/img/tirar1.png")));
		lblNewLabel_5.setBounds(223, 299, 152, 105);
		panel.add(lblNewLabel_5);
		label_22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label_22.setIcon(new ImageIcon(DZGame.class.getResource("/img/sturno2.png")));
			}
			public void mouseExited(MouseEvent e) {
				label_22.setIcon(new ImageIcon(DZGame.class.getResource("/img/sturno1.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				sonidoBoton("audio/grito1.wav");
				cambioJugador(lblP,lblJugador,lblNJ);
			}
		});
		label_22.setIcon(new ImageIcon(DZGame.class.getResource("/img/sturno1.png")));
		label_22.setBounds(205, 416, 198, 92);
		panel.add(label_22);
		
		//ELEMENTOS GRÁFICOS DEL INTERFAZ

		JLabel lblTurnoDe = new JLabel("Turno de:");
		lblTurnoDe.setForeground(Color.RED);
		lblTurnoDe.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTurnoDe.setBackground(Color.GRAY);
		lblTurnoDe.setBounds(9, 0, 90, 61);
		panel.add(lblTurnoDe);

		JLabel label_19 = new JLabel("");
		label_19.setBounds(506, 286, 50, 50);
		panel.add(label_19);

		JLabel label_20 = new JLabel("");
		label_20.setBounds(506, 347, 50, 50);
		panel.add(label_20);

		JLabel label_18 = new JLabel("");
		label_18.setBounds(140, 347, 50, 50);
		panel.add(label_18);

		JLabel label_15 = new JLabel("");
		label_15.setBounds(140, 286, 50, 50);
		panel.add(label_15);

		JLabel label_16 = new JLabel("");
		label_16.setBounds(20, 347, 50, 50);
		panel.add(label_16);

		JLabel label_17 = new JLabel("");
		label_17.setBounds(80, 347, 50, 50);
		panel.add(label_17);

		JLabel label = new JLabel("");
		label.setBounds(20, 286, 50, 50);
		panel.add(label);

		JLabel label_14 = new JLabel("");
		label_14.setBounds(80, 286, 50, 50);
		panel.add(label_14);

		JLabel label_13 = new JLabel("");
		label_13.setIcon(null);
		label_13.setBounds(506, 103, 50, 50);
		panel.add(label_13);

		JLabel label_12 = new JLabel("");
		label_12.setIcon(null);
		label_12.setBounds(506, 164, 50, 50);
		panel.add(label_12);

		JLabel label_11 = new JLabel("");
		label_11.setIcon(null);
		label_11.setBounds(506, 225, 50, 50);
		panel.add(label_11);

		JLabel label_10 = new JLabel("");
		label_10.setIcon(null);
		label_10.setBounds(288, 225, 50, 50);
		panel.add(label_10);

		JLabel label_9 = new JLabel("");
		label_9.setIcon(null);
		label_9.setBounds(288, 164, 50, 50);
		panel.add(label_9);

		JLabel label_8 = new JLabel("");
		label_8.setIcon(null);
		label_8.setBounds(288, 103, 50, 50);
		panel.add(label_8);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(null);
		label_4.setBounds(140, 164, 50, 50);
		panel.add(label_4);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(null);
		label_6.setBounds(80, 225, 50, 50);
		panel.add(label_6);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(null);
		label_1.setBounds(140, 103, 50, 50);
		panel.add(label_1);

		JLabel label_5 = new JLabel("");
		label_5.setIcon(null);
		label_5.setBounds(20, 225, 50, 50);
		panel.add(label_5);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(null);
		label_2.setBounds(20, 164, 50, 50);
		panel.add(label_2);

		JLabel label_3 = new JLabel("");
		label_3.setIcon(null);
		label_3.setBounds(80, 164, 50, 50);
		panel.add(label_3);

		JLabel label_0 = new JLabel("");
		label_0.setIcon(null);
		label_0.setBounds(80, 103, 50, 50);
		panel.add(label_0);

		JLabel label_7 = new JLabel("");
		label_7.setIcon(null);
		label_7.setBounds(140, 225, 50, 50);
		panel.add(label_7);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(null);
		lblNewLabel_3.setBounds(20, 103, 50, 50);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(DZGame.class
				.getResource("/img/contador.png")));
		lblNewLabel_2.setBounds(465, 5, 91, 56);
		panel.add(lblNewLabel_2);

		lblP = new JLabel("0");
		lblP.setForeground(Color.GREEN);
		lblP.setFont(new Font("Dialog", Font.BOLD, 23));
		lblP.setBackground(Color.GRAY);
		lblP.setBounds(566, -2, 33, 61);
		panel.add(lblP);

		lblJugador = new JLabel("");
		lblJugador.setForeground(Color.GREEN);
		lblJugador.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 18));
		lblJugador.setBackground(Color.GRAY);
		lblJugador.setBounds(228, 0, 227, 61);
		panel.add(lblJugador);

		lblNJ = new JLabel("");
		lblNJ.setForeground(Color.ORANGE);
		lblNJ.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 18));
		lblNJ.setBackground(Color.GRAY);
		lblNJ.setBounds(205, 0, 33, 61);
		panel.add(lblNJ);

		//Almacenamos los JLabel en arrays para poder asignar las imágenes de las caras de los dados según el resultado
		//de las tiradas. Ésto se puede ver más claramente en el método tirada()
		
		zonaDisparos[0] = label_13;
		zonaDisparos[1] = label_12;
		zonaDisparos[2] = label_11;
		zonaDisparos[3] = label_19;
		zonaDisparos[4] = label_20;

		zonaHuellas[0] = label_8;
		zonaHuellas[1] = label_9;
		zonaHuellas[2] = label_10;

		zonaCerebros[0] = lblNewLabel_3;
		zonaCerebros[1] = label_0;
		zonaCerebros[2] = label_1;
		zonaCerebros[3] = label_2;
		zonaCerebros[4] = label_3;
		zonaCerebros[5] = label_4;
		zonaCerebros[6] = label_5;
		zonaCerebros[7] = label_6;
		zonaCerebros[8] = label_7;
		zonaCerebros[9] = label;
		zonaCerebros[10] = label_14;
		zonaCerebros[11] = label_15;
		zonaCerebros[12] = label_16;
		zonaCerebros[13] = label_17;
		zonaCerebros[14] = label_18;

		final JLabel lblNewLabel_1 = new JLabel("Jugador");
		lblNewLabel_1.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 18));
		lblNewLabel_1.setBackground(Color.GRAY);
		lblNewLabel_1.setForeground(Color.ORANGE);
		lblNewLabel_1.setBounds(128, 0, 90, 61);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-1, 0, 625, 527);
		lblNewLabel.setIcon(new ImageIcon(DZGame.class.getResource("/img/fondo00.png")));
		panel.add(lblNewLabel);
	}
	
	// METODOS

	/** secuenciaInicio() - Crea nuevos jugadores, tras especificar número y nombres*/
	private void secuenciaInicio() {
		do {
			numjugadores = JOptionPane.showInputDialog(
					"Indica el numero de jugadores de la partida:", null);
			try {
				nj = Integer.parseInt(numjugadores);
				if (nj < 2 || nj > 10) //Controlamos que el número de jugadores esté comprendido entre 2 y 1o
					JOptionPane.showMessageDialog(null,
							"Solo entre 2 y 10 jugadores!!!");
			} catch (NumberFormatException nfe) { 
				//Controlamos con una excepción que no se pueda introducir cualquier valor que no sea numérico
				JOptionPane.showMessageDialog(null, "Valor incorrecto");
			}
		} while (nj < 2 || nj > 10);

		listaJugadores = new Jugador[nj]; //Si el nombre escrito pasa todos los "controles", creamos un nuevo jugador

		try {
			nuevosJugadores();
		} catch (NullPointerException npe) {
			System.exit(0);
		}
	}

	/** nuevosJugadores() - Comprueba que los nombres introducidos cumplen la expresión regular
	 * y que no existen nombres de jugador duplicados*/
	private void nuevosJugadores() {
		RegEx comprueba = new RegEx(validaUsuario); //Creamos una nueva instancia de RegEx, le pasamos el patrón al constructor
		String nuevoNombre = null;
		int existe = 0;
		for (int i = 0; i < nj; i++) {
			do {
				nuevoNombre = JOptionPane.showInputDialog("Nombre de Jugador "
						+ (i + 1) + ": "); //Recogemos un nuevo nombre
				if (!comprueba.check(nuevoNombre)) //Si cumple la expresión regular...
					JOptionPane // ...lo indicamos con un mensaje de error
							.showMessageDialog(null,
									"Introduce un nombre de entre 3 y 16 caracteres alfanuméricos");
					existe = 0; 
				for (int j = 0; j < nj; j++) {
					//Comprobamos que el nombre no está repetido comparando con los demás
					if(listaJugadores[j] != null && listaJugadores[j].getNombre().equals(nuevoNombre))
						existe++; //LLevamos la cuenta de las coincidencias
				}
				if(existe == 1)
					JOptionPane
					.showMessageDialog(null, //Si se repite, notificamos del error
							"El nombre de jugador especificado ya existe");
					
				//El bucle no cesará mientras los nombres introducidos no sean correctos o estén repetidos
			} while (!comprueba.check(nuevoNombre) || existe > 0);
			//Una vez hecho esto, ya podemos crear un nuevo jugador
			listaJugadores[i] = new Jugador(nuevoNombre);
		}
	}

	/** vaciarTodo() - Limpia los paneles de tiradas de la ventana del juego, vaciando todos los JLabel
	 * y reiniciando los valores de la variables que llevan el recuento de las tiradas para el procesamiento
	 * de imágenes */
	private void vaciarTodo() {
		vaciaDisparos();
		vaciaHuellas();
		vaciaCerebros();
		cuentaDisparos = 0;
		cuentaCerebros = 0;
		cuentaHuellas = 0;
	}

	/** Limpia el panel de tiradas de tipo Disparo */
	private void vaciaDisparos() {
		for (int j = 0; j < zonaDisparos.length; j++) {
			zonaDisparos[j].setIcon(null);
		}
	}

	/** Limpia el panel de tiradas de tipo Huella */
	private void vaciaHuellas() {
		for (int j = 0; j < zonaHuellas.length; j++) {
			zonaHuellas[j].setIcon(null);
		}
	}

	/** Limpia el panel de tiradas de tipo Cerebro */
	private void vaciaCerebros() {
		for (int j = 0; j < zonaCerebros.length; j++) {
			zonaCerebros[j].setIcon(null);
		}
	}

	/** tirada() - Realiza una tirada de tres dados */
	private void tirada() {
		for (int i = 0; i < 3; i++) {
			/* Un bucle for hasta 3 que genera un aleatorio entre 1 y 3, es decir: 3 dados cada uno con un 
			 * color aleatorio que especifica la dificultad de la tirada: verde(fácil), amarillo(medio) y rojo(difícil)*/
			tipo = (int) (Math.random() * 3 + 1);
			imgD = tipo;
			switch (tipo) { //Dependiendo del tipo obtenemos un array con las caras del dado
			case 1:
				valores = Dado.VERDE.getValores();
				break;
			case 2:
				valores = Dado.AMARILLO.getValores();
				break;
			case 3:
				valores = Dado.ROJO.getValores();
				break;
			}
			
			//Con un aleatorio entre 1 y 6 obtenemos una cara aleatoria del dado
			tirada = (int) (Math.random() * 6);

			//Dependiendo del resultado, contabilizamos la puntuación del turno
			if (valores[tirada] == "cerebro") {
				numCerebros++;
				imgC = 1;
			} else if (valores[tirada] == "disparo") {
				numDisparos++;
				imgC = 3;
			} else {
				imgC = 2;
			}

			/*Hemos ido almacenando los resultados en variables auxiliares
			 * imgD almacena 1, 2 o 3 dependiendo del color del dado
			 * imgC almacena 1, 2 o 3 dependiendo de la cara del dado
			 * 
			 * Concatenamos los valores en un string, el resultado es el nombre numerico que hemos dado
			 * a las imágenes de las caras de los dados*/
			imagenDado = String.valueOf(imgD + "" + imgC);

			if (imgC == 1) { //Dependiendo de la cara del dado (Cerebro, Huella, Disparo) almacenamos la imagen
							 //aleatoria en una de las tres áreas de JLabel de la ventana
				if (cuentaCerebros >= zonaCerebros.length) { //Si al colocar una imagen el panel ya está lleno, 
															 //lo vaciamos y vuelta a empezar
					vaciaCerebros();
					cuentaCerebros = 0;
				}
				//Especificamos la ruta con el valor concatenado en un elemento del array del panel correspondiente
				//e incrementamos manualmente el contador para ir colocando más imágenes en posiciones nuevas
				zonaCerebros[cuentaCerebros].setIcon(new ImageIcon(DZGame.class
						.getResource("/img/" + imagenDado + ".png")));
				cuentaCerebros++;
			} else if (imgC == 2) {
				if (cuentaHuellas >= zonaHuellas.length) {
					vaciaHuellas();
					cuentaHuellas = 0;
				}
				zonaHuellas[cuentaHuellas].setIcon(new ImageIcon(DZGame.class
						.getResource("/img/" + imagenDado + ".png")));
				cuentaHuellas++;
			} else {
				if (cuentaDisparos >= zonaDisparos.length) {
					vaciarTodo();
				}
				zonaDisparos[cuentaDisparos].setIcon(new ImageIcon(DZGame.class
						.getResource("/img/" + imagenDado + ".png")));
				cuentaDisparos++;
			}

		}
		lblNumCerebros.setText(String.valueOf(numCerebros)); //Mostramos los puntos acumulados en el turno
		lblNumDisparos.setText(String.valueOf(numDisparos));
		
		//Comprobamos al final de cada tirada si el jugador ha conseguido 13 cerebros (gana) o 3 disparos (pierde el turno)
		ganador();
		muerto();
	}

	/** cambioJugador() - Pasa el turno al siguiente jugador */
	private void cambioJugador(JLabel lblP, JLabel lblJugador,
			JLabel lblNJ) {
		//Si en el turno actual el jugador actual no ha hecho nada, no puede pasar al siguiente turno,
		//tiene que tirar al menos una vez
		if(numCerebros == 0 && numDisparos == 0){
			sonido2.stop();
			JOptionPane.showMessageDialog(null, "Debes tirar los dados al menos una vez antes de plantarte");
		}
		else{ //Si se planta...
			listaJugadores[jugadorActual]
					.setPuntuacion(listaJugadores[jugadorActual].getPuntuacion()
							+ numCerebros); 
			//Almacenamos el número de Cerebros obtenidos en el turno al total de puntos del jugador durante la partida

			numCerebros = 0; //Reseteamos las variables auxiliares
			numDisparos = 0;
			lblNumCerebros.setText(String.valueOf("0")); //Reseteamos las puntuaciones del interfaz gráfico
			lblNumDisparos.setText(String.valueOf("0"));

			if (jugadorActual == listaJugadores.length - 1) { //Si ha sido el turno del último jugador, pasamos al primero...
				jugadorActual = 0;
			} else { //... si no, simplemente pasamos al siguiente jugador
				jugadorActual++;
			}
			
			//Mostramos los datos del siguiente jugador en la parte superior del interfaz gráfico
			lblJugador.setText(String.valueOf(listaJugadores[jugadorActual]
					.getNombre()));
			lblNJ.setText(String.valueOf(jugadorActual + 1));
			lblP.setText(String.valueOf(listaJugadores[jugadorActual]
					.getPuntuacion()));

			vaciarTodo(); //Limpiamos los paneles de tiradas para que el siguiente jugador pueda realizar las suyas propias
		}
	}

	/** Comprueba si el jugador actual ha ganado la partida */
	private void ganador() {
		//Si el jugador acumula 13 o más puntos o la suma de sus puntos y de los cerebros obtenidos en su último
		//turno dan como resultado esa cantidad...
		if (listaJugadores[jugadorActual].getPuntuacion() >= 13
				|| listaJugadores[jugadorActual].getPuntuacion() + numCerebros >= 13) {
			lblP.setText(String.valueOf(listaJugadores[jugadorActual]
					.getPuntuacion() + numCerebros)); //Mostramos la suma en el contador final
			Ganador ganador = null; //Instanciamos el JDialog Ganador
			try {
				ganador = new Ganador(jugadorActual, //le pasamos como parametros los datos del jugador actual
						//(el que ha ganado la partida, ya que lo acabamos de comprobar)
						listaJugadores[jugadorActual].getNombre());
				//Eclipse introduce estas excepciones para poder reproducir sonido
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			sonido1.stop(); //Paramos la musica
			ganador.setVisible(true); //Hacemos visible el JDialog
			
			//Añadimos nuevas funciones al botón "Nueva Partida" del JDialog...
			JButton botonNuevaPartida = ganador.getBotonNuevaPartida(); //Obtenemos el botón con get
			botonNuevaPartida.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) { //Añadimos una nueva funcionalidad
					nuevaPartida(); //En el JDialog Ganador, este botón solo cierra su correspondiente ventana
									//Desde aquí le indicamos que, además, inicie una nueva partida después de cerrarse
				}
			});
			ganador.setBotonNuevaPartida(botonNuevaPartida); //Con set enviamos el botón al JDialog con sus nuevas funcionalidades
		}
	}

	/** Comprobamos si despues de tirar, el jugador ha perdido su turno (3 o más Disparos) */
	private void muerto() {
		if (numDisparos >= 3) {
			numCerebros = 0; //Con 3 o más disparos, el jugador pierde los Cerebros acumulados en su turno
			
			//Hacemos lo mismo que con Ganador para mostrar un JDialog con nuevas funcionalidades añadidas a su botón
			Muerto muerto = null;
			try {
				muerto = new Muerto(jugadorActual,
						listaJugadores[jugadorActual].getNombre());
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			muerto.setVisible(true);
			JButton botonOk = muerto.getOkButton();
			botonOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cambioJugador(lblP,lblJugador,lblNJ);
				}
			});
			muerto.setOkButton(botonOk);
		}
	}
	
	/** Especifica la ruta de los ficheros de sonido de los botones "Tirar", "Siguiente Turno" y "Nueva partida" (con excepciones) */
	private void sonidoBoton(String archivoAudio) {
		try {
			sonido2 = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		
		try {
			sonido2.open(AudioSystem.getAudioInputStream(getClass().getResource(archivoAudio)));
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sonido2.start();
	}
	
	/** Comienza una nueva partida */
	private void nuevaPartida() {
		label_23.setVisible(false); //Oculta la imagen con funcionalidad de botón que aparece la primera vez que
									//se abre el programa
		sonido1.stop();
		
		lblP.setText("");
		lblJugador.setText("");
		lblNJ.setText("");
		
		secuenciaInicio();
		
		lblP.setText(String.valueOf(listaJugadores[jugadorActual].getPuntuacion()));
		lblJugador.setText(listaJugadores[jugadorActual].getNombre());
		lblNJ.setText(String.valueOf(jugadorActual+1));
		
		vaciarTodo();
		numCerebros = 0;
		numDisparos = 0;
		lblNumCerebros.setText("0");
		lblNumDisparos.setText("0");
		
		sonido1.start();
		sonido1.loop(Clip.LOOP_CONTINUOUSLY);
	}
}