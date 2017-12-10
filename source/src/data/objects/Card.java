package data.objects;

public class Card {
	private int number;
	private Customer customer;
	
	public Card(int number) {
		this.number = number;
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
}