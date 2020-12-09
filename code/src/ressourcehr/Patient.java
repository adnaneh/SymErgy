package ressourcehr;

import java.util.ArrayList;
import java.util.Random;

public class Patient extends RessourceHR  {

	//constructor 
	public Patient(String name, String surname, String insurance) {
		super(name, surname);
		this.insurance= insurance;
		Random rand = new Random();
		//On donne un niveau de sévérité aléatoire lorsqu'il n'est pas défini
		this.sLevel=1+rand.nextInt(5);
		
	}
	
	//constructor, insurance is random and name is patientN surname is patientN
	public Patient(int severity) {
		super();
		this.setName("Patient"+this.getId());
		this.setSurname("Patient"+this.getId());
		//this.setState("waiting-registration");
		Random rand = new Random();
		int n= rand.nextInt(3);
		switch (n) {
		case 0:
			this.insurance= "none";
			break;
		case 1:
			this.insurance="silver";
			break;
		case 2:
			this.insurance= "gold";
			break;
			}
		
		this.sLevel= severity;
		this.setState("arrived");
		
	}
	//Les charges du patient
	private double charges = 0;
	//Le physicien en charge du patient
	private Physician physician;
	//Type d'assurance aucune ou silver:50% ou gold: 80%
	private String insurance;
	//Severity level
	private int sLevel;
	//Arrival time
	private double arrivalTime;
	//the patient's treatment history
	private ArrayList<PatientMilestone> history = new ArrayList<PatientMilestone> ();

	
	public double getLenghOfStay() {
		if (this.getState().equals("released")) {
			return this.getHistory().get(this.getHistory().size()-1).getTimestamp()-this.getHistory().get(0).getTimestamp(); //Temps de sortie-temps d'entrée
		}
		else {return 0;}
		 
	}
	public double getDtodTime() {
		if (this.getHistory().size()>5) { // Le patient a effectivement été consulté
			return this.getHistory().get(5).getTimestamp()-this.getHistory().get(0).getTimestamp();
		}
		else {
			return 0;
		}
	}
	

	public void updateHistory(PatientMilestone milestone){
		history.add(milestone);
	}

	
	public String toString() {
		String res="";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " State: " + this.getState();
		res+= " Severity level: " + this.sLevel;
		res+= " ArrivalTime: "+ this.getArrivalTime();
		
		return res;
	}
	//method to compute the charges based on the insurance
	public void computeCharges(double d) {

		switch(this.insurance) {
		case "none":
			this.charges = this.charges+d;
			break;
		case "silver":
			this.charges += this.charges +d/2;
			break;
		case "gold":
			this.charges+= this.charges+d*0.8;
			break;
		}
		
	}
	
	//getters and setters
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public int getsLevel() {
		return sLevel;
	}
	public void setsLevel(int sLevel) {
		this.sLevel = sLevel;
	}
	public ArrayList<PatientMilestone> getHistory() {
		return history;
	}
	public void setHistory(ArrayList<PatientMilestone> history) {
		this.history = history;
	}
	public double getArrivalTime() {
		return arrivalTime;
	}
	public Physician getPhysician() {
		return physician;
	}
	public void setPhysician(Physician physician) {
		this.physician = physician;
	}
	public double getCharges() {
		return charges;
	}
	public void setCharges(double charges) {
		this.charges = charges;
	}
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}








	
	
}
