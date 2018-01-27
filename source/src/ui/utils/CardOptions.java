package ui.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import FXML.FXLoader;
import data.objects.Card;
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

public class CardOptions implements Callback<TableColumn.CellDataFeatures<Card, HBox>,ObservableValue<HBox>>{
	UI_Main parent;
	CustomerDB customerDB;
	
	public CardOptions(CustomerDB db, UI_Main parent) {
		customerDB = db;
		this.parent = parent;
	}
	
    @Override
    public ObservableValue<HBox> call(CellDataFeatures<Card, HBox> param) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(5, 5, 5, 5));
        hbox.setSpacing(10);
        ArrayList<Button> btnList = new ArrayList<Button>();
        
        Button btnDelete = new Button();
        
        
        /* See if there's a Customer assigned */
        Customer customer = param.getValue().getCustomer();
        if (customer != null) {
        	Button btnUnassign = new Button();
        	Button btnInfo = new Button();
        	
        	/* Set action for info button */
            btnInfo.setOnAction(new EventHandler<ActionEvent>() { 
            	public void handle(ActionEvent act) {
            		infoOnClick(param.getValue().getCustomer()); 
            }});
            
            /* Set action for unassign button */
            btnUnassign.setOnAction(new EventHandler<ActionEvent>() { 
            	public void handle(ActionEvent act) {
            		unassignOnClick(param.getValue().getCustomer());
            }});
            
            
            ImageView imgInfo = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_INFO_FILENAME));
            ImageView imgUnassign = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_MINUS_FILENAME));
            imgUnassign.setFitWidth(24);
            imgUnassign.setFitHeight(24);
            imgInfo.setFitWidth(24);
            imgInfo.setFitHeight(24);
            btnInfo.setGraphic(imgInfo);
            btnUnassign.setGraphic(imgUnassign);
            btnList.add(btnUnassign);
            btnList.add(btnInfo);
        }
        
        /* Set action for delete button */
        btnDelete.setOnAction(new EventHandler<ActionEvent>() { 
        	public void handle(ActionEvent act) {
        		deleteOnClick(param.getValue());
        }});
        
        
        btnList.add(btnDelete);
        for (Button b : btnList) {
            b.setMaxWidth(32);
            b.setMaxHeight(32);
            b.setPrefWidth(32);
            b.setPrefHeight(32);
            hbox.getChildren().add(b);
        }
        
        ImageView imgDelete = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_DELETE_FILENAME));
        imgDelete.setFitWidth(24);
        imgDelete.setFitHeight(24);
        btnDelete.setGraphic(imgDelete);

        return new ReadOnlyObjectWrapper<HBox>(hbox);
    }
    
    private void infoOnClick(Customer customer) {
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
    }
    
    private void deleteOnClick(Card card) {
    	boolean userConfirmed = false;
		UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
		dialog.init(Locale.getString(CONST.TXT_CONFIRM_DELETE) + " " +
		Locale.getString(CONST.TXT_CARD) + " " + card.getNumber() + " "
				+ Locale.getString(CONST.TXT_FROM_SYSTEM) + "?", parent.getStage(), 
				Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
		userConfirmed = dialog.call();
		if (userConfirmed) {
			try {
				customerDB.removeSingleObject(card);
				parent.refreshView();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
    }
    
    private void unassignOnClick(Customer customer) {
    	boolean userConfirmed = false;
    	UI_YesNoDialog dialog = UI_YesNoDialog.getInstance();
    	dialog.init(Locale.getString(CONST.TXT_CONFIRM_UNASSIGN) + " [" + customer.getId() + "] " + customer.getName() + " " +
    	customer.getSurname() + "?", parent.getStage(), 
    					Locale.getString(CONST.TXT_YES), Locale.getString(CONST.TXT_NO), DialogType.CONFIRM);
    	userConfirmed = dialog.call();
    	if (userConfirmed) {
    		try {
				customerDB.assignCard(customer, null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    }

}
