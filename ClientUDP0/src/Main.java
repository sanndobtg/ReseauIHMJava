
public class Main {

	public static void main(String[] args) {
		try 
		{
			UDPClient client = new UDPClient("localhost", 7777);
			client.send("youpi");
		}
		catch (Exception e)
		{
			System.err.println("[ERREUR ]" + e);
		}
		

	}

}
