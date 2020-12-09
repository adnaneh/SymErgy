package eventspackage;

import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;

public class WaitingResult extends EventType {
	//Constructor
	public WaitingResult(Patient patient,Room room) {
		super();
		this.setType("WAITINGRESULT");
		this.setDuration(0);
		this.setPatient(patient);
		this.setLocation(room);
		this.setBeginning(false); 	
	}
	
	
	//Default constructor
	public WaitingResult() {
		super();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
