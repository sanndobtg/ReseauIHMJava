package donnees;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe utilitaire qui permet de sérialiser et désérialiser des objets Java.
 *
 * @author Eric Cariou
 */
public class Serialisation {

	/**
	 * Sérialise un objet quelconque.
	 *
	 * @param obj l'objet à sérialiser (qui doit implémenter l'interface
	 *            <code>Serializable</code>)
	 * @return le tableau d'octets qui contient la sérialisation de l'objet
	 * @throws IOException en cas d'erreur de sérialisation
	 */
	public static byte[] serialiser(Serializable obj) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bytes);
		output.writeObject(obj);
		return bytes.toByteArray();
	}

	/**
	 * Instancie un objet à partir de sa version sérialisée.
	 *
	 * @param buffer le tableau d'octets qui contient la sérialisation de l'objet
	 * @return un objet instancié à partir du contenu sérialisé
	 * @throws IOException            en cas d'erreur de désérialisation
	 * @throws ClassNotFoundException si la classe de l'objet n'est pas connue
	 */
	public static Object deserialiser(byte[] buffer) throws IOException, ClassNotFoundException {
		ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
		return objectInputStream.readObject();
	}

	/**
	 * Constructeur privé pour ne pas pouvoir instancier la classe.
	 */
	private Serialisation() {

	}
}
