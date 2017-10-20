package br.com.carlos.cashMachine.vo;

import java.util.List;

import lombok.Data;

/**
 * This class represents the banknotes response with all banknotes needed to customer
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
@Data
public class BanknotesResponseVo {
	
	private Double totalRequested;
	
	private List<BanknoteVo> banknotes;
}
