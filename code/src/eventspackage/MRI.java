package eventspackage;


import healthservice.HealthService;
import ressourcehr.Patient;
import room.MRIRoom;
import visitorpattern.Visitor;

public class MRI extends Examination {
	public MRI(Patient patient,MRIRoom room, HealthService hs) {
		super(hs);
		this.setType("MRI");
		double duration =hs.getP().generateSample();
		this.setDuration(duration);
		this.setPatient(patient);
		this.setLocation(room);
		this.setCost(1);
		this.setBeginning(true);
		}
	

	

	public MRI() {
		super();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}
	

	

}
