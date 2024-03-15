package donnees;

/**
 * Interface des méthodes de gestion de personnes.
 *
 * @author Eric Cariou
 */
public interface IGestionPersonnes {

	/**
	 * Ajoute une personne dans la liste et retourne son identificateur. Si la
	 * personne existait déjà, retourne l'identificateur qu'elle avait dans la
	 * liste.
	 * 
	 * @param p la personne à ajouter dans la liste
	 * @return l'identificateur de la personne
	 */
	int addPersonne(Personne p);

	/**
	 * Récupère une personne dans la liste à partir de son identifiant.
	 * 
	 * @param id l'identifiant de la personne à récupérer
	 * @throws InvalidIdException dans le cas où l'identifiant n'est attribué à
	 *                            aucune personne
	 */
	Personne getPersonne(int id) throws InvalidIdException;

	/**
	 * Récupére l'identifiant d'une personne.
	 * 
	 * @param p la personne dont on veut récupérer l'identifiant
	 * @return l'identificateur de la personne ou -1 si la personne n'existe pas
	 */
	int getId(Personne p);
}