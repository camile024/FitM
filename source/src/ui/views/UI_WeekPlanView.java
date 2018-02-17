package ui.views;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import data.Attendance;
import data.Reservation;
import data.objects.Activity;
import data.objects.GymDay;
import data.objects.WeekPlan;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ui.dialogs.UI_ActivityDialog;
import ui.mains.UI_Main;
import ui.utils.ActivityOptions;
import ui.utils.ResourceLocalizer;

public class UI_WeekPlanView extends UI_View {

	@FXML
	Text FXID_VIEW_TITLE_TEXT;
    @FXML
    ComboBox<String> FXID_DAY_COMBO;
    @FXML
    ComboBox<Activity> FXID_ACTIVITY_COMBO;
    @FXML
    Text FXID_D_MON;
    @FXML
    Text FXID_D_TUE;
    @FXML
    Text FXID_D_WED;
    @FXML
    Text FXID_D_THU;
    @FXML
    Text FXID_D_FRI;
    @FXML
    Text FXID_D_SAT;
    @FXML
    Text FXID_D_SUN;
    @FXML
    VBox FXID_MON;
    @FXML
    VBox FXID_TUE;
    @FXML
    VBox FXID_WED;
    @FXML
    VBox FXID_THU;
    @FXML
    VBox FXID_FRI;
    @FXML
    VBox FXID_SAT;
    @FXML
    VBox FXID_SUN;
    @FXML
    Button FXID_ADD_BTN;

    private WeekPlan plan;
    
    @Override
    public void init(CustomerDB customerDB, UI_Main parent) {
        
        plan = customerDB.getWeekPlan();
        super.init(customerDB, parent);
        
    }
    
    @Override
    public void refreshView() {
        FXID_VIEW_TITLE_TEXT.setText(Locale.getString(CONST.TXT_TITLE_WEEKPLAN));
        FXID_D_MON.setText(Locale.getString(CONST.TXT_MON));
        FXID_D_TUE.setText(Locale.getString(CONST.TXT_TUE));
        FXID_D_WED.setText(Locale.getString(CONST.TXT_WED));
        FXID_D_THU.setText(Locale.getString(CONST.TXT_THU));
        FXID_D_FRI.setText(Locale.getString(CONST.TXT_FRI));
        FXID_D_SAT.setText(Locale.getString(CONST.TXT_SAT));
        FXID_D_SUN.setText(Locale.getString(CONST.TXT_SUN));
        FXID_ADD_BTN.setText(Locale.getString(CONST.TXT_ADD));
        
        FXID_DAY_COMBO.setPromptText(Locale.getString(CONST.TXT_PROMPT_WEEKDAY));
        FXID_ACTIVITY_COMBO.setPromptText(Locale.getString(CONST.TXT_PROMPT_ACTIVITY));
        fillCombos();
        fillWeekdays();
    }
    
    @FXML
    public void btnAddOnClick() {
    	addActivity(FXID_DAY_COMBO.getSelectionModel().getSelectedIndex()+1, FXID_ACTIVITY_COMBO.getValue());
    	refreshView();
    }

    private void fillCombos() {
    	ObservableList<Activity> activities = FXCollections.observableArrayList();
    	ObservableList<String> days = FXCollections.observableArrayList(
    			Locale.getString(CONST.TXT_MONDAY),
    			Locale.getString(CONST.TXT_TUESDAY),
    			Locale.getString(CONST.TXT_WEDNESDAY),
    			Locale.getString(CONST.TXT_THURSDAY),
    			Locale.getString(CONST.TXT_FRIDAY),
    			Locale.getString(CONST.TXT_SATURDAY),
    			Locale.getString(CONST.TXT_SUNDAY));
    	
    	activities.addAll(customerDB.getActivities().values());
    	FXID_DAY_COMBO.setItems(days);
    	FXID_ACTIVITY_COMBO.setItems(activities);
    	
    }
    
    
    private void fillWeekdays() {
    	fillDay(FXID_MON, 1);
    	fillDay(FXID_TUE, 2);
    	fillDay(FXID_WED, 3);
    	fillDay(FXID_THU, 4);
    	fillDay(FXID_FRI, 5);
    	fillDay(FXID_SAT, 6);
    	fillDay(FXID_SUN, 7);
    }
    
    private void fillDay(VBox box, int day) {
    	box.getChildren().clear();
    	ArrayList<Activity> activities = plan.getActivities().get(day);
    	for (Activity a : activities) {
    		HBox dayBox = new HBox();
    		dayBox.setPrefHeight(64);
    		dayBox.setSpacing(5);
    		
    		Region separator = new Region();
    		HBox.setHgrow(separator, Priority.ALWAYS);
    		
    		dayBox.getStyleClass().add("frame2");
    		dayBox.setPadding(new Insets(3, 3, 3, 3));
    		dayBox.setAlignment(Pos.CENTER_LEFT);
    		
    		Text actText = new Text(a.getName());
    		actText.setTextAlignment(TextAlignment.LEFT);;
    		actText.getStyleClass().add("regLabel");
    		
    		Button btn = new Button();
    		btn.setAlignment(Pos.CENTER_RIGHT);
    		btn.setMaxHeight(16);
    		btn.setMaxWidth(16);
    		btn.setPrefWidth(16);
    		btn.setPrefHeight(16);
    		btn.getStyleClass().add("calendarHappened");
    		ImageView imgDelete = new ImageView(ResourceLocalizer.getImage(CONST.RES_IMG_BTN_DELETE_FILENAME));
    		imgDelete.setFitWidth(16);
            imgDelete.setFitHeight(16);
            btn.setGraphic(imgDelete);
    		btn.setOnAction(new EventHandler<ActionEvent>() { 
            	public void handle(ActionEvent act) {
            		removeActivity(day, a); 
            }});
    		dayBox.getChildren().add(actText);
    		dayBox.getChildren().add(separator);
    		dayBox.getChildren().add(btn);
    		box.getChildren().add(dayBox);
    	
    	}
    	
    	
    }
    
    
    private void removeActivity(int day, Activity act) {
    	try {
    		plan.removeActivity(day, act);
			customerDB.updateSingleObject(plan, false);
			refreshView();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
    private void addActivity(int day, Activity act) {
    	try {
    		plan.addActivity(day, act);
			customerDB.updateSingleObject(plan, false);
			refreshView();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
  

}
