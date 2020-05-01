package fr.christophelouer.commons.uuid;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * classe qui convertit des UUID vers un tableau de bytes et inversement.
 *
 * @author CDT RBN
 *
 */

public final class UUIDUtils
{

	private UUIDUtils()
	{
		// protection du constructeur pour cette classe "utilitaire"
	}

	/**
	 * convertit un UUID en tableau d'octets (16 octets = 128 bits)
	 *
	 * @param uuid
	 *            uuid à obtenir sous forme de byte[]
	 * @return tableau d'octets contenant la réprésentation de l'UUID
	 */
	public static final byte[] convertToBytes(final UUID uuid)
	{
		final byte[] buffer = new byte[16];
		final ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return buffer;
	}

	/***
	 * convertit un tableau d'octets (16 octets = 128 bits) en UUID.
	 *
	 * @param buffer
	 *            tableau d'octets à convertir en UUID
	 * @return instance du UUID.
	 */
	public static final UUID convertFromBytes(final byte[] buffer)
	{
		if (buffer.length == 16)
		{
			final ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
			return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
		}
		else
		{
			throw new IllegalArgumentException("Le tableau d'octets ne représente pas un UUID valide : 16 octets obligatoire (128 bits)");
		}
	}
}
