package br.com.carlos.cashMachine.util;

import org.springframework.util.StringUtils;

/**
 * This util class contains all methods to change the value of entity if needed.
 * 
 * @author carlos proença(carlos_proenca@live.com)
 *
 */
public final class MapperUtils {

	public static String changeValueIfExists(String valueToChange, String newValue) {
		if(StringUtils.hasText(newValue)) {
			valueToChange = newValue;
		}
		return valueToChange;
	}
	
	public static Double changeValueIfExists(Double valueToChange, Double newValue) {
		if(newValue != null) {
			valueToChange = newValue;
		}
		return valueToChange;
	}
}
