package eventspackage;


import ressourcehr.Patient;
import ressourcehr.Physician;
import room.Room;
import visitorpattern.Visitor;

public class Verdict extends EventType {
	public Verdict(Patient patient, Physician physician, Room room) {
		super();
		this.setType("VERDICT");
		this.setDuration(4); // We assume that a physician always make 4 minutes to emit a verdict
		this.setPatient(patient);
		this.setPhysician(physician);
		this.setLocation(room);
		this.setBeginning(true);
		
	}
	

	public Verdict() {
		super();
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
