package ui.dialogs;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import data.objects.Activity;
import data.objects.Customer;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.mains.UI_Main;
import ui.utils.DialogType;

public class UI_CustomerInfoDialog extends UI_Dialog {
	private boolean scanned = false;
	private boolean editMode = false;
	private boolean wasEdited = false;
	private Customer customer;
	private int openAdded = 0;
	private UI_Main parent;
	
	@FXML
	Button FXID_ACCEPT_BTN;
	@FXML
	Button FXID_CANCEL_BTN;
	@FXML
	Button FXID_EDIT_BTN;
	@FXML
	Button FXID_ENTRIES_BTN;
	@FXML
	Button FXID_EXTEND_BTN;
	@FXML
	Button FXID_HELP_BTN;
	@FXML
	Button FXID_CARD_BTN;
	@FXML
	Text FXID_NAME;
	@FXML
	Text FXID_SURNAME;
	@FXML
	Text FXID_PHONE;
	@FXML
	Text FXID_DOB;
	@FXML
	Text FXID_CARD;
	@FXML
	Text FXID_OPEN;
	@FXML
	Text FXID_ENTRIES;
	@FXML
	Text FXID_ACTIVITIES;
	@FXML
	Text FXID_CUSTOMER_NAME;
	@FXML
	TextField FXID_NAME_FIELD;
	@FXML
	TextField FXID_SURNAME_FIELD;
	@FXML
	TextField FXID_PHONE_FIELD;
	@FXML
	TextField FXID_DOB_FIELD;
	@FXML
	TextField FXID_CARD_FIELD;
	@FXML
	TextField FXID_OPEN_FIELD;
	@FXML
	TextField FXID_ENTRIES_FIELD;
	@FXML
	ListView<Activity> FXID_ACTIVITIES_FIELD;
	@FXML
	HBox FXID_ACTIVITIES_BOX;

	
	
	public void init(CustomerDB db, UI_Main parent, Stage self, Customer customer, boolean scanned) {
		super.init(db, parent.getStage(), self);
		this.scanned = scanned;
		this.customer = customer;
		this.parent = parent;
		refreshView();
		loadData();
	}
	
	@FXML
	public void btnAcceptOnClick() {
		boolean userConfirmed = false;
		if (wasEdited) {
			
			UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
			dialog.init(Locale.getString(CONST.TXT_CONFIRM_SAVE_CHANGES), parent.getStage(), 
					Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
			userConfirmed = dialog.call();
		} else {
			userConfirmed = true;	
		}
		
		if (userConfirmed && validateData()) {
			saveChanges();
			try {
				customerDB.updateSingleObject(customer, false);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			self.close();
			parent.refreshView();
		}
	}
	
	@FXML
	public void btnCancelOnClick() {
		self.close();
	}
	
	@FXML
	public void btnHelpOnClick() {
		
	}
	
	@FXML
	public void btnEditOnClick() {
		toggleEditMode();
	}
	
	@FXML
	public void btnEntriesOnClick() {
		
	}
	
	@FXML
	public void btnExtendOnClick() {
		
	}
	
	@FXML
	public void btnAssignCardOnClick() {
		
	}
	
	public void refreshView() {
		FXID_ACCEPT_BTN.setText(Locale.getString(CONST.TXT_ACCEPT));
		FXID_CANCEL_BTN.setText(Locale.getString(CONST.TXT_CANCEL));
		FXID_EDIT_BTN.setText(Locale.getString(CONST.TXT_BTN_EDIT_MODE));
		FXID_ENTRIES_BTN.setText(Locale.getString(CONST.TXT_BTN_ADD_ENTRIES));
		FXID_EXTEND_BTN.setText(Locale.getString(CONST.TXT_BTN_EXTEND));
		FXID_CARD_BTN.setText(Locale.getString(CONST.TXT_BTN_GET_CARD));
		FXID_NAME.setText(Locale.getString(CONST.TXT_NAME));
		FXID_SURNAME.setText(Locale.getString(CONST.TXT_SURNAME));
		FXID_DOB.setText(Locale.getString(CONST.TXT_DOB));
		FXID_PHONE.setText(Locale.getString(CONST.TXT_PHONE));
		FXID_CARD.setText(Locale.getString(CONST.TXT_CARDNUM));
		FXID_OPEN.setText(Locale.getString(CONST.TXT_OPEN));
		FXID_ENTRIES.setText(Locale.getString(CONST.TXT_ENTRIES));
		FXID_ACTIVITIES.setText(Locale.getString(CONST.TXT_ACTIVITIES));
		FXID_CUSTOMER_NAME.setText(customer.getName() + " " + customer.getSurname());
		FXID_CARD_BTN.setDisable(!editMode);
		FXID_ACTIVITIES_FIELD.setVisible(scanned);
		FXID_ACTIVITIES_BOX.setVisible(scanned);
		FXID_HELP_BTN.setVisible(scanned);
		FXID_NAME_FIELD.setEditable(editMode);
		FXID_SURNAME_FIELD.setEditable(editMode);
		FXID_DOB_FIELD.setEditable(editMode);
		FXID_PHONE_FIELD.setEditable(editMode);
		FXID_CARD_FIELD.setEditable(editMode);
		if (editMode) {
			FXID_NAME_FIELD.setId(CONST.FIELD_ENABLED);
			FXID_SURNAME_FIELD.setId(CONST.FIELD_ENABLED);
			FXID_PHONE_FIELD.setId(CONST.FIELD_ENABLED);
			FXID_DOB_FIELD.setId(CONST.FIELD_ENABLED);
			FXID_CARD_FIELD.setId(CONST.FIELD_ENABLED);
		} else {
			FXID_NAME_FIELD.setId(CONST.FIELD_DISABLED);
			FXID_SURNAME_FIELD.setId(CONST.FIELD_DISABLED);
			FXID_PHONE_FIELD.setId(CONST.FIELD_DISABLED);
			FXID_DOB_FIELD.setId(CONST.FIELD_DISABLED);
			FXID_CARD_FIELD.setId(CONST.FIELD_DISABLED);
		}

		
	}
	
	public void loadData() {
		FXID_NAME_FIELD.setText(customer.getName());
		FXID_SURNAME_FIELD.setText(customer.getSurname());
		FXID_DOB_FIELD.setText(customer.getDOB());
		FXID_PHONE_FIELD.setText(customer.getPhone());
		FXID_ENTRIES_FIELD.setText(String.valueOf(customer.getEntries()));
		FXID_OPEN_FIELD.setText(customer.openDateProperty().get());
		FXID_CARD_FIELD.setText(customer.cardProperty().get());
		FXID_CUSTOMER_NAME.setText(customer.getName() + " " + customer.getSurname());
		
	}
	
	public void saveChanges() {
		customer.setName(FXID_NAME_FIELD.getText());
		customer.setSurname(FXID_SURNAME_FIELD.getText());
		customer.setPhone(FXID_PHONE_FIELD.getText());
		customer.setDOB(FXID_DOB_FIELD.getText());
		try {
			if (FXID_CARD_FIELD.getText().equals(Locale.getString(CONST.TXT_NONE)) || FXID_CARD_FIELD.getText().equals("")) {
				customerDB.assignCard(customer, null);
			} else {
				customerDB.assignCard(customer, customerDB.getCard(Integer.parseInt(FXID_CARD_FIELD.getText())));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		customer.setEntries(Integer.parseInt(FXID_ENTRIES_FIELD.getText()));
		customer.getOpenDate().setTime(customer.getOpenDate().getTime() + TimeUnit.DAYS.toMillis(openAdded));
	}
	
	public void toggleEditMode() {
		wasEdited = true;
		editMode = (!editMode);
		refreshView();
	}
	
	private boolean validateData() {
		UI_ConfirmDialog dialog;
		dialog = UI_ConfirmDialog.getInstance();
		if (FXID_NAME_FIELD.getText().trim().length() <= 0) {
			dialog.init(Locale.getString(CONST.TXT_ERR_NAME_FIELD_EMPTY), parent.getStage(), Locale.getString(CONST.TXT_ACCEPT), DialogType.ERROR);
			dialog.call();
			return false;
		}
		if (FXID_SURNAME_FIELD.getText().trim().length() <= 0) {
			dialog.init(Locale.getString(CONST.TXT_ERR_SURNAME_FIELD_EMPTY), parent.getStage(), Locale.getString(CONST.TXT_ACCEPT), DialogType.ERROR);
			dialog.call();
			return false;
		}
		String cardText = FXID_CARD_FIELD.getText().trim();
		if (!(cardText.equals(Locale.getString(CONST.TXT_NONE)) || cardText.equals(""))) {
			try { 
				Integer.parseInt(cardText);
			} catch(Exception ex) {
				dialog.init(Locale.getString(CONST.TXT_ERR_CARD_FIELD_INVALID), parent.getStage(), Locale.getString(CONST.TXT_ACCEPT), DialogType.ERROR);
				dialog.call();
				return false;
			}
		}
		
		
		return true;
	}
	
}
