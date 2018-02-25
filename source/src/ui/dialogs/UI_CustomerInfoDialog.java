package ui.dialogs;

import java.io.FileNotFoundException;
import java.util.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import data.objects.Activity;
import data.objects.Card;
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
	private Date initOpenDate;
	
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
		customer.setOpenDate(initOpenDate);
		parent.refreshView();
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
		String result = null; 
		int parsedResult = 0;
		int parsedBase = 0;
		UI_InputDialog dialog = UI_InputDialog.getInstance();
		dialog.init(Locale.getString(CONST.TXT_TITLE_MODIFY_ENTRIES), Locale.getString(CONST.TXT_PROMPT_ENTRIES), parent.getStage(), 
				Locale.getString(CONST.TXT_BTN_ADD_ENTRIES), DialogType.CONFIRM);
		
		do {
			result = dialog.call();
		} while (!validateIntegerNum(result));
		if (result.trim().equals("") || result.trim().equals("0")) {
			result = "0";
		} else {
			wasEdited = true;
		}
		parsedResult = Integer.parseInt(result);
		parsedBase = Integer.parseInt(FXID_ENTRIES_FIELD.getText());
		FXID_ENTRIES_FIELD.setText(String.valueOf(parsedResult + parsedBase));
		
		
		
	}
	
	@FXML
	public void btnExtendOnClick() {
		/* Prepare dialog, get valid output */
		String result = null; 
		int parsedResult = 0;
		UI_InputDialog dialog = UI_InputDialog.getInstance();
		dialog.init(Locale.getString(CONST.TXT_TITLE_MODIFY_OPEN), Locale.getString(CONST.TXT_PROMPT_OPEN), parent.getStage(), 
				Locale.getString(CONST.TXT_BTN_EXTEND), DialogType.CONFIRM);
		
		do {
			result = dialog.call();
		} while (!validateIntegerNum(result));
		if (result.trim().equals("")) {
			result = "0";
		}
		parsedResult = Integer.parseInt(result);
		
		/* Get current date */
		Date currentDate = Locale.getCurrentDate();
		long millisPerDay = TimeUnit.DAYS.toMillis(1);
		long currentMillis = (currentDate.getTime() / millisPerDay) * millisPerDay;
		currentDate = new Date(currentMillis);
		boolean reset = false;
		/* Date currently in the box is earlier - we're re-setting it 
		 * (sets to '0 day (yesterday)' */
		if (customer.getOpenDate().compareTo(currentDate) < 0) {
			customer.setOpenDate(new Date(currentDate.getTime() - millisPerDay));
			reset = true;
		} 
		
		/* Negative number - subtract from current open date */
		if (customer.getOpenDate().compareTo(currentDate) >= 0 && parsedResult < 0) {
			if (!reset) {
				customer.setOpenDate(new Date(TimeUnit.DAYS.toMillis(parsedResult) + customer.getOpenDate().getTime()));
				/* Make sure not to go out of lower bounds 
				 * (if triggered - sets to '0 days (yesterday)' */
				if (customer.getOpenDate().compareTo(currentDate) < 0) {
					customer.setOpenDate(new Date(currentDate.getTime() - millisPerDay));
				}
			}
			
		}
		
		/* Check if we're actually adding */
		if (parsedResult > 0) {
			customer.setOpenDate(new Date(TimeUnit.DAYS.toMillis(parsedResult) + customer.getOpenDate().getTime()));
		}
		
		
		
		
		FXID_OPEN_FIELD.setText(customer.openDateProperty().get());	
		wasEdited = true;
	}
	
	@FXML
	public void btnAssignCardOnClick() {
		UI_CardDialog dialog = UI_CardDialog.getInstance();
		dialog.init(customerDB, parent.getStage(), false);
		dialog.call();
		if (dialog.getCard() >= 0) {
			FXID_CARD_FIELD.setText(String.valueOf(dialog.getCard()));
		} else {
			FXID_CARD_FIELD.setText(Locale.getString(CONST.TXT_NONE));
		}
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
		initOpenDate = customer.getOpenDate();
		
	}
	
	public void saveChanges() {
		customer.setName(FXID_NAME_FIELD.getText());
		customer.setSurname(FXID_SURNAME_FIELD.getText());
		customer.setPhone(FXID_PHONE_FIELD.getText());
		customer.setDOB(FXID_DOB_FIELD.getText());
		/* Same as within UI_CustomerAddDialog but doesn't create a new card if invalid number in the field.
		 * When inspecting a customer, a valid existing card has to be assigned.
		 */
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
			dialog.init(Locale.getString(CONST.TXT_ERR_NAME_FIELD_EMPTY), parent.getStage(), Locale.getString(CONST.TXT_OK), DialogType.ERROR);
			dialog.call();
			return false;
		}
		if (FXID_SURNAME_FIELD.getText().trim().length() <= 0) {
			dialog.init(Locale.getString(CONST.TXT_ERR_SURNAME_FIELD_EMPTY), parent.getStage(), Locale.getString(CONST.TXT_OK), DialogType.ERROR);
			dialog.call();
			return false;
		}
		String cardText = FXID_CARD_FIELD.getText().trim();
		if (!(cardText.equals(Locale.getString(CONST.TXT_NONE)) || cardText.equals(""))) {
			try { 
				Integer.parseInt(cardText);
			} catch(Exception ex) {
				dialog.init(Locale.getString(CONST.TXT_ERR_CARD_FIELD_INVALID), parent.getStage(), Locale.getString(CONST.TXT_OK), DialogType.ERROR);
				dialog.call();
				return false;
			}
			
			/* Card not in the system */
			if (customerDB.getCard(Integer.parseInt(cardText)) == null) {
				dialog.init(Locale.getString(CONST.TXT_ERR_CARD_NOT_FOUND), parent.getStage(), Locale.getString(CONST.TXT_OK), DialogType.ERROR);
				dialog.call();
				return false;
			}
			
			/* Check if the card is already assigned to a customer - ask user whether to overwrite */
			Card card = customerDB.getCard(Integer.parseInt(cardText));
			if (card != null && card.getCustomer() != null && card.getCustomer() != customer) {
				UI_YesNoDialog ynDialog = UI_YesNoDialog.getInstance();
				ynDialog.init(Locale.getString(CONST.TXT_ERR_CARD_ASSIGNED), parent.getStage(),
						Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
				return ynDialog.call();
			}
		}
		
		
		return true;
	}
	
	/**
	 * Validates a String. Returns true when:
	 * a) String is empty ("")
	 * b) String is a valid positive/negative number (incl. 0)
	 * When false is returned, an error message is displayed too.
	 * @param string
	 * @return
	 */
	private boolean validateIntegerNum(String string) {
		boolean result = false;
		UI_ConfirmDialog dialog = UI_ConfirmDialog.getInstance();
		if (string == null) {
			result = false;
		} else if (string.trim().equals("")) {
			result = true;
		} else {
			try {
				Integer.parseInt(string);
				result = true;
			} catch (Exception ex) {
				result = false;
			}
		}
		
		if (result == false) {
			dialog.init(Locale.getString(CONST.TXT_ERR_INVALID_NUMBER), parent.getStage(), Locale.getString(CONST.TXT_OK), DialogType.ERROR);
			dialog.call();
		}
		
		return result;
	}
}
