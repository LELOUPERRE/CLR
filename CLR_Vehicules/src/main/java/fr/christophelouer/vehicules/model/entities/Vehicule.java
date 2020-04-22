package fr.christophelouer.vehicules.model.entities;

import java.util.UUID;

import fr.christophelouer.vehicules.model.exceptions.VehiculeException;
import fr.christophelouer.vehicules.model.references.C;
import fr.christophelouer.vehicules.model.references.Marque;
import fr.christophelouer.vehicules.model.references.TypeCarburant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(of="id")
@ToString
public abstract class Vehicule {
	@Getter
	private UUID id = UUID.randomUUID();
	@Getter @Setter
	private String immat;
	@Getter @Setter
	private Marque marque;
	@Getter @Setter
	private String modele;
	@Getter @Setter
	private double volumeReservoirLitre;
	@Getter @Setter
	private TypeCarburant typeCarburant;
	
	@Getter
	private boolean demarre = false;
	
	@Getter
	private int vitesse; 
	
	/**
	 * Permet de démarrer le véhicule.
	 * @throws VehiculeException Exception levée si le véhicule est déjà démarré.
	 */
	public void demarrer() throws VehiculeException {
		if(this.demarre) {
			throw new VehiculeException(C.VEHICULE_DEMARRE_EXCEPTION);
		}
		
		this.demarre = true;
	}
	
	/**
	 * permet d'arrêter le véhicule.
	 * @throws VehiculeException Exception levée si le véhicule roule à une vitesse 
	 * supérieure à 0 km/h ou s'il est arrêté.
	 */
	public void arreter() throws VehiculeException {
		if(!this.demarre) {
			throw new VehiculeException(C.VEHICULE_ARRETE_EXCEPTION);
		}
		if(this.vitesse > 0) {
			throw new VehiculeException(C.VEHICULE_NE_PEUT_ETRE_ARRETE_EXCEPTION);
		}
		this.demarre = false;
	}
	
	/**
	 * permet de faire accelerer le véhicule jusqu'à la vitesse indiquée en paramètre.
	 * @param vitesse vitesse à atteindre.
	 * @throws VehiculeException Exception levée si le véhicule n'est pas démarré, ou s'il
	 * roule à une vitesse supérieure à celle demandée.
	 */
	public void accelerer(int vitesse) throws VehiculeException {
		if(!this.demarre) {
			throw new VehiculeException(C.VEHICULE_NON_DEMARRE_EXCEPTION);
		}
		if(this.vitesse > vitesse) {
			throw new VehiculeException(C.VITESSE_TROP_ELEVEE_EXCEPTION);
		}
		this.vitesse = vitesse; 
	}
	
	/**
	 * permet de réduire la vitesse du véhicule à la vitesse passée en paramètre.
	 * Si la vitesse courant est inférieur à la vitesse demandée, le véhicule rest 
	 * à la même vitesse.
	 * @param vitesse
	 * @throws VehiculeException Exception levée si le véhicule n'est pas démarré, ou s'il
	 * roule à une vitesse plus basse que celle demandée.
	 */
	public void decelerer(int vitesse) throws VehiculeException {
		if(!this.demarre) {
			throw new VehiculeException(C.VEHICULE_NON_DEMARRE_EXCEPTION);
		}
		if(vitesse > this.vitesse) {
			throw new VehiculeException(C.VITESSE_TROP_BASSE_EXCEPTION);
		}
		this.vitesse = vitesse;
	}


}
