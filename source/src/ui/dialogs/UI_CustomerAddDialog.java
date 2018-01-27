package ui.dialogs;

import java.io.FileNotFoundException;
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

public class UI_CustomerAddDialog extends UI_Dialog {
	private UI_Main parent;
	private Customer customer;
	
	@FXML
	Button FXID_ACCEPT_BTN;
	@FXML
	Button FXID_CANCEL_BTN;
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
	Text FXID_TITLE;
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

	
	
	public void init(CustomerDB db, UI_Main parent, Stage self) {
		super.init(db, parent.getStage(), self);
		this.parent = parent;
		refreshView();
	}
	
	@FXML
	public void btnAcceptOnClick() {
		boolean userConfirmed = false;
			
			UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
			dialog.init(Locale.getString(CONST.TXT_CONFIRM_SAVE_CHANGES), parent.getStage(), 
					Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
			userConfirmed = dialog.call();
		if (userConfirmed && validateData()) {
			saveChanges();
			try {
				customerDB.addSingleObject(customer);
			} catch (Exception e) {
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
		FXID_CARD_BTN.setText(Locale.getString(CONST.TXT_BTN_GET_CARD));
		FXID_NAME.setText(Locale.getString(CONST.TXT_NAME));
		FXID_SURNAME.setText(Locale.getString(CONST.TXT_SURNAME));
		FXID_DOB.setText(Locale.getString(CONST.TXT_DOB));
		FXID_PHONE.setText(Locale.getString(CONST.TXT_PHONE));
		FXID_CARD.setText(Locale.getString(CONST.TXT_CARDNUM));	
		FXID_TITLE.setText(Locale.getString(CONST.TXT_TITLE_ADD_CUSTOMER));
	}
	
	
	public void saveChanges() {
		customer = new Customer();
		customer.setName(FXID_NAME_FIELD.getText());
		customer.setSurname(FXID_SURNAME_FIELD.getText());
		customer.setPhone(FXID_PHONE_FIELD.getText());
		customer.setDOB(FXID_DOB_FIELD.getText());
		try {
			/* See if valid card number in the text field (or no-card indicator) */
			if (FXID_CARD_FIELD.getText().equals(Locale.getString(CONST.TXT_NONE)) || FXID_CARD_FIELD.getText().equals("")) {
				/* No card */
				customerDB.assignCard(customer, null);
			} else {
				/* A card has been assigned - check if we've got it already */
				Card card = customerDB.getCard(Integer.parseInt(FXID_CARD_FIELD.getText()));
				if (card != null) {
					/* we do - assign that one */
					customerDB.assignCard(customer, card);
				} else {
					/* we don't - create a new one with given number */
					card = new Card(Integer.parseInt(FXID_CARD_FIELD.getText()));
					try {
						customerDB.addSingleObject(card);
					} catch (Exception e) {
						e.printStackTrace();
					}
					customerDB.assignCard(customer, card);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
			
			/* Check if the card is already assigned to a customer - ask user whether to overwrite */
			Card card = customerDB.getCard(Integer.parseInt(cardText));
			if (card != null && card.getCustomer() != null) {
				UI_YesNoDialog ynDialog = UI_YesNoDialog.getInstance();
				ynDialog.init(Locale.getString(CONST.TXT_ERR_CARD_ASSIGNED), parent.getStage(),
						Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
				return ynDialog.call();
			}
		}
		
		
		return true;
	}
}
