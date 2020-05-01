/**
 * 
 */
package fr.christophelouer.utilconsole.common.file.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.christophelouer.utilconsole.common.Constantes;
import fr.christophelouer.utilconsole.common.file.util.exceptions.GestionFichiersException;

/**
 * Regroupe les méthodes utilitaires pour la gestion des fichiers.
 * 
 * @author Christophe LOUËR
 *
 */
public final class GestionFichiers {

	private GestionFichiers() {
	}

	/**
	 * Lit le fichier texte en paramètre et retourne les enregistrements dans une
	 * liste de chaines de caractères. Chaque élément de la liste correspond à un
	 * enregistrement du fichier texte.
	 * 
	 * @param path path nio du fichier à exploiter
	 * @return liste contenant les enregistrements du fichier.
	 * @throws GestionFichiersException levée si le fichier ne peut être lu.
	 */
	public static List<String> lireFichierTexte(final Path path) throws GestionFichiersException {
		List<String> resultat = new ArrayList<>();
		try {
			resultat = Files.lines(path).collect(Collectors.toList());
		} catch (IOException e) {
			throw new GestionFichiersException(Constantes.PB_LECTURE_FICHIER_EXCEPTION, e);
		}
		return resultat;

	}

	/**
	 * Lit le fichier texte en paramètre et retourne les enregistrements dans une
	 * liste de chaines de caractères. Chaque élément de la liste correspond à un
	 * enregistrement du fichier texte.
	 * 
	 * @param url url du fichier exploité.
	 * @return liste contenant les enregistrements du fichier.
	 * @throws GestionFichiersException levée si le fichier ne peut être lu.
	 */
	public static List<String> lireFichierTexte(final URL url) throws GestionFichiersException {
		Path sourceFile = null;
		try {
			sourceFile = Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			throw new GestionFichiersException(Constantes.URL_MAL_FORMATEE_EXCEPTION);
		}
		return lireFichierTexte(sourceFile);

	}

	/**
	 * Lit le fichier texte en paramètre et retourne les enregistrements dans une
	 * liste de chaines de caractères. Chaque élément de la liste correspond à un
	 * enregistrement du fichier texte.
	 * 
	 * @param nomFichier chemin complet sur le système de fichiers du fichier exploité.
	 * @return liste contenant les enregistrements du fichier.
	 * @throws GestionFichiersException levée si le fichier ne peut être lu.
	 */
	public static List<String> lireFichierTexte(final String nomFichier) throws GestionFichiersException {
		Path sourceFile = Paths.get(nomFichier);
		return lireFichierTexte(sourceFile);
	}

	/**
	 * Lit le fichier texte en paramètre et retourne les enregistrements dans un
	 * flux de chaines de caractères.
	 * 
	 * @param nomFichier nom du fichier lu.
	 * @return Flux de chaines de caractères (stream)
	 * @throws GestionFichiersException levée si le fichier ne peut être lu.
	 */
	public static Stream<String> lireFichierTexteStream(final String nomFichier) throws GestionFichiersException {
		Stream<String> resultat;
		try {
			resultat = lireFichierTexte(nomFichier).stream();
		} catch (GestionFichiersException e) {
			throw new GestionFichiersException(Constantes.PB_LECTURE_FICHIER_EXCEPTION, e);
		}
		return resultat;
	}

	/**
	 * Ecrit un fichier texte à partir de la liste en paramètre.
	 * 
	 * @param lst        liste de chaines de caractères
	 * @param nomFichier nom du fichier à écrire.
	 * @throws IOException levée si le fichier ne peut être écrit.
	 */
	public static void ecrireFichierTexte(final List<String> lst, final String nomFichier) throws IOException {
		Path sourceFile = Paths.get(nomFichier);
		Files.write(sourceFile, lst);
	}

	/**
	 * Ecrit un fichier texte à partir du flux texte en paramètre (Stream).
	 * 
	 * @param stream     flux texte à traiter.
	 * @param nomFichier nom du fichier texte à écrire.
	 * @throws IOException levée si le fichier ne peut être écrit.
	 */
	public static void ecrireFichierTexteStream(final Stream<String> stream, final String nomFichier)
			throws IOException {
		Path p = Paths.get(nomFichier);
		Files.write(p, stream.collect(Collectors.toList()));
	}

	/**
	 * Lecture des octets d'un fichier et copies dans dans un flux d'octets.
	 * 
	 * @param nomFichier Fichier lu.
	 * @return Flux d'octets (stream).
	 * @throws IOException Levée si le fichier en paramètre ne peut être lu.
	 */
	public static Stream<Byte> lireFichierOctets(final String nomFichier) throws IOException {
		Path p = Paths.get(nomFichier);
		byte[] bytes = Files.readAllBytes(p);
		List<Byte> lstBytes = new ArrayList<>();
		for (int i = 0; i < bytes.length; i++) {
			lstBytes.add(Byte.valueOf(bytes[i]));
		}

		return lstBytes.parallelStream();
	}
	
	/**
	 * Ecrit un tableau d'octets dans un fichier.
	 * @param bytes tableau d'octets à traiter.
	 * @param nomFichier Fichier à écrire.
	 * @throws IOException Levée si le fichier en paramètre ne peut être écrit.
	 */
	public static void ecrireFichierOctets(final byte[] bytes,final String nomFichier) throws IOException {
		Path p = Paths.get(nomFichier);
		Files.write(p, bytes);
	}

}
