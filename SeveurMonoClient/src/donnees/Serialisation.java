package donnees;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe utilitaire qui permet de s�rialiser et d�s�rialiser des objets Java.
 *
 * @author Eric Cariou
 */
public class Serialisation {

	/**
	 * S�rialise un objet quelconque.
	 *
	 * @param obj l'objet � s�rialiser (qui doit impl�menter l'interface
	 *            <code>Serializable</code>)
	 * @return le tableau d'octets qui contient la s�rialisation de l'objet
	 * @throws IOException en cas d'erreur de s�rialisation
	 */
	public static byte[] serialiser(Serializable obj) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bytes);
		output.writeObject(obj);
		return bytes.toByteArray();
	}

	/**
	 * Instancie un objet � partir de sa version s�rialis�e.
	 *
	 * @param buffer le tableau d'octets qui contient la s�rialisation de l'objet
	 * @return un objet instanci� � partir du contenu s�rialis�
	 * @throws IOException            en cas d'erreur de d�s�rialisation
	 * @throws ClassNotFoundException si la classe de l'objet n'est pas connue
	 */
	public static Object deserialiser(byte[] buffer) throws IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
		return objectInputStream.readObject();
	}

	/**
	 * Constructeur priv� pour ne pas pouvoir instancier la classe.
	 */
	private Serialisation() {

	}
}
