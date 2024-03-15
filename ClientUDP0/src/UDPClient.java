
public class UDPClient {
	private MessageSender sender; 
	
	public UDPClient(String host, int port) throws Exception {
		sender = new MessageSender(host,port);
	}
	
	public void send(String message) {
		try {
			sender.sendMessage(message);
			System.out.println("message envoyer a : " + sender.address + ":" + sender.port);
		}
		catch (Exception e)
		{
			 System.err.println("[ERREUR]" + e);
		}
		finally
		{
			sender.close();
		}
	}
}
