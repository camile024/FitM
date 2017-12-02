package engine;

import java.util.HashMap;

public abstract class Locale {
	private static HashMap<String, String> stringBase;
	
	/**
	 * Loads a language
	 * @param lang
	 */
	public static void load(Language lang) {
		stringBase = new HashMap<String, String>();
	}
	
	/**
	 * Returns a localized string based on the key
	 * @param key	string key
	 * @return	value in currently selected language
	 */
	public static String getString(String key) {
		if (stringBase.get(key) == null) {
			return key;
		} else {
			return stringBase.get(key);
		}
	}
	
}
