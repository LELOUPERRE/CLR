package fr.christophelouer.commons.tests.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")

// lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = "name")

// JPA
@Entity
@Table(name = "DATA_BEAN")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

/**
 * TODO : écrire.
 * 
 * @author CDT RBN
 *
 */
public class BusinessBean extends AbstractBusinessBean
{
	@Getter
	@Setter
	String name;
}
