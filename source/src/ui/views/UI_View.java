package ui.views;

import engine.CustomerDB;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ui.mains.UI_Main;

public abstract class UI_View {
   
    protected CustomerDB customerDB;
    protected UI_Main parent;
    
    @FXML
    BorderPane FXID_MAIN_VIEW;
    
    @FXML
    Text FXID_VIEW_TITLE_TEXT;
    
    public void init(CustomerDB customerDB, UI_Main parent) {
        this.parent = parent;
        this.customerDB = customerDB;
        refreshView();
    }
    
    public abstract void refreshView();
}
