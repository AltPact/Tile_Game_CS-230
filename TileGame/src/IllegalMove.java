/**
 * A exception that should be thrown with a illegal move is made.
 * @author Alex Ullman (851732) and Joshua Sinderberry (851800)
 * @version 1.0
 */
public class IllegalMove extends Exception {
	private static final long serialVersionUID = 6356271595469886221L;
	
	public IllegalMove(String s) {
		super(s);
	}

}
