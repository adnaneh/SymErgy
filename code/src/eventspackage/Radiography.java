package eventspackage;

import java.util.Random;

import healthservice.HealthService;
import ressourcehr.Patient;
import room.RadiographyRoom;
import visitorpattern.Visitor;



public class Radiography extends Examination {
	public Radiography(Patient patient, RadiographyRoom room, HealthService hs) {
		super(hs);
		this.setType("XRAY");
		Random rand= new Random();
		double duration = hs.getP().generateSample();
		this.setDuration(duration);
		this.setLocation(room);
		this.setCost(1);
		this.setPatient(patient);
		this.setBeginning(true);
	}

	public Radiography() {
		super();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

	

}
