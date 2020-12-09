package ressourcehr;

import main.Ressource;
import room.Room;

public class RessourceHR extends Ressource {
	public RessourceHR() {
		super();
	}
	
	private String name;
	private String surname;
	private String state = "idle";
	private Room location;
	public RessourceHR(String name, String surname) {
		super();
		this.name=name;
		this.surname= surname;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Room getLocation() {
		return location;
	}

	public void setLocation(Room location) {
		this.location = location;
	}
	
	
	
}
