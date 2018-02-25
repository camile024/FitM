package ui.dialogs;

import java.io.IOException;

import FXML.FXLoader;
import engine.CONST;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.utils.DialogType;
import ui.utils.ResourceLocalizer;

public class UI_InputDialog extends UI_Dialog {
	private Stage self;
	private Stage parent;
	private DialogType dialogType;
	private String input = "";
	
	@FXML
	Button FXID_ACCEPT_BTN;
	@FXML
	Text FXID_TEXT;
	@FXML
	ImageView FXID_IMAGE;
	@FXML
	TextField FXID_INPUT;
	
	/**
	 * Returns a prepared UI_ConfirmDialog instance (still needs to be initialised)
	 * @return
	 */
	public static UI_InputDialog getInstance() {
		Stage stage = new Stage();
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_INPUT_DIALOG_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_InputDialog uiDialog = ((UI_InputDialog)(loader.getController()));
        
        
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
	public void btnAcceptOnClick() {
		input = FXID_INPUT.getText();
		self.close();
	}
	
	/**
	 * Initialises the dialog
	 * @param text	Text to be displayed to the user
	 * @param parent	Parent Stage object to return to after user makes their decision
	 * @param confirmText	
	 * @param dialogType
	 */
	public void init(String title, String text, Stage parent, String confirmText, DialogType dialogType) {
		this.parent = parent;
		self.setTitle(title);
		FXID_ACCEPT_BTN.setText(confirmText);
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
	
	public String call() {
		self.showAndWait();
		return input;
	}
}
