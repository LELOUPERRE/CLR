package fr.christophelouer.commons.tests.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import fr.christophelouer.commons.uuid.GeneratedUUID;
import lombok.Getter;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractBusinessBean implements Serializable
{

	@Id
	@GeneratedUUID
	@Getter
	private String id;

	@Version
	@Getter
	private Long version;
}

