package healthservice;

import java.util.ArrayList;

import probability.ProbabilityDistribution;
import ressourcehr.Patient;

public class HealthService {
	//name: X-ray, MRI scan, blood-test, consultation
	private String name;
	// attribute waiting queue of the patients
	private ArrayList<Patient> wList= new ArrayList<Patient> ();
	//attribute: distribution of probability representing the duration of the service
	private ProbabilityDistribution p;
	//cost of the service
	private double cost=0;
	//put new patient in waiting queue
	public void addPatient(Patient p){
		this.wList.add(p);
	}
	
	//getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Patient> getwList() {
		return wList;
	}
	public void setwList(ArrayList<Patient> wList) {
		this.wList = wList;
	}
	public ProbabilityDistribution getP() {
		return p;
	}
	public void setP(ProbabilityDistribution p) {
		this.p = p;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	};

	

	
}
