import java.io.*;

public class MessageReceiver {
    private ObjectInputStream input;

    public MessageReceiver(ObjectInputStream input) {
        this.input = input;
    }

    public String receiveMessage() throws IOException, ClassNotFoundException {
        return (String) input.readObject();
    }
}