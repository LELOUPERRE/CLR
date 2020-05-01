package fr.christophelouer.dao.common.dao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public final class BDDUtils {

	private BDDUtils() {
	}

	/**
	 * Retourne une connexion vers une base de données. Les paramètres
	 * de connexion sont transmis dans par un flux de type properties contenant
	 * les propriétés suivantes :
	 * DRIVERS=mysql
	 * PORT=3306
	 * HOST=localhost
	 * BDD_NAME=nom_base_de_donnees
	 * USER=login
	 * PASSWORD=mot_de_passe
	 * @param is flux de type properties.
	 * @return un Optional contenant une Connection vers la base de données. l'Optional
	 * peut être vide s'il n'est pas possible d'obtenir une connexion vers la base.
	 */
	public static Optional<Connection> getConnection(InputStream is) {
		Optional<Connection> oCnx = Optional.empty();
		Optional<Properties> oProp = lireProperties(is);
		try {
			if(oProp.isPresent()) {
				Properties prop = oProp.get();
				oCnx = Optional.ofNullable(DriverManager.getConnection(formaterUrlBdd(prop.getProperty("DRIVER"), 
																					  prop.getProperty("HOST"), 
																					  prop.getProperty("PORT"), 
																					  prop.getProperty("BDD_NAME")), 
														 			   prop.getProperty("USER"), 
														 			   prop.getProperty("PASSWORD")));
			}
		} catch (SQLException e) {
		}
		
		return oCnx;
	}
	
	private static Optional<Properties> lireProperties(InputStream is) {
		Optional<Properties> oProp = Optional.empty();
		try {
			Properties prop = new Properties();
			prop.load(is);
			oProp = Optional.ofNullable(prop);
		} catch (IOException e) {
		}
		return oProp;
	}

	/**
	 * Retourne une connexion vers une base de données. 
	 * @param driver driver du SGBD (ex : mysql)
	 * @param port port de connexion à la base de données.
	 * @param host référence du serveur où se trouve la base de données.
	 * @param bddName nom de la base de données.
	 * @param user login de connexion à la base de données.
	 * @param pwd mot de passe associé au login.
	 * @return un Optional contenant une Connection vers la base de données. l'Optional
	 * peut être vide s'il n'est pas possible d'obtenir une connexion vers la base.
	 */
	public static Optional<Connection> getConnection(String driver,String port,String host,String bddName,String user,String pwd) {
		Optional<Connection> oCnx = Optional.empty();
		try {
			Connection cnx = DriverManager.getConnection(formaterUrlBdd(driver,host,port,bddName), user, pwd);
			oCnx = Optional.ofNullable(cnx);
		} catch (SQLException e) {
			
		}
		return oCnx;
	}

	private static String formaterUrlBdd(String driver, String host, String port, String bddName) {
		return String.format("jdbc:%s://%s:%s/%s", driver,host,port,bddName);
	}

}
