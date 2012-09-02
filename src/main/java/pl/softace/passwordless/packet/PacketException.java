package pl.softace.passwordless.packet;

/**
 * 
 * Contains exceptions about packets.
 * 
 * @author lukasz.nowak@homersoft.com
 *
 */
public class PacketException extends Exception {

	/**
	 * Automatically generated UID.
	 */
	private static final long serialVersionUID = 6345226090334987324L;
	
	
	/**
	 * Constructor.
	 * 
	 * @param message	exception message
	 */
	public PacketException(String message) {
		super(message);
	}
}
