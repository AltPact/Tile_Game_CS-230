
/**
 * This exception is thrown when a fire tile is called on a fire 
 * tile will set a player alight.
 * @author Joshua Sinderberry (851800)
 * @version 1.0
 */
public class IllegalFireException extends Exception {
	private static final long serialVersionUID = -2661013545331405150L;
	
	/**
	 * Constructor that constructs a exception using the super classes
	 * constructor.
	 * @param s
	 */
	public IllegalFireException(String s) {
		super(s);
		
	}

}
