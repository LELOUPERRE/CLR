package fr.christophelouer.dao.model.entities;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(of="id")
public class Personne {
	@Getter
	UUID id = UUID.randomUUID();
	@Getter @Setter
	String nom;
	@Getter @Setter
	String prenom;
	public Personne(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}
	
	
	

}
