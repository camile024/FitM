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
		public static final String DATE_FORMAT_DAYLESS = "yyyy-MM";
		public static final String FIELD_ENABLED = "field_enabled";
		public static final String FIELD_DISABLED = "field_disabled";
		public static final int INDEX_RESERVATIONS = 0;
		public static final int INDEX_ATTENDANTS = 1;
		
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
		
		/* 4 - OTHER */
		public static final int DEFAULT_HIGHEST_ID = -1;
		
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
		public static final String WEEKPLAN_FILENAME = "WEEKPLAN";
		
		/* 2 - DIRECTORIES */
		public static final String CUSTOMER_DIR = DATA_DIR + "/CUSTOMERS/";
		public static final String CARD_DIR = DATA_DIR + "/CARDS/";
		public static final String CALENDAR_DIR = DATA_DIR + "/CALENDAR/";
		
		/* 3 - FULL PATHS */
		public static final String CUSTOMER_LIST_PATH = CUSTOMER_DIR + CUSTOMER_LIST_FILENAME;
		public static final String CARD_LIST_PATH = CARD_DIR + CARD_LIST_FILENAME;
		public static final String ACT_LIST_PATH = DATA_DIR + ACT_LIST_FILENAME;
		public static final String LANG_PL_PATH = DATA_DIR + LANG_PL_FILENAME;
		public static final String LANG_EN_PATH = DATA_DIR + LANG_EN_FILENAME;
		public static final String WEEKPLAN_PATH = DATA_DIR + WEEKPLAN_FILENAME;
		
	
		/* 4 - TESTS */
			/* 4.1 - FILENAMES */
			public static final String TESTS_FILEREADER1_FILENAME = "TEST_FILE_READER_1";
			public static final String TESTS_FILEREADER2_FILENAME = "TEST_FILE_READER_2_ACT";
			public static final String TESTS_FILEREADER3_FILENAME = "DATA/WEEKPLAN";
			public static final String TESTS_FILEREADER4_FILENAME = "DATA/CALENDAR/2018-05-22";
			public static final String TESTS_FILESAVER1_FILENAME = "TEST_FILE_SAVER_1";
			public static final String TESTS_FILESAVER2_FILENAME = "TEST_FILE_SAVER_2_ACT";
			
			/* 4.2 - FULL PATHS */
			public static final String TESTS_FILEREADER1_PATH = TESTS_DIR + TESTS_FILEREADER1_FILENAME;
			public static final String TESTS_FILEREADER2_PATH = TESTS_DIR + TESTS_FILEREADER2_FILENAME;
			public static final String TESTS_FILEREADER3_PATH = TESTS_DIR + TESTS_FILEREADER3_FILENAME;
			public static final String TESTS_FILEREADER4_PATH = TESTS_DIR + TESTS_FILEREADER4_FILENAME;
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
		    public static final String FXML_VIEW_CARD_FILENAME = "FX_VIEW_CARD.fxml";
		    public static final String FXML_VIEW_ACTIVITY_FILENAME = "FX_VIEW_ACTIVITY.fxml";
		    public static final String FXML_VIEW_CALENDAR_FILENAME = "FX_VIEW_CALENDAR.fxml";
		    public static final String FXML_VIEW_WEEKPLAN_FILENAME = "FX_VIEW_WEEKPLAN.fxml";
		    /* 1.2 - Dialogs */
		    public static final String FXML_CUSTOMER_INFO_DIALOG_FILENAME = "FX_DLG_CUSTOMER_INFO.fxml";
		    public static final String FXML_CUSTOMER_ADD_DIALOG_FILENAME = "FX_DLG_CUSTOMER_ADD.fxml";
		    public static final String FXML_CARD_DIALOG_FILENAME = "FX_DLG_CARD.fxml";
		    public static final String FXML_ACTIVITY_DIALOG_FILENAME = "FX_DLG_ACTIVITY.fxml";
		    public static final String FXML_YES_NO_DIALOG_FILENAME = "FX_DLG_YES_NO.fxml";
		    public static final String FXML_CONFIRM_DIALOG_FILENAME = "FX_DLG_CONFIRM.fxml";
		    public static final String FXML_INPUT_DIALOG_FILENAME = "FX_DLG_INPUT.fxml";
		    
		
		/* 2 - DIRECTORIES */

		/* 3 - FULL PATHS */
		public static final String FXML_SPLASH_PATH = FXML_DIR + FXML_SPLASH_FILENAME;
		public static final String FXML_MAIN_PATH = FXML_DIR + FXML_MAIN_FILENAME;
		public static final String FXML_CSS_STYLES_PATH = FXML_DIR + FXML_CSS_STYLES_FILENAME;
		    /* 3.1 - Views */
		    public static final String FXML_VIEW_CUSTOMER_PATH = FXML_DIR + FXML_VIEW_CUSTOMER_FILENAME;
		    public static final String FXML_VIEW_CARD_PATH = FXML_DIR + FXML_VIEW_CARD_FILENAME;
		    public static final String FXML_VIEW_ACTIVITY_PATH = FXML_DIR + FXML_VIEW_ACTIVITY_FILENAME;
		    public static final String FXML_VIEW_CALENDAR_PATH = FXML_DIR + FXML_VIEW_CALENDAR_FILENAME;
		    public static final String FXML_VIEW_WEEKPLAN_PATH = FXML_DIR + FXML_VIEW_WEEKPLAN_FILENAME;
		    
		    /* 3.2 - Dialogs */
		    public static final String FXML_CUSTOMER_INFO_DIALOG_PATH = FXML_DIR + FXML_CUSTOMER_INFO_DIALOG_FILENAME;
		    public static final String FXML_CUSTOMER_ADD_DIALOG_PATH = FXML_DIR + FXML_CUSTOMER_ADD_DIALOG_FILENAME;
		    public static final String FXML_YES_NO_DIALOG_PATH = FXML_DIR + FXML_YES_NO_DIALOG_FILENAME;
		    public static final String FXML_CONFIRM_DIALOG_PATH = FXML_DIR + FXML_CONFIRM_DIALOG_FILENAME;
		    public static final String FXML_INPUT_DIALOG_PATH = FXML_DIR + FXML_INPUT_DIALOG_FILENAME;
		    public static final String FXML_CARD_DIALOG_PATH = FXML_DIR + FXML_CARD_DIALOG_FILENAME;
		    public static final String FXML_ACTIVITY_DIALOG_PATH = FXML_DIR + FXML_ACTIVITY_DIALOG_FILENAME;
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
		    public static final String RES_IMG_INFO_FILENAME = "icon_info2.png";
		    public static final String RES_IMG_ERROR_FILENAME = "icon_error.png";
		    public static final String RES_IMG_SUCCESS_FILENAME = "icon_success.png";
		    public static final String RES_IMG_CONFIRM_FILENAME = "icon_confirm.png";
		    public static final String RES_IMG_PLUS_FILENAME = "icon_plus.png";
		    public static final String RES_IMG_MINUS_FILENAME = "icon_minus.png";
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
		    public static final String RES_IMG_INFO_PATH = RES_IMG_DIR + RES_IMG_INFO_FILENAME;
		    public static final String RES_IMG_ERROR_PATH = RES_IMG_DIR + RES_IMG_ERROR_FILENAME;
		    public static final String RES_IMG_SUCCESS_PATH = RES_IMG_DIR + RES_IMG_SUCCESS_FILENAME;
		    public static final String RES_IMG_CONFIRM_PATH = RES_IMG_DIR + RES_IMG_CONFIRM_FILENAME;
		    public static final String RES_IMG_PLUS_PATH = RES_IMG_DIR + RES_IMG_PLUS_FILENAME;
		    public static final String RES_IMG_MINUS_PATH = RES_IMG_DIR + RES_IMG_MINUS_FILENAME;
		    
		    
		    
	
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
        		public static final String TXT_NAME2 = "NAME2";
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
        		public static final String TXT_SUCCESS = "SUCCESS";
        		public static final String TXT_ERROR = "ERROR";
        		public static final String TXT_INFO = "INFO";
        		public static final String TXT_CARD = "CARD";
        		public static final String TXT_ADD = "ADD";
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
        		public static final String TXT_LOADING_WEEKPLAN = "LOADING8";
        		public static final String TXT_PATH = "PATH";
        		public static final String TXT_SETTING = "SETTING";
        		public static final String TXT_INITIALIZING = "INITIALIZING";
        	/* 1.3 - Titles */
            	public static final String TXT_TITLE_SPLASH = "TITLE_SPLASH";
            	public static final String TXT_TITLE_MAIN = "TITLE_MAIN";
            	public static final String TXT_TITLE_VIEW_CUSTOMER = "TITLE_VIEW_CUSTOMER";
            	public static final String TXT_TITLE_VIEW_CARD = "TITLE_VIEW_CARD";
            	public static final String TXT_TITLE_VIEW_ACTIVITY = "TITLE_VIEW_ACTIVITY";
            	public static final String TXT_TITLE_ADD_CUSTOMER = "TITLE_ADD_CUSTOMER";
            	public static final String TXT_TITLE_ADD_CARD = "TITLE_ADD_CARD";
            	public static final String TXT_TITLE_ADD_ACTIVITY = "TITLE_ADD_ACTIVITY";
            	public static final String TXT_TITLE_WEEKPLAN = "TITLE_WEEKPLAN";
            	public static final String TXT_TITLE_MODIFY_ENTRIES = "TITLE_MODIFY_ENTRIES";
            	public static final String TXT_TITLE_MODIFY_OPEN = "TITLE_MODIFY_OPEN";
            /* 1.4 - Button names */
            	public static final String TXT_BTN_ACT_LIST = "ACT_LIST";
            	public static final String TXT_BTN_CUST_LIST = "CUST_LIST";
            	public static final String TXT_BTN_CARD_LIST = "CARD_LIST";
            	public static final String TXT_BTN_ADD_CUSTOMER = "ADD_CUST";
            	public static final String TXT_BTN_ADD_CARD = "ADD_CARD";
            	public static final String TXT_BTN_ADD_ACTIVITY = "ADD_ACTIVITY";
            	public static final String TXT_BTN_EDIT_MODE = "EDIT_MODE";
            	public static final String TXT_BTN_ADD_ENTRIES = "ADD_ENTRIES";
            	public static final String TXT_BTN_EXTEND = "EXTEND_OPEN";
            	public static final String TXT_BTN_GET_CARD = "ASSIGN_CARD";
            	public static final String TXT_BTN_PLANNER = "PLANNER";
          
            /* 1.5 - Labels */
            	public static final String TXT_SEARCH = "SEARCH";
            	public static final String TXT_SCAN_NOTE = "SCAN_NOTE";
            	public static final String TXT_CARDNUM = "CARD_NUM";
            	public static final String TXT_ACTIVITIES = "ACTIVITIES";
            /* 1.6 Error messages */
            	public static final String TXT_ERR_NAME_FIELD_EMPTY = "ERR_NAME_FIELD_EMPTY";
            	public static final String TXT_ERR_SURNAME_FIELD_EMPTY = "ERR_SURNAME_FIELD_EMPTY";
            	public static final String TXT_ERR_CARD_FIELD_INVALID = "ERR_CARD_FIELD_INVALID";
            	public static final String TXT_ERR_CARD_FIELD_INVALID2 = "ERR_CARD_FIELD_INVALID2";
            	public static final String TXT_ERR_CARD_EXISTS = "ERR_CARD_EXISTS";
            	public static final String TXT_ERR_CARD_NOT_FOUND = "ERR_CARD_NOT_FOUND";
            	public static final String TXT_ERR_CARD_ASSIGNED = "ERR_CARD_ASSIGNED";
            	public static final String TXT_ERR_INVALID_NUMBER = "ERR_INVALID_NUMBER";
            /* 1.7 Confirm messages */
            	public static final String TXT_CONFIRM_SAVE_CHANGES = "CONFIRM_SAVE_CHANGES";
            	public static final String TXT_CONFIRM_DELETE = "CONFIRM_DELETE";
            	public static final String TXT_CONFIRM_UNASSIGN = "CONFIRM_UNASSIGN";
            	public static final String TXT_FROM_SYSTEM = "CONFIRM_FROM_SYSTEM";
            /* 1.8 Other messages */
            	public static final String TXT_MSG_CARD_SCAN_REQUEST = "CARD_SCAN_REQUEST";
            	public static final String TXT_MSG_CARD_SCAN_REQUEST_WRITABLE = "CARD_SCAN_REQUEST_WRITABLE";
            	public static final String TXT_MSG_ADD_ACTIVITY = "ADD_ACTIVITY_MSG";
            	public static final String TXT_MSG_EDIT_ACTIVITY = "EDIT_ACTIVITY_MSG";
            	public static final String TXT_PROMPT_WEEKDAY = "PROMPT_WEEKDAY";
            	public static final String TXT_PROMPT_ACTIVITY = "PROMPT_ACTIVITY";
            	public static final String TXT_PROMPT_ENTRIES = "PROMPT_ENTRIES";
            	public static final String TXT_PROMPT_OPEN = "PROMPT_OPEN";
		/* 2 - CALENDAR STRINGS */
            	public static final String TXT_CALENDAR = "TITLE_CALENDAR";
            	public static final String TXT_MON = "MON";
            	public static final String TXT_TUE = "TUE";
            	public static final String TXT_WED = "WED";
            	public static final String TXT_THU = "THU";
            	public static final String TXT_FRI = "FRI";
            	public static final String TXT_SAT = "SAT";
            	public static final String TXT_SUN = "SUN";
            	
            	public static final String TXT_MONDAY = "MONDAY";
            	public static final String TXT_TUESDAY = "TUESDAY";
            	public static final String TXT_WEDNESDAY = "WEDNESDAY";
            	public static final String TXT_THURSDAY = "THURSDAY";
            	public static final String TXT_FRIDAY = "FRIDAY";
            	public static final String TXT_SATURDAY = "SATURDAY";
            	public static final String TXT_SUNDAY = "SUNDAY";   
            	
            	public static final String TXT_JANUARY = "JANUARY";
            	public static final String TXT_FEBRUARY = "FEBRUARY";
            	public static final String TXT_MARCH = "MARCH";
            	public static final String TXT_APRIL = "APRIL";
            	public static final String TXT_MAY = "MAY";
            	public static final String TXT_JUNE = "JUNE";
            	public static final String TXT_JULY = "JULY";
            	public static final String TXT_AUGUST = "AUGUST";
            	public static final String TXT_SEPTEMBER = "SEPTEMBER";
            	public static final String TXT_OCTOBER = "OCTOBER";
            	public static final String TXT_NOVEMBER = "NOVEMBER";
            	public static final String TXT_DECEMBER = "DECEMBER";
        /* 3 - TEST-RELATED STRING CONSTANTS */
			public static final String TXT_TEST = "TEST";
			public static final String TXT_TEST2 = "TEST2";
	/** END OF LOCALIZATION **/
	
}
