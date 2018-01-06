package engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.mains.UI_Splash;

public class Application extends javafx.application.Application {
    CustomerDB database;
    
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}
	
	public void run() {
	    /* Initialize language (in case settings fail later) */
	    try {
            Locale.load(Language.EN);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
	    launch();   
	}
	
	@Override
	public void start(Stage mainStage) throws InterruptedException {
	        /* FXML part */
	        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(CONST.FXML_SPLASH_PATH));
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
	        
	        /* Load settings, initialize language */
	        try {
	            Settings.loadSettings();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        
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
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        /* Everything loaded - show main window */
	        uiSplash.setStatusTitle(CONST.TXT_LOADING_FINALIZING);
	        uiSplash.setStatus("");
	        
	        Timeline timer = new Timeline(            
                new KeyFrame(Duration.seconds(3), event -> mainStage.close())
	        );
	        timer.play();
	}

}
