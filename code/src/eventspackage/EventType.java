package eventspackage;


import healthservice.HealthService;
import main.Message;
import ressourcehr.Nurse;
import ressourcehr.Patient;
import ressourcehr.Physician;
import ressourcehr.Transporter;
import room.Room;
import visitorpattern.Visitable;
import visitorpattern.Visitor;

public class EventType implements Visitable{
	private String type;
	private double duration;
	private int cost;
	private Patient patient;
	private Physician physician;
	private Nurse nurse;
	private Transporter transporter;
	private Room location;
	private HealthService hs;
	private boolean isBeginning = true; // valeur qui vaut true si c'est une event qui marque le début d'une action et false si cela l'achève
	
	
	
	
	
	public EventType() {super();}
	public EventType(HealthService hs) {super();this.hs=hs;}
	public EventType(String type, int duration, int cost) {
		super();
		this.type = type;
		this.duration = duration;
		this.cost = cost;
		
	}
	
	public Event getEndingEvent() {
		return new Event(); //Cette méthode sera ovveridée par toutes les classes hérités de EventType
	} 
	public void messagePhysi(double timestamp) {
		String s = this.patient.getName()+ " is in state "+ this.patient.getState();
		Message m = new Message(s, timestamp);
		if (this.physician!=null) {
			this.physician.getMsgs().add(m);
		}
	}
	
	//Getters & Setters
	public String getType() {return type;}
	public void setType(String type) {this.type = type;}
	public double getDuration() {return duration;}
	public void setDuration(double duration2) {this.duration = duration2;}
	public int getCost() {return cost;}
	public void setCost(int cost) {this.cost = cost;}
	public Patient getPatient() {return patient;}
	public void setPatient(Patient patient) {this.patient = patient;}
	public Room getLocation() {return location;}
	public void setLocation(Room location) {this.location = location;}

	public boolean isBeginning() {
		return isBeginning;
	}
	public void setBeginning(boolean isBeginning) {
		this.isBeginning = isBeginning;
	}
	public Physician getPhysician() {
		return physician;
	}
	public void setPhysician(Physician physician) {
		this.physician = physician;
	}
	public Nurse getNurse() {
		return nurse;
	}
	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	
	// the accept method will be overrided in subclasses
	@Override
	public void accept(Visitor visitor) {
		
		
	}
	public HealthService getHs() {
		return hs;
	}
	public void setHs(HealthService hs) {
		this.hs = hs;
	}

	

	
}
