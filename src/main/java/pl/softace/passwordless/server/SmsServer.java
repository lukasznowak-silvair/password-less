package pl.softace.passwordless.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Enumeration;

import pl.softace.passwordless.R;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

/**
 * 
 * Socket server for accepting clients.
 * 
 * @author lukasz.nowak@homersoft.com
 *
 */
public class SmsServer implements Runnable {

	/**
	 * For test purposes.
	 */
	private static final String LOGIN = "login";
	
	/**
	 * For test purposes.
	 */
	private static final String PASSWORD = "password";
	
	/**
	 * For test purposes.
	 */
	private static final int PORT = 8080;
	
	/**
	 * Server socket for accepting connections.
	 */
	private ServerSocket serverSocket;	
	
	/**
	 * Indicates that server is running.
	 */
	private boolean isRunning;
	
	/**
	 * Authorized client.
	 */
	private SocketChannel authorizedChannel;
	
	/**
	 * Context activity for the thread.
	 */
	private Activity activity;
	
	
	/**
	 * Constructor.
	 * 
	 * @param textView		text view with status
	 */
	public SmsServer(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Stops the thread.
	 */
	public final void stop() {
		isRunning = false;
		
		try {
			new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort()).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Implementation of the runnable method.
	 */
	public void run() {			
		try {
			serverSocket = new ServerSocket(PORT);
			//serverSocket.setSoTimeout(1000);
									
			StringBuilder builder = new StringBuilder();
			builder.append("Server started.\n\n");
			builder.append("IP: ");
			builder.append(getLocalIpAddress());
			builder.append("\n");
			builder.append("Port: ");
			builder.append(PORT);
			setStatus(builder.toString());
			
			isRunning = true;
			while (isRunning) {						
				try {
					Socket socket = serverSocket.accept();
					if (socket != null && isRunning) {
						authorizeClient(socket);

						if (authorizedChannel != null) {
							ByteBuffer buffer = ByteBuffer.allocate(64);
							buffer.put(new String("test").getBytes());
							authorizedChannel.write(buffer);
						}
					}
				} catch (IOException e) {
					// do nothing
				}				
			}			
			
			serverSocket.close();
			
			builder = new StringBuilder();
			builder.append("Server stopped.");		
			setStatus(builder.toString());
			
		} catch (IOException e) {
			Log.e("SmsServer", e.toString());
			// TODO: print status
		}			
	}

	/**
	 * Sets status on the main activity.
	 * 
	 * @param status
	 */
	private void setStatus(final String status) {
		activity.runOnUiThread(new Runnable() {				
			public void run() {				
				((TextView) activity.findViewById(R.id.textViewStatus)).setText(status);					
			}
		});
	}
	
	/**
	 * Authorizes client.
	 * 
	 * @param socket
	 */
	private void authorizeClient(Socket socket) {
		boolean authorized = false;
		
		SocketChannel channel = socket.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(64);
		
		try {
			int bytesRead = channel.read(buffer);
			while (bytesRead != -1) {
				buffer.flip();
				
				//TODO: process message or read for the rest if not complete
				authorized = true;				
				if (authorized) {
					authorizedChannel = channel;
					break;
				}
			}
			
		} catch (Exception e) {
			Log.e("SmsServer", e.toString());
		}
	}
	
	/**
	 * Gets local IP address.
	 * 
	 * @return
	 */
	private String getLocalIpAddress() {
      try {
          for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); 
        		  en.hasMoreElements();) {
              NetworkInterface intf = en.nextElement();
              for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); 
               enumIpAddr.hasMoreElements();) {
                  InetAddress inetAddress = enumIpAddr.nextElement();
                  if (!inetAddress.isLoopbackAddress()) {
                      return inetAddress.getHostAddress().toString();
                  }
              }
          }
      } catch (Exception ex) {
          Log.e("IP Address", ex.toString());
      }
      return null;
  }
}
