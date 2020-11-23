package fr.jayrex.calendrier.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

	public Integer getInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException exception) {
			return null;
		}
	}

}