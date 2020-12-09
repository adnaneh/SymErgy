package room;

public class BloodTestRoom extends Room {

	public BloodTestRoom() {
		super();
	}

	public BloodTestRoom(String name) {
		super(name);
	}

	public String toString() {
		String res="BloodTestRoom ";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " Availability: "+ this.isDispo();
		
		return res;
	}



}
