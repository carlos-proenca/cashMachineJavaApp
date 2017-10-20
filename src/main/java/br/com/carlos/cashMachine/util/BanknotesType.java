package br.com.carlos.cashMachine.util;

/**
 * The banknotes type enum
 * This class contains all the banknote available to be calculated into API
 * To increase the calculation precision just add a new banknote type 
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
public enum BanknotesType {
	
	ONE_HUNDRED (100, "R$ 100"),
	FIFTY		(50,  "R$ 50"),
	TWENTY		(20,  "R$ 20"),
	TEN			(10,  "R$ 10");
	
	private Integer value;
	private String description;
	
	BanknotesType(Integer value,  String description){
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}
}
