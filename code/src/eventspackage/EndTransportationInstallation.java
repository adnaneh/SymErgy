package eventspackage;

import ressourcehr.Nurse;
import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;

public class EndTransportationInstallation extends EventType {
	public EndTransportationInstallation(Patient patient, Nurse nurse, Room room) {
		super();
		this.setType("ENDINSTALLATION");
		this.setPatient(patient);
		this.setNurse(nurse);
		this.setLocation(room);
		this.setBeginning(false);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
