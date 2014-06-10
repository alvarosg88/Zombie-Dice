package dadosZombies;
import javax.swing.*;
import java.lang.reflect.Method;

/**Abrir URL - Clase para abrir enlaces web desde un programa en Java
 * @author Álvaro Serrano García
 * @version 1.0*/

public class AbrirURL {
	
	/**MÉTODO PRINCIPAL*/
	
        public static void openURL(String url) { //Siempre pasamos la url como parámetro String
                String osName = System.getProperty("os.name"); //Recogemos en un String el SO en el que nos encontramos
                try {
                        if (osName.startsWith("Windows")) //Si el SO es Windows...
                                Runtime.getRuntime().exec( //Utiliza la clase Runtime para abrir la url
                                                "rundll32 url.dll,FileProtocolHandler " + url);
                        else { //Si el SO no es Windows...
                                String[] browsers = { "firefox", "opera", "konqueror",
                                                "epiphany", "mozilla", "netscape" }; 
                                				// Se almacenan en un array los nombres de los navegadores más comunes
                                String browser = null;
                                for (int count = 0; count < browsers.length && browser == null; count++)
                                        if (Runtime.getRuntime().exec(
                                                        new String[] { "which", browsers[count] })
                                                        .waitFor() == 0)
                                                browser = browsers[count]; 
                                				//Recorremos el array para saber mediante Runtime qué navegador tenemos instalado por defecto
                                Runtime.getRuntime().exec(new String[] { browser, url }); 
                                //Y volvemos a usar Runtime para abrir la url con ese navegador
                        }
                } catch (Exception e) { 
                	//Lanzamos una excepcion si se diera el caso de que Runtime no encontrara ningún navegador predeterminado
                        JOptionPane.showMessageDialog(null, "Error in opening browser"
                                        + ":\n" + e.getLocalizedMessage());
                }
        }
}
