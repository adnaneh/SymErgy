package eventspackage;

import ressourcehr.Nurse;
import ressourcehr.Patient;
import visitorpattern.Visitor;

public class EndRegistration extends EventType {
	public EndRegistration(Patient patient, Nurse nurse) {
		super();
		this.setType("ENDREGIST");
		this.setPatient(patient);
		this.setNurse(nurse);
		this.setBeginning(false);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
