package fr.christophelouer.commons.tests.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("serial")
@Entity
@Table(name = "DATA_BEAN")
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = "nameSpecialized")
public class BusinessBeanSpecialized extends BusinessBean
{
	@Getter
	@Setter
	String nameSpecialized;
}
