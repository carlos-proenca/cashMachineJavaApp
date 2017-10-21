package br.com.carlos.cashMachine.vo;

import lombok.Data;

/**
 * This class represents a single banknote needed by customer
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
@Data
public class BanknoteVo {
	private String type;
	private Integer quantity;
}
