package fr.christophelouer.vehicules.model.dao;

import java.util.UUID;

import common.dao.Dao;
import fr.christophelouer.vehicules.model.entities.Vehicule;

public final class VehiculeDaoFactory {

	private VehiculeDaoFactory() {
		super();
	}

	public static Dao<Vehicule, UUID> daoVehiculeFactory() {
		return new VehiculeMemoireDaoImpl();
	}
}
