package engine;

import java.io.FileNotFoundException;

public abstract class Settings {
	private static Language lang = Language.PL;
	private static boolean firstRun = true;
	private static String fileName = CONST.APP_CFG_PATH;

	
	public static void loadSettings() throws FileNotFoundException {
		FileReader fr = new FileReader(fileName);
		fr.load();
		fr.filterFielded();
		
		firstRun = Boolean.valueOf(fr.getField(CONST.CFG_FIRST_RUN));
		
		String language = fr.getField(CONST.CFG_LANG);
		if (language.equals("PL")) {
			setLanguage(Language.PL);
		} else if (language.equals("EN")) {
			setLanguage(Language.EN);
		}
	}
	
	public static void setLanguage(Language lang) throws FileNotFoundException {
		Settings.lang = lang;
		Locale.load(lang);
		saveSettings();
	}
	
	public static Language getLanguage() {
		return lang;
	}
	
	public static boolean getFirstRun() {
		return firstRun;
	}
	
	public static String getFileName() {
		return fileName;
	}
	
	public static void setFileName(String fileName) {
		Settings.fileName = fileName; 
	}
	
	public static void markFirstRun() {
		firstRun = false;
		saveSettings();
	}
	
	public static void saveSettings() {
		
	} 
	
}
