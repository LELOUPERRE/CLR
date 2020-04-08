package common.view.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import common.Constantes;

public final class Formatage {

	private Formatage() {
	}

	/**
	 * Formate une date selon le pattern indiqué.
	 * Si le pattern est invalide, la date sera affichée selon le pattern defini
	 * par défaut.
	 * @param date
	 * @param pattern
	 * @return la date formatée selon le pattern indique.
	 */
	public static String formaterDate(final Date date,final String pattern) {
		String retour = "";
		SimpleDateFormat sdf;
		try {
			sdf = new SimpleDateFormat(pattern);
		} catch (IllegalArgumentException e) {
			sdf = new SimpleDateFormat(Constantes.PATTERN_DATE_PAR_DEFAUT);
		}

		if(!Objects.isNull(date)) {
			retour = sdf.format(date);
		}
		
		return retour;
	}

}
