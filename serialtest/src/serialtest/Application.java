package serialtest;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jpos.util.JposPropertiesConst;

public class Application extends javafx.application.Application {
	
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}

	
	
	public void run() {
		//System.setProperty(JposPropertiesConst.JPOS_POPULATOR_FILE_PROP_NAME, "jpos.xml");
	    launch();   
	}
	
	@Override
	public void start(Stage mainStage) {
	        /* FXML part */
	        FXMLLoader loader = new FXMLLoader(Application.class.getClassLoader().getResource("serialtest/ui.fxml"));
	        VBox pane = null;
            try {
                pane = (VBox) loader.load();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(-1);
            }
	        UI_Controller cntrl = ((UI_Controller)(loader.getController()));
	        
	       
	        Scene scene = new Scene(pane);
	        
	        
	        mainStage.setScene(scene);
	        mainStage.setTitle("FitM Serial Port Test");
	        mainStage.setResizable(true);
	        mainStage.setAlwaysOnTop(true);
	        mainStage.centerOnScreen();
	        mainStage.show();
	        /* End of FXML part */
	        
	        
	        
	}
}
