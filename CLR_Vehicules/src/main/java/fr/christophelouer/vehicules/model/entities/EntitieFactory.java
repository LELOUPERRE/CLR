package fr.christophelouer.vehicules.model.entities;

import fr.christophelouer.vehicules.model.references.Marque;
import fr.christophelouer.vehicules.model.references.TypeCarburant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
