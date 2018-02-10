package ui.views;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import data.objects.Activity;
import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import ui.dialogs.UI_ActivityDialog;
import ui.mains.UI_Main;
import ui.utils.ActivityOptions;

public class UI_CalendarView extends UI_View {

	@FXML
	Text FXID_VIEW_TITLE_TEXT;
    @FXML
    GridPane FXID_CALENDAR_PANE;
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
    Button FXID_PLANNER_BTN;
    @FXML
    Button FXID_INFO_BTN;
    @FXML
    Button FXID_MONTH_BTN;

    private int month;
    private Date currentDate;
    
    @Override
    public void init(CustomerDB customerDB, UI_Main parent) {
        currentDate = new Date();
        LocalDate localDate = LocalDate.now();
        month = localDate.getMonthValue();
        
        super.init(customerDB, parent);
        
    }
    
    @Override
    public void refreshView() {
        FXID_VIEW_TITLE_TEXT.setText(Locale.getString(CONST.TXT_CALENDAR));
        FXID_INFO_BTN.setText(Locale.getString(CONST.TXT_INFO));
        FXID_PLANNER_BTN.setText(Locale.getString(CONST.TXT_BTN_PLANNER));
        FXID_D_MON.setText(Locale.getString(CONST.TXT_MON));
        FXID_D_TUE.setText(Locale.getString(CONST.TXT_TUE));
        FXID_D_WED.setText(Locale.getString(CONST.TXT_WED));
        FXID_D_THU.setText(Locale.getString(CONST.TXT_THU));
        FXID_D_FRI.setText(Locale.getString(CONST.TXT_FRI));
        FXID_D_SAT.setText(Locale.getString(CONST.TXT_SAT));
        FXID_D_SUN.setText(Locale.getString(CONST.TXT_SUN));
        FXID_MONTH_BTN.setText(getMonthString(month) + " " + LocalDate.now().getYear());
        fillCalendar();
    }
    
    @FXML
    public void btnPrevOnClick() {
    	
    }
    
    @FXML
    public void btnNextOnClick() {
    	
    }
    
    @FXML
    public void btnPlannerOnClick() {
    	
    }
    
    @FXML
    public void btnInfoOnClick() {
    	
    }
    
    private void fillCalendar() {
    	FXID_CALENDAR_PANE.getChildren().clear();
    	LocalDate localDate = LocalDate.now();
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.MONTH, month-1);
    	int daysInAMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	calendar.set(Calendar.DAY_OF_MONTH, 0);
    	int firstWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
    	calendar.set(Calendar.MONTH, decMonth(month-1));
    	int daysInPrevMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	int startPrev = daysInPrevMonth - firstWeekDay + 2;
    	int currentColumn = 0;
    	int currentRow = 0;
    	/* Generate pre-month days */
    	for (int i = startPrev; i <= daysInPrevMonth; i++) {
    		Button btn = new Button();
    		btn.setText(String.valueOf(i));
    		btn.getStyleClass().add("calendarButton");
    		btn.setDisable(true);
    		FXID_CALENDAR_PANE.add(btn, currentColumn, currentRow);
    		currentColumn++;
    	}
    	/* Finish the calendar */
    	int day = 0;
    	while (day++ <= daysInAMonth) {
    		if (currentColumn > 6) {
    			currentRow++;
    			currentColumn = 0;
    		}
    		Button btn = new Button();
    		GridPane.setVgrow(btn, Priority.ALWAYS);
    		GridPane.setHgrow(btn, Priority.ALWAYS);
    		btn.setText(String.valueOf(day));
    		btn.getStyleClass().add("calendarButton");
    		FXID_CALENDAR_PANE.add(btn, currentColumn, currentRow);
    		currentColumn++;
    		
    	}
    	/* Fill the rest of the week */
    	day = 1;
    	while(currentColumn < 7) {
    		Button btn = new Button();
    		btn.setText(String.valueOf(day++));
    		btn.getStyleClass().add("calendarButton");
    		btn.setDisable(true);
    		FXID_CALENDAR_PANE.add(btn, currentColumn, currentRow);
    		currentColumn++;
    	}
    }
    
    private int decMonth(int monthNum) {
    	if (monthNum > 1) {
    		return monthNum - 1;
    	} else {
    		return 12;
    	}
    }
    
    private int incMonth(int monthNum) {
    	return (monthNum % 12) + 1;
    }
    private String getMonthString(int monthNum) {
    	switch (monthNum) {
    		case 1:
    			return Locale.getString(CONST.TXT_JANUARY);
    		case 2:
    			return Locale.getString(CONST.TXT_FEBRUARY);
    		case 3:
    			return Locale.getString(CONST.TXT_MARCH);
    		case 4:
    			return Locale.getString(CONST.TXT_APRIL);
    		case 5:
    			return Locale.getString(CONST.TXT_MAY);
    		case 6:
    			return Locale.getString(CONST.TXT_JUNE);
    		case 7:
    			return Locale.getString(CONST.TXT_JULY);
    		case 8:
    			return Locale.getString(CONST.TXT_AUGUST);
    		case 9:
    			return Locale.getString(CONST.TXT_SEPTEMBER);
    		case 10:
    			return Locale.getString(CONST.TXT_OCTOBER);
    		case 11:
    			return Locale.getString(CONST.TXT_NOVEMBER);
    		case 12:
    			return Locale.getString(CONST.TXT_DECEMBER);
    	}
    	return "";
    }

    private Node getNodeFromPane(int col, int row) {
        for (Node node : FXID_CALENDAR_PANE.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}
