package br.com.carlos.cashMachine.util.exception;

/**
 * This class represents the customer Not found business exception
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 8684582221547064457L;
	
	public CustomerNotFoundException(String message) {
		super(message);
	}
}