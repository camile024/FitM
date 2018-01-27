package ui.dialogs;

import java.io.IOException;

import FXML.FXLoader;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
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

public class UI_CardDialog extends UI_Dialog {
	private Stage self;
	private Stage parent;
	private boolean createMode = false;
	private int cardNum = -1;
	
	
	@FXML
	Button FXID_ACCEPT_BTN;
	@FXML
	Button FXID_CANCEL_BTN;
	@FXML
	Text FXID_TEXT;
	@FXML
	TextField FXID_CARD_FIELD;
	@FXML
	ImageView FXID_IMAGE;
	
	/**
	 * Returns a prepared UI_CardDialog instance (still needs to be initialised)
	 * @return
	 */
	public static UI_CardDialog getInstance() {
		Stage stage = new Stage();
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_CARD_DIALOG_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_CardDialog uiDialog = ((UI_CardDialog)(loader.getController()));
        
        
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
		try {
			cardNum = Integer.parseInt(FXID_CARD_FIELD.getText());
			self.close();
		} catch (Exception ex) {
			UI_ConfirmDialog errDialog = UI_ConfirmDialog.getInstance();
			errDialog.init(Locale.getString(CONST.TXT_ERR_CARD_FIELD_INVALID2), self, CONST.TXT_ACCEPT, DialogType.ERROR);
			errDialog.call();
		}
		
	}
	
	@FXML
	public void btnCancelOnClick() {
		self.close();
	}
	

	/**
	 * Initialises the dialog
	 * @param customerDB
	 * @param parent
	 * @param createNew Whether it's a Card creation/registration dialog or trying to scan a card
	 * 			that already exists in the system
	 */
	public void init(CustomerDB customerDB, Stage parent, boolean createNew) {
		this.parent = parent;
		FXID_ACCEPT_BTN.setText(Locale.getString(CONST.TXT_ACCEPT));
		FXID_CANCEL_BTN.setText(Locale.getString(CONST.TXT_CANCEL));
		this.createMode = createNew;
		
		if (createNew) {
			FXID_TEXT.setText(Locale.getString(CONST.TXT_MSG_CARD_SCAN_REQUEST_WRITABLE));
					
		} else {
			FXID_TEXT.setText(Locale.getString(CONST.TXT_MSG_CARD_SCAN_REQUEST));
			//FXID_ACCEPT_BTN.setVisible(false); //disabled for debugging purposes
		}
		FXID_IMAGE.setImage(ResourceLocalizer.getImage(CONST.RES_IMG_PLUS_FILENAME));
		
		FXID_IMAGE.setFitWidth(48);
		FXID_IMAGE.setFitHeight(48);
		
		
		
	}
	
	public void setSelf(Stage self) {
		this.self = self;
	}
	
	public void call() {
		self.showAndWait();
	}
	
	public int getCard() {
		return cardNum;
	}

}
