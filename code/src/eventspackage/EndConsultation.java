package eventspackage;

import ressourcehr.Patient;
import ressourcehr.Physician;
import room.Room;
import visitorpattern.Visitor;

public class EndConsultation extends EventType {
	public EndConsultation(Patient patient, Physician physician, Room room) {
		super();
		this.setType("ENDCONSULTATION");
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
