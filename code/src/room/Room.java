package room;

import main.RessourceNHR;

public class Room extends RessourceNHR {
	private String name;
	public Room (String name) {
		this.name = name;
	}
	public Room () {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
