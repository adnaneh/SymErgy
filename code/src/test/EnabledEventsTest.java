package test;


import org.junit.jupiter.api.Test;

import eventspackage.BloodTest;
import eventspackage.Consultation;
import eventspackage.MRI;
import eventspackage.Radiography;
import eventspackage.Registration;
import eventspackage.TransportationInstallation;
import eventspackage.TransportationTest;
import eventspackage.Verdict;
import eventspackage.WaitingResult;
import main.ED;
import main.EnabledEvents;
import ressourcehr.Nurse;
import ressourcehr.Patient;
import ressourcehr.Physician;
import ressourcehr.Transporter;
import room.BloodTestRoom;
import room.BoxRoom;
import room.MRIRoom;
import room.RadiographyRoom;
import room.ShockRoom;

class EnabledEventsTest {
	
	/*On veut tester pour chaque méthode si lorsqu'on a les ressources disponibles l'event devient enabled
	et n'ai pas enabled lorsqu'on n'a pas les ressources*/
	/*On veut aussi tester si on ajoute seulement un eventType par type d'évènement
	par exemple la méthode visitBloodTest ne doit pas ajouter plus d'un eventType BloodTest à l'evenQueue*/
	
	//Tests sur visit(BloodTest o)
	@Test
	void testVisitBloodTestEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		//On crée les ressources qui enable l'eventType qui sont dance ce cas...
		//Une room
		ed.getBloodTestRoomList().add(new BloodTestRoom());
		//Un patient dans la waiting list
		ed.getBtService().getwList().add(p);
		//On test si un event a été ajouté à la queue
		events.visit(new BloodTest());
		assert(!events.getEnabledQueue().isEmpty());
		//On test si l'event ajouté est du type souhaité
		assert(events.getEnabledQueue().get(0).getClass()==BloodTest.class);
	}
	@Test
	void testVisitBloodTestNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		ed.getBloodTestRoomList().add(new BloodTestRoom());
		events.visit(new BloodTest());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitBloodTestNotEnabledWhenNoRoomAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getBtService().getwList().add(p);
		events.visit(new BloodTest());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitBloodTestAddsNoMoreThanOneEventType() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getBtService().getwList().add(p);
		ed.getBtService().getwList().add(p1);
		//On ajoute 2 Rooms
		ed.getBloodTestRoomList().add(new BloodTestRoom());
		ed.getBloodTestRoomList().add(new BloodTestRoom());
		int size = events.getEnabledQueue().size();
		events.visit(new BloodTest());
		assert(events.getEnabledQueue().size()==size+1);
	}
	
	
	//Tests sur visit(MRI o)
	@Test
	void testVisitMRIEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getmRIRoomList().add(new MRIRoom());
		ed.getMriService().getwList().add(p);
		events.visit(new MRI());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==MRI.class);
	}
	@Test
	void testVisitMRINotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		ed.getmRIRoomList().add(new MRIRoom());
		events.visit(new MRI());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitMRINotEnabledWhenNoRoomAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getMriService().getwList().add(p);
		events.visit(new MRI());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitMRIAddsNoMoreThanOneEventType() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getMriService().getwList().add(p);
		ed.getMriService().getwList().add(p1);
		//On ajoute 2 Rooms
		ed.getmRIRoomList().add(new MRIRoom());
		ed.getmRIRoomList().add(new MRIRoom());
		int size = events.getEnabledQueue().size();
		events.visit(new MRI());
		assert(events.getEnabledQueue().size()==size+1);
		//On test si le patient pris est le premier patient de la wating list
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}

	
	
	
	
	//Tests sur visit(Radiography o)
	@Test
	void testVisitRadiographyEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getRadiographyRoomList().add(new RadiographyRoom());
		ed.getRadioService().getwList().add(p);
		events.visit(new Radiography());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==Radiography.class);
	}
	@Test
	void testVisitRadiographyNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		ed.getRadiographyRoomList().add(new RadiographyRoom());
		events.visit(new Radiography());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitRadiographyNotEnabledWhenNoRoomAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getRadioService().getwList().add(p);
		events.visit(new Radiography());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitRadiographyAddsNoMoreThanOneEventType() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getRadioService().getwList().add(p);
		ed.getRadioService().getwList().add(p1);
		//On ajoute 2 Rooms
		ed.getRadiographyRoomList().add(new RadiographyRoom());
		ed.getRadiographyRoomList().add(new RadiographyRoom());
		int size = events.getEnabledQueue().size();
		events.visit(new Radiography());
		assert(events.getEnabledQueue().size()==size+1);
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}
	
	
	
	
	
	//Tests sur visit(Consultation o)
	@Test
	void testVisitConsultationEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		ed.getConsultationService().getwList().add(p);
		events.visit(new Consultation());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==Consultation.class);
	}
	@Test
	void testVisitConsultationNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		events.setEd(ed);
		events.visit(new Consultation());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
void testVisitConsultationNotEnabledWhenNoPhysicianAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getConsultationService().getwList().add(p);
		events.visit(new Consultation());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitConsultationAddsNoMoreThanOneEventTypeAndFirstPatientInWaitingLineIsTakenFirst() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getConsultationService().getwList().add(p);
		ed.getConsultationService().getwList().add(p1);
		//On ajoute 2 physiciens
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		Physician ph1= new Physician();
		ed.getPhysicianList().add(ph1);
		int size = events.getEnabledQueue().size();
		events.visit(new Consultation());
		assert(events.getEnabledQueue().size()==size+1);
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}
	
	
	
	
	
	
	//Tests sur visit(Registration o)
	@Test
	void testVisitRegistrationEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		ed.getRegService().getwList().add(p);
		events.visit(new Registration());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==Registration.class);
	}
	@Test
	void testVisitRegistrationNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		events.setEd(ed);
		events.visit(new Registration());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitRegistrationNotEnabledWhenNoNurseAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getRegService().getwList().add(p);
		events.visit(new Registration());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitRegistrationAddsNoMoreThanOneEventTypeAndFirstPatientInWaitingLineIsTakenFirst() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getRegService().getwList().add(p);
		ed.getRegService().getwList().add(p1);
		//On ajoute 2 Nurse
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		Nurse ph1= new Nurse();
		ed.getNurseList().add(ph1);
		int size = events.getEnabledQueue().size();
		events.visit(new Registration());
		assert(events.getEnabledQueue().size()==size+1);
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}
	
	
	
	
	
	//Tests sur visit(TrasnportationInstallation o)
	@Test
	void testVisitTransportationInstallationEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		ed.getTiService().getwList().add(p);
		ed.getBoxRoomList().add(new BoxRoom());
		ed.getShockRoomList().add(new ShockRoom());
		events.visit(new TransportationInstallation());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==TransportationInstallation.class);
	}
	@Test
	void testVisitTransportationInstallationNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		ed.getBoxRoomList().add(new BoxRoom());
		ed.getShockRoomList().add(new ShockRoom());
		events.setEd(ed);
		events.visit(new TransportationInstallation());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitTransportationInstallationNotEnabledWhenNoNurseAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getBoxRoomList().add(new BoxRoom());
		ed.getShockRoomList().add(new ShockRoom());
		ed.getTiService().getwList().add(p);
		events.visit(new TransportationInstallation());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitTransportationInstallationNotEnabledWhenNoRoomAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		ed.getTiService().getwList().add(p);
		events.visit(new TransportationInstallation());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitTransportationInstallationAddsNoMoreThanOneEventTypeAndFirstPatientInWaitingLineIsTakenFirst() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getTiService().getwList().add(p);
		ed.getTiService().getwList().add(p1);
		//On ajoute 2 Nurse
		Nurse ph= new Nurse();
		ed.getNurseList().add(ph);
		Nurse ph1= new Nurse();
		ed.getNurseList().add(ph1);
		//On ajoute 2 BoxRooms et 2 ShockRooms
		ed.getBoxRoomList().add(new BoxRoom());
		ed.getShockRoomList().add(new ShockRoom());
		ed.getBoxRoomList().add(new BoxRoom());
		ed.getShockRoomList().add(new ShockRoom());
		int size = events.getEnabledQueue().size();
		events.visit(new TransportationInstallation());
		assert(events.getEnabledQueue().size()==size+1);
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}
	
	
	
	
	//Tests sur visit(TransportationTest o)
	@Test
	void testVisitTransportationTestEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		p.setState("needing-bloodtest");
		ed.getPatientList().add(p);
		Transporter ph= new Transporter();
		ed.getTransporterList().add(ph);
		ed.getTtService().getwList().add(p);
		events.visit(new TransportationTest());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==TransportationTest.class);
	}
	@Test
	void testVisitTransportationTestNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		Transporter ph= new Transporter();
		ed.getTransporterList().add(ph);
		events.setEd(ed);
		events.visit(new TransportationTest());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitTransportationTestNotEnabledWhenNoTransporterAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		p.setState("needing-bloodtest");
		ed.getPatientList().add(p);
		ed.getTtService().getwList().add(p);
		events.visit(new TransportationTest());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitTransportationTestAddsNoMoreThanOneEventTypeAndFirstPatientInWaitingLineIsTakenFirst() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		p.setState("needing-bloodtest");
		Patient p1= new Patient(2);
		p1.setState("needing-bloodtest");
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getTtService().getwList().add(p);
		ed.getTtService().getwList().add(p1);
		//On ajoute 2 Transporter
		Transporter ph= new Transporter();
		ed.getTransporterList().add(ph);
		Transporter ph1= new Transporter();
		ed.getTransporterList().add(ph1);
		int size = events.getEnabledQueue().size();
		events.visit(new TransportationTest());
		assert(events.getEnabledQueue().size()==size+1);
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}
	
	
	
	
	
	
	//Tests sur visit(Verdict o)
	@Test
	void testVisitVerdictEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		p.setPhysician(ph);
		ed.getVerdictService().getwList().add(p);
		events.visit(new Verdict());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==Verdict.class);
	}
	@Test
	void testVisitVerdictNotEnabledWhenNoPatientInQueue() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		events.setEd(ed);
		events.visit(new Verdict());
		assert(events.getEnabledQueue().isEmpty());

	}
	@Test
	void testVisitVerdictNotEnabledWhenNoPhysicianAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		ed.getVerdictService().getwList().add(p);
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		p.setPhysician(ph);
		//On rend le physicien indisponible
		ph.fill();
		events.visit(new Verdict());
		assert(events.getEnabledQueue().isEmpty());
	}
	@Test
	void testVisitVerdictAddsNoMoreThanOneEventTypeAndFirstPatientInWaitingLineIsTakenFirst() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		Patient p1= new Patient(2);
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getVerdictService().getwList().add(p);
		ed.getVerdictService().getwList().add(p1);
		//On ajoute 2 Physician
		Physician ph= new Physician();
		ed.getPhysicianList().add(ph);
		Physician ph1= new Physician();
		ed.getPhysicianList().add(ph1);
		//On affecte les deux physiciens
		p.setPhysician(ph);
		p.setPhysician(ph1);
		int size = events.getEnabledQueue().size();
		events.visit(new Verdict());
		assert(events.getEnabledQueue().size()==size+1);
		assert(events.getEnabledQueue().get(0).getPatient()==p);
	}
	
	
	
	
	//Tests sur visit(WatingResult o)
	@Test
	void testVisitWaitingResultEnabledWhenRessourcesAvailable() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		p.setState("exam-passed");
		ed.getPatientList().add(p);
		events.visit(new WaitingResult());
		assert(!events.getEnabledQueue().isEmpty());
		assert(events.getEnabledQueue().get(0).getClass()==WaitingResult.class);
	}
	@Test
	void testVisitWaitingResultNotEnabledWhenNoPatientHasPassedExam() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		Patient p= new Patient(1);
		ed.getPatientList().add(p);
		events.visit(new WaitingResult());
		System.out.println(events.getEnabledQueue());
		assert(events.getEnabledQueue().isEmpty());
	}

	@Test
	void testVisitWaitingResultAddsNoMoreThanOneEventType() {
		
		ED ed = new ED("ED");
		EnabledEvents events= new EnabledEvents();
		events.setEd(ed);
		//On ajoute 2 patients
		Patient p= new Patient(1);
		p.setState("exam-passed");
		Patient p1= new Patient(2);
		p1.setState("exam-passed");
		//Patient p is first patient in queue
		ed.getPatientList().add(p);
		ed.getPatientList().add(p1);
		ed.getRegService().getwList().add(p);
		ed.getRegService().getwList().add(p1);
		int size = events.getEnabledQueue().size();
		events.visit(new WaitingResult());
		assert(events.getEnabledQueue().size()==size+1);
	}
}
