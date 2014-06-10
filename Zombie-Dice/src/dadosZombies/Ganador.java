package dadosZombies;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**Ganador - JDialog que se lanza cuando un jugador ha ganado la partida (13 cerebros)
 * @see DZGame.java
 * @author Álvaro Serrano García
 * @version 1.0*/

public class Ganador extends JDialog {

	/** JPanel */
	private final JPanel contentPanel = new JPanel();
	/** int jugadorActual - recoge el valor de DZGame */
	static int jugadorActual;
	/** String nombreJugador - recoge el valor de DZGame */
	static String nombreJugador;
	public JButton botonNuevaPartida;
	private Clip sonido1 = null;

	/**
	 * MÉTODO PRINCIPAL
	 */
	public static void main(String[] args) {
		try {
			Ganador dialog = new Ganador(jugadorActual, nombreJugador);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setResizable(false); //Para que la ventana no se pueda redimensionar
			dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Para deshabilitar el botón "X"
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	public Ganador(int jugadorActual, String nombreJugador) throws LineUnavailableException, IOException, UnsupportedAudioFileException 
	//Esta excepciones las incorpora Eclipse cuando se introduce la reproducción de sonido en el código
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(Ganador.class.getResource("/img/icono0.png")));
		setTitle("FIN DE PARTIDA");
		
		//Instancia e inicialización de Clip
		sonido1 = AudioSystem.getClip();
		//Asignación de la url del archivo
		sonido1.open(AudioSystem.getAudioInputStream(getClass().getResource("audio/horda.wav")));
		//Comienza la reproducción
		sonido1.start();
		//Repetir la pista continuamente (con un valor entero se puede especificar un número finito de veces como parámetro)
		sonido1.loop(Clip.LOOP_CONTINUOUSLY);
		
		setBounds(100, 100, 520, 289);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel_2 = new JLabel("HA GANADO LA PARTIDA");
			lblNewLabel_2.setForeground(Color.GREEN);
			lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(115, 152, 296, 39);
			contentPanel.add(lblNewLabel_2);
		}
		{
			//Mostramos en el JLabel los datos que hemos recogido de DZGame
			JLabel lblNewLabel_1 = new JLabel("Jugador "+(jugadorActual+1)+" ["+nombreJugador+"]");
			lblNewLabel_1.setForeground(Color.GREEN);
			lblNewLabel_1.setFont(new Font("Tunga", Font.PLAIN, 25));
			lblNewLabel_1.setBounds(150, 110, 344, 57);
			contentPanel.add(lblNewLabel_1);
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Ganador.class.getResource("/img/ganador.gif")));
		lblNewLabel.setBounds(0, 0, 504, 213);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Salir");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				okButton.setForeground(Color.DARK_GRAY);
				okButton.setBackground(Color.LIGHT_GRAY);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Al pulsar en Salir, se cierra el programa
						System.exit(0);
					}
				});
				{
					botonNuevaPartida = new JButton("Nueva partida");
					botonNuevaPartida.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							sonido1.stop();
							dispose();
						}
					});
					botonNuevaPartida.setFont(new Font("Tahoma", Font.BOLD, 11));
					botonNuevaPartida.setBackground(Color.LIGHT_GRAY);
					botonNuevaPartida.setForeground(Color.DARK_GRAY);
					buttonPane.add(botonNuevaPartida);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	//Métodos get y set del botón de Nueva Partida (ver explicación en el método ganador() de DZGame.java
	public JButton getBotonNuevaPartida() {
		return botonNuevaPartida;
	}

	public void setBotonNuevaPartida(JButton botonNuevaPartida) {
		this.botonNuevaPartida = botonNuevaPartida;
	}
}
