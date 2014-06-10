package dadosZombies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 CLASE DE PLANTILLA PARA EXPRESIONES REGULARES
 * @author Alvaro Serrano Garcia
 * @version 1.0
 */

public class RegEx {
	
	/** Campo regerxPattern - Objeto de la clase Pattern*/
	private Pattern regexPattern = null;

	/** Constructor 
	 * @param String cadena*/
	public RegEx(String cadena) {
		super();
		regexPattern = Pattern.compile(cadena);
	}
	
	/**
	 Devuelve true si hay coincidencias
	 * @param cadena
	 * @returns true o false dependiendo del resultado
	 */
	public boolean check(String cadena){
		Matcher cadenaMatcher = regexPattern.matcher(cadena);
		return cadenaMatcher.find();
	}
	
}