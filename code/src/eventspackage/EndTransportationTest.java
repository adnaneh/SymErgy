package eventspackage;

import ressourcehr.Patient;
import ressourcehr.Transporter;
import visitorpattern.Visitor;

public class EndTransportationTest extends EventType {
	private String examType;
	public EndTransportationTest(Patient patient, Transporter transporter, String examType) {
		super();
		this.setType("ENDTEST");
		this.setPatient(patient);
		this.setTransporter(transporter);
		this.setBeginning(false);
		this.examType=examType;
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
