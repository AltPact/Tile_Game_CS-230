/**
 * This class acts as a illegal backtrack exception, this is when a backtrack action tile is performed
 * but the backtrack move is illegal. 
 * @author Joshua Sinderberry (851800)
 *
 */
public class IllegalBackTrackException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IllegalBackTrackException(String s) {
		super(s);
	}
	

}
