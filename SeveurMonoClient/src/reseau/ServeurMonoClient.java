package reseau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import donnees.*;


public class ServeurMonoClient {
    private ServerSocket serverSocket;

    // Démarrer le serveur sur le port spécifié
    public void demarrerServeur(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Serveur démarré sur le port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connecté: " + clientSocket.getInetAddress());
                    IGestionPersonnes dataManager = new DataManager();
                    gererClient(clientSocket, dataManager);
                } catch (IOException e) {
                    System.err.println("Impossible de démarrer le serveur: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Impossible de démarrer le serveur:" + e.getMessage());
        } finally {
            arreterServeur();
        }
    }

    // Gérer la connexion avec le client
    private void gererClient(Socket clientSocket, IGestionPersonnes dataManager) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream());

            Object inputObject;
            while ((inputObject = objectIn.readObject()) != null) {
                if (inputObject instanceof String) {
                    String inputLine = (String) inputObject;
                    // Exemple d'implémentation du traitement des requêtes client
                    String[] requestParts = inputLine.split(" ");
                    String command = requestParts[0].toUpperCase();
                    try {
                        switch (command) {
                            case "ADD": {
                                int age = Integer.parseInt(requestParts[1]);
                                String nom = requestParts[2];
                                Personne p = new Personne(age, nom);
                                int id = dataManager.addPersonne(p);
                                objectOut.writeObject("SUCCESS: La personne avec cet id a été ajoutée : " + id);
                                break;
                            }

                            case "GET": {
                                int id = Integer.parseInt(requestParts[1]);
                                Personne p = dataManager.getPersonne(id);
                                objectOut.writeObject("SUCCESS: Personne trouvée - " + p);
                                break;
                            }

                            case "ID": {
                                int age = Integer.parseInt(requestParts[1]);
                                String nom = requestParts[2];
                                Personne p = new Personne(age, nom);
                                int id = dataManager.getId(p);
                                if (id != -1) {
                                    objectOut.writeObject("SUCCESS: ID de la personne: " + id);
                                } else {
                                    objectOut.writeObject("ERROR: Personne non trouvée");
                                }
                                break;
                            }

                            default:
                                objectOut.writeObject("ERROR: Commande non reconnue");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        objectOut.writeObject("ERROR: Format de nombre invalide");
                    } catch (InvalidIdException e) {
                        objectOut.writeObject("ERROR: " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        objectOut.writeObject("ERROR: Format incorrect");
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la communication avec le client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture de la Socket client: " + e.getMessage());
            }
        }
    }

    // Arrêter le serveur
    public void arreterServeur() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du serveur: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        int port = 1234; // Utilisez le port que vous souhaitez pour le serveur
        ServeurMonoClient serveur = new ServeurMonoClient();
        serveur.demarrerServeur(port);
    }
    
    
}
