package fr.christophelouer.vehicules.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.christophelouer.vehicules.model.exceptions.VehiculeException;
import fr.christophelouer.vehicules.model.references.Marque;
import fr.christophelouer.vehicules.model.references.TypeCarburant;

class VoitureTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testDemarrer() {
		try {
			Voiture v = EntitieFactory.fabriquerVoiture("AA-123-BB", Marque.AUDI, "A3", TypeCarburant.GAZOLE, 65);
			// test que la voiture n'est pas demarrée
			assertFalse(v.isDemarre());
			v.demarrer();
			// test que la voiture est demarrée
			assertTrue(v.isDemarre());
			// test de tentative de démarrage de la voikture alors qu'elle est déjà demarrée
			assertThrows(VehiculeException.class, ()->v.demarrer());
		} catch (VehiculeException e) {
			fail("Exception levée alors que la voiture n'est pas démarrée et que l'on tente de la démarrer");
		}
	}

	@Test
	void testArreter() {
		try {
			Voiture v = EntitieFactory.fabriquerVoiture("AA-123-BB", Marque.AUDI, "A3", TypeCarburant.GAZOLE, 65);
			// test que la voiture n'est pas demarrée
			assertFalse(v.isDemarre());
			// test de tentative d'arrêt d'une voiture qui n'est pas démarrée
			assertThrows(VehiculeException.class, ()->v.arreter());
			v.demarrer();
			// test que la voiture est demarrée
			assertTrue(v.isDemarre());
			v.accelerer(50);
			// test que la voiture roule à 50 km/h
			assertEquals(50, v.getVitesse());
			// test de tentative d'arrêt d'une voiture qui roule
			assertThrows(VehiculeException.class, ()->v.arreter());

		} catch (VehiculeException e) {
			fail("Exception levée alors que la voiture n'est pas démarrée et que l'on tente de la démarrer");
		}
	}

	@Test
	void testAccelerer() {
		try {
			Voiture v = EntitieFactory.fabriquerVoiture("AA-123-BB", Marque.AUDI, "A3", TypeCarburant.GAZOLE, 65);
			// test que la voiture n'est pas demarrée
			assertFalse(v.isDemarre());
			// test de tentative de faire accelerer une voiture qui n'est pas démarrée
			assertThrows(VehiculeException.class, ()->v.accelerer(50));
			v.demarrer();
			// test que la voiture est demarrée
			assertTrue(v.isDemarre());
			v.accelerer(50);
			// test que la voiture roule à 50 km/h
			assertEquals(50, v.getVitesse());
			// test de tentative de faire accelerer une voiture à une vitesse inférieure à seul à laquelle elle roule
			assertThrows(VehiculeException.class, ()->v.accelerer(40));
			
			v.accelerer(100);
			// test que la voiture roule bien à 100 km/h
			assertEquals(100, v.getVitesse());

		} catch (VehiculeException e) {
			fail("Exception levée alors que la voiture n'est pas démarrée et que l'on tente de la démarrer");
		}
	}

	@Test
	void testDecelerer() {
		try {
			Voiture v = EntitieFactory.fabriquerVoiture("AA-123-BB", Marque.AUDI, "A3", TypeCarburant.GAZOLE, 65);
			// test que la voiture n'est pas demarrée
			assertFalse(v.isDemarre());
			// test de tentative de faire decelerrer une voiture qui n'est pas démarrée
			assertThrows(VehiculeException.class, ()->v.decelerer(50));
			v.demarrer();
			// test que la voiture est demarrée
			assertTrue(v.isDemarre());
			v.accelerer(50);
			// test que la voiture roule à 50 km/h
			assertEquals(50, v.getVitesse());
			// test de tentative de faire décelerer une voiture à une vitesse supérieure à seul à laquelle elle roule
			assertThrows(VehiculeException.class, ()->v.decelerer(100));
			
			v.decelerer(40);
			// test que la voiture roule bien à 40 km/h
			assertEquals(40, v.getVitesse());

		} catch (VehiculeException e) {
			fail("Exception levée alors que la voiture n'est pas démarrée et que l'on tente de la démarrer");
		}
	}

}
