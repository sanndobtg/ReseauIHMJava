package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private String hostName;
    private int port;
    private Socket socket;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public boolean connexion() {
        try {
            socket = new Socket(hostName, port);
            objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu: " + hostName);
            return false;
        } catch (IOException e) {
            System.err.println("Impossible d'établir une connexion I/O avec " + hostName);
            return false;
        }
    }

    public void gererMessages() {
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            System.out.println("Client connecté au serveur " + hostName + " sur le port " + port);
            System.out.println("Tapez vos commandes (ADD, GET, ID) ou 'exit' pour quitter:");

            while (true) {
                System.out.print(">");
                userInput = stdIn.readLine();
                if ("exit".equalsIgnoreCase(userInput)) {
                    objectOut.writeObject(userInput);
                    objectOut.flush();
                    break; // Exit the loop
                }

                // Send request to server
                objectOut.writeObject(userInput);
                objectOut.flush();

                // Read and display server response
                Object response = objectIn.readObject();
                if (response instanceof String) {
                    System.out.println("Réponse du serveur: " + response);
                } else {
                    System.out.println("Réponse inattendue du serveur de type " + response.getClass());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la communication avec le serveur: " + e.getMessage());
        } finally {
            fermerConnexion();
        }
    }

    private void fermerConnexion() {
        try {
            if (objectIn != null) objectIn.close();
            if (objectOut != null) objectOut.close();
            if (socket != null) socket.close();
            System.out.println("Déconnexion du client");
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 1234);
        if (client.connexion()) {
            client.gererMessages();
        }
    }
}