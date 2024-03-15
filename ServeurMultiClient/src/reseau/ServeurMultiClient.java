package reseau;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import donnees.*;


public class ServeurMultiClient {
	private ServerSocket serverSocket;
	private boolean running = true; //nous permettra de savoir si le serveur est bien en mode execution 
	
	public void demarrerServeur(int port) {
		try {
			IGestionPersonnes dataManager = new DataManager();
			serverSocket = new ServerSocket(port);
			System.out.println("Le serveur ecoute sur le port: "+port);
			
			
			while(running) {
				try(Socket socket = serverSocket.accept()){
					System.out.println("Client connecter avec: "+ socket.getInetAddress());
					//ici nous allons remplacer gererclient(socket,dataManager)par le lancement d'un thread a chaque nouvelle connexion 
					(new ThreadClient(socket, dataManager)).start();
				}catch(IOException e) {
					if(running) 
					{
						System.err.println("Impossible de demarer le serveur"+e.getMessage());
						
					}
					else 
					{
						System.out.println("Le serveur s'est arrete !");
					}
					
				}
				
			}
			
		}catch(IOException e) {
			System.err.println("ERREUR de connexion"+e.getMessage());
		}
	}
	
	// Gérer la connexion avec le client, comme le threats on pris le relai niveau gestion des client(un thread par client) il ne sert plus a rien
		//il faut sois le mettre publique si on veut s'en servir plus tard dans d'autre classe ou le supprimer carrement 
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
    
    //methode pour arreter le serveur
    public void arreterServeur() {
    	running = false;
    	if(serverSocket != null && !serverSocket.isClosed()) {
    		try {
    			serverSocket.close();
    		}catch(IOException e ){
    			System.err.println("ERREUR de ferture de la socket" +e.getMessage());
    		}
    	}
    }
    
    
    
	//classe prive qui me permet de gerer les threads
	private class ThreadClient extends Thread{
		
		private Socket socket;
		private IGestionPersonnes dataManager;
		
		public ThreadClient(Socket socket, IGestionPersonnes dataManager) {
			this.socket=socket;
			this.dataManager = dataManager;
		}
		
		//methode du thread
		public void run() {
			//on appel la methode de gestion client sur l'instance anglobante du serveur
			ServeurMultiClient.this.gererClient(socket,dataManager);
		}
	}
	
	
	//code main
	public static void main(String[] args) {
		int port = 1234; //example de port 
		ServeurMultiClient serveur  = new ServeurMultiClient() ;
		serveur.demarrerServeur(port);
	}
	
}
