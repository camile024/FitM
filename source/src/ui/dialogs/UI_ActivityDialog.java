package ui.dialogs;

import java.io.IOException;

import FXML.FXLoader;
import data.objects.Activity;
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

public class UI_ActivityDialog extends UI_Dialog {
	private Stage self;
	private Stage parent;
	Activity current;
	
	
	@FXML
	Button FXID_ACCEPT_BTN;
	@FXML
	Button FXID_CANCEL_BTN;
	@FXML
	Text FXID_TEXT;
	@FXML
	TextField FXID_ACTIVITY_FIELD;
	@FXML
	ImageView FXID_IMAGE;
	
	/**
	 * Returns a prepared UI_CardDialog instance (still needs to be initialised)
	 * @return
	 */
	public static UI_ActivityDialog getInstance() {
		Stage stage = new Stage();
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_ACTIVITY_DIALOG_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_ActivityDialog uiDialog = ((UI_ActivityDialog)(loader.getController()));
        
        
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
		boolean userConfirmed = false;
		
		UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
		dialog.init(Locale.getString(CONST.TXT_CONFIRM_SAVE_CHANGES), parent, 
				Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
		userConfirmed = dialog.call();
		if (userConfirmed) {
			if (FXID_ACTIVITY_FIELD.getText().trim().equals("")) {
				UI_ConfirmDialog errDialog = UI_ConfirmDialog.getInstance();
				errDialog.init(Locale.getString(CONST.TXT_ERR_NAME_FIELD_EMPTY), self, CONST.TXT_ACCEPT, DialogType.ERROR);
				errDialog.call();
			} else {
				if (current == null) {
					Activity newAct = new Activity(FXID_ACTIVITY_FIELD.getText());
					try {
						customerDB.addSingleObject(newAct);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					current.setName(FXID_ACTIVITY_FIELD.getText());
				}
				
				self.close();
				
			}
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
	public void init(CustomerDB customerDB, Stage parent, Activity current) {
		this.parent = parent;
		this.customerDB = customerDB;
		FXID_ACCEPT_BTN.setText(Locale.getString(CONST.TXT_ACCEPT));
		FXID_CANCEL_BTN.setText(Locale.getString(CONST.TXT_CANCEL));
		this.current = current;
		
		if (current == null) {
			FXID_TEXT.setText(Locale.getString(CONST.TXT_MSG_ADD_ACTIVITY));
		} else {
			FXID_TEXT.setText(Locale.getString(CONST.TXT_MSG_EDIT_ACTIVITY));
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
	
	public Activity getActivity() {
		return current;
	}

}
