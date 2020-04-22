package fr.christophelouer.model.facade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.christophelouer.vehicules.model.entities.Vehicule;
import fr.christophelouer.vehicules.model.exceptions.VehiculeException;
import fr.christophelouer.vehicules.model.facade.VehiculeFacade;
import fr.christophelouer.vehicules.model.facade.VehiculeFacadeFactory;
import fr.christophelouer.vehicules.model.references.C;

class VehiculeFacadeTest {
	
	private VehiculeFacade vehiculeFacade;

	@BeforeEach
	void setUp() throws Exception {
		this.vehiculeFacade = VehiculeFacadeFactory.getInstanceVehiculeFacade();
	}

	@Test
	void testGetListeVehicules() {
		try {
			List<Vehicule> vehicules = this.vehiculeFacade.getListeVehicules(10);
			// test qu'il y a bien 10 véhicules généré
			assertEquals(10, vehicules.size());
			// test que les voitures sont à l'arrêt
			vehicules.forEach(v->assertFalse(v.isDemarre()));
			
		} catch (VehiculeException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetListeVehiculesRoulant() {
		try {
			List<Vehicule> vehicules = this.vehiculeFacade.getListeVehiculesRoulant(10);

			vehicules.forEach(System.out::println);
			// test qu'il y a bien 10 véhicules généré
			assertEquals(10, vehicules.size());
			// test que les voitures sont démarrée
			vehicules.forEach(v->assertTrue(v.isDemarre()));
			
			// test que les véhicules ont une vitesse supérieure à 0 km/h
			vehicules.forEach(v->assertNotEquals(0, v.getVitesse()));
			// test que les véhicules ont une vitesse inférieure à la vitesse max
			assertTrue(vehicules.stream().noneMatch(v->v.getVitesse() > C.VITESSE_MAX));
		} catch (VehiculeException e) {
			fail(e.getMessage());
		}
	}

}
