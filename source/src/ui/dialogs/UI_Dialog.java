package ui.dialogs;

import engine.CustomerDB;
import javafx.stage.Stage;

public abstract class UI_Dialog {
	protected CustomerDB customerDB;
	protected Stage parent;
	protected Stage self;
	
	
	public void init(CustomerDB db, Stage parent, Stage self) {
		customerDB = db;
		this.parent = parent;
		this.self = self;
	}
	
	public void refreshView() {
		
	}
}
