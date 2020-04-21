package net.etrs.vehicule.model.dao;

import java.util.UUID;

import common.dao.Dao;
import net.etrs.vehicule.model.entities.Vehicule;

public final class VehiculeDaoFactory {

	private VehiculeDaoFactory() {
		super();
	}

	public static Dao<Vehicule, UUID> daoVehiculeFactory() {
		return new VehiculeMemoireDaoImpl();
	}
}
