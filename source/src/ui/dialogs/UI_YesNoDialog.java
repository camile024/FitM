package ui.dialogs;

import java.io.IOException;

import FXML.FXLoader;
import engine.CONST;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.utils.DialogType;
import ui.utils.ResourceLocalizer;

public class UI_YesNoDialog extends UI_Dialog {
	private Stage self;
	private Stage parent;
	private DialogType dialogType;
	private boolean decision = false;
	
	@FXML
	Button FXID_ACCEPT_BTN;
	@FXML
	Button FXID_CANCEL_BTN;
	@FXML
	Text FXID_TEXT;
	@FXML
	ImageView FXID_IMAGE;
	
	/**
	 * Returns a prepared UI_YesNoDialog instance (still needs to be initialised)
	 * @return
	 */
	public static UI_YesNoDialog getInstance() {
		Stage stage = new Stage();
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_YES_NO_DIALOG_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_YesNoDialog uiDialog = ((UI_YesNoDialog)(loader.getController()));
        
        
        Scene scene = new Scene(pane);
        
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle(CONST.APP_NAME);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        /* End of FXML part */
        uiDialog.setSelf(stage);
        return uiDialog;
	}
	
	@FXML
	public void btnCancelOnClick() {
		decision = false;
		self.close();
	}
	
	@FXML
	public void btnAcceptOnClick() {
		decision = true;
		self.close();
	}
	
	/**
	 * Initialises the dialog
	 * @param text	Text to be displayed to the user
	 * @param parent	Parent Stage object to return to after user makes their decision
	 * @param yesText	
	 * @param noText
	 * @param dialogType
	 */
	public void init(String text, Stage parent, String yesText, String noText, DialogType dialogType) {
		this.parent = parent;
		FXID_ACCEPT_BTN.setText(yesText);
		FXID_CANCEL_BTN.setText(noText);
		FXID_TEXT.setText(text);
		this.dialogType = dialogType;
		
		switch (dialogType) {
			case SUCCESS:
				FXID_IMAGE.setImage(ResourceLocalizer.getImage(CONST.RES_IMG_SUCCESS_FILENAME));
				break;
			case ERROR:
				FXID_IMAGE.setImage(ResourceLocalizer.getImage(CONST.RES_IMG_ERROR_FILENAME));
				break;
			case INFO:
				FXID_IMAGE.setImage(ResourceLocalizer.getImage(CONST.RES_IMG_INFO_FILENAME));
				break;
			case CONFIRM:
				FXID_IMAGE.setImage(ResourceLocalizer.getImage(CONST.RES_IMG_CONFIRM_FILENAME));
				break;
			default:
					
		}
		
		FXID_IMAGE.setFitWidth(48);
		FXID_IMAGE.setFitHeight(48);
		
	}
	
	public void setSelf(Stage self) {
		this.self = self;
	}
	
	public boolean call() {
		self.showAndWait();
		return decision;
	}

}
