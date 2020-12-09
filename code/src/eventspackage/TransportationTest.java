package eventspackage;

import ressourcehr.Patient;
import ressourcehr.Transporter;
import room.Room;
import visitorpattern.Visitor;

public class TransportationTest extends EventType {
	private String examType;
	
	public TransportationTest(Patient patient, Transporter transporter, String examType, Room r) {
		super();
		this.setType("TEST");
		this.setDuration(5);
		this.setTransporter(transporter);
		this.setPatient(patient);
		this.setExamType(examType);
		this.setBeginning(true);
		this.setLocation(r);
		
	}

	public TransportationTest() {
		super();
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
}
