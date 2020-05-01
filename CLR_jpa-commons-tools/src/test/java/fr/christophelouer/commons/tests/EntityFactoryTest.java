package fr.christophelouer.commons.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

import fr.christophelouer.commons.jpa.EntityFactory;
import lombok.ToString;
import lombok.extern.apachecommons.CommonsLog;
import net.entetrs.commons.uuid.GeneratedUUID;

/**
 * testeur d'EntityFactory générique et d'injection UUID.
 * 
 * @author CDT RBN
 *
 */

@CommonsLog
public class EntityFactoryTest
{

	private static String UUID_REGEXP = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

	/**
	 * classe interne pour le test d'injection UUID.
	 *
	 */
	@ToString
	public static class TestBeanUUIDInjector
	{
		@GeneratedUUID
		private UUID idUUID;	
	}

	@Test
	public void testGeneratedUUIDInjector()
	{
		EntityFactory<TestBeanUUIDInjector> factory = new EntityFactory<>(TestBeanUUIDInjector.class);
		TestBeanUUIDInjector bean = factory.newInstance();
		assertNotNull(bean);
		log.info(bean);
		assertNotNull(bean.idUUID);		
	}
	
	/**
	 * classe interne pour le test d'injection String.
	 *
	 */
	@ToString
	public static class TestBeanStringInjector
	{
		@GeneratedUUID
		private String idStringHexa;
		
		@GeneratedUUID
		private String idStringBase64;
	}
	
	@Test
	public void testGeneratedStringInjector()
	{
		EntityFactory<TestBeanStringInjector> factory = new EntityFactory<>(TestBeanStringInjector.class);
		TestBeanStringInjector bean = factory.newInstance();
		assertNotNull(bean);
		log.info(bean);
		assertNotNull(bean.idStringBase64);	
		assertNotNull(bean.idStringHexa);	
		assertTrue(bean.idStringHexa.matches(UUID_REGEXP));
	}
	
	/**
	 * classe interne pour le test d'injection tableau de bytes.
	 *
	 */
	@ToString
	public static class TestBeanByteInjector
	{
		@GeneratedUUID
		private byte[] idBytes = new byte[16];
	}
	
	@Test
	public void testGeneratedBytesInjector()
	{
		EntityFactory<TestBeanByteInjector> factory = new EntityFactory<>(TestBeanByteInjector.class);
		TestBeanByteInjector bean = factory.newInstance();
		assertNotNull(bean);
		log.info(bean);
		assertNotNull(bean.idBytes);	
		assertTrue(UUID.nameUUIDFromBytes(bean.idBytes).toString().matches(UUID_REGEXP));
	}
	
	/**
	 * classe interne pour le test d'injection directe d'un instance de UUID.
	 *
	 */
	@ToString
	public static class TestBeanUUIDInstanceInjector
	{
		@GeneratedUUID
		private UUID uuid;
	}
	
	@Test
	public void testGeneratedUUIDInstanceInjector()
	{
		EntityFactory<TestBeanUUIDInstanceInjector> factory = new EntityFactory<>(TestBeanUUIDInstanceInjector.class);
		TestBeanUUIDInstanceInjector bean = factory.newInstance();
		assertNotNull(bean);
		log.info(bean);
		assertNotNull(bean.uuid);	
		assertTrue(bean.uuid.toString().matches(UUID_REGEXP));
	}
	
	/**
	 * test la factory paramétrée avec un Supplier <T>.
	 */
	@Test
	public void testEntityFactorySupplier()
	{
		EntityFactory<TestBeanUUIDInstanceInjector> factory = new EntityFactory<>(TestBeanUUIDInstanceInjector::new);
		TestBeanUUIDInstanceInjector bean = factory.newInstance();
		assertNotNull(bean);
		log.info(bean);
		assertNotNull(bean.uuid);	
		assertTrue(bean.uuid.toString().matches(UUID_REGEXP));
	}
}
