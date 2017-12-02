package engine;

import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class Locale {
	private static HashMap<String, String> stringBase;
	
	/**
	 * Loads a language
	 * @param lang
	 * @throws FileNotFoundException 
	 */
	public static void load(Language lang) throws FileNotFoundException {
		stringBase = new HashMap<String, String>();
		FileReader fr;
		if (lang == Language.PL) {
			fr = new FileReader(CONST.LANG_PL_PATH);
		} else {
			fr = new FileReader(CONST.LANG_EN_PATH);
		}
		fr.load();
		fr.filterFielded();
		stringBase = fr.getFilteredContents();
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
