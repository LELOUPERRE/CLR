package fr.christophelouer.dao.common.dao.utils;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.UUID;

import fr.christophelouer.dao.common.dao.exceptions.SetIdentifiantException;

public final class UUIDUtils {
	
	private UUIDUtils() {
		super();
	}

	/** 
	 * retourne un tableau d'octets qui représente un UUID sur 128 bits.
	 * 
	 * @param uuid
	 * 		uuid à convertir
	 * @return
	 * 		représentation sous forme d'un tableau d'octets.
	 */
	public static byte[] toByteArray(UUID uuid)
	{
		// allocation d'un buffer de 16 octets (128 bits).
		ByteBuffer byteBuffer = ByteBuffer.allocate(16);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	/**
	 * retourne l'instance de UUID qui correspond au tableau d'octets.
	 * 
	 * @param data
	 * 		tableau d'octets à convertir
	 * @return
	 * 		instance de UUID.
	 */
	public static UUID fromByteArray(byte[] data)
	{
		ByteBuffer bb = ByteBuffer.wrap(data);
		long firstLong = bb.getLong();
		long secondLong = bb.getLong();
		return new UUID(firstLong, secondLong);
	}
	
	/**
	 * Modifie l'identifiant d'un objet en lui affectant keyValue.
	 * Cette méthode peut être utile lors de la lecture en base de données d'une entité, pour 
	 * modifier après contruction d'objets l'identifiant technique (ex: identifiant de type UUID).
	 * @param entity objet concerné par la modification.
	 * @param keyName nom de l'attribut correspondant à l'identifiant technique.
	 * @param keyValue valeur de l'identifiant à affecter.
	 * @throws SetIdentifiantException Exception levée si l'opération n'a pu être réalisée.
	 */
	public static <T,K> void setIdentifiant(T entity,String keyName,K keyValue) throws SetIdentifiantException {
		Field[] fields = entity.getClass().getDeclaredFields();
		boolean existe = false;
		for(Field f : fields) {
			if(keyName.equals(f.getName())) {
				existe = true;
				f.setAccessible(true);
				try {
					f.set(entity,keyValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new SetIdentifiantException(String.format("L'attribut %s n'existe pas dans la classe %s", keyName,entity.getClass().getName()),e.getCause());
				}
				f.setAccessible(false);
			}
		}
		
		if(!existe) {
			throw new SetIdentifiantException(String.format("L'attribut %s n'existe pas dans la classe %s", keyName,entity.getClass().getName()));
		}
		
		
	}		


	public static <T,K> void setIdentifiant2(T entity,K keyValue) throws Exception {

		
		Field[] fields = entity.getClass().getDeclaredFields();
		boolean existe = false;
		for(Field f : fields) {
			if (f.isAnnotationPresent(IdTech.class)) {
				existe = true;
				f.setAccessible(true);
				f.set(entity,keyValue);
				f.setAccessible(false);
			}
		}
		
		if(!existe) {
			throw new Exception(String.format("Pas d'attribut annoté par %s n'existe pas dans la classe %s", IdTech.class,entity.getClass().getName()));
		}
	}
	
	
}
