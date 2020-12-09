package eventspackage;

import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;

public class EndMRI extends EventType {
	public EndMRI(Patient patient, Room room) {
		super();
		this.setType("ENDMRI");
		this.setPatient(patient);
		this.setLocation(room);
		this.setBeginning(false);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
