package donnees;

/**
 * Exception qui est lev�e quand on demande � r�cup�rer une personne � partir
 * d'un identifiant incorrect (c'est-�-dire quand aucune personne ne poss�de cet
 * identifiant).
 *
 * @author Eric Cariou
 */
public class InvalidIdException extends Exception {

	private static final long serialVersionUID = 8640023736440470869L;

	/**
	 * Cr�e une nouvelle exception
	 * 
	 * @param message le message d�crivant l'erreur
	 */
	public InvalidIdException(String message) {
		super(message);
	}
}
