package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import healthservice.BloodTestService;
import healthservice.ConsultationService;
import healthservice.MRIService;
import healthservice.RadiographyService;
import healthservice.RegistrationService;
import healthservice.TransportationInstallationService;
import healthservice.TransportationTestService;
import healthservice.VerdictService;
import ressourcehr.Nurse;
import ressourcehr.Patient;
import ressourcehr.Physician;
import ressourcehr.Transporter;
import room.BloodTestRoom;
import room.BoxRoom;
import room.Corridor;
import room.HealthServiceWaitingRoom;
import room.MRIRoom;
import room.RadiographyRoom;
import room.ShockRoom;
import room.WaitingRoom;

public class ED { // La classe ED (emergency department) qui contient toutes les variables de l'ED
	//name of the ED
	private String name;
	//constructor defining the ED name only
	public ED(String name) {
		this.name= name;
	}
	//default constructor
	//TODO supprimer les initialisations si clui marche
	public ED() {
		this.name = "testED";
		for (int i=0; i<5; i++) {
			this.nurseList.add(new Nurse());
		}
		for (int i=0; i<3; i++) {
			this.physicianList.add(new Physician());
		}
		for (int i=0; i<4; i++) {
			this.transporterList.add(new Transporter());
		}
		for (int i=0; i<1; i++) {
			this.shockRoomList.add(new ShockRoom());
		}
		for (int i=0; i<4; i++) {
			this.boxRoomList.add(new BoxRoom());
		}
		for (int i=0; i<1; i++) {
			this.mRIRoomList.add(new MRIRoom());
		}
		for (int i=0; i<1; i++) {
			this.radiographyRoomList.add(new RadiographyRoom());
		}
		for (int i=0; i<1; i++) {
			this.bloodTestRoomList.add(new BloodTestRoom());
		}
		for (int i=0; i<3; i++) {
			this.waitingRoomList.add(new WaitingRoom());
		}
		
	}
	
	private MRIService mriService =new MRIService();
	private BloodTestService btService = new BloodTestService();
	private RadiographyService radioService =new RadiographyService();
	private RegistrationService regService= new RegistrationService();
	private TransportationInstallationService tiService = new TransportationInstallationService();
	private TransportationTestService ttService= new TransportationTestService();
	private VerdictService verdictService = new VerdictService();
	private ConsultationService consultationService = new ConsultationService();
	private ArrayList<Patient> patientList = new ArrayList<Patient>();
	private ArrayList<Patient> patientHistory = new ArrayList<Patient>();
	private ArrayList<Nurse> nurseList= new ArrayList<Nurse>();
	private ArrayList<Physician> physicianList= new ArrayList<Physician> ();
	private ArrayList<Transporter> transporterList= new ArrayList<Transporter>();
	private ArrayList<BloodTestRoom> bloodTestRoomList= new  ArrayList<BloodTestRoom>();
	private ArrayList<BoxRoom> boxRoomList= new ArrayList<BoxRoom>() ;
	private ArrayList<MRIRoom> mRIRoomList= new ArrayList<MRIRoom>();
	private ArrayList<RadiographyRoom> radiographyRoomList= new  ArrayList<RadiographyRoom>();
	private ArrayList<ShockRoom> shockRoomList= new ArrayList<ShockRoom>();
	private ArrayList<WaitingRoom> waitingRoomList= new ArrayList<WaitingRoom>();
	private Corridor corridor= new Corridor();
	private HealthServiceWaitingRoom hsWaitingRoom = new HealthServiceWaitingRoom();
	
	
	//Méthode pour trier la liste des physicien dans l'ordre croissant du nombre de patients.
	//C'est pour que les physiciens avec le moins de patients soient affectés en premier aux nouveaux patients
	public void sortPhysi () {
		Collections.sort(this.physicianList, new EventTimestampComparator());

		}
		//utilisation de Comparator pour pouvoir trier facilement les event par ordre chronologique
	class EventTimestampComparator implements Comparator<Physician> {
	    public int compare(Physician e1, Physician e2) {
    	    if (e1.getPatients().size()< e2.getPatients().size()) {return -1;}
            if (e1.getPatients().size() > e2.getPatients().size()) {return 1;}
            return 0;
	    }
	}
	
	//methods for the CLUI
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addRoom(BloodTestRoom r) {
		this.bloodTestRoomList.add(r);
	}
	public void addRoom(BoxRoom r) {
		this.boxRoomList.add(r);
	}
	public void addRoom(MRIRoom r) {
		this.mRIRoomList.add(r);
	}
	public void addRoom(RadiographyRoom r) {
		this.radiographyRoomList.add(r);
	}
	public void addRoom(ShockRoom r) {
		this.shockRoomList.add(r);
	}
	public void addRoom(WaitingRoom r) {
		this.waitingRoomList.add(r);
	}

	
	public void addNurse(String name, String surname) {
		Nurse nurse = new Nurse (name, surname);
		this.nurseList.add(nurse);
		
		
	}

	public void addNurse() {
		Nurse nurse = new Nurse();
		this.nurseList.add(nurse);
		
		
	}

	public void addPhysi(String name, String surname) {
		Physician physician = new Physician(name, surname);
		this.physicianList.add(physician);
	}
	public void addPhysi() {
		Physician physician = new Physician();
		this.physicianList.add(physician);
	}

	public void addPatient(String name, String surname, String insurance) {
		Patient patient = new Patient(name, surname, insurance);
		this.patientList.add(patient);
		
	}
	public Patient findPatientById(int id) {
		for (Patient p: patientList) {
			//On renvoie le patient avec l'Id correspondant.
			if (p.getId()== id) {
				return p;
			}
		}
		return null;
	}
	public void setInsurance(int patientId, String insurance) {
		Patient p= this.findPatientById(patientId);
		p.setInsurance(insurance);
		
	}
	
	public String toString() {
		String res ="This is the state of ED named "+ this.name+ "\n"+"\n"+"\n"+"\n";
		res +=" The Patients"+"\n";
		for (Patient o : this.patientList) {
			res+=o.toString() +"\n";
		}
		res +=" The Physicians"+"\n";
		for (Physician o : this.physicianList) {
			res+=o.toString() +"\n";
		}
		res+= " The Nurses "+ "\n";
		for (Nurse o: this.nurseList) {
			res+=o.toString() +"\n";
		}
		res+= " The Transporters " + "\n";
		for (Transporter o: this.transporterList) {
			res+=o.toString() +"\n";
		}
		res +=" The BloodTestRooms"+"\n";
		for (BloodTestRoom o : this.bloodTestRoomList) {
			res+=o.toString() +"\n";
		}
		res +=" The BoxRooms"+"\n";
		for (BoxRoom o : this.boxRoomList) {
			res+=o.toString() +"\n";
		}
		res +=" The MRIRooms"+"\n";
		for (MRIRoom o : this.mRIRoomList) {
			res+=o.toString() +"\n";
		}
		res +=" The RadiographyRooms"+"\n";
		for (RadiographyRoom o : this.radiographyRoomList) {
			res+=o.toString() +"\n";
		}
		res +=" The ShockRooms"+"\n";
		for (ShockRoom o : this.shockRoomList) {
			res+=o.toString() +"\n";
		}
		res +=" The WaitingRooms"+"\n";
		for (WaitingRoom o : this.waitingRoomList) {
			res+=o.toString() +"\n";
		}
		int n = 0;
		for (Patient p : this.patientList) {
			if (p.getLocation()==this.getCorridor()) {
				n+=1;
			}
		}
		res +=" Patients in the Corridor " +n ;
		return res;
	}	
	
	

	//Getters & Setters
	public ArrayList<Patient> getPatientList() {
		return patientList;
	}

	public void setPatientList(ArrayList<Patient> patientList) {
		this.patientList = patientList;
	}

	public ArrayList<Nurse> getNurseList() {
		return nurseList;
	}

	public void setNurseList(ArrayList<Nurse> nurseList) {
		this.nurseList = nurseList;
	}

	public ArrayList<Physician> getPhysicianList() {
		return physicianList;
	}

	public void setPhysicianList(ArrayList<Physician> physicianList) {
		this.physicianList = physicianList;
	}

	public ArrayList<Transporter> getTransporterList() {
		return transporterList;
	}

	public void setTransporterList(ArrayList<Transporter> transporterList) {
		this.transporterList = transporterList;
	}

	public ArrayList<BloodTestRoom> getBloodTestRoomList() {
		return bloodTestRoomList;
	}

	public void setBloodTestRoomList(ArrayList<BloodTestRoom> bloodTestRoomList) {
		this.bloodTestRoomList = bloodTestRoomList;
	}

	public void setBoxRoomList(ArrayList<BoxRoom> boxRoomList) {
		this.boxRoomList = boxRoomList;
	}
	public ArrayList<BoxRoom> getBoxRoomList() {
		return boxRoomList;
	}

	public ArrayList<MRIRoom> getmRIRoomList() {
		return mRIRoomList;
	}

	public void setmRIRoomList(ArrayList<MRIRoom> mRIRoomList) {
		this.mRIRoomList = mRIRoomList;
	}

	public ArrayList<RadiographyRoom> getRadiographyRoomList() {
		return radiographyRoomList;
	}

	public void setRadiographyRoomList(ArrayList<RadiographyRoom> radiographyRoomList) {
		this.radiographyRoomList = radiographyRoomList;
	}

	public ArrayList<ShockRoom> getShockRoomList() {
		return shockRoomList;
	}

	public void setShockRoomList(ArrayList<ShockRoom> shockRoomList) {
		this.shockRoomList = shockRoomList;
	}

	public ArrayList<WaitingRoom> getWaitingRoomList() {
		return waitingRoomList;
	}

	public void setWaitingRoomList(ArrayList<WaitingRoom> waitingRoomList) {
		this.waitingRoomList = waitingRoomList;
	}
	
	public Corridor getCorridor() {
		return corridor;
	}
	public void setCorridor(Corridor corridor) {
		this.corridor = corridor;
	}
	public HealthServiceWaitingRoom getHsWaitingRoom() {
		return hsWaitingRoom;
	}
	public void setHsWaitingRoom(HealthServiceWaitingRoom hsWaitingRoom) {
		this.hsWaitingRoom = hsWaitingRoom;
	}
	public MRIService getMriService() {
		return mriService;
	}
	public void setMriService(MRIService mriService) {
		this.mriService = mriService;
	}
	public BloodTestService getBtService() {
		return btService;
	}
	public void setBtService(BloodTestService btService) {
		this.btService = btService;
	}
	public RadiographyService getRadioService() {
		return radioService;
	}
	public void setRadioService(RadiographyService radioService) {
		this.radioService = radioService;
	}
	public RegistrationService getRegService() {
		return regService;
	}
	public void setRegService(RegistrationService regService) {
		this.regService = regService;
	}
	public TransportationInstallationService getTiService() {
		return tiService;
	}
	public void setTiService(TransportationInstallationService tiService) {
		this.tiService = tiService;
	}
	public TransportationTestService getTtService() {
		return ttService;
	}
	public void setTtService(TransportationTestService ttService) {
		this.ttService = ttService;
	}
	public VerdictService getVerdictService() {
		return verdictService;
	}
	public void setVerdictService(VerdictService verdictService) {
		this.verdictService = verdictService;
	}
	public ConsultationService getConsultationService() {
		return consultationService;
	}
	public void setConsultationService(ConsultationService consultationService) {
		this.consultationService = consultationService;
	}
	public ArrayList<Patient> getPatientHistory() {
		return patientHistory;
	}
	public void setPatientHistory(ArrayList<Patient> patientHistory) {
		this.patientHistory = patientHistory;
	}

}
