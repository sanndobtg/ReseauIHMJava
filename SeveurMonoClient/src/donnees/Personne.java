package donnees;

import java.util.Objects;

/**
 * Classe qui décrit une personne. Implémente <code>java.io.Serializable</code>
 * car doit transiter à travers des sockets et des flux.
 *
 * @author Eric Cariou
 */
public class Personne implements java.io.Serializable {

	private static final long serialVersionUID = 8876713465328887612L;

	/**
	 * Age de la personne
	 */
	private int age;

	/**
	 * Nom de la personne
	 */
	private String nom;

	/**
	 * Crèe une nouvelle personne
	 * 
	 * @param a son age
	 * @param n son nom
	 */
	public Personne(int a, String n) {
		age = a;
		nom = n;
	}

	public String toString() {
		return ("nom : " + nom + ", age : " + age);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Ne surtout pas oublier de rédéfinir la méthode <code>equals</code> pour que
	 * la comparaison de personne fonctionne aussi sur des copies d'objets (comme
	 * c'est le cas par principe quand ils transitent à travers le réseau et des
	 * flux)
	 */
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Personne))
			return false;

		Personne p = (Personne) obj;
		return ((age == p.age) && (nom.equals(p.nom)));
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, nom);
	}

}
