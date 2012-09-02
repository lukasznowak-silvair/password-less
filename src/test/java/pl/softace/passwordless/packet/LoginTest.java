package pl.softace.passwordless.packet;

import java.nio.ByteBuffer;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * Class for testing the {@link Login} class.
 * 
 * @author lkawon@gmail.com
 *
 */
public class LoginTest {

	/**
	 * Creates bytes from login packet.
	 */
	@Test
	public void createBytesFromLoginPacket() {		
		// given					
		ByteBuffer buffer = ByteBuffer.allocate(256);
		buffer.put(Login.ID_TAG);
		buffer.put((byte) "login".length());
		buffer.put("login".getBytes());
		buffer.put((byte) "password".length());
		buffer.put("password".getBytes());
		buffer.put(Packet.END_TAG);
		buffer.flip();
		
		Login login = new Login();
		login.setLogin("login");
		login.setPassword("password");		
		
		// when
		byte[] bytes = login.getBytes();
		
		// then
		Assert.assertTrue(bytes.length > 0);
		Assert.assertEquals(bytes, buffer.array());
	}
	
	/**
	 * Creates login packet from bytes.
	 * @throws PacketException 
	 */
	@Test
	public void createLoginPacket() throws PacketException {
		// given					
		ByteBuffer buffer = ByteBuffer.allocate(256);
		buffer.put(Login.ID_TAG);
		buffer.put((byte) "login".length());
		buffer.put("login".getBytes());
		buffer.put((byte) "password".length());
		buffer.put("password".getBytes());
		buffer.put(Packet.END_TAG);
		buffer.flip();					
		
		// when
		Login login = Login.create(buffer.array());
		
		// then
		Assert.assertEquals(login.getLogin(), "login");
		Assert.assertEquals(login.getPassword(), "password");
	}
}
