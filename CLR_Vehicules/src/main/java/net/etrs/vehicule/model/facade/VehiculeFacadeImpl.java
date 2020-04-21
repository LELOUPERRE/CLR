package net.etrs.vehicule.model.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import common.dao.Dao;
import common.dao.exceptions.DaoException;
import net.etrs.vehicule.model.dao.VehiculeDaoFactory;
import net.etrs.vehicule.model.entities.Vehicule;
import net.etrs.vehicule.model.exceptions.VehiculeException;
import net.etrs.vehicule.model.references.C;

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

	public static void main(String[] args) throws VehiculeException {
		VehiculeFacade vf = VehiculeFacadeFactory.getInstanceVehiculeFacade();
		int i = 0;
		for (Vehicule v : vf.getListeVehicules(100)) {
			i++;
			System.out.println(i + " - " + v.toString());
		}
	} 

	@Override
	public List<Vehicule> getListeVehiculesRoulant(int nombreVehicule) throws VehiculeException {
		List<Vehicule> vehicules = this.getListeVehicules(nombreVehicule);
		try {
			Random r = new Random();
			for (Vehicule v : vehicules) {
				if(!v.isDemarre()) {
					v.demarrer();
					v.accelerer(r.nextInt(C.VITESSE_MAX+1));
				}
			}
		} catch (VehiculeException e) {
			// logger
			e.printStackTrace();
		}

		return vehicules;

	}
}
