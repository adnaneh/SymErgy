package room;

public class MRIRoom extends Room {

	public MRIRoom() {
		super();
	}

	public MRIRoom(String name) {
		super(name);
	}
	public String toString() {
		String res="MRIRoom ";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " Availability: "+ this.isDispo();
		
		return res;
	}
}
