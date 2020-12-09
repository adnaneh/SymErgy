package main;

import java.util.ArrayList;

import eventspackage.BloodTest;
import eventspackage.Consultation;
import eventspackage.EndBloodTest;
import eventspackage.EndConsultation;
import eventspackage.EndMRI;
import eventspackage.EndRadiography;
import eventspackage.EndRegistration;
import eventspackage.EndTransportationInstallation;
import eventspackage.EndTransportationTest;
import eventspackage.EndVerdict;
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
import ressourcehr.Physician;
import room.WaitingRoom;
import room.WaitingRoomSpot;
import visitorpattern.Visitor;

public class EnabledEvents implements Visitor{

	//default constructor
	public EnabledEvents(){
		
		
	}
	
	//Constructor used in simulation
	public EnabledEvents(ED ed){ //Créer la liste des évenements possibles à partir de d'un état de l'ED
		this.ed= ed;
		//On initialise la possibleQueue avec les EventType qu'on veut intégrer à la simulation
		
		this.possibleQueue.add(new Verdict());
		this.possibleQueue.add(new WaitingResult());
		this.possibleQueue.add(new BloodTest());
		this.possibleQueue.add(new Radiography());
		this.possibleQueue.add(new MRI());
		this.possibleQueue.add(new TransportationTest());
		this.possibleQueue.add(new Consultation());
		this.possibleQueue.add(new TransportationInstallation());
		this.possibleQueue.add(new Registration());
		this.possibleQueue.add(new PatientArrival());

		/*Ensuite on parcoure les EventType de la possibleQueue et on actualise la enabledQueue 
		en fonction de l'état de l'Emergency department ed*/
		for (EventType e: this.possibleQueue) {
			e.accept(this);
			
		}		
		}

	//Attributes
	//Liste des types event autorisés
	private ArrayList<EventType> enabledQueue = new ArrayList<EventType>();
	//Liste de tous les types d'event possibles même non autorisés
	private ArrayList<EventType> possibleQueue = new ArrayList<EventType>();
	//L'emergency departement associé a la enabledQueue
	private ED ed;
	//visit methods
	

	@Override
	public void visit(BloodTest e) {
		//On test si la waiting List du service correspondant, ici BloodTest, n'est pas vide
		if (!ed.getBtService().getwList().isEmpty()){
		//Si elle n'est pas vide on prend le premier patient de la liste
		Patient p3= ed.getBtService().getwList().get(0); 
		//On cherche ensuite si les ressources nécessaires à l'event sont disponibles ici on cherche une BloodTestRoom
		for (int j=0;j<ed.getBloodTestRoomList().size();j++) {
			if (ed.getBloodTestRoomList().get(j).isDispo()) {
				/*Si on trouve une ressource on continue de chercher les autres ressources ou on ajout l'eventType
				à la enabledQueue si on a trouvé toutes les ressources nécessaires
				dans ce cas on a seulement besoin d'un patient dans la waiting list et d'une BloodTestRoom
				donc on autorise l'EventType en l'ajoutant a la EnabledQUeue*/
				this.enabledQueue.add(new BloodTest(p3,ed.getBloodTestRoomList().get(j), ed.getBtService()));
				/*Le break permet d'arrêter la recherche d'un type de ressource à chaque fois
				qu'on en trouve une disponible*/
				break;
			}
		
		}
		
		}
		
		
	}


	@Override
	public void visit(Consultation e) {
		
		for (Patient p: ed.getConsultationService().getwList()) {
			
			for (Physician physi: ed.getPhysicianList()) {// On cherche un physicien disponible
			if (physi.isDispo()) {
					this.enabledQueue.add(new Consultation(p,physi,p.getLocation(),ed.getConsultationService()));
					break;
				}	
			}
			break;
		}
		
	}

	@Override
	public void visit(MRI e) {
		if (!ed.getMriService().getwList().isEmpty()) {
			
		Patient p4 = ed.getMriService().getwList().get(0);
		for (int j=0;j<ed.getmRIRoomList().size();j++) {
			if (ed.getmRIRoomList().get(j).isDispo()) {
				this.enabledQueue.add(new MRI(p4,ed.getmRIRoomList().get(j),ed.getMriService()));
				break;
			}
		}
		}
	}
	@Override
	public void visit(PatientArrival e) {
		
		WaitingRoomSpot spot=null;
		for (WaitingRoom r : ed.getWaitingRoomList()) {
			for (WaitingRoomSpot s: r.getSpots()) {
				if (s.isDispo()){
					spot=s;
					break;
				}
			}
			if (spot!=null) {
				break;
			}
		}
		//Si on trouve une place alors on autorise l'arrivée des patients dans une waiting room
		if (spot!=null) {
			this.enabledQueue.add(new PatientArrival(new Patient(1), spot));
			this.enabledQueue.add(new PatientArrival(new Patient(2), spot));
			this.enabledQueue.add(new PatientArrival(new Patient(3), spot));
			this.enabledQueue.add(new PatientArrival(new Patient(4), spot));
			this.enabledQueue.add(new PatientArrival(new Patient(5),spot));
		}
		//Sinon on autorise l'arrivée des patients dans le couloir
		else {
		this.enabledQueue.add(new PatientArrival(new Patient(1), ed.getCorridor()));
		this.enabledQueue.add(new PatientArrival(new Patient(2), ed.getCorridor()));
		this.enabledQueue.add(new PatientArrival(new Patient(3), ed.getCorridor()));
		this.enabledQueue.add(new PatientArrival(new Patient(4), ed.getCorridor()));
		this.enabledQueue.add(new PatientArrival(new Patient(5),ed.getCorridor()));
		}
		
		
	}


	@Override
	public void visit(Radiography e) {
		if (!ed.getRadioService().getwList().isEmpty()) {
		Patient p5=ed.getRadioService().getwList().get(0);
		for (int j=0;j<ed.getRadiographyRoomList().size();j++) {
			if (ed.getRadiographyRoomList().get(j).isDispo()) {
;
				this.enabledQueue.add(new Radiography(p5,ed.getRadiographyRoomList().get(j),ed.getRadioService()));
				break;
			}
		}
		}
	}


	@Override
	public void visit(Registration e) {
	
		if (!ed.getRegService().getwList().isEmpty()) {
		Patient p1= ed.getRegService().getwList().get(0); //On prend le premier patient dans la liste d'attente de Registration
		//System.out.println(p1.getState());
			for (int j=0;j<ed.getNurseList().size();j++) {
				if (ed.getNurseList().get(j).isDispo()) {
					this.enabledQueue.add(new Registration(p1,ed.getNurseList().get(j)));
					//Une fois qu'on a trouvé une infirmière disponible on arrête la boucle
					break;
				}
			}
		}
	
	}

	@Override
	public void visit(TransportationInstallation e) {

		Boolean eventEnabled= false;
		for (Patient p: ed.getTiService().getwList()) { //On cherche le premier patient dans la liste d'attente d'installation de sévérité 1 ou 2
		if(p.getsLevel()<3 ) {//Installation des patients de sévérité 1 ou 2

			for (int j=0;j<ed.getNurseList().size();j++) {
				if (ed.getNurseList().get(j).isDispo()) {
					for (int k=0;k<ed.getShockRoomList().size();k++) {
						if (ed.getShockRoomList().get(k).isDispo()) {
							this.enabledQueue.add(new TransportationInstallation(p,ed.getNurseList().get(j),ed.getShockRoomList().get(k)));
							eventEnabled= true;
							break;
						}
						
					}
					break;
				}
				
			}
		break;//Si les ressources ne sont pas disponibles on arrête la boucle sur les patients on a donc bien le premier patient à la sévérité recherchée 
		}
		}
		if (!eventEnabled) {//if TransportationInstallation was not enabled we keep looking to enable it with patients of different severity
		for (Patient p: ed.getTiService().getwList()) {
		if(p.getsLevel()>2 ) {//Installation des patients de sévérité 3,4,ou5
			for (int j=0;j<ed.getNurseList().size();j++) {
				if (ed.getNurseList().get(j).isDispo()) {
					for (int k=0;k<ed.getBoxRoomList().size();k++) {
						if (ed.getBoxRoomList().get(k).isDispo()) {
							this.enabledQueue.add(new TransportationInstallation(p,ed.getNurseList().get(j),ed.getBoxRoomList().get(k)));
							break;
						}
					}
				break;
				}
			}
		break;
		}
		}

	}
	}
	@Override
	public void visit(TransportationTest e) {
		if (!ed.getTtService().getwList().isEmpty()) {
		Patient p2= ed.getTtService().getwList().get(0);
		for (int j=0;j<ed.getTransporterList().size();j++) {
			if (ed.getTransporterList().get(j).isDispo()) {
				switch(p2.getState()) {
				case "needing-bloodtest":
					this.enabledQueue.add(new TransportationTest(p2,ed.getTransporterList().get(j), "BLOOD", ed.getHsWaitingRoom()));
					break;
				case "needing-xray":
					this.enabledQueue.add(new TransportationTest(p2,ed.getTransporterList().get(j), "XRAY", ed.getHsWaitingRoom()));
					break;
				case "needing-mri":
					this.enabledQueue.add(new TransportationTest(p2,ed.getTransporterList().get(j), "MRI", ed.getHsWaitingRoom()));
					break;
			}
				break;//Une fois qu'on a trouvé un transporter on arrête d'en chercher d'autre et on autorise l'event.
		}
		
	}
		}
	}


	@Override
	public void visit(Verdict e) {
		for (Patient p6: ed.getVerdictService().getwList()) {
			//On cherche le premier patient dans la waiting list dont le physicien attribué est disponible
			
			if (p6.getPhysician().isDispo()) {
				this.enabledQueue.add(new Verdict(p6,p6.getPhysician(),p6.getLocation()));
				break;//Une fois qu'on a trouvé un physicien on arrête de chercher des events de ce type
			}	
			
		}
		
	}

	@Override
	public void visit(WaitingResult e) {

		
		WaitingRoomSpot spot=null;
		for (WaitingRoom r : ed.getWaitingRoomList()) {
			for (WaitingRoomSpot s: r.getSpots()) {
				if (s.isDispo()){
					spot=s;
					break;
				}
			}
			if (spot!=null) {
				break;
			}
		}
		for (int i=0;i<ed.getPatientList().size();i++) {
			//System.out.println(ed.getPatientList().get(i).getState());
		if(ed.getPatientList().get(i).getState().equals("exam-passed")) {
			
					if (spot!=null) {
						this.enabledQueue.add(new WaitingResult(ed.getPatientList().get(i),spot));
					}
					else {
						this.enabledQueue.add(new WaitingResult(ed.getPatientList().get(i),ed.getCorridor()));
				}
			
					break;
					}
		
		}
	
	}







	//Les autres visit methods sont inutilisées par cette class
	@Override
	public void visit(EndBloodTest endBloodTest) {
		// TODO Auto-generated method stub
		
	}








	@Override
	public void visit(EndConsultation endConsultation) {
		// TODO Auto-generated method stub
		
	}








	@Override
	public void visit(EndMRI endMRI) {
		// TODO Auto-generated method stub
		
	}








	@Override
	public void visit(EndRadiography endRadiography) {
		// TODO Auto-generated method stub
		
	}








	@Override
	public void visit(EndRegistration endRegistration) {
		// TODO Auto-generated method stub
		
	}








	@Override
	public void visit(EndTransportationInstallation endTransportationInstallation) {
		// TODO Auto-generated method stub
		
	}








	@Override
	public void visit(EndTransportationTest endTransportationTest) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void visit(EndVerdict endVerdict) {
		// TODO Auto-generated method stub
		
	}
	//Getters & Setters
	public ArrayList<EventType> getEnabledQueue() {return enabledQueue;}
	public void setEnabledQueue(ArrayList<EventType> enabledQueue) {this.enabledQueue = enabledQueue;}

	public ED getEd() {
		return ed;
	}

	public void setEd(ED ed) {
		this.ed = ed;
	}


}
