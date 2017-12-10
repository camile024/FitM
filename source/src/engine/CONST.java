package engine;

public abstract class CONST {
	
	
	
	/************************** *
	/*							*
	/* 			APP 			*
	/*							*
	/* ************************ */
	
		/* 1 - GENERAL CONSTANTS */
		public static final double VERSION = 0.01;
		public static final String APP_NAME = "FitM";
		public static final String APP_CFG_NAME = "CONFIG";
		
		/* 2 - MAIN DIRECTORIES */
		public static final String APP_DIR = "./";
		public static final String DATA_DIR = APP_DIR + "DATA/";
		public static final String TESTS_DIR = APP_DIR + "TESTS/";
		public static final String TESTS_DATA_DIR = TESTS_DIR + "DATA/";
		
		/* 3 - MAIN PATHS */
		public static final String APP_CFG_PATH = APP_DIR + APP_CFG_NAME;
		
	/** END OF APP **/
		
	/************************** *
	/*							*
	/* 	   DEFAULT_VALUES		*
	/*							*
	/* ************************ */
		/* 1 - CUSTOMER */
		public static final int DEFAULT_CUSTOMER_CARD_NUMBER = -1;
		
		/* 2 - CARD */
		public static final int DEFAULT_CARD_CUSTOMER_ID = -1;
		
		/* 3 - TEST */
		public static final String DEFAULT_TEST_SUFFIX = "_DEF";
		
	/** END OF DEFAULT_VALUES **/
	
	/************************** *
	/*							*
	/* 			CFG				*
	/*							*
	/* ************************ */
		/* 1 - CFG FIELDS */
		public static final String CFG_FIRST_RUN = "firstRun";
		public static final String CFG_LANG = "lang"; // PL/EN
		
		/* 2 - TEST PATH */
		public static final String TEST_CFG_NAME = "TEST_CONFIG";
		public static final String TEST_CFG_PATH = TESTS_DIR + TEST_CFG_NAME;
		
	/** END OF CFG **/
	
	/************************** *
	/*							*
	/* 	   DATA FILES FIELDS	*
	/*							*
	/* ************************ */
		/* 1 - CUSTOMER FILE FIELDS */
		public static final String CUSTOMER_ID = "id";
		public static final String CUSTOMER_NAME = "name";
		public static final String CUSTOMER_SURNAME = "surname";
		public static final String CUSTOMER_DOB = "DOB";
		public static final String CUSTOMER_PHONE = "phone";
		public static final String CUSTOMER_OPEN = "open";
		public static final String CUSTOMER_ENTRIES = "entries";
		public static final String CUSTOMER_CARD_NUMBER = "cardNum";
		
		/* 2 - CARD FILE FIELDS */
		public static final String CARD_NUMBER = "number";
		public static final String CARD_CUSTOMER_ID = "customerID";
	
	/** END OF DATA FILES FIELDS **/
	
	/************************** *
	/*							*
	/* 	   DATA FILES DIRS		*
	/*							*
	/* ************************ */
		/* 1 - FILENAMES */
		public static final String ACT_LIST_FILENAME = "ACT_LIST";
		public static final String CUSTOMER_LIST_FILENAME = "CS_LIST";
		public static final String CARD_LIST_FILENAME = "CARD_LIST";
		public static final String LANG_PL_FILENAME = "LANG_PL";
		public static final String LANG_EN_FILENAME = "LANG_EN";
		
		/* 2 - DIRECTORIES */
		public static final String CUSTOMER_DIR = DATA_DIR + "/CUSTOMERS/";
		public static final String CARD_DIR = DATA_DIR + "/CARDS/";
		
		/* 3 - FULL PATHS */
		public static final String CUSTOMER_LIST_PATH = CUSTOMER_DIR + CUSTOMER_LIST_FILENAME;
		public static final String CARD_LIST_PATH = CUSTOMER_DIR + CARD_LIST_FILENAME;
		public static final String ACT_LIST_PATH = DATA_DIR + ACT_LIST_FILENAME;
		public static final String LANG_PL_PATH = DATA_DIR + LANG_PL_FILENAME;
		public static final String LANG_EN_PATH = DATA_DIR + LANG_EN_FILENAME;
		
	
		/* 4 - TESTS */
			/* 4.1 - FILENAMES */
			public static final String TESTS_FILEREADER1_FILENAME = "TEST_FILE_READER_1";
			public static final String TESTS_FILEREADER2_FILENAME = "TEST_FILE_READER_2_ACT";
			public static final String TESTS_FILESAVER1_FILENAME = "TEST_FILE_SAVER_1";
			public static final String TESTS_FILESAVER2_FILENAME = "TEST_FILE_SAVER_2_ACT";
			
			/* 4.2 - FULL PATHS */
			public static final String TESTS_FILEREADER1_PATH = TESTS_DIR + TESTS_FILEREADER1_FILENAME;
			public static final String TESTS_FILEREADER2_PATH = TESTS_DIR + TESTS_FILEREADER2_FILENAME;
			public static final String TESTS_FILESAVER1_PATH = TESTS_DIR + TESTS_FILESAVER1_FILENAME;
			public static final String TESTS_FILESAVER2_PATH = TESTS_DIR + TESTS_FILESAVER2_FILENAME;
			
	/** END OF DATA FILES DIRS **/
	
	
	/************************** *
	/*							*
	/* 	   LOCALIZATION			*
	/*							*
	/* ************************ */	
		/* 1 - STRING CONSTANTS */
		public static final String TXT_OK = "OK";
		public static final String TXT_YES = "YES";
		public static final String TXT_NO = "NO";
		public static final String TXT_CANCEL = "CANCEL";
		public static final String TXT_ABORT = "ABORT";
		public static final String TXT_EXIT = "EXIT";
		public static final String TXT_ACCEPT = "ACCEPT";
		public static final String TXT_EDIT = "EDIT";
		public static final String TXT_SETTINGS = "SETTINGS";
		public static final String TXT_SAVE = "SAVE";
		public static final String TXT_DISCARD = "DISCARD";
			
		/* 2 - TEST-RELATED STRING CONSTANTS */
			public static final String TXT_TEST = "TEST";
			public static final String TXT_TEST2 = "TEST2";
	/** END OF LOCALIZATION **/
	
}
