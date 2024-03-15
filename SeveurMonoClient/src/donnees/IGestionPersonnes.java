package donnees;

/**
 * Interface des m�thodes de gestion de personnes.
 *
 * @author Eric Cariou
 */
public interface IGestionPersonnes {

	/**
	 * Ajoute une personne dans la liste et retourne son identificateur. Si la
	 * personne existait d�j�, retourne l'identificateur qu'elle avait dans la
	 * liste.
	 * 
	 * @param p la personne � ajouter dans la liste
	 * @return l'identificateur de la personne
	 */
	int addPersonne(Personne p);

	/**
	 * R�cup�re une personne dans la liste � partir de son identifiant.
	 * 
	 * @param id l'identifiant de la personne � r�cup�rer
	 * @throws InvalidIdException dans le cas o� l'identifiant n'est attribu� �
	 *                            aucune personne
	 */
	Personne getPersonne(int id) throws InvalidIdException;

	/**
	 * R�cup�re l'identifiant d'une personne.
	 * 
	 * @param p la personne dont on veut r�cup�rer l'identifiant
	 * @return l'identificateur de la personne ou -1 si la personne n'existe pas
	 */
	int getId(Personne p);
}