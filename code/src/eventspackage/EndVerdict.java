package eventspackage;

import ressourcehr.Patient;
import ressourcehr.Physician;
import room.Room;
import visitorpattern.Visitor;

public class EndVerdict extends EventType {
	public EndVerdict(Patient patient, Physician physician, Room room) {
		super();
		this.setType("ENDVERDICT");
		this.setPatient(patient);
		this.setPhysician(physician);
		this.setLocation(room);
		this.setBeginning(false);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
