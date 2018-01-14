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
		public static final String FXML_DIR = "FXML/";
		public static final String TESTS_DATA_DIR = TESTS_DIR + "DATA/";
		
		/* 3 - MAIN PATHS */
		public static final String APP_CFG_PATH = APP_DIR + APP_CFG_NAME;
		
	/** END OF APP **/
		
		
		
		
	/************************** *
    /*                          *
    /*    DATA/CONFIG VALS      *
    /*                          *
    /* ************************ */
		
		/* 1 - GENERAL VALS */
		public static final int DATE_REFRESH_DELAY = 1000;
		public static final String DATE_FORMAT_FULL = "HH:mm:ss dd/MM";
		public static final String DATE_FORMAT_OPEN = "dd-MM-yyyy";
		public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
		
    /** END OF DATA/CONFIG VALS **/
		
		
		
		
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
		public static final String CARD_LIST_PATH = CARD_DIR + CARD_LIST_FILENAME;
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
    /*                          *
    /*      UI FILES            *
    /*                          *
    /* ************************ */
        /* 1 - FILENAMES */
		public static final String FXML_SPLASH_FILENAME = "FX_SPLASH.fxml";
		public static final String FXML_MAIN_FILENAME = "FX_MAIN.fxml";
		public static final String FXML_CSS_STYLES_FILENAME = "styles.css";
		    /* 1.1 - Views */
		    public static final String FXML_VIEW_CUSTOMER_FILENAME = "FX_VIEW_CUSTOMER.fxml";
		    
		
		/* 2 - DIRECTORIES */

		/* 3 - FULL PATHS */
		public static final String FXML_SPLASH_PATH = FXML_DIR + FXML_SPLASH_FILENAME;
		public static final String FXML_MAIN_PATH = FXML_DIR + FXML_MAIN_FILENAME;
		public static final String FXML_CSS_STYLES_PATH = FXML_DIR + FXML_CSS_STYLES_FILENAME;
		    /* 3.1 - Views */
		    public static final String FXML_VIEW_CUSTOMER_PATH = FXML_DIR + FXML_VIEW_CUSTOMER_FILENAME;
		    
    /** END OF UI FILES **/
		

    /************************** *
    /*                          *
    /*      RESOURCES           *
    /*                          *
    /* ************************ */		
		/* 1 - FILENAMES */
		    /* 1.1 - Images */
		    public static final String RES_IMG_LOGO_FILENAME = "logo.png";
		    public static final String RES_IMG_BGGRADIENT_FILENAME = "background_gradient.png";
		    public static final String RES_IMG_BTN_UK_FILENAME = "uk.png";
		    public static final String RES_IMG_BTN_PL_FILENAME = "pl_icon.png";
		    public static final String RES_IMG_BTN_EDIT_FILENAME = "icon_edit.png";
		    public static final String RES_IMG_BTN_DELETE_FILENAME = "icon_delete.png";
		    public static final String RES_IMG_BTN_INFO_FILENAME = "icon_info.png";
		/* 2 - DIRECTORIES */
		public static final String RES_IMG_DIR = "img/";
		/* 2 - FULL PATHS */
	    /* PATHS RELATIVE TO THE ResourceLocalizer CLASS */
		    /* 2.1 - Images */
		    public static final String RES_IMG_LOGO_PATH = RES_IMG_DIR + RES_IMG_LOGO_FILENAME; 
		    public static final String RES_IMG_BGGRADIENT_PATH = RES_IMG_DIR + RES_IMG_BGGRADIENT_FILENAME;
		    public static final String RES_IMG_BTN_UK_PATH = RES_IMG_DIR + RES_IMG_BTN_UK_FILENAME;
		    public static final String RES_IMG_BTN_PL_PATH = RES_IMG_DIR + RES_IMG_BTN_PL_FILENAME;
		    public static final String RES_IMG_BTN_DELETE_PATH = RES_IMG_DIR + RES_IMG_BTN_DELETE_FILENAME;
		    public static final String RES_IMG_BTN_INFO_PATH = RES_IMG_DIR + RES_IMG_BTN_INFO_FILENAME;
		    public static final String RES_IMG_BTN_EDIT_PATH = RES_IMG_DIR + RES_IMG_BTN_EDIT_FILENAME;
		    
		    
		    
	
	/************************** *
	/*							*
	/* 	   LOCALIZATION			*
	/*							*
	/* ************************ */	
		/* 1 - STRING CONSTANTS */
		    /* 1.1 - General */
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
        		public static final String TXT_ABOUT = "ABOUT";
        		public static final String TXT_NAME = "NAME";
        		public static final String TXT_SURNAME = "SURNAME";
        		public static final String TXT_PHONE = "PHONE";
        		public static final String TXT_DOB = "DOB";
        		public static final String TXT_ENTRIES = "ENTRIES";
        		public static final String TXT_OPEN = "OPEN";
        		public static final String TXT_OPTIONS = "OPTIONS";
        		public static final String TXT_ID = "ID";
        		public static final String TXT_NONE = "NONE";
        		public static final String TXT_DAYS = "DAYS";
        	/* 1.2 - Initialization */
                public static final String TXT_VERSION = "VERSION";
        		public static final String TXT_LOADING = "LOADING";
        		public static final String TXT_LOADING_SETTINGS = "LOADING1";
        		public static final String TXT_LOADING_CUSTOMERS = "LOADING2";
        		public static final String TXT_LOADING_ACTIVITIES = "LOADING3";
        		public static final String TXT_LOADING_CARDS = "LOADING4";
        		public static final String TXT_LOADING_DATABASE = "LOADING5";
        		public static final String TXT_LOADING_FINALIZING = "LOADING6";
        		public static final String TXT_LOADING_IMAGES = "LOADING7";
        		public static final String TXT_PATH = "PATH";
        		public static final String TXT_SETTING = "SETTING";
        		public static final String TXT_INITIALIZING = "INITIALIZING";
        	/* 1.3 - Titles */
            	public static final String TXT_TITLE_SPLASH = "TITLE_SPLASH";
            	public static final String TXT_TITLE_MAIN = "TITLE_MAIN";
            	public static final String TXT_TITLE_VIEW_CUSTOMER = "TITLE_VIEW_CUSTOMER";
            /* 1.4 - Button names */
            	public static final String TXT_BTN_ACT_LIST = "ACT_LIST";
            	public static final String TXT_BTN_CUST_LIST = "CUST_LIST";
            	public static final String TXT_BTN_CARD_LIST = "CARD_LIST";
            	public static final String TXT_BTN_ADD_CUSTOMER = "ADD_CUST";
            /* 1.5 - Labels */
            	public static final String TXT_SEARCH = "SEARCH";
            	public static final String TXT_SCAN_NOTE = "SCAN_NOTE";
		
		/* 2 - TEST-RELATED STRING CONSTANTS */
			public static final String TXT_TEST = "TEST";
			public static final String TXT_TEST2 = "TEST2";
	/** END OF LOCALIZATION **/
	
}
