package br.com.carlos.cashMachine.util.exception;

/**
 * This class represents the  Unauthorized Withdrawal business exception
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
public class UnauthorizedWithdrawalException extends RuntimeException {
	private static final long serialVersionUID = -4682358711900291835L;
	
	public UnauthorizedWithdrawalException(String message) {
		super(message);
	}
}
