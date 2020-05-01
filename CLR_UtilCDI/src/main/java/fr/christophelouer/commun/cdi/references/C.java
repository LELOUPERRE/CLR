package fr.christophelouer.commun.cdi.references;

import fr.christophelouer.commun.cdi.annotations.MethodWeldExecute;

public final class C {

	public static final String EXECUTION_WELD_EXCEPTION = "Exécution de la classe imossible dans le context Weld";
	public static final String METHODE_A_EXECUTEE_NON_TROUVEE_EXCEPTION = String.format("Aucune méthode n'est annotée par %s",MethodWeldExecute.class.getSimpleName());

	private C() {
	}

}
