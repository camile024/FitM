package ui.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import data.objects.Activity;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import ui.dialogs.UI_ActivityDialog;
import ui.dialogs.UI_YesNoDialog;
import ui.mains.UI_Main;

public class ActivityOptions implements Callback<TableColumn.CellDataFeatures<Activity, HBox>,ObservableValue<HBox>>{
	UI_Main parent;
	CustomerDB customerDB;
	
	public ActivityOptions(CustomerDB db, UI_Main parent) {
		customerDB = db;
		this.parent = parent;
	}
	
    @Override
    public ObservableValue<HBox> call(CellDataFeatures<Activity, HBox> param) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        ArrayList<Button> btnList = new ArrayList<Button>();
        
        Button btnDelete = new Button();
        Button btnEdit = new Button();
        
        btnList.add(btnDelete);
        btnList.add(btnEdit);
        
        for (Button b : btnList) {
            b.setMaxWidth(32);
            b.setMaxHeight(32);
            b.setPrefWidth(32);
            b.setPrefHeight(32);
            hbox.getChildren().add(b);
        }
 
        /* Set action for delete button */
        btnDelete.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent act) {
        		deleteOnClick(param.getValue());
        }});
        
        /* Set action for edit button */
        btnEdit.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent act) {
        		editOnClick(param.getValue());
        }});

        ImageView imgDelete = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_DELETE_FILENAME));
        ImageView imgEdit = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_EDIT_FILENAME));
        imgDelete.setFitWidth(24);
        imgDelete.setFitHeight(24);
        imgEdit.setFitWidth(24);
        imgEdit.setFitHeight(24);
        btnDelete.setGraphic(imgDelete);
        btnEdit.setGraphic(imgEdit);

        return new ReadOnlyObjectWrapper<HBox>(hbox);
    }
    
    
    
 
    
    
    private void deleteOnClick(Activity activity) {
    	boolean userConfirmed = false;
		UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
		dialog.init(Locale.getString(CONST.TXT_CONFIRM_DELETE) + " " +
		 " [" + activity.getId() + "] '" + activity.getName() + "' " +
				Locale.getString(CONST.TXT_FROM_SYSTEM) + "?", parent.getStage(), 
				Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
		userConfirmed = dialog.call();
		if (userConfirmed) {
			try {
				customerDB.removeSingleObject(activity);
				parent.refreshView();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
    }
    
    private void editOnClick(Activity activity) {
    	UI_ActivityDialog dialog = UI_ActivityDialog.getInstance();
    	dialog.init(customerDB, parent.getStage(), activity);
    	dialog.call();
    	parent.refreshView();
    }
    
    
   

}
