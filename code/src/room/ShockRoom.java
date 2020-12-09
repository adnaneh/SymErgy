package room;

public class ShockRoom extends Room {

	public ShockRoom() {
		super();
	}

	public ShockRoom(String name) {
		super(name);
	}
	public String toString() {
		String res="ShockRoom ";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " Availability: "+ this.isDispo();
		
		return res;
	}
}
