package room;

public class RadiographyRoom extends Room {

	public RadiographyRoom() {
		super();
	}

	public RadiographyRoom(String name) {
		super(name);
	}
	public String toString() {
		String res="RadiographyRoom ";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " Availability: "+ this.isDispo();
		
		return res;
	}
}
