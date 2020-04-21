package net.etrs.vehicule.model.facade;

import java.util.List;

import net.etrs.vehicule.model.entities.Vehicule;
import net.etrs.vehicule.model.exceptions.VehiculeException;

public interface VehiculeFacade {
	/**
	 * Méthode retournant une liste de véhicules à l'arrêt. 
	 * Un même véhicule peut apparaître plusieurs fois dans la liste.
	 * @param nombreVehicule
	 * nombre de véhichules demandé
	 * @return
	 * Liste des véhicules.
	 * @throws VehiculeException 
	 */
	public List<Vehicule> getListeVehicules(int nombreVehicule) throws VehiculeException;
	/**
	 * Méthode retournant une liste de véhicules démarrés et roulant à une vitesse entre
	 * 0 et 200 km/h.
	 * @param nombreVehicule
	 * nombre de véhichules demandé
	 * @return
	 * Liste des véhicules.
	 * @throws VehiculeException 
	 */
	public List<Vehicule> getListeVehiculesRoulant(int nombreVehicule) throws VehiculeException;
}
