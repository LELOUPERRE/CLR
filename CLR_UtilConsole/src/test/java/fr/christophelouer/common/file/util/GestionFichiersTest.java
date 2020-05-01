package fr.christophelouer.common.file.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.christophelouer.common.file.util.GestionFichiers;
import fr.christophelouer.common.file.util.exceptions.GestionFichiersException;

class GestionFichiersTest {
	
	private String nomFichierLu;
	private String nomFichierEcrit;
	private String nomFichierImage;

	@BeforeEach
	void setUp() throws Exception {
		String repertoireCourantProjet = System.getProperty("user.dir");
		this.nomFichierLu = String.format("%s%ssrc%stest%sresources%s%s",
										repertoireCourantProjet,
										File.separator,
										File.separator,
										File.separator,
										File.separator,
										"citations");
		this.nomFichierEcrit = String.format("%s%ssrc%stest%sresources%s%s",
				repertoireCourantProjet,
				File.separator,
				File.separator,
				File.separator,
				File.separator,
				"test.txt");
		this.nomFichierImage = String.format("%s%ssrc%stest%sresources%s%s",
				repertoireCourantProjet,
				File.separator,
				File.separator,
				File.separator,
				File.separator,
				"tintin.jpg");
	}

	@Test
	void testLireFichierTexte() throws IOException, GestionFichiersException {
		List<String> enreg = GestionFichiers.lireFichierTexte(this.nomFichierLu);
		assertEquals(3, enreg.size());
		assertEquals("La vie, c'est comme une bicyclette, il faut avancer pour ne pas perdre l'équilibre.", enreg.get(0));
		assertEquals("Il faut deux ans pour apprendre à parler et toute une vie pour apprendre à se taire.", enreg.get(1));
		assertEquals("Une femme sans homme, c'est comme un poisson sans bicyclette.", enreg.get(2));
	}

	@Test
	void testLireFichierTexteIOException() {
		assertThrows(GestionFichiersException.class, () -> GestionFichiers.lireFichierTexte("blabla.txt"));

	}
	
	@Test
	void testEcrireFichierTexte() throws GestionFichiersException, IOException {
		List<String> lst = Arrays.asList("Bonjour le suis CLR","Je suis enseignant à l'ETRS");
		GestionFichiers.ecrireFichierTexte(lst, this.nomFichierEcrit);
		Path fichierEcrit = Paths.get(this.nomFichierEcrit);
		assertEquals(this.nomFichierEcrit, fichierEcrit.toString());
		List<String> enreg = GestionFichiers.lireFichierTexte(nomFichierEcrit);
		assertEquals(2, enreg.size());
		assertEquals("Bonjour le suis CLR", enreg.get(0));
		assertEquals("Je suis enseignant à l'ETRS", enreg.get(1));

	}
	
	@Test
	void testLireFichierOctets() throws IOException {
		String fichierTempo = "src/test/resources/testLireFichierOctets.jpg";
		Path p = Paths.get(fichierTempo);
		
		// parcours du stream et écriture dans le fichier test.jpg.
		List<Byte> lstBytes = GestionFichiers.lireFichierOctets(this.nomFichierImage).collect(Collectors.toList());
		byte[] tableauBytes = new byte[lstBytes.size()];
		for (int i = 0; i < lstBytes.size(); i++) {
			tableauBytes[i] = lstBytes.get(i);
		}
		Files.write(p, tableauBytes);
		
		List<Byte> lstBytes2 = GestionFichiers.lireFichierOctets(this.nomFichierImage).collect(Collectors.toList());
		List<Byte> lstBytes3 = GestionFichiers.lireFichierOctets(fichierTempo).collect(Collectors.toList());
		
		int i = 0;
		for (Byte b : lstBytes2) {
			assertEquals(lstBytes3.get(i), b);
			i++;
		}
		
		// suppression du fichier test.jpg
//		Path testPath = Paths.get("test.jpg");
//		assertTrue(testPath.toFile().delete());
	}
	
	@Test
	void testEcrireFichierOctetsStream() throws IOException {
		String nomFichierEcrit = "src/test/resources/testEcrireFichierOctetsStream.jpg";
		
		List<Byte> lstBytes = GestionFichiers.lireFichierOctets(this.nomFichierImage).collect(Collectors.toList());
		byte[] tableauBytes = new byte[lstBytes.size()];
		for (int i = 0; i < lstBytes.size(); i++) {
			tableauBytes[i] = lstBytes.get(i);
		}
		GestionFichiers.ecrireFichierOctets(tableauBytes, nomFichierEcrit);
		
		Path pathFichierLu = Paths.get(this.nomFichierImage);
		Path pathFichierEcrit = Paths.get(nomFichierEcrit);
		assertEquals(Files.getAttribute(pathFichierLu, "size"), Files.getAttribute(pathFichierEcrit, "size"));
	}

}
