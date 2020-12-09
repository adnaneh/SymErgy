package ressourcehr;

import java.util.ArrayList;
import java.util.Random;



//import com.google.common.collect.Range;


import main.Message;

public class Physician extends RessourceHR{
	//constructors
	public Physician(String name, String surname) {
		super(name, surname);
	}
	public Physician() {
		super();
		this.setName("physician"+this.getId());
		this.setSurname("physician"+this.getId());
	}
	
	//username
	private String username;
	//Liste des patients en cours d'examination ou ayant été examinés 
	private ArrayList<Patient> patientsHistory= new ArrayList<Patient>();
	//Liste des patients en cours d'examination
	private ArrayList<Patient> patients= new ArrayList<Patient>();
	//Message box qui stocke des remarques sur les patients examinés
	private ArrayList<Message> msgs = new ArrayList<Message>();

	//start handling of a (newly arrived) patient
	public void handleNewPatient(Patient p) {}
	//emit a verdict for a treated patient 
	public void emitVerdict(Patient p) {}
	//prescribe a given exam (e.g. x-ray, RMI, etc) to a treated patient each one is represented by an int
	public String genTest(){
		//generate number x in [0;1]
		Random r = new Random();
		double x = r.nextDouble();
		//no test required
		if (0<x && x<0.35){return("exam-passed");}
		//require radiography
		if (0.35 <x && x<0.55){return("needing-xray");}
		//require bloodtest
		if (0.55 <x && x<0.95){return("needing-bloodtest");}
		//require MRI
		if (0.95<x && x<1){return("needing-mri");}
		return null;
	}
	public void receiveMessage(Message m) {
		msgs.add(m);
	}
	//display the content of the message-box 
	public void displayBoxContent() {
		for (Message m: msgs){
			System.out.println(" Message received at" + m.getD());
			System.out.println(" Message Id"+ m.getId());
			System.out.println(m.getStr());
		}
	}
	
	//ToString Method
	public String toString() {
		String res="";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " State: " + this.getState();
		res+="\n";
		res+=" Mail-box ";
		for (Message m: msgs){
			res+="\n";
			res+=" Message received at " + m.getD();
			res+=" Message Id "+ m.getId();
			res+=" " +m.getStr()+ " ";
		}
		
		return res;
	}
	//handle a newly received message by removing it
	public void removeMessage(Message m) {
		this.msgs.remove(m);
	}
	//handle a newly received message by storing it useless?
	public void storeMessage() {
		
	}
	//handle a newly received message by marking it as unread.
	public void markUnreadMessage(Message m){
		m.setUnread(true);
	}
	public ArrayList<Patient> getPatients() {
		return this.patients;
	}
	public void addPatient(Patient patient) {
		if (!this.patients.contains(patient)) {
			this.patients.add(patient);
		}
	}
	public ArrayList<Message> getMsgs() {
		return msgs;
	}
	public void setMsgs(ArrayList<Message> msgs) {
		this.msgs = msgs;
	}
	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	public ArrayList<Patient> getPatientsHistory() {
		return patientsHistory;
	}
	public void setPatientsHistory(ArrayList<Patient> patientsHistory) {
		this.patientsHistory = patientsHistory;
	}


}