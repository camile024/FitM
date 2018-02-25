package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import FXML.FXLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.mains.UI_Main;
import ui.mains.UI_Splash;
import ui.utils.ResourceLocalizer;

public class Application extends javafx.application.Application {
    CustomerDB database;
    
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}
	
	public void run() {
		/* Load settings, initialize language */
        try {
            Settings.loadSettings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	    
	    /* Initialize Date */
	    Locale.initDate(CONST.DATE_REFRESH_DELAY); //10 mins
	    launch();   
	}
	
	@Override
	public void start(Stage mainStage) throws InterruptedException {
	        /* FXML part */
	        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_SPLASH_PATH);
	        BorderPane pane = null;
            try {
                pane = (BorderPane) loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(-1);
            }
	        UI_Splash uiSplash = ((UI_Splash)(loader.getController()));
	        
	        
	        uiSplash.init(mainStage);
	        Scene scene = new Scene(pane, 600, 300);
	        
	        mainStage.initStyle(StageStyle.UNDECORATED);
	        mainStage.setScene(scene);
	        mainStage.setTitle("Splash");
	        mainStage.setResizable(false);
	        mainStage.setAlwaysOnTop(true);
	        mainStage.centerOnScreen();
	        mainStage.show();
	        /* End of FXML part */

	        
	        /* Initialize the database */
	        database = new CustomerDB(CONST.APP_DIR);
	        
	        try {
	            uiSplash.setStatusTitle(CONST.TXT_LOADING_DATABASE);
	            /* Load Activities */
	            uiSplash.setStatus(CONST.TXT_LOADING_ACTIVITIES);
	            database.initActivities();
	            /* Load Customers */
                uiSplash.setStatus(CONST.TXT_LOADING_CUSTOMERS);
	            database.initCustomers();
	            /* Load Cards */
                uiSplash.setStatus(CONST.TXT_LOADING_CARDS);
	            database.initCards();
	            /* Load Images */
	            uiSplash.setStatus(CONST.TXT_LOADING_IMAGES);
                ResourceLocalizer.loadImages();
                /* Initialise WeekPlan */
                uiSplash.setStatus(CONST.TXT_LOADING_WEEKPLAN);
                database.initWeekPlan();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        /* Everything loaded - show main window */
	        uiSplash.setStatusTitle(CONST.TXT_LOADING_FINALIZING);
	        uiSplash.setStatus("");
	        
	        Timeline timer = new Timeline(            
                new KeyFrame(Duration.seconds(3), event -> {
                    mainStage.close();
                    mainWindowInit();
                })
	        );
	        timer.play();
	        
	        
	}
	
	private void mainWindowInit() {
	    Rectangle2D screenDimensions = Screen.getPrimary().getBounds();
	    /* FXML part */
	    Stage stage = new Stage();
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_MAIN_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_Main uiMain = ((UI_Main)(loader.getController()));
        
        //stage.setFullScreen(true);
        stage.setWidth(screenDimensions.getWidth() / 1.2);
        stage.setHeight(screenDimensions.getHeight() / 1.2);
        stage.setMinHeight(800);
        stage.setMinWidth(960);
        Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());
        
        
        stage.setScene(scene);
        stage.setTitle("FitM");
        //stage.setResizable(false);
        stage.centerOnScreen();
        
        uiMain.init(stage, database);
        stage.show();
        /* End of FXML part */
	}

}
