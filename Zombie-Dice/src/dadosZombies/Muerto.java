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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

/** Muerto - JDialog que abre una ventana cuando un jugador pierde el turno (3 o mas Disparos)
 * @see DZGame
 * @author Álvaro Serrano García
 * @version 1.0*/

public class Muerto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	/** int jugadorActual - Recogido de DZGame*/
	static int jugadorActual;
	/** String nombreJugador - Recogido de DZGame*/
	static String nombreJugador;
	/** JButton okButton - Boton "OK" que será recogido por DZGame*/
	public JButton okButton = null;

	/**
	 * MÉTODO PRINCIPAL
	 */
	public static void main(String[] args) {
		try {
			Muerto dialog = new Muerto(jugadorActual, nombreJugador);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setResizable(false);
			dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
	public Muerto(int jugadorActual, String nombreJugador) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Muerto.class.getResource("/img/icono0.png")));
		setTitle("FIN DE TURNO");
		
		//Introducir sonido de disparo (sólo se reproduce una vez)
		Clip sonido1 = AudioSystem.getClip();
		sonido1.open(AudioSystem.getAudioInputStream(getClass().getResource("audio/disparo.wav")));
		sonido1.start();
		
		setBounds(100, 100, 520, 289);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel_2 = new JLabel("3+ DISPAROS, HAS MUERTO!!!");
			lblNewLabel_2.setForeground(Color.RED);
			lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(90, 152, 400, 39);
			contentPanel.add(lblNewLabel_2);
		}
		{
			//Mostramos los datos del jugador recogidos desde DZGame
			JLabel lblNewLabel_1 = new JLabel("Jugador "+(jugadorActual+1)+" ["+nombreJugador+"]");
			lblNewLabel_1.setForeground(Color.RED);
			lblNewLabel_1.setFont(new Font("Tunga", Font.PLAIN, 25));
			lblNewLabel_1.setBounds(150, 110, 344, 57);
			contentPanel.add(lblNewLabel_1);
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Muerto.class.getResource("/img/disparo.gif")));
		lblNewLabel.setBounds(0, 0, 504, 213);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.DARK_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setFont(new Font("Tahoma", Font.BOLD, 11));
				okButton.setForeground(Color.ORANGE);
				okButton.setBackground(Color.GRAY);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();//cerar la ventana al pulsar "OK"
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	//Métodos get y set de JButton "OK" - Ver la explicación en el método estático muerto() en DZGame

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}
	
	
}
