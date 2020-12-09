package main;

import eventspackage.BloodTest;
import eventspackage.Consultation;
import probability.DetDistribution;
import probability.ProbabilityDistribution;

import java.util.ArrayList;
import java.util.Iterator;

import eventspackage.EndBloodTest;
import eventspackage.EndConsultation;
import eventspackage.EndMRI;
import eventspackage.EndRadiography;
import eventspackage.EndRegistration;
import eventspackage.EndTransportationInstallation;
import eventspackage.EndTransportationTest;
import eventspackage.EndVerdict;
import eventspackage.Event;
import eventspackage.EventType;
import eventspackage.MRI;
import eventspackage.PatientArrival;
import eventspackage.Radiography;
import eventspackage.Registration;
import eventspackage.TransportationInstallation;
import eventspackage.TransportationTest;
import eventspackage.Verdict;
import eventspackage.WaitingResult;
import ressourcehr.Patient;
import ressourcehr.PatientMilestone;
import room.Corridor;
import visitorpattern.Visitor;

public class SimErgy implements Visitor {
	//On associe à Symergie une eventQueue, une enabled Queue, un état state, un temps de simulation symTime
	private ED ed;
	private EventQueue eventQueue = new EventQueue();
	private EnabledEvents enabledEvents;
	public double simTime = 0;
	private ArrayList<Event> eventHistory = new ArrayList<Event>();// Historique des events
	
	//Distributions de probabilité de l'arrivée des patients avec valeurs par défaut
	private ProbabilityDistribution L1arrivalDist=  new DetDistribution(10);
	private ProbabilityDistribution L2arrivalDist= new DetDistribution(10);
	private ProbabilityDistribution L3arrivalDist =new DetDistribution(10);
	private ProbabilityDistribution L4arrivalDist =new DetDistribution(10);
	private ProbabilityDistribution L5arrivalDist =new DetDistribution(10);

	//constructor
	public SimErgy (ED ed) {

		this.ed = ed;

	}
	

	
	//Methode pour enlever le premier event la queue et le retourner. Si c'est un BeginningEvent, alors crée un event de fin correspondant
	public Event dequeue() {
		Event e = this.getEventQueue().getQueue().get(0);
		if (this.getEventQueue().getQueue().isEmpty()) {return null;}
		this.getEventQueue().getQueue().remove(0);
		return(e);
	}
	
	//Actualize les différentes queues en fonction du nouvel état de l'ED
	public void getNewEnabledQueue(ED ed) {
		
		EnabledEvents newEnabledEvents = new EnabledEvents(ed); //Création de la nouvelle EnabledQueue

		Iterator<Event> i = this.eventQueue.getQueue().iterator();	
		while (i.hasNext()) {

			Event e1=i.next();
			if(e1.getEventType().isBeginning()) {//Test d'abord si l'event n'est pas un ending event pour ne pas supprimer les ending events car ils ne sont pas dans enabledEvents
			Boolean enabled=false;
			for (EventType e2: newEnabledEvents.getEnabledQueue()) {
				if (e2.getType().equals(e1.getEventType().getType())){
					enabled=true;
					break;
					}
			}
			if (!enabled) {
				i.remove();
			}
			}

		}

		ArrayList <Event> auxList=new ArrayList<Event>();
		for (EventType e1: newEnabledEvents.getEnabledQueue()) { //Parcours des éléments de l'ancienne EnabledQueue

			Boolean notEvented=true;
			for (Event e2: this.eventQueue.getQueue()) {
				if (e2.getEventType().getType().equals(e1.getType())){notEvented=false;break;};
			}

			if (notEvented) {
				if (e1.getClass().equals(PatientArrival.class)) {
					int slevel = e1.getPatient().getsLevel();
				//Si l'event autorisé est un patient arrival alors le temps de réalisation dépend aussi de la distribution de probabilité
					switch(slevel) {
					case 1:
						auxList.add(new Event(e1,this.getSimTime()+this.L1arrivalDist.generateSample()));
						break;
					case 2:
						auxList.add(new Event(e1,this.getSimTime()+this.L2arrivalDist.generateSample()));
						break;
					case 3:
						auxList.add(new Event(e1,this.getSimTime()+this.L3arrivalDist.generateSample()));
						break;
					case 4:
						auxList.add(new Event(e1,this.getSimTime()+this.L4arrivalDist.generateSample()));
						break;
					case 5:
						auxList.add(new Event(e1,this.getSimTime()+this.L5arrivalDist.generateSample()));	
						break;
					}
				}
					//Si l'event autorisé n'est pas un patient arrival alors l'event se réalise au temps courant
					else {
						auxList.add(new Event(e1,this.getSimTime()));
					}
			//Now we add all elements of the auxList to the eventQueue
			}		
			}
		for(Event e:auxList) {
			this.eventQueue.getQueue().add(e);
		}
		

		this.eventQueue.sort(); //On retrie la nouvelle eventQueue obtenue
		this.setEnabledEvents(newEnabledEvents); //On change la enabledEventsQueue par la nouvelle associée au nouvel état
	}
	
	//Méthode qui renvoie le door to door time moyen global
	public double dtdTime() {
		int patients=0;
		double total=0;
		for (Patient p: this.ed.getPatientHistory()) {
			if (p.getHistory().size()>5) {
				patients++;
				total+=p.getDtodTime();
			}
		}
		return total/patients;
	}
	
	//Méthode qui renvoie le lengthOfStayTime moyen global
	public double lengthOfStayTime() {
		int patients=0;
		double total=0;
		for (Patient p: this.ed.getPatientHistory()) {
			if (p.getState().equals("released")) {
				patients++;
				total+=p.getLenghOfStay();
			}
		}
		return total/patients;

	}
	//Méthode qui renvoie le door to door time moyen pour les patients d'une certaine sévérité
	public double dtdTime(int s) {
		int patients=0;
		double total=0;
		for (Patient p: this.ed.getPatientHistory()) {
			//On garde seulement les patients de sévérité s
			if (p.getsLevel()==s) {
				if (p.getHistory().size()>5) {
					patients++;
					total+=p.getDtodTime();
				}
			}
		}
		return total/patients;
	}
	
	//Méthode qui renvoie le lengthOfStayTime moyen pour les patients d'une certaine sévérité
	public double lengthOfStayTime(int s) {
		int patients=0;
		double total=0;
		for (Patient p: this.ed.getPatientHistory()) {
			if (p.getsLevel()==s) {
				if (p.getState().equals("released")) {
					patients++;
					total+=p.getLenghOfStay();
				}
			}
		}
		return total/patients;

	}
	
	// visit methods
	public void visit(BloodTest e) {
			e.getPatient().setState("taking-blood-exam");
			e.getPatient().updateHistory(new PatientMilestone("taking-blood-exam",this.simTime));
			e.getLocation().fill();
			e.getPatient().setLocation(e.getLocation());
			e.messagePhysi(this.simTime);
			//On ajoute l'endingEvent à l'eventQueue
			this.eventQueue.getQueue().add(new Event(new EndBloodTest(e.getPatient(),e.getLocation()),this.simTime + e.getDuration()));
			//On ajoute le cout du test aux charges du patient
			e.getPatient().computeCharges(e.getHs().getCost());
			
			//On supprime le patient de la waiting list
			ed.getBtService().getwList().remove(e.getPatient());
	}	
	public void visit(EndBloodTest e) {
			e.getPatient().setState("exam-passed");
			e.getPatient().updateHistory(new PatientMilestone("exam-passed",this.simTime));
			e.getLocation().empty();
			e.getPatient().setLocation(this.ed.getHsWaitingRoom());
			e.messagePhysi(this.simTime);
		
		
	}

	public void visit(Consultation e) {

			e.getPatient().setState("being-consulted");
			e.getPatient().updateHistory(new PatientMilestone("being-consulted",this.simTime));
			e.getPhysician().fill();
			e.getPhysician().setState("consulting");
			e.getPhysician().addPatient(e.getPatient());
			e.getPhysician().getPatientsHistory().add(e.getPatient());
			e.getPatient().setPhysician(e.getPhysician());
			this.eventQueue.getQueue().add(new Event(new EndConsultation(e.getPatient(),e.getPhysician(),e.getLocation()),this.simTime + e.getDuration()));
			e.getPatient().computeCharges(e.getHs().getCost());
			ed.getConsultationService().getwList().remove(e.getPatient());
			//On retrie la liste des physiciens pour prendre en compte l'affectation du patient 
			ed.sortPhysi();
	}
	public void visit(EndConsultation e) {
			e.getPatient().setState(e.getPhysician().genTest());
			e.getPatient().updateHistory(new PatientMilestone(e.getPhysician().genTest(),this.simTime));
			e.getPhysician().setState("idle");
			e.getPhysician().empty();
			e.getLocation().empty();
			e.getPatient().setLocation(this.ed.getHsWaitingRoom());
			e.messagePhysi(this.simTime);
			if (e.getPatient().getState()!="exam-passed") {
			this.ed.getTtService().getwList().add(e.getPatient());
			}
		
	}
	public void visit(MRI e) {

			e.getPatient().setState("taking-mri-exam");
			e.getPatient().updateHistory(new PatientMilestone("taking-mri-exam",this.simTime));
			e.getLocation().fill();
			e.getPatient().setLocation(e.getLocation());
			e.messagePhysi(this.simTime);
			this.eventQueue.getQueue().add(new Event(new EndMRI(e.getPatient(),e.getLocation()),this.simTime + e.getDuration()));
			e.getPatient().computeCharges(e.getHs().getCost());
			ed.getMriService().getwList().remove(e.getPatient());
	}
	public void visit(EndMRI e) {
			e.getPatient().setState("exam-passed");
			e.getPatient().updateHistory(new PatientMilestone("exam-passed",this.simTime));
			e.getLocation().empty();
			e.getPatient().setLocation(this.ed.getHsWaitingRoom());
			e.messagePhysi(this.simTime);
		
		
		
	}
	public void visit(PatientArrival e) {
			e.getPatient().setState("waiting-registration");
			e.getPatient().updateHistory(new PatientMilestone("waiting-registration",this.simTime));
			e.getPatient().setLocation(e.getLocation());
			e.getPatient().setArrivalTime(this.simTime);
			//On ajoute le patient à la liste des patients de l'ED
			if (!e.getLocation().getClass().equals(Corridor.class)) {
				e.getLocation().fill();
			}
			this.ed.getPatientList().add(e.getPatient());
			this.ed.getPatientHistory().add(e.getPatient());
			this.ed.getRegService().getwList().add(e.getPatient());

		
	}
	public void visit(Radiography e) {

			e.getPatient().setState("taking-xray-exam");
			e.getPatient().updateHistory(new PatientMilestone("taking-xray-exam",this.simTime));
			e.getLocation().fill();
			e.getPatient().setLocation(e.getLocation());
			e.messagePhysi(this.simTime);
			this.eventQueue.getQueue().add(new Event(new EndRadiography(e.getPatient(),e.getLocation()),this.simTime + e.getDuration()));
			e.getPatient().computeCharges(e.getHs().getCost());
			ed.getRadioService().getwList().remove(e.getPatient());
			
	}
	public void visit(EndRadiography e) {
			e.getPatient().setState("exam-passed");
			e.getPatient().updateHistory(new PatientMilestone("exam-passed",this.simTime));
			e.getLocation().empty();
			e.getPatient().setLocation(this.ed.getHsWaitingRoom());
			e.messagePhysi(this.simTime);
		}
	
	
	public void visit(Registration e) {
			ed.getRegService().getwList().remove(e.getPatient());

			e.getNurse().setState("registering");

			e.getPatient().updateHistory(new PatientMilestone("registering",this.simTime));

			e.getNurse().fill();//Rendre l'infirmière indisponible

			e.getPatient().setState("registering");


			e.getPatient().updateHistory(new PatientMilestone("registering",this.simTime));
			this.eventQueue.getQueue().add(new Event(new EndRegistration(e.getPatient(),e.getNurse()),this.simTime + e.getDuration()));

		}
	public void visit(EndRegistration e) {
			e.getPatient().setState("waiting-installation");
			e.getPatient().updateHistory(new PatientMilestone("waiting-installation",this.simTime));
			e.getNurse().setState("idle");
			e.getNurse().empty();//Rendre l'infirmière disponible
			e.getPatient().setLocation(e.getLocation());
			this.ed.getTiService().getwList().add(e.getPatient());
		}
		
	
	public void visit(TransportationInstallation e) {

			e.getPatient().setState("installing");
			e.getPatient().updateHistory(new PatientMilestone("installing",this.simTime));
			e.getNurse().setState("installing");
			e.getNurse().fill();
			e.getLocation().fill();//On occupe la chambre où est transporté le patient
			e.getPatient().setLocation(e.getLocation());
			this.eventQueue.getQueue().add(new Event(new EndTransportationInstallation(e.getPatient(),e.getNurse(),e.getLocation()),this.simTime + e.getDuration()));
			ed.getTiService().getwList().remove(e.getPatient());
		}
	public void visit(EndTransportationInstallation e) {
			e.getPatient().setState("waiting-consultation");
			e.getPatient().updateHistory(new PatientMilestone("waiting-consultation",this.simTime));
			e.getNurse().setState("idle");
			e.getNurse().empty();
			e.getPatient().setLocation(e.getLocation());
			this.ed.getConsultationService().getwList().add(e.getPatient());
		}
		
	
	public void visit(TransportationTest e) {

			if (e.getExamType().equals("BLOOD")) {e.getPatient().setState("transport-bloodtest");
			e.getPatient().updateHistory(new PatientMilestone("transport-bloodtest",this.simTime));}
			if (e.getExamType().equals("XRAY")) {e.getPatient().setState("transport-xray");
			e.getPatient().updateHistory(new PatientMilestone("transport-xray",this.simTime));}
			if (e.getExamType().equals("MRI")) {e.getPatient().setState("transport-mri");
			e.getPatient().updateHistory(new PatientMilestone("transport-mri",this.simTime));}
			e.getTransporter().setState("busy");
			e.getTransporter().fill();
			e.getPatient().setLocation(e.getLocation());
			e.messagePhysi(this.simTime);
			this.eventQueue.getQueue().add(new Event(new EndTransportationTest(e.getPatient(),e.getTransporter(),e.getExamType()),this.simTime + e.getDuration()));
			ed.getTtService().getwList().remove(e.getPatient());
		}
	public void visit(EndTransportationTest e) {
			if (e.getExamType().equals("BLOOD")) {e.getPatient().setState("waiting-bloodtest");
			e.getPatient().updateHistory(new PatientMilestone("waiting-bloodtest",this.simTime));
			this.ed.getBtService().getwList().add(e.getPatient());}
			if (e.getExamType().equals("XRAY")) {e.getPatient().setState("waiting-xray");
			e.getPatient().updateHistory(new PatientMilestone("waiting-xray",this.simTime));
			this.ed.getRadioService().getwList().add(e.getPatient());}
			if (e.getExamType().equals("MRI")) {e.getPatient().setState("waiting-mri");
			e.getPatient().updateHistory(new PatientMilestone("waiting-mri",this.simTime));
			this.ed.getMriService().getwList().add(e.getPatient());}
			e.getTransporter().setState("idle");
			e.getTransporter().empty();
			e.messagePhysi(this.simTime);
		}
		
	
	public void visit(Verdict e) {

			e.getPatient().setState("being-verdicted");
			e.getPatient().updateHistory(new PatientMilestone("being-verdicted",this.simTime));
			e.getPhysician().setState("verdicting");
			e.getPhysician().fill();
			e.getPhysician().addPatient(e.getPatient());
			e.messagePhysi(this.simTime);
			this.eventQueue.getQueue().add(new Event(new EndVerdict(e.getPatient(),e.getPhysician(),e.getLocation()),this.simTime + e.getDuration()));
			ed.getVerdictService().getwList().remove(e.getPatient());
		}
	public void visit(EndVerdict e) {
			e.getPatient().setState("released");
			e.getPatient().updateHistory(new PatientMilestone("released",this.simTime));
			e.getPhysician().setState("idle");
			e.getPhysician().empty();
			e.getLocation().empty();
			e.getPatient().setLocation(null);//représente le fait que le patient a quitté l'ED
			this.ed.getPatientList().remove(e.getPatient());
			e.messagePhysi(this.simTime);
			e.getPhysician().getPatients().remove(e.getPatient());
			
		}
			
	
	public void visit(WaitingResult e) {
			e.getPatient().setState("waiting-verdict");
			e.getPatient().updateHistory(new PatientMilestone("waiting-verdict",this.simTime));
			e.getPatient().setLocation(e.getLocation());
			if (!e.getLocation().getClass().equals(Corridor.class)) {
				e.getLocation().fill();
			}
			e.messagePhysi(this.simTime);
			this.ed.getVerdictService().getwList().add(e.getPatient());
		
		
	}
	//Getters & Setters
	public ED getED() {return ed;}
	public void setED(ED ed) {this.ed = ed;}
	public EventQueue getEventQueue() {return eventQueue;}
	public void setEventQueue(EventQueue eventQueue) {this.eventQueue = eventQueue;}
	public EnabledEvents getEnabledEvents() {return enabledEvents;}
	public void setEnabledEvents(EnabledEvents enabledEvents) {this.enabledEvents = enabledEvents;}
	public double getSimTime() {return simTime;}
	public void setSimTime(double d) {this.simTime = d;}

	public void setL1arrivalDist(ProbabilityDistribution d){

		L1arrivalDist=d;
	}
	public void setL2arrivalDist(ProbabilityDistribution d){

		L2arrivalDist=d;
	}
	public void setL3arrivalDist(ProbabilityDistribution d){

		L3arrivalDist=d;
	}
	public void setL4arrivalDist(ProbabilityDistribution d){

		L4arrivalDist=d;
	}
	public void setL5arrivalDist(ProbabilityDistribution d){

		L5arrivalDist=d;
	}

	//Lancement de la boucle de simulation
	public static void main(String[] args) {

		double tempsMax = 100;
		ED ed = new ED();
		
		SimErgy simergy= new SimErgy(ed);
		
		while (simergy.getSimTime() < tempsMax) {
			
			 simergy.getNewEnabledQueue(simergy.getED()); //Crée la nouvelle enabledEventQueue à partir du nouvel état du system
			 simergy.getEventQueue().sort(); // Retrie l'eventQueue dans l'ordre chronologique
			 Event nextEvent = simergy.dequeue(); //On retire l'event de la queue
			 simergy.setSimTime(nextEvent.getTimestamp());// On visit l'event pour mettre à jour l'état du système
			 nextEvent.getEventType().accept(simergy);//On ajoute l'event à l'historique
			 simergy.getEventHistory().add(nextEvent);//On actualise le temps de la simulation
			 
	}
		

	}



	public ArrayList<Event> getEventHistory() {
		return eventHistory;
	}



	public void setEventHistory(ArrayList<Event> eventHistory) {
		this.eventHistory = eventHistory;
	}}
		
