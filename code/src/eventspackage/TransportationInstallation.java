package eventspackage;


import ressourcehr.Nurse;
import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;

public class TransportationInstallation extends EventType {
	//Require a nurse and a room( box room or shock room)
	
	public TransportationInstallation(Patient patient, Nurse nurse, Room room) {
		super();
		this.setType("INSTALLATION");
		this.setDuration(2);
		this.setPatient(patient);
		this.setNurse(nurse);
		this.setLocation(room);
		this.setBeginning(true);
		
	}

	public TransportationInstallation() {
		super();
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

}
