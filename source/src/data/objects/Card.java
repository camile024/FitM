package data.objects;

import engine.CONST;
import engine.Locale;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Card {
	private int number;
	private Customer customer;
	
	private SimpleIntegerProperty numberProperty;
	private SimpleStringProperty customerIdProperty;
	private SimpleStringProperty customerNameProperty;
	private SimpleStringProperty customerSurnameProperty;
	
	public Card(int number) {
		this.number = number;
		numberProperty = new SimpleIntegerProperty();
		customerIdProperty = new SimpleStringProperty();
		customerNameProperty = new SimpleStringProperty();
		customerSurnameProperty = new SimpleStringProperty();
	}
	
	public Card(int number, Customer customer) {
		this(number);
		this.customer = customer;
	}

	
	
	/* **************************** */
	/*								*/
	/* 		GETTERS/SETTERS 		*/
	/*								*/
	/* **************************** */
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getNumber() {
		return number;
	}
	
	public SimpleIntegerProperty numberProperty() {
		numberProperty.set(number);
		return numberProperty;
	}
	
	public SimpleStringProperty customerIdProperty() {
		if (customer != null) {
			customerIdProperty.set(String.valueOf(customer.getId()));
		} else {
			customerIdProperty.set(Locale.getString(CONST.TXT_NONE));
		}
		return customerIdProperty;
	}
	
	public SimpleStringProperty customerNameProperty() {
		if (customer != null) {
			customerNameProperty.set(customer.getName());
		} else {
			customerNameProperty.set(Locale.getString(""));
		}
		return customerNameProperty;
	}
	
	public SimpleStringProperty customerSurnameProperty() {
		if (customer != null) {
			customerSurnameProperty.set(customer.getSurname());
		} else {
			customerSurnameProperty.set(Locale.getString(""));
		}
		return customerSurnameProperty;
	}
}