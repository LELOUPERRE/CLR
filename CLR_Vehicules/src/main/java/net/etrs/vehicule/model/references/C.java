package net.etrs.vehicule.model.references;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class C {

	public static final String ERREUR_CREATION_VEHICULE_EXCEPTION = "Erreur de création de véhicule";
	public static final String VEHICULE_EXISTANT_EXCEPTION = "Tentative de création d'un véhicule déjà présent";
	public static final String VEHICULE_NON_ATTACHE_EXCEPTION = "Véhicule non attaché";
	public static final String VEHICULE_INEXISTANT_EXCEPTION = "Tentative de suppression d'un véhicule inexistant";
	public static final String ERREUR_SUPPRESSION_VEHICULE_EXCEPTION = "Erreur de suppression de véhicule";
	public static final String UUID_NULL_EXCEPTION = "UUID non attaché";
	public static final String AUCUN_VEHICULE_DISPO_EXCEPTION = "Aucun véhicule n'est disponible";
		
	public static final String VEHICULE_NE_PEUT_ETRE_ARRETE_EXCEPTION = "Le véhicule roule trop vite pour être arrêté";
	public static final String VEHICULE_NON_DEMARRE_EXCEPTION = "Le véhicule n'est pas démarré";
	public static final String VITESSE_TROP_ELEVEE_EXCEPTION = "Le véhicule roule à une vitesse plus élevée que celle demandée";
	public static final String VITESSE_TROP_BASSE_EXCEPTION = "Le véhicule roule à une vitesse plus basse que celle demandée";
	public static final String VEHICULE_ARRETE_EXCEPTION = "Le véhicule est arrêté";
	public static final String VEHICULE_DEMARRE_EXCEPTION = "Le véhicule est démarré";

	public static final int VITESSE_MAX = 200;
	public static final String FACADE_PAS_DE_VEHICULE_DISPO_EXCEPTION = "Pas de véhicule disponible";
}
