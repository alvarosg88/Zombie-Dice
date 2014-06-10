package dadosZombies;

/**Enumeración Dado - Se utiliza para definir la estructura y complejidad de los dados del juego
 * @author Álvaro Serrano García
 * @version 1.0*/

public enum Dado{
	
	/**Dado VERDE*/
	VERDE(new String[] {"cerebro","cerebro","cerebro","huella","huella","disparo"}),  
	/**Dado AMARILLO*/
	AMARILLO(new String[] {"cerebro","cerebro","huella","huella","disparo","disparo"}),
	/**Dado ROJO*/
	ROJO(new String[] {"cerebro","huella","huella","disparo","disparo","disparo"});
	
	/**String[] valores - Array en el que se almacenan las caras del dado*/
    private String[] valores;
    
    /**Constructor
     * @param String[] valores*/
    private Dado(String[] valores)
    {
        this.valores = valores;
    }

    /**Para obtener los valores de un dado
     * @return String[] valores*/
	public String[] getValores() {
		return valores;
	}


    
    
    
}
