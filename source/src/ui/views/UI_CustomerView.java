package ui.views;


import data.objects.Customer;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.mains.UI_Main;
import ui.utils.CustomerOptions;

public class UI_CustomerView extends UI_View {
   
    
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
    TableColumn<Customer, CustomerOptions> FXID_COLUMN_OPTIONS;
    
    private final ObservableList<Customer> tableData = FXCollections.observableArrayList();

    
    @Override
    public void init(CustomerDB customerDB, UI_Main parent) {
        FXID_COLUMN_ID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        FXID_COLUMN_NAME.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        FXID_COLUMN_SURNAME.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
        FXID_COLUMN_DOB.setCellValueFactory(new PropertyValueFactory<Customer, String>("DOB"));
        FXID_COLUMN_PHONE.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        FXID_COLUMN_OPEN.setCellValueFactory(new PropertyValueFactory<Customer, String>("openDate"));
        FXID_COLUMN_ENTRIES.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("entries"));
        
        FXID_TABLE.setItems(tableData);
        
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

        reloadCustomers();
    }
    
    public void reloadCustomers() {
        tableData.clear();
        for (Customer c : customerDB.getCustomers().values()) {
            tableData.add(c);
        }
    }

}
