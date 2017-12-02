package data.objects;

public class Activity {
	
	/* Highest ID loaded in the memory */
	private static int highestID = 0;
	
	private int id;
	private String name;
	
	public Activity(int id, String name) {
		this.id = id;
		this.name = name;
		if (id > highestID) {
			highestID = id;
		}
	}
	
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
}
