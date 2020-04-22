package fr.christophelouer.vehicules.model.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import common.dao.Dao;
import common.dao.exceptions.DaoException;
import fr.christophelouer.vehicules.model.dao.VehiculeDaoFactory;
import fr.christophelouer.vehicules.model.entities.Vehicule;
import fr.christophelouer.vehicules.model.exceptions.VehiculeException;
import fr.christophelouer.vehicules.model.references.C;

public class VehiculeFacadeImpl implements VehiculeFacade {
	private Dao<Vehicule, UUID> dao = VehiculeDaoFactory.daoVehiculeFactory();

	public VehiculeFacadeImpl() {
		dao.init();
	}

	@Override
	public List<Vehicule> getListeVehicules(int nombreVehicule) throws VehiculeException {
		List<Vehicule> liste = new ArrayList<>();
		for (int i = 0; i < nombreVehicule; i++) {
			liste.add(this.tirageAleatoireVehicule());
		}

		return liste;
	}

	private Vehicule tirageAleatoireVehicule() throws VehiculeException {
		try {
			Random r = new Random();
			List<Vehicule> listeVehicule = dao.readAll();
			int indice = r.nextInt(listeVehicule.size());
			return listeVehicule.get(indice);

		} catch (DaoException e) {
			throw new VehiculeException(C.FACADE_PAS_DE_VEHICULE_DISPO_EXCEPTION, e);
		}

	}

	@Override
	public List<Vehicule> getListeVehiculesRoulant(int nombreVehicule) throws VehiculeException {
		List<Vehicule> vehicules = this.getListeVehicules(nombreVehicule);
		try {
			for (Vehicule v : vehicules) {
				if(!v.isDemarre()) {
					v.demarrer();
					v.accelerer(calculerVitesse());
				}
			}
		} catch (VehiculeException e) {
			// logger
			e.printStackTrace();
		}

		return vehicules;

	}

	private int calculerVitesse() {
		int resultat = 0;
		Random r = new Random();
		do {
			resultat = r.nextInt(C.VITESSE_MAX+1);
		} while (resultat < 0);
		
		return resultat;
	}
}
