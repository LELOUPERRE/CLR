package fr.christophelouer.dao.common.dao.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.christophelouer.dao.common.dao.exceptions.DeserialisationException;
import fr.christophelouer.dao.common.dao.exceptions.SerialisationException;
import fr.christophelouer.dao.references.C;

public final class Serialiser {

	private Serialiser() {
	}

	/**
	 * Sérialise un objet dans un fichier.
	 * 
	 * @param c
	 * @param chemin
	 * @throws SerialisationException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static <T> void serialiser(T c, String chemin) throws SerialisationException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
			oos.writeObject(c);
		} catch (IOException e) {
			throw new SerialisationException(C.SERIALISATION_EXCEPTION, e.getCause());
		}

	}

	/**
	 * Désérialise un objet à partir du nom de fichier en paramètre.
	 * 
	 * @param chemin fichier a désérialiser.
	 * @return
	 * @throws DeserialisationException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialiser(String chemin) throws DeserialisationException {
		Path path = Paths.get(chemin);
		if (!Files.exists(path) || Files.isDirectory(path)) {
			throw new DeserialisationException(C.PAS_UN_FICHIER_EXCEPTION);
		}

		T retour = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
			retour = (T) ois.readObject();
			return retour;
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserialisationException(C.DESERIALISATION_EXCEPTION, e.getCause());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialiser(Path path) throws DeserialisationException {
		if (!Files.exists(path) || Files.isDirectory(path)) {
			throw new DeserialisationException(C.PAS_UN_FICHIER_EXCEPTION);
		}

		T retour = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toString()))) {
			retour = (T) ois.readObject();
			return retour;
		} catch (IOException | ClassNotFoundException e) {
			throw new DeserialisationException(C.DESERIALISATION_EXCEPTION, e.getCause());
		}
	}

	public static <T> List<T> deserialiserTout(String chemin) throws DeserialisationException {
		Path path = Paths.get(chemin);
		if (!Files.exists(path) || !Files.isDirectory(path)) {
			throw new DeserialisationException(C.PAS_UN_REPERTOIRE_EXCEPTION);
		}

		try {
			List<T> lstObjets = new ArrayList<>();
			Path repertoire = Paths.get(chemin);
			DirectoryStream<Path> fichiers = Files.newDirectoryStream(repertoire, p -> p.toString().endsWith(".obj"));
			for (Path p : fichiers) {
				lstObjets.add(deserialiser(p));
			}
			return lstObjets;
		} catch (IOException e) {
			throw new DeserialisationException(e);
		}

	}

}
