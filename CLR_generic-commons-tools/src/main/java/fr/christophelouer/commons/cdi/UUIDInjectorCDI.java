package fr.christophelouer.commons.cdi;


import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.christophelouer.commons.uuid.GeneratedUUID;
import fr.christophelouer.commons.uuid.UUIDRepresentation;
import fr.christophelouer.commons.uuid.UUIDUtils;

/**
 * producteur CDI pour les annotation GeneratedUUID et Inject.
 * 
 * @author CDT RBN
 *
 */

@ApplicationScoped
public class UUIDInjectorCDI
{
	protected static Log log = LogFactory.getLog(UUIDInjectorCDI.class);

	protected UUIDInjectorCDI()
	{
		// protection mais ouverture pour CDI.
	}

	/**
	 * méthode de génération de UUID sous forme de String HEXA, utilisable via
	 * CDI.
	 * 
	 * @return chaine représentant un UUID en HEXA.
	 * 
	 */
	@Produces
	@GeneratedUUID(representation = UUIDRepresentation.HEXA_STRING)
	public static String generateUUIDHexa()
	{
		log.info("Génération UUID HEXA par @Produces");
		return UUID.randomUUID().toString();
	}

	/**
	 * méthode de génération de UUID sous forme de String BASE64, utilisable via
	 * CDI.
	 * 
	 * @return chaine représentant un UUID en BASE64.
	 * 
	 */
	@Produces
	@GeneratedUUID(representation = UUIDRepresentation.BASE64_STRING)
	public static String generateUUIDBase64()
	{
		log.info("Génération UUID BASE64 par @Produces");
		return Base64.encodeBase64URLSafeString(UUIDUtils.convertToBytes(UUID.randomUUID()));
	}

	/**
	 * méthode de génération de UUID sous forme de tableau d'octets, utilisable
	 * via CDI.
	 * 
	 * @return tableau de 128 octets représentant un UUID.
	 * 
	 */
	@Produces
	@GeneratedUUID(representation = UUIDRepresentation.BYTE_ARRAY)
	public static byte[] generateUUIDByteArray()
	{
		log.info("Génération UUID BYTE_ARRAY par @Produces");
		return UUIDUtils.convertToBytes(UUID.randomUUID());
	}
	
	/**
	 * méthode de génération de UUID sous forme de tableau d'octets, utilisable
	 * via CDI.
	 * 
	 * @return tableau de 128 octets représentant un UUID.
	 * 
	 */
	@Produces
	@GeneratedUUID(representation = UUIDRepresentation.UUID_INSTANCE)
	public static UUID generateUUIDInstance()
	{
		log.info("Génération UUID par @Produces");
		return UUID.randomUUID();
	}
}
