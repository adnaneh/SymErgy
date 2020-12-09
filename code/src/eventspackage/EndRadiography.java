package eventspackage;

import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;

public class EndRadiography extends EventType{
	public EndRadiography(Patient patient,Room room) {
		super();
		this.setType("ENDXRAY");
		this.setPatient(patient);
		this.setLocation(room);
		this.setBeginning(false);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
