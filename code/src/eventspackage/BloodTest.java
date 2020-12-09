package eventspackage;


import healthservice.HealthService;
import ressourcehr.Patient;
import room.BloodTestRoom;
import visitorpattern.Visitor;

public class BloodTest extends Examination {
	public BloodTest(Patient patient,BloodTestRoom room, HealthService hs) { //Constructeur pour créer un BeginningEventType de bloodtest dans une bloodtestroom
		super(hs);
		this.setType("BLOOD");
		double duration = hs.getP().generateSample();
		this.setDuration(duration);
		this.setPatient(patient);
		this.setCost(1);
		this.setBeginning(true);
		this.setLocation(room);
	}
	

	public BloodTest() {
		super();
	}

	

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
	

	
	
	

}
