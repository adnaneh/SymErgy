package room;


public class WaitingRoomSpot extends Room {
	//These are the spots available in the waiting Room, each spot can be filled by a Patient 
	private WaitingRoom wr;
	public WaitingRoomSpot(WaitingRoom wr) {
		super();
		this.wr = wr;
	}
	public WaitingRoomSpot() {
		super();
	}
}
