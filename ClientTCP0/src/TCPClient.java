import java.io.IOException;

public class TCPClient {
    private ConnectionManager connectionManager;
    private MessageSender sender;
    private MessageReceiver receiver;

    public TCPClient(String hostname, int port) throws IOException {
        connectionManager = new ConnectionManager(hostname, port);
        sender = new MessageSender(connectionManager.getOutput());
        receiver = new MessageReceiver(connectionManager.getInput());
    }

    public void sendMessage(String messageToSend) {
        try {
            sender.sendMessage(messageToSend);
            String response = receiver.receiveMessage();
            System.out.println("Received from server: " + response);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                connectionManager.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}