package net.etrs.vehicule.model.exceptions;

@SuppressWarnings("serial")
public class VehiculeException extends Exception {

	public VehiculeException(String message) {
		super(message);
	}

	public VehiculeException(String message, Throwable cause) {
		super(message, cause);
	}


}
