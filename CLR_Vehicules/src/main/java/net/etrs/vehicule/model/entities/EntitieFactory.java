package net.etrs.vehicule.model.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.etrs.vehicule.model.references.Marque;
import net.etrs.vehicule.model.references.TypeCarburant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntitieFactory {

	public static Voiture fabriquerVoiture(String immat, Marque marque, String modele, TypeCarburant typeCarburant,
			double volumeReservoirLitre) {
		Voiture v = new Voiture();
		v.setImmat(immat);
		v.setMarque(marque);
		v.setModele(modele);
		v.setTypeCarburant(typeCarburant);
		v.setVolumeReservoirLitre(volumeReservoirLitre);
		return v;
	}

	public static Camion fabriquerCamion(String immat, Marque marque, String modele, TypeCarburant typeCarburant,
			double volumeReservoirLitre) {
		Camion v = new Camion();
		v.setImmat(immat);
		v.setMarque(marque);
		v.setModele(modele);
		v.setTypeCarburant(typeCarburant);
		v.setVolumeReservoirLitre(volumeReservoirLitre);
		return v;
	}
	

}
