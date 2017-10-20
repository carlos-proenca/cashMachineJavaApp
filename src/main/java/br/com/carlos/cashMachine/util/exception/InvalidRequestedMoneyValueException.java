package br.com.carlos.cashMachine.util.exception;

/**
 * This class represents the Invalid Requested Money Value business exception
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
public class InvalidRequestedMoneyValueException extends RuntimeException {
	private static final long serialVersionUID = -4582753911372317966L;

	public InvalidRequestedMoneyValueException(String message) {
		super(message);
	}
}