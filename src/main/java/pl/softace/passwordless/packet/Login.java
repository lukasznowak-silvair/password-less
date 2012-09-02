package pl.softace.passwordless.packet;

import java.nio.ByteBuffer;

/**
 * 
 * Object represents login packet.
 * 
 * @author lukasz.nowak@homersoft.com
 *
 */
public class Login extends Packet {

	/**
	 * Login packet identifier.
	 */
	public static final byte ID_TAG = (byte) 0x01;
	
	/**
	 * Login.
	 */
	private String login;
	
	/**
	 * Password.
	 */
	private String password;
	
	
	public Login() {
		super(ID_TAG);
	}

	/**
	 * @return the login
	 */
	public final String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public final void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see pl.softace.passwordless.packet.Packet#getBytes()
	 */
	@Override
	public byte[] getBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(256);
		buffer.put(ID_TAG);
		
		if (login != null) {
			buffer.put((byte) login.length());
			buffer.put(login.getBytes());
		}
		if (password != null) {
			buffer.put((byte) password.length());
			buffer.put(password.getBytes());
		}
		
		buffer.put(END_TAG);
		buffer.flip();
		
		return buffer.array();
	}
	
	public static Login create(byte[] bytes) throws PacketException {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		byte id = buffer.get();
		if (id != ID_TAG) {
			throw new PacketException("Incorrect packet ID.");
		}
		
		Login login = new Login();
		login.setLogin(new String(Packet.getParameter(buffer)));
		login.setPassword(new String(Packet.getParameter(buffer)));
		
		return login;
	}
}
