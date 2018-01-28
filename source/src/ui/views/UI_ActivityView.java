package ui.views;

import java.util.LinkedList;

import data.objects.Activity;
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
import ui.dialogs.UI_ActivityDialog;
import ui.mains.UI_Main;
import ui.utils.ActivityOptions;

public class UI_ActivityView extends UI_View {

	   
    @FXML
    Text FXID_SEARCH_LABEL;
    @FXML
    TextField FXID_SEARCH_FIELD;
    @FXML
    Button FXID_SEARCH_BTN;
    @FXML
    Button FXID_ADD_BTN;
    @FXML
    TableView<Activity> FXID_TABLE;
    @FXML
    TableColumn<Activity, Integer> FXID_COLUMN_ID;
    @FXML
    TableColumn<Activity, String> FXID_COLUMN_NAME;
    @FXML
    TableColumn<Activity, HBox> FXID_COLUMN_OPTIONS;
    
    private final ObservableList<Activity> tableData = FXCollections.observableArrayList();
    private String currentFilter = "";
    
    
    @FXML
    public void btnSearchOnClick() {
        filterTable(FXID_SEARCH_FIELD.getText());
    }
    
    @FXML
    public void btnAddOnClick() {
    	UI_ActivityDialog dialog = UI_ActivityDialog.getInstance();
    	dialog.init(customerDB, parent.getStage(), (Activity)null);
    	dialog.call();
    	parent.refreshView();
    }
    
    
    @Override
    public void init(CustomerDB customerDB, UI_Main parent) {
        FXID_COLUMN_ID.setCellValueFactory(new PropertyValueFactory<Activity, Integer>("id"));
        FXID_COLUMN_NAME.setCellValueFactory(new PropertyValueFactory<Activity, String>("name"));
        FXID_COLUMN_OPTIONS.setCellValueFactory(new ActivityOptions(customerDB, parent));
        
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
        FXID_VIEW_TITLE_TEXT.setText(Locale.getString(CONST.TXT_TITLE_VIEW_ACTIVITY));
        FXID_COLUMN_ID.setText(Locale.getString(CONST.TXT_ID));
        FXID_COLUMN_NAME.setText(Locale.getString(CONST.TXT_NAME2));
        FXID_COLUMN_OPTIONS.setText(Locale.getString(CONST.TXT_OPTIONS));
        FXID_SEARCH_LABEL.setText(Locale.getString(CONST.TXT_SEARCH) + ":");
        FXID_SEARCH_BTN.setText(Locale.getString(CONST.TXT_SEARCH));
        FXID_ADD_BTN.setText(Locale.getString(CONST.TXT_BTN_ADD_ACTIVITY));
        
        filterTable(currentFilter);
    }
    
    public void realoadActivities() {
        tableData.clear();
        for (Activity c : customerDB.getActivities().values()) {
            tableData.add(c);
        }
    }
    

    
    public void filterTable(String text) {
    	currentFilter = text;
        if (text.equals("")) {
            realoadActivities();
        } else {
            text = text.toUpperCase();
            LinkedList<Activity> firstPriority = new LinkedList<Activity>();
            LinkedList<Activity> secondPriority = new LinkedList<Activity>();
            
            
            for (Activity a : customerDB.getActivities().values()) {
                /* Get name matches first */
            	if (String.valueOf(a.getName()).toUpperCase().contains(text)) {
            		firstPriority.add(a);
                } else if (String.valueOf(a.getId()).toUpperCase().contains(text)) { //get ID next
                    secondPriority.add(a);
                }
            }
            
            tableData.clear();
            tableData.addAll(firstPriority);
            tableData.addAll(secondPriority);
        }
        
    }

}
