package fr.christophelouer.vehicules.model.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import common.dao.exceptions.DaoException;
import fr.christophelouer.vehicules.model.entities.Vehicule;
import fr.christophelouer.vehicules.model.references.C;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VehiculeMemoireDaoImpl extends AbstractVehiculeDao {
	
	private List<Vehicule> persist = new ArrayList<>();
	
	@Override
	public void create(Vehicule v) throws DaoException {
		if(Objects.isNull(v)) {
			throw new DaoException(C.VEHICULE_NON_ATTACHE_EXCEPTION,new NullPointerException());
		}
		if(this.exist(v)) {
			throw new DaoException(C.VEHICULE_EXISTANT_EXCEPTION);
		}
		if(!persist.add(v)) {
			throw new DaoException(C.ERREUR_CREATION_VEHICULE_EXCEPTION);
		}
	}

	@Override
	public void delete(Vehicule v) throws DaoException {
		if(v == null) {
			throw new DaoException(C.VEHICULE_NON_ATTACHE_EXCEPTION,new NullPointerException());
		}
		if(!this.exist(v)) {
			throw new DaoException(C.VEHICULE_INEXISTANT_EXCEPTION);
		}
		if(!persist.remove(v)) {
			throw new DaoException(C.ERREUR_SUPPRESSION_VEHICULE_EXCEPTION);
		}
	}

	@Override
	public boolean exist(Vehicule v) {
		if(persist.contains(v)) {
			return true;
		}
		return false;
	}

	@Override
	public Vehicule read(UUID id) throws DaoException {
		if(Objects.isNull(id)) {
			throw new DaoException(C.UUID_NULL_EXCEPTION, new NullPointerException());
		}
		
		Vehicule resultat = null;
		for (Vehicule v : persist) {
			if(v.getId().equals(id)) {
				resultat = v;
			}
		}
		if(Objects.isNull(resultat)) {
			throw new DaoException(C.VEHICULE_INEXISTANT_EXCEPTION);
		}
		
		return resultat;
	}

	@Override
	public List<Vehicule> readAll() throws DaoException{
		if(this.persist.isEmpty()) {
			throw new DaoException(C.AUCUN_VEHICULE_DISPO_EXCEPTION);
		}
		return Collections.unmodifiableList(persist);
	}


}
