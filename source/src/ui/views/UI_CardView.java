package ui.views;

import java.util.LinkedList;
import data.objects.Card;
import data.objects.Customer;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ui.dialogs.UI_CardDialog;
import ui.dialogs.UI_ConfirmDialog;
import ui.mains.UI_Main;
import ui.utils.CardOptions;
import ui.utils.DialogType;

public class UI_CardView extends UI_View {

	   
    @FXML
    Text FXID_SEARCH_LABEL;
    @FXML
    TextField FXID_SEARCH_FIELD;
    @FXML
    Button FXID_SEARCH_BTN;
    @FXML
    Button FXID_ADD_BTN;
    @FXML
    TableView<Card> FXID_TABLE;
    @FXML
    TableColumn<Card, Integer> FXID_COLUMN_ID;
    @FXML
    TableColumn<Card, String> FXID_COLUMN_NUMBER;
    @FXML
    TableColumn<Card, String> FXID_COLUMN_NAME;
    @FXML
    TableColumn<Card, String> FXID_COLUMN_SURNAME;
    @FXML
    TableColumn<Card, HBox> FXID_COLUMN_OPTIONS;
    
    private final ObservableList<Card> tableData = FXCollections.observableArrayList();
    private String currentFilter = "";
    
    
    @FXML
    public void btnSearchOnClick() {
        filterTable(FXID_SEARCH_FIELD.getText());
    }
    
    @FXML
    public void btnAddOnClick() {
    	UI_CardDialog dialog = UI_CardDialog.getInstance();
    	dialog.init(customerDB, parent.getStage(), true);
    	dialog.call();
    	/* if a valid card scanned/not cancelled */
    	if (dialog.getCard() > 0) {
    		Card newCard = new Card(dialog.getCard());
    		/* check if a card with this num already exists */
    		if (customerDB.getCard(newCard.getNumber()) == null) {
    			/* if not, add it, refresh view */
    			try {
					customerDB.addSingleObject((Card) newCard);
				} catch (Exception e) {
					e.printStackTrace();
				}
    			parent.refreshView();
    		/* card exists - show error */
    		} else {
    			UI_ConfirmDialog errDialog = UI_ConfirmDialog.getInstance();
    			errDialog.init(Locale.getString(CONST.TXT_ERR_CARD_EXISTS), parent.getStage(), Locale.getString(CONST.TXT_OK), DialogType.ERROR);
    			errDialog.call();
    		}
    		
    	}
    }
    
    
    @Override
    public void init(CustomerDB customerDB, UI_Main parent) {
        FXID_COLUMN_ID.setCellValueFactory(new PropertyValueFactory<Card, Integer>("customerId"));
        FXID_COLUMN_NUMBER.setCellValueFactory(new PropertyValueFactory<Card, String>("number"));
        FXID_COLUMN_NAME.setCellValueFactory(new PropertyValueFactory<Card, String>("customerName"));
        FXID_COLUMN_SURNAME.setCellValueFactory(new PropertyValueFactory<Card, String>("customerSurname"));
        FXID_COLUMN_OPTIONS.setCellValueFactory(new CardOptions(customerDB, parent));
        
        FXID_TABLE.setItems(tableData);
        
        /* Pressing enter will filter too */
        FXID_SEARCH_FIELD.setOnAction( EventHandler -> {
            btnSearchOnClick();
        });
        
        /* If removed all text, un-filter automatically */
        FXID_SEARCH_FIELD.setOnKeyTyped(EventHandler -> {
            if (FXID_SEARCH_FIELD.getText().length() == 0) {
                btnSearchOnClick();
            }
        });
        
        
        super.init(customerDB, parent);
        
    }
    
    @Override
    public void refreshView() {
        FXID_VIEW_TITLE_TEXT.setText(Locale.getString(CONST.TXT_TITLE_VIEW_CARD));
        FXID_COLUMN_ID.setText(Locale.getString(CONST.TXT_ID));
        FXID_COLUMN_NUMBER.setText(Locale.getString(CONST.TXT_CARDNUM));
        FXID_COLUMN_NAME.setText(Locale.getString(CONST.TXT_NAME));
        FXID_COLUMN_SURNAME.setText(Locale.getString(CONST.TXT_SURNAME));
        FXID_COLUMN_OPTIONS.setText(Locale.getString(CONST.TXT_OPTIONS));
        FXID_SEARCH_LABEL.setText(Locale.getString(CONST.TXT_SEARCH) + ":");
        FXID_SEARCH_BTN.setText(Locale.getString(CONST.TXT_SEARCH));
        FXID_ADD_BTN.setText(Locale.getString(CONST.TXT_BTN_ADD_CARD));
        
        filterTable(currentFilter);
    }
    
    public void reloadCards() {
        tableData.clear();
        for (Card c : customerDB.getCards().values()) {
            tableData.add(c);
        }
    }
    

    
    public void filterTable(String text) {
    	currentFilter = text;
        if (text.equals("")) {
            reloadCards();
        } else {
            text = text.toUpperCase();
            LinkedList<Card> firstPriority = new LinkedList<Card>();
            LinkedList<Card> secondPriority = new LinkedList<Card>();
            LinkedList<Card> thirdPriority = new LinkedList<Card>();
            
            
            for (Card c : customerDB.getCards().values()) {
            	Customer customer = c.getCustomer();
                /* Get (name + surname) / (Sname + name) matches first */
            	if (String.valueOf(c.getNumber()).toUpperCase().contains(text)) {
            		firstPriority.add(c);
            	} else if ( customer != null && ((customer.getName() + " " + customer.getSurname()).toUpperCase().contains(text) ||
                        (customer.getSurname() + " " + customer.getName()).contains(text))){
                    secondPriority.add(c);
                } else if (customer != null && (String.valueOf(customer.getId()).toUpperCase().contains(text))) { //get ID next
                    thirdPriority.add(c);
                }
            }
            
            tableData.clear();
            tableData.addAll(firstPriority);
            tableData.addAll(secondPriority);
            tableData.addAll(thirdPriority);
        }
        
    }
    


}
