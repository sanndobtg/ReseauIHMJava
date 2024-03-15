import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class MessageSender {
	private DatagramSocket socket;
	InetAddress address;
	int port;
	
	public MessageSender(String host, int port)throws Exception{
		this.port =port;
		this.address = InetAddress.getByName(host);
		this.socket = new DatagramSocket();
		
	}
	
	public void sendMessage(String message) throws Exception {
		byte[] data = message.getBytes();
		DatagramPacket packet = new DatagramPacket(data,data.length, address, port);
		socket.send(packet);
	}
	
	public void close() {
		socket.close();
	}

}

