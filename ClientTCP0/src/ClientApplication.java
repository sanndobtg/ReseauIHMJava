import java.io.IOException;

public class ClientApplication {
    public static void main(String[] args) {
        try {
            TCPClient client = new TCPClient("localhost", 7777);
            client.sendMessage("youpi");
        } catch (IOException e) {
            System.out.println("Client failed to start: " + e.getMessage());
        }
    }
}