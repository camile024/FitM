package ui.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import FXML.FXLoader;
import data.objects.Customer;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import ui.dialogs.UI_CustomerInfoDialog;
import ui.dialogs.UI_YesNoDialog;
import ui.mains.UI_Main;

public class CustomerOptions implements Callback<TableColumn.CellDataFeatures<Customer, HBox>,ObservableValue<HBox>> {
	UI_Main parent;
	CustomerDB customerDB;
	
	public CustomerOptions(CustomerDB db, UI_Main parent) {
		customerDB = db;
		this.parent = parent;
	}
	
    @Override
    public ObservableValue<HBox> call(CellDataFeatures<Customer, HBox> param) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        ArrayList<Button> btnList = new ArrayList<Button>();
        Button btnInfo = new Button();
        Button btnDelete = new Button();
        Button btnEdit = new Button();
        
        /* Set action for info button */
        btnInfo.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent act) {
        		infoOnClick(param.getValue(), false); 
        }});
        
        /* Set action for edit button */
        btnEdit.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent act) {
        		infoOnClick(param.getValue(), true); //same as info but straight into editable
        }});
        
        /* Set action for delete button */
        btnDelete.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent act) {
        		deleteOnClick(param.getValue());
        }});
        
        btnList.add(btnInfo);
        btnList.add(btnEdit);
        btnList.add(btnDelete);
        for (Button b : btnList) {
            b.setMaxWidth(32);
            b.setMaxHeight(32);
            b.setPrefWidth(32);
            b.setPrefHeight(32);
            hbox.getChildren().add(b);
        }
        ImageView imgInfo = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_INFO_FILENAME));
        ImageView imgEdit = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_EDIT_FILENAME));
        ImageView imgDelete = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_DELETE_FILENAME));
        imgInfo.setFitWidth(24);
        imgInfo.setFitHeight(24);
        imgDelete.setFitWidth(24);
        imgDelete.setFitHeight(24);
        imgEdit.setFitWidth(24);
        imgEdit.setFitHeight(24);
        
        btnInfo.setGraphic(imgInfo);
        btnEdit.setGraphic(imgEdit);
        btnDelete.setGraphic(imgDelete);

        return new ReadOnlyObjectWrapper<HBox>(hbox);
    }
    
    private void infoOnClick(Customer customer, boolean editable) {
    	Stage stage = new Stage();
    	/* FXML part */
        FXMLLoader loader = FXLoader.getLoader(CONST.FXML_CUSTOMER_INFO_DIALOG_PATH);
        BorderPane pane = null;
        try {
            pane = (BorderPane) loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        UI_CustomerInfoDialog uiDialog = ((UI_CustomerInfoDialog)(loader.getController()));
        
        
        uiDialog.init(customerDB, parent, stage, customer, false);
        Scene scene = new Scene(pane);
        
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.setTitle(customer.getName() + " " + customer.getSurname());
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        /* End of FXML part */
        
        if (editable) {
        	uiDialog.toggleEditMode();
        }
    }
    
    private void deleteOnClick(Customer customer) {
    	boolean userConfirmed = false;
		UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
		dialog.init(Locale.getString(CONST.TXT_CONFIRM_DELETE) + " '" + customer.getName() + "' "
				+ Locale.getString(CONST.TXT_FROM_SYSTEM) + "?", parent.getStage(), 
				Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
		userConfirmed = dialog.call();
		if (userConfirmed) {
			try {
				customerDB.removeSingleObject(customer);
				parent.refreshView();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
    }







}
