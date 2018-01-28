package data.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Activity {
	
	/* Highest ID loaded in the memory */
	private static int highestID = -1;
	
	private int id;
	private int order;
	private String name;
	
	private SimpleStringProperty nameProperty;
	private SimpleIntegerProperty idProperty;
	
	
	public Activity(String name) {
		this(highestID + 1, name);
	}
	
	public Activity(int id, String name) {
		this.id = id;
		this.name = name;
		if (id > highestID) {
			highestID = id;
		} else {
			this.id = ++highestID;
		}
		
		nameProperty = new SimpleStringProperty();
		idProperty = new SimpleIntegerProperty();
	}
	
	public static void resetID() {
		highestID = -1;
	}
	
	public boolean equals(Activity act) {
		return (act.getId() == this.getId());
	}
	
	
	/* **************************** */
	/*								*/
	/* 		GETTERS/SETTERS 		*/
	/*								*/
	/* **************************** */
	
	public static int getHighestId() {
		return highestID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public SimpleIntegerProperty idProperty() {
		idProperty.set(id);
		return idProperty;
	}
	
	public SimpleStringProperty nameProperty() {
		nameProperty.set(name);
		return nameProperty;
	}
}
