package eventspackage;

import ressourcehr.Nurse;
import ressourcehr.Patient;
import visitorpattern.Visitor;

public class Registration extends EventType {
	// Register the patient
	// Requires a nurse
	
	public Registration(Patient patient, Nurse nurse) {
		super();
		this.setType("REGIST");
		this.setDuration(2); //We assume that a nurse needs 2min to register a patient
		this.setNurse(nurse);
		this.setPatient(patient);
		this.setBeginning(true);
	}
	public Registration() {
		super();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
