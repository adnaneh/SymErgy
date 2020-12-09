package room;

public class BoxRoom extends Room {

	public BoxRoom() {
		super();
	}

	public BoxRoom(String name) {
		super(name);
	}
	public String toString() {
		String res="BoxRoom ";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " Availability: "+ this.isDispo();
		
		return res;
	}

}
