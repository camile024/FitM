package ui.views;


import java.io.IOException;
import java.util.LinkedList;

import FXML.FXLoader;
import data.objects.Customer;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.dialogs.UI_CustomerAddDialog;
import ui.dialogs.UI_CustomerInfoDialog;
import ui.mains.UI_Main;
import ui.utils.CustomerOptions;

public class UI_CustomerView extends UI_View {
   
    @FXML
    Text FXID_SEARCH_LABEL;
    @FXML
    TextField FXID_SEARCH_FIELD;
    @FXML
    Button FXID_SEARCH_BTN;
    @FXML
    Button FXID_ADD_BTN;
    @FXML
    TableView<Customer> FXID_TABLE;
    @FXML
    TableColumn<Customer, Integer> FXID_COLUMN_ID;
    @FXML
    TableColumn<Customer, String> FXID_COLUMN_NAME;
    @FXML
    TableColumn<Customer, String> FXID_COLUMN_SURNAME;
    @FXML
    TableColumn<Customer, String> FXID_COLUMN_DOB;
    @FXML
    TableColumn<Customer, String> FXID_COLUMN_PHONE;
    @FXML
    TableColumn<Customer, Integer> FXID_COLUMN_ENTRIES;
    @FXML
    TableColumn<Customer, String> FXID_COLUMN_OPEN;
    @FXML
    TableColumn<Customer, HBox> FXID_COLUMN_OPTIONS;
    
    private final ObservableList<Customer> tableData = FXCollections.observableArrayList();
    private String currentFilter = "";
    
    
    @FXML
    public void btnSearchOnClick() {
        filterTable(FXID_SEARCH_FIELD.getText());
    }
    
    @FXML
    public void btnAddOnClick() {
    	Stage stage = new Stage();
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_CUSTOMER_ADD_DIALOG_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_CustomerAddDialog uiDialog = ((UI_CustomerAddDialog)(loader.getController()));
        
        
        uiDialog.init(customerDB, parent, stage);
        Scene scene = new Scene(pane);
        
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle(Locale.getString(CONST.TXT_TITLE_ADD_CUSTOMER));
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        /* End of FXML part */
    }
    
    
    @Override
    public void init(CustomerDB customerDB, UI_Main parent) {
        FXID_COLUMN_ID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        FXID_COLUMN_NAME.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        FXID_COLUMN_SURNAME.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
        FXID_COLUMN_DOB.setCellValueFactory(new PropertyValueFactory<Customer, String>("DOB"));
        FXID_COLUMN_PHONE.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        FXID_COLUMN_OPEN.setCellValueFactory(new PropertyValueFactory<Customer, String>("openDate"));
        FXID_COLUMN_ENTRIES.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("entries"));
        
        FXID_COLUMN_OPTIONS.setCellValueFactory(new CustomerOptions(customerDB, parent));
        
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
        FXID_VIEW_TITLE_TEXT.setText(Locale.getString(CONST.TXT_TITLE_VIEW_CUSTOMER));
        FXID_COLUMN_ID.setText(Locale.getString(CONST.TXT_ID));
        FXID_COLUMN_NAME.setText(Locale.getString(CONST.TXT_NAME));
        FXID_COLUMN_SURNAME.setText(Locale.getString(CONST.TXT_SURNAME));
        FXID_COLUMN_DOB.setText(Locale.getString(CONST.TXT_DOB));
        FXID_COLUMN_PHONE.setText(Locale.getString(CONST.TXT_PHONE));
        FXID_COLUMN_ENTRIES.setText(Locale.getString(CONST.TXT_ENTRIES));
        FXID_COLUMN_OPEN.setText(Locale.getString(CONST.TXT_OPEN));
        FXID_COLUMN_OPTIONS.setText(Locale.getString(CONST.TXT_OPTIONS));
        FXID_SEARCH_LABEL.setText(Locale.getString(CONST.TXT_SEARCH) + ":");
        FXID_SEARCH_BTN.setText(Locale.getString(CONST.TXT_SEARCH));
        FXID_ADD_BTN.setText(Locale.getString(CONST.TXT_BTN_ADD_CUSTOMER));
        
        filterTable(currentFilter);
    }
    
    public void reloadCustomers() {
        tableData.clear();
        for (Customer c : customerDB.getCustomers().values()) {
            tableData.add(c);
        }
    }
    

    
    public void filterTable(String text) {
    	currentFilter = text;
        if (text.equals("")) {
            reloadCustomers();
        } else {
            text = text.toUpperCase();
            LinkedList<Customer> firstPriority = new LinkedList<Customer>();
            LinkedList<Customer> secondPriority = new LinkedList<Customer>();
            LinkedList<Customer> thirdPriority = new LinkedList<Customer>();
            LinkedList<Customer> fourthPriority = new LinkedList<Customer>();
            
            
            for (Customer c : customerDB.getCustomers().values()) {
                /* Get (name + surname) / (Sname + name) matches first */
                if ((c.getName() + " " + c.getSurname()).toUpperCase().contains(text) ||
                        (c.getSurname() + " " + c.getName()).contains(text)){
                    firstPriority.add(c);
                } else if (String.valueOf(c.getId()).toUpperCase().contains(text)) { //get ID next
                    secondPriority.add(c);
                } else if (c.getPhone().toUpperCase().contains(text) ||
                        c.getDOB().contains(text)) { //DOB / Phone
                    thirdPriority.add(c);
                } else if (String.valueOf(c.getEntries()).contains(text) ||
                        c.openDateProperty().get().contains(text)) { //Entries / Open days
                    fourthPriority.add(c);
                }
            }
            
            tableData.clear();
            tableData.addAll(firstPriority);
            tableData.addAll(secondPriority);
            tableData.addAll(thirdPriority);
            tableData.addAll(fourthPriority);
        }
        
    }
    

}
