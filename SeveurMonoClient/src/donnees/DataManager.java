package donnees;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe qui g�re un ensemble de personne via 3 op�rations.
 *
 * @author Eric Cariou
 */
public class DataManager implements IGestionPersonnes {

	/**
	 * M�thode qui sert � artificiellement faire durer l'ex�cution des m�thodes
	 * m�tier. Permet de v�rifier l'exclusion mutuelle sur ces m�thodes ou de faire
	 * attendre un client sur la r�ponse � lui envoyer.
	 */
	private void pause() {
		// changer cette valeur pour la borne max en secondes de pause
		int maxSecondes = 3;

		int duree = (int) (Math.random() * maxSecondes * 1000);
		System.out.print("Pause de duree " + duree + " ms ... ");
		try {
			Thread.sleep(duree);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("termin�e");
	}

	/**
	 * Map qui contient les personnes. Cl� = identifiant, valeur = personne.
	 */
	private HashMap<Integer, Personne> personnes = new HashMap<>();

	/**
	 * Compteur pour g�n�rer l'identifiant unique d'une personne
	 */
	int max = 0;

	@Override
	public synchronized int addPersonne(Personne p) {
		pause();
		if (!personnes.containsValue(p)) {
			max++;
			personnes.put(max, p);
			return max;
		}
		return this.getId(p);
	}

	@Override
	public synchronized Personne getPersonne(int id) throws InvalidIdException {
		pause();
		Personne p = personnes.get(id);

		if (p == null)
			throw new InvalidIdException("Index de personne non valide : " + id);
		return p;
	}

	@Override
	public synchronized int getId(Personne p) {
		pause();
		for (Map.Entry<Integer, Personne> entry : personnes.entrySet()) {
			if (entry.getValue().equals(p))
				return entry.getKey();
		}
		return -1;
	}

	/**
	 * Programme qui permet en local de tester le fonctionnement de la classe
	 * <code>DataManager</code> et de ses op�rations de gestion de personnes.
	 * Ensuite il faudra faire ex�cuter ces m�mes op�rations � distance lors de
	 * demandes de clients.
	 */
	public static void main(String argv[]) {
		DataManager dm = new DataManager();
		Personne p;
		int id;

		System.out.println("\n** Remplissage de la liste **\n");

		p = new Personne(29, "G�rard");
		id = dm.addPersonne(p);
		System.out.println("- Ajout de % " + p + " % avec identifiant = " + id);

		p = new Personne(20, "Marie-Germaine");
		id = dm.addPersonne(p);
		System.out.println("- Ajout de % " + p + " % avec identifiant = " + id);

		p = new Personne(29, "G�rard");
		id = dm.addPersonne(p);
		System.out.println("- Ajout de % " + p + " % avec identifiant = " + id);

		p = new Personne(42, "Saturnin");
		id = dm.addPersonne(p);
		System.out.println("- Ajout de % " + p + " % avec identifiant = " + id);

		System.out.println("\n** Interrogation de la liste **\n");

		try {
			p = dm.getPersonne(2);
			System.out.println("- Personne d'identificateur 2 = " + p);
		} catch (InvalidIdException e) {
			System.err.println("[Erreur] Personne d'identificateur 2 : " + e);
		}

		try {
			p = dm.getPersonne(5);
			System.out.println("- Personne d'identificateur 5 = " + p);
		} catch (InvalidIdException e) {
			System.err.println("[Erreur] Personne identificateur 5 : " + e);
		}

		System.out.println("- Identificateur de G�rard, 29 ans = " + dm.getId(new Personne(29, "G�rard")));
		System.out.println("- Identificateur de Maurice, 54 ans = " + dm.getId(new Personne(54, "Maurice")));
		System.out.println("- Identificateur de Marie-Germaine, 20 ans = " + dm.getId(new Personne(20, "Marie-Germaine")));
	}
}
