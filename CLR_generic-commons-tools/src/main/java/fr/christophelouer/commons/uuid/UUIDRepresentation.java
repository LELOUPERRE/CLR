package fr.christophelouer.commons.uuid;

/**
 * mode de représenation d'un UUID. 
 * Cet enum est utilisée par et pour les injections CDI de GeneratedUUID.
 * 
 * @author CDT RBN
 *
 */
public enum UUIDRepresentation
{
	/**
	 * sous forme d'un tableau d'octets bruts.
	 */
	BYTE_ARRAY, 
	
	/**
	 * sous forme d'une chaine Hexadécimale, conforme à 
	 * la spécification RFC 4122.
	 */
	HEXA_STRING, 
	
	/**
	 * sous forme encodée en base 64.
	 */
	BASE64_STRING,
	
	/**
	 * sous forme d'une instance de la classe UUID.
	 */
	UUID_INSTANCE;
}
