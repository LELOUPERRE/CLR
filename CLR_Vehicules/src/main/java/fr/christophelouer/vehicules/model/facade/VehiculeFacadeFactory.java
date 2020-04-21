package fr.christophelouer.vehicules.model.facade;

public final class VehiculeFacadeFactory {

	private static VehiculeFacade instanceVehiculeFacade = null;
	
	private VehiculeFacadeFactory() {
	}

	public static VehiculeFacade getInstanceVehiculeFacade() {
		if(instanceVehiculeFacade == null) {
			instanceVehiculeFacade = new VehiculeFacadeImpl();
		}
		return instanceVehiculeFacade;
	}
}
