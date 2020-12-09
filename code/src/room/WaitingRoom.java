package room;

import java.util.ArrayList;

public class WaitingRoom extends Room {

	//Constructors
	public WaitingRoom(String name) {
		super(name);
		//the wating room has 10 spots by default
		for (int i = 0; i<10; i++) {
			this.spots.add(new WaitingRoomSpot(this));
		}
	}
	public WaitingRoom() {
		super();
		//the waiting room has 10 spots by default
		for (int i = 0; i<10; i++) {
			this.spots.add(new WaitingRoomSpot(this));
		}
	}
	
	private ArrayList<WaitingRoomSpot> spots = new ArrayList<WaitingRoomSpot>();

	public ArrayList<WaitingRoomSpot> getSpots() {
		return spots;
	}
	public void setSpots(ArrayList<WaitingRoomSpot> spots) {
		this.spots = spots;
	}
	public String toString() {
		String res="WaitingRoom ";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		int n=0;
		for (WaitingRoomSpot s: this.getSpots()) {
			if (s.isDispo()) {
				n+=1;
			}
		}
		res+= " Number of spots Available: " + n;
		
		return res;
	}



}