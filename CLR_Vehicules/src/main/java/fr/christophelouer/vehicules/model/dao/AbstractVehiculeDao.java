package fr.christophelouer.vehicules.model.dao;

import java.util.UUID;

import common.dao.Dao;
import common.dao.exceptions.DaoException;
import fr.christophelouer.vehicules.model.entities.EntitieFactory;
import fr.christophelouer.vehicules.model.entities.Vehicule;
import fr.christophelouer.vehicules.model.references.Marque;
import fr.christophelouer.vehicules.model.references.TypeCarburant;

public abstract class AbstractVehiculeDao implements Dao<Vehicule, UUID> {
	@Override
	public void init() {
		creerVehicule(true,"AA-123-AA", Marque.AUDI, "A4", TypeCarburant.GAZOLE, 65.0);
		creerVehicule(true,"BB-234-BB", Marque.FIAT, "PANDA", TypeCarburant.SP95, 35.0);
		creerVehicule(true,"CC-345-CC", Marque.ALFA_ROMEO, "MITO", TypeCarburant.SP95, 45.0);
		creerVehicule(true,"DD-456-DD", Marque.PEUGEOT, "504", TypeCarburant.SP98, 56.0);
		creerVehicule(true,"EE-789-EE", Marque.RENAULT, "R21", TypeCarburant.GPL, 65);
		creerVehicule(true,"FF-123-FF", Marque.AUDI, "A1", TypeCarburant.SP95, 45.0);
		creerVehicule(true,"GG-234-GG", Marque.MERCEDES, "VIANO", TypeCarburant.GAZOLE, 75.0);
		creerVehicule(true,"HH-345-HH", Marque.RENAULT, "CLIO", TypeCarburant.SP95, 45.0);
		creerVehicule(true,"II-456-II", Marque.VOLKSWAGEN, "GOLF", TypeCarburant.GAZOLE, 50.0);
		creerVehicule(true,"JJ-789-JJ", Marque.PEUGEOT, "106", TypeCarburant.SP95, 45.0);
		creerVehicule(false,"KK-123-KK", Marque.MERCEDES, "ALTROS", TypeCarburant.GAZOLE, 200.0);
	}

	private void creerVehicule(boolean voiture,String immat,Marque marque,String modele,TypeCarburant typeCarburant,double volumeReservoirLitre) {
		try {
			Vehicule v;
			if(voiture) {
				v = EntitieFactory.fabriquerVoiture(immat, marque, modele, typeCarburant, volumeReservoirLitre);
			}
			else {
				v = EntitieFactory.fabriquerCamion(immat, marque, modele, typeCarburant, volumeReservoirLitre);
			}
			this.create(v);
		} catch (DaoException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
