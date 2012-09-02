package pl.softace.passwordless.packet;

import java.nio.ByteBuffer;

/**
 * 
 * Abstract part of the packet.
 * 
 * @author lukasz.nowak@homersoft.com
 *
 */
public abstract class Packet {

	/**
	 * Indicates the end of the packet.
	 */
	public static final byte END_TAG = (byte) 0xFF;
	
	/**
	 * Indicates the packet type.
	 */
	private byte id;
	
	
	/**
	 * Constructor.
	 * 
	 * @param id	packet identifier
	 */
	public Packet(byte id) {
		
	}

	/**
	 * @return the id
	 */
	public final byte getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(byte id) {
		this.id = id;
	}
	
	/**
	 * Converts packet to bytes.
	 * 
	 * @return	packet as byte array
	 */
	public abstract byte[] getBytes();
	
	/**
	 * Get parameter from byte buffer.
	 * 
	 * @param buffer	byte buffer
	 * @return			parameter as byte array
	 */
	protected static byte[] getParameter(ByteBuffer buffer) {		
		byte length = buffer.get();
		byte[] param = new byte[length];
		buffer.get(param);
		
		return param;
	}
}
