package eventspackage;


import healthservice.HealthService;
import main.Message;
import ressourcehr.Patient;
import ressourcehr.Physician;
import room.Room;
import visitorpattern.Visitor;


public class Consultation extends EventType {
	//Requires a physician
	public Consultation(Patient patient, Physician physician, Room room,HealthService hs) {
		super(hs);
		this.setType("CONSULTATION");
		double d = hs.getP().generateSample(); 
		this.setDuration(d);
		this.setPatient(patient);
		this.setPhysician(physician);
		this.setLocation(room);
		this.setCost(1);
		this.setBeginning(true);
		
	}
	
	//On override la méthode messagePhysi dans consultation pour indiquer que c'est un nouveau patient
	@Override
	public void messagePhysi(double timestamp) {
		String s = this.getPatient().getName()+ " was affected to you and is waiting consultation";
		Message m = new Message(s, timestamp);
		if (this.getPhysician()!=null) {
			this.getPhysician().getMsgs().add(m);
		}
	}


	public Consultation() {
		super();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

}
