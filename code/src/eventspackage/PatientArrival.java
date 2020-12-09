package eventspackage;

import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;


public class PatientArrival extends EventType {
	public PatientArrival(Patient patient, Room r) {
		super();
		this.setPatient(patient);
		this.setType("ARR"+Integer.toString(this.getPatient().getsLevel()));
		this.setDuration(0);
		this.setLocation(r);
		
		this.setBeginning(false);
	}

	public PatientArrival() {
		super();
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
	
}
