package ui.mains;

import java.io.FileNotFoundException;
import java.io.IOException;

import FXML.FXLoader;
import engine.CONST;
import engine.CustomerDB;
import engine.Language;
import engine.Locale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.utils.MainViewMode;
import ui.utils.ResourceLocalizer;
import ui.views.UI_ActivityView;
import ui.views.UI_CalendarView;
import ui.views.UI_CardView;
import ui.views.UI_CustomerView;
import ui.views.UI_View;

public class UI_Main {

    private CustomerDB customerDB;
    private MainViewMode viewMode;
    private Stage self;
    private UI_View currentViewController;
    
    @FXML
    Text FXID_SCAN_NOTE;
    @FXML
    Text FXID_TIME_LABEL;
    @FXML
    Button FXID_ACT_LIST_BTN;
    @FXML
    Button FXID_CUSTOMER_LIST_BTN;
    @FXML
    Button FXID_CARD_LIST_BTN;
    @FXML
    Button FXID_EXIT_BTN;
    @FXML
    Button FXID_PL_BTN;
    @FXML
    Button FXID_EN_BTN;
    @FXML
    Button FXID_SETTINGS_BTN;
    @FXML
    Button FXID_CONTACT_BTN;
    @FXML
    Button FXID_CALENDAR_BTN;
    @FXML
    BorderPane FXID_MAIN_VIEW;
    @FXML
    BorderPane FXID_BORDERPANE_MAIN;
    
    public void init(Stage self, CustomerDB db) {
        this.self = self;
        customerDB = db;
        switchView(MainViewMode.CUSTOMER);
        refreshView();
    }
    
    public void refreshView() {
        /* Backgrounds */
        BackgroundSize iconSize = new BackgroundSize(64, 64, false, false, false, false);
        BackgroundImage backgroundPLImg = new BackgroundImage(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_PL_FILENAME),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                iconSize);
        FXID_PL_BTN.setBackground(new Background(backgroundPLImg));
        
        BackgroundImage backgroundUKImg = new BackgroundImage(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_UK_FILENAME),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                iconSize);
        FXID_EN_BTN.setBackground(new Background(backgroundUKImg));
        
        
        /* Set button captions */
        FXID_ACT_LIST_BTN.setText(Locale.getString(CONST.TXT_BTN_ACT_LIST));
        FXID_CUSTOMER_LIST_BTN.setText(Locale.getString(CONST.TXT_BTN_CUST_LIST));
        FXID_CARD_LIST_BTN.setText(Locale.getString(CONST.TXT_BTN_CARD_LIST));
        FXID_EXIT_BTN.setText(Locale.getString(CONST.TXT_EXIT));
        FXID_SETTINGS_BTN.setText(Locale.getString(CONST.TXT_SETTINGS));
        FXID_CONTACT_BTN.setText(Locale.getString(CONST.TXT_ABOUT));
        FXID_CALENDAR_BTN.setText(Locale.getString(CONST.TXT_CALENDAR));
        FXID_SCAN_NOTE.setText(Locale.getString(CONST.TXT_SCAN_NOTE));
        FXID_PL_BTN.setText("");
        FXID_EN_BTN.setText("");
        
        FXID_TIME_LABEL.textProperty().bind(Locale.dateProperty());
        
        /* Set general text */
        
        /* Refresh the list view based on choice */
        currentViewController.refreshView();
        
    }
    
    @FXML
    public void btnClientListOnClick() {
        switchView(MainViewMode.CUSTOMER);
    }
    
    @FXML
    public void btnCalendarOnClick() {
    	switchView(MainViewMode.CALENDAR);
    }
    
    @FXML
    public void btnCardListOnClick() {
        switchView(MainViewMode.CARD);
    }
    
    @FXML
    public void btnActListOnClick() {
        switchView(MainViewMode.ACTIVITY);
    }
    
    @FXML
    public void btnENOnClick() {
        try {
            Locale.load(Language.EN);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        refreshView();
    }
    
    @FXML
    public void btnPLOnClick() {
        try {
            Locale.load(Language.PL);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        refreshView();
    }
    
    @FXML
    public void btnExitOnClick() {
        
    }
    
    @FXML
    public void btnSettingsOnClick() {
        
    }
    
    @FXML
    public void btnContactOnClick() {
        
    }
    
    public Stage getStage() {
        return self;
    }

    public void switchView(MainViewMode mode) {
        BorderPane pane = null;
        viewMode = mode;
        switch (mode) {
            case CARD:
            	pane = setCardView();
                break;
            case CUSTOMER:
                pane = setCustomerView();
                break;
            case ACTIVITY:
            	pane = setActivityView();
                break;
            case CALENDAR:
            	pane = setCalendarView();
        }
        
        FXID_MAIN_VIEW.setCenter(pane);
    }
    
    public CustomerDB getCustomerDB() {
        return customerDB;
    }
    
    public BorderPane getMainView() {
        return FXID_MAIN_VIEW;
    }
    
    public MainViewMode getMainViewMode() {
        return viewMode;
    }
    
    
    private BorderPane setCustomerView() {
        /* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_VIEW_CUSTOMER_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_CustomerView uiView = ((UI_CustomerView)(loader.getController()));
        uiView.init(customerDB, this);
        currentViewController = uiView;
        /* End of FXML part */
        return pane;
    }
    
    private BorderPane setCalendarView() {
        /* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_VIEW_CALENDAR_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_CalendarView uiView = ((UI_CalendarView)(loader.getController()));
        uiView.init(customerDB, this);
        currentViewController = uiView;
        /* End of FXML part */
        return pane;
    }
    
    private BorderPane setCardView() {
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_VIEW_CARD_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_CardView uiView = ((UI_CardView)(loader.getController()));
        uiView.init(customerDB, this);
        currentViewController = uiView;
        /* End of FXML part */
        return pane;
    }
    
    private BorderPane setActivityView() {
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_VIEW_ACTIVITY_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_ActivityView uiView = ((UI_ActivityView)(loader.getController()));
        uiView.init(customerDB, this);
        currentViewController = uiView;
        /* End of FXML part */
        return pane;
    }
    
    
}
