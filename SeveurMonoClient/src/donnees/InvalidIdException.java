package donnees;

/**
 * Exception qui est levée quand on demande à récupérer une personne à partir
 * d'un identifiant incorrect (c'est-à-dire quand aucune personne ne possède cet
 * identifiant).
 *
 * @author Eric Cariou
 */
public class InvalidIdException extends Exception {

	private static final long serialVersionUID = 8640023736440470869L;

	/**
	 * Crèe une nouvelle exception
	 * 
	 * @param message le message décrivant l'erreur
	 */
	public InvalidIdException(String message) {
		super(message);
	}
}
