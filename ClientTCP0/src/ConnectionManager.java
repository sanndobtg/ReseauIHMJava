import java.io.*;
import java.net.*;

public class ConnectionManager {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ConnectionManager(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void close() throws IOException {
        if (input != null) input.close();
        if (output != null) output.close();
        if (socket != null) socket.close();
    }
}