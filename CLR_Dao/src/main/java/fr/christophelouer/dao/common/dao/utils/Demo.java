package fr.christophelouer.dao.common.dao.utils;

import java.util.UUID;




public class Demo {

	@IdTech
	private String id = UUID.randomUUID().toString();
	private String nom;
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getId() {
		return id;
	}

	public static void main(String[] args) throws Exception {
		
		Demo d = new Demo();
		System.out.println("id avant modif : "+d.getId().toString());
//		UUIDUtils.setIdentifiant(d, "id","test");
		UUIDUtils.setIdentifiant2(d, "test");
		System.out.println("id après modif : "+d.getId());
		
		
	}

}
