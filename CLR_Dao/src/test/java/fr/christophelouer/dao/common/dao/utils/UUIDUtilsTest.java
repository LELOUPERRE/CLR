package fr.christophelouer.dao.common.dao.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.christophelouer.dao.common.dao.exceptions.SetIdentifiantException;
import fr.christophelouer.dao.common.dao.utils.UUIDUtils;
import fr.christophelouer.dao.model.entities.EntitiesFactory;
import fr.christophelouer.dao.model.entities.Personne;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
class UUIDUtilsTest {
	Personne personne;

	@BeforeEach
	void setUp() throws Exception {
		this.personne = EntitiesFactory.fabriquerPersonne("HADDOCK","Archibald");
	}

	@Test
	void testSetIdentifiant() {
		try {
			Personne p = EntitiesFactory.fabriquerPersonne("HADDOCK", "HADDOCK");
			assertNotEquals(this.personne.getId(), p.getId());
			UUIDUtils.setIdentifiant(p, "id", this.personne.getId());
			assertEquals(this.personne.getId(), p.getId());
		} catch (SetIdentifiantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	}

}
