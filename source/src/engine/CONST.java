package engine;

public abstract class CONST {
	
	/* App */
		public static final double VERSION = 0.01;
		public static final String APP_NAME = "FitM";
		public static final String APP_CFG_NAME = "CONFIG";
		
		public static final String APP_DIR = "./";
		public static final String DATA_DIR = APP_DIR + "DATA/";
		public static final String TESTS_DIR = DATA_DIR + "TESTS/";
		
		public static final String APP_CFG_PATH = APP_DIR + APP_CFG_NAME;

	/**/
		
	/* Config fields */
		public static final String CFG_FIRST_RUN = "firstRun";
		public static final String CFG_LANG = "lang"; // PL/EN
		
			/* Test */
			public static final String TEST_CFG_NAME = "TEST_CONFIG";
			public static final String TEST_CFG_PATH = TESTS_DIR + TEST_CFG_NAME;
	
	
	/*  Data files */
		public static final String ACT_LIST_FILENAME = "ACT_LIST";
		public static final String LANG_PL_FILENAME = "LANG_PL";
		public static final String LANG_EN_FILENAME = "LANG_EN";
		
		public static final String ACT_LIST_PATH = DATA_DIR + ACT_LIST_FILENAME;
		public static final String LANG_PL_PATH = DATA_DIR + LANG_PL_FILENAME;
		public static final String LANG_EN_PATH = DATA_DIR + LANG_EN_FILENAME;
	
			/* Tests */
			public static final String TESTS_FILEREADER1_FILENAME = "TEST_FILE_READER_1";
			public static final String TESTS_FILEREADER2_FILENAME = "TEST_FILE_READER_2_ACT";
			public static final String TESTS_FILESAVER1_FILENAME = "TEST_FILE_SAVER_1";
			public static final String TESTS_FILESAVER2_FILENAME = "TEST_FILE_SAVER_2_ACT";
			
			public static final String TESTS_FILEREADER1_PATH = TESTS_DIR + TESTS_FILEREADER1_FILENAME;
			public static final String TESTS_FILEREADER2_PATH = TESTS_DIR + TESTS_FILEREADER2_FILENAME;
			
			public static final String TESTS_FILESAVER1_PATH = TESTS_DIR + TESTS_FILESAVER1_FILENAME;
			public static final String TESTS_FILESAVER2_PATH = TESTS_DIR + TESTS_FILESAVER2_FILENAME;
			/**/
	/**/
	
	
			
	/* String constants to be used by Locale */
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
			
			/* Tests */
			public static final String TXT_TEST = "TEST";
			public static final String TXT_TEST2 = "TEST2";
			
			/**/
	/**/
	
}
