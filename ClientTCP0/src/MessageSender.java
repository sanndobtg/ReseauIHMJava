import java.io.*;

public class MessageSender {
    private ObjectOutputStream output;

    public MessageSender(ObjectOutputStream output) {
        this.output = output;
    }

    public void sendMessage(String message) throws IOException {
        output.writeObject(message);
    }
}