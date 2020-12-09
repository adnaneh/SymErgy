package CLUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


import eventspackage.Event;
import eventspackage.PatientArrival;
import eventspackage.Registration;
import main.ED;
import main.SimErgy;
import probability.DetDistribution;
import probability.ExpDistribution;
import probability.UnifDistribution;
import ressourcehr.Patient;
import ressourcehr.Transporter;
import room.BloodTestRoom;
import room.BoxRoom;
import room.MRIRoom;
import room.RadiographyRoom;
import room.Room;
import room.ShockRoom;
import room.WaitingRoom;
import room.WaitingRoomSpot;

public class CLUI {
	//La liste des ED du système 
	private EDList edList = new EDList();
	
	
	//commandline methods
	public void creatED (String EDname) throws NameAlreadyUsedException{
		//On cherche d'abord s'il n'y a pas déjà un ED de même nom
		for (SimErgy sim : this.edList.getsList()) {
			if (sim.getED().getName().equals(EDname)) {
				throw new NameAlreadyUsedException("This ED name is already used");
			}
		}
		//create ED 
		ED newED = new ED(EDname);
		//initialize the simulation of the ED
		SimErgy newSimErgy = new SimErgy(newED);
		//Ajouter le simulateur dans la liste des simulateurs dans l'objest edList
		edList.addSim(newSimErgy);
		System.out.println("ED added");
		
	}
	public void addRoom(String EDName, String RoomType, String RoomName) throws ObjectNotFoundException, BadInstructionException{
		//Retrouver la simulation associée à l'ED
		SimErgy simergy=edList.getSimbyName(EDName);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ EDName);
		}
		//Récupérer l’ED
		ED ed = simergy.getED();
		switch (RoomType) {
		//The RoomType arguments must be one of these
		//BloodTestRoom, BoxRoom, MRIRoom, RadiographyRoom, ShockRoom or WaitingRoom
		case "BloodTestRoom":
			ed.addRoom(new BloodTestRoom("RoomName"));
			System.out.println("Room Added");
			break;
		case "BoxRoom":
			ed.addRoom(new BoxRoom("RoomName"));
			System.out.println("Room Added");
			break;
		case "MRIRoom":
			ed.addRoom(new MRIRoom("RoomName"));
			System.out.println("Room Added");
			break;
		case "RadiographyRoom":
			ed.addRoom(new RadiographyRoom("RoomName"));
			System.out.println("Room Added");
			break;
		case "ShockRoom":
			ed.addRoom(new ShockRoom("RoomName"));
			System.out.println("Room Added");
			break;
		case "WaitingRoom":
			ed.addRoom(new WaitingRoom("RoomName"));
			System.out.println("Room Added");
			break;
		default:
			throw new BadInstructionException("This room type is not defined");
		}
		
	}
	public void addRadioService(String[] args) throws ObjectNotFoundException, BadInstructionException, NumberFormatException {
		if (args.length!=4 && args.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		switch (args[2]) {
		case "uniform":
			ed.getRadioService().setP(new UnifDistribution(Double.parseDouble(args[3]), Double.parseDouble(args[4])));
			break;
		case "exp":
			ed.getRadioService().setP(new ExpDistribution(Double.parseDouble(args[3])));
			break;
		case "determinist":
			ed.getRadioService().setP(new DetDistribution(Double.parseDouble(args[3])));	
			break;
		default:
			throw new BadInstructionException("This Distribution is not defined");
		}
		System.out.println("Xray service added");
	}
	public void addMRIService(String[] args) throws ObjectNotFoundException, BadInstructionException, NumberFormatException{
		if (args.length!=4 && args.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		switch (args[2]) {
		case "uniform":
			ed.getMriService().setP(new UnifDistribution(Double.parseDouble(args[3]), Double.parseDouble(args[4])));
			break;
		case "exp":
			ed.getMriService().setP(new ExpDistribution(Double.parseDouble(args[3])));
			break;
		case "determinist":
			ed.getMriService().setP(new DetDistribution(Double.parseDouble(args[3])));	
			break;
		default:
			throw new BadInstructionException("This Distribution is not defined");
		}
		System.out.println("MRI service added");
	}
	public void addBloodTestService(String[] args) throws BadInstructionException, ObjectNotFoundException, NumberFormatException {
		if (args.length!=4 && args.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		switch (args[2]) {
		case "uniform":
			ed.getBtService().setP(new UnifDistribution(Double.parseDouble(args[3]), Double.parseDouble(args[4])));
			break;
		case "exp":
			ed.getBtService().setP(new ExpDistribution(Double.parseDouble(args[3])));
			break;
		case "determinist":
			ed.getBtService().setP(new DetDistribution(Double.parseDouble(args[3])));	
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
		}
		System.out.println("BloodTest service added");
	}

	public void addNurse(String[] argumentsArray) throws ObjectNotFoundException, BadInstructionException {
		if (argumentsArray.length!=2 && argumentsArray.length!=4) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		if (argumentsArray.length==3) {
			ed.addNurse(argumentsArray[2], argumentsArray[3]);
		}
		else {
			ed.addNurse();
		}
		System.out.println("Nurse added");
	}
	public void addPhysi(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException {
		if (argumentsArray.length!=2 && argumentsArray.length!=4) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		if (argumentsArray.length==3) {
			ed.addPhysi(argumentsArray[2], argumentsArray[3]);
		}
		else {
			ed.addPhysi();
		}
		System.out.println("Physician added");
	}
	public void setL1ArrivalDist(String[] argumentsArray) throws ObjectNotFoundException, BadInstructionException, NumberFormatException {
		if (argumentsArray.length!=4 && argumentsArray.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		switch(argumentsArray[2]) {
		case "exp":
			simergy.setL1arrivalDist( new ExpDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		case "uniform":
			simergy.setL1arrivalDist( new UnifDistribution(Double.parseDouble(argumentsArray[3]), Double.parseDouble(argumentsArray[4])));
			break;
		case "determinist":
			simergy.setL1arrivalDist( new DetDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
		}
		System.out.println("Distribution set");

	}
	public void setL2ArrivalDist(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException, NumberFormatException {
		if (argumentsArray.length!=4 && argumentsArray.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		switch(argumentsArray[2]) {
		case "exp":
			simergy.setL2arrivalDist( new ExpDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		case "uniform":
			simergy.setL2arrivalDist( new UnifDistribution(Double.parseDouble(argumentsArray[3]), Double.parseDouble(argumentsArray[4])));
			break;
		case "determinist":
			simergy.setL2arrivalDist( new DetDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
		}
		System.out.println("Distribution set");

	}
	public void setL3ArrivalDist(String[] argumentsArray) throws ObjectNotFoundException, BadInstructionException, NumberFormatException {
		if (argumentsArray.length!=4 && argumentsArray.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		switch(argumentsArray[2]) {
		case "exp":
			simergy.setL3arrivalDist( new ExpDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		case "uniform":
			simergy.setL3arrivalDist( new UnifDistribution(Double.parseDouble(argumentsArray[3]), Double.parseDouble(argumentsArray[4])));
			break;
		case "determinist":
			simergy.setL3arrivalDist( new DetDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
		}
		System.out.println("Distribution set");

	}
	public void setL4ArrivalDist(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException, NumberFormatException {
		if (argumentsArray.length!=4 && argumentsArray.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		switch(argumentsArray[2]) {
		case "exp":
			simergy.setL4arrivalDist( new ExpDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		case "uniform":
			simergy.setL4arrivalDist( new UnifDistribution(Double.parseDouble(argumentsArray[3]), Double.parseDouble(argumentsArray[4])));
			break;
		case "determinist":
			simergy.setL4arrivalDist( new DetDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
		}
		System.out.println("Distribution set");

	}
	public void setL5ArrivalDist(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException, NumberFormatException {
		if (argumentsArray.length!=4 && argumentsArray.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		switch(argumentsArray[2]) {
		case "exp":
			simergy.setL5arrivalDist( new ExpDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		case "uniform":
			simergy.setL5arrivalDist( new UnifDistribution(Double.parseDouble(argumentsArray[3]), Double.parseDouble(argumentsArray[4])));
			break;
		case "determinist":
			simergy.setL5arrivalDist( new DetDistribution(Double.parseDouble(argumentsArray[3])));
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
		}
		System.out.println("Distribution set");

	}
	public void addPatient(String[] argumentsArray) throws ObjectNotFoundException, BadInstructionException{
		if (argumentsArray.length!=5) {
			throw new BadInstructionException("Wrong number of arguments");
		}

		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		Patient p=new Patient(argumentsArray[2], argumentsArray[3], argumentsArray[4]);
		//On cherche un endroit disponible dans une waiting room et si on en trouve pas on met le patient dans le corridor
		Room room = null;
		for (WaitingRoom r : ed.getWaitingRoomList()) {
			for (WaitingRoomSpot s: r.getSpots()) {
				if (s.isDispo()){
					room=s;
					break;
				}
			}
			if (room!=null) {
				break;
			}
		}
		//Si on ne trouve pas de waiting room on va dans le corridor
		if (room==null) {
			room= ed.getCorridor();
		}
		//Créer l'eventtype d'arrivée du patient
		PatientArrival pa= new PatientArrival(p, room);
		//Créer l'event 
		Event e = new Event(pa, simergy.getSimTime());
		//Exécuter l'event pour mettre à jour l'état du patient et du système
		pa.accept(simergy);
		simergy.getEventHistory().add(e);
		System.out.println("Patient added");
	}
	public void registerPatient(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException, NumberFormatException  {
		if (argumentsArray.length!=3 ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		int patientId= Integer.parseInt(argumentsArray[2]);
		//On trouve le patient avec son ID
		Patient p1= ed.findPatientById(patientId);
		if (p1==null) {
			throw new ObjectNotFoundException("There is no Patient with ID "+ argumentsArray[2]); 
		}
		//On crée un booléen pour voir si le patient réussi a etre enregistré
		Boolean registered = false;
		//On cherche si une infirmière est disponible
		Registration r1= null;
		for (int j=0;j<ed.getNurseList().size();j++) {
			if (ed.getNurseList().get(j).isDispo()) {
				//Si une infirimère est disponible on crée l'event de registration
				r1= new Registration(p1,ed.getNurseList().get(j));
				//Et on indique que le patient a bien été enregistré
				registered = true;
				//Une fois qu'on a trouvé une infirmière disponible on arrête la boucle
				break;
			}
		}
		//Si le patient est enregistré..
		if (registered) {
			//On met à jour l'état du système après l'exécution de l'enregistrement
			Event e = new Event(r1, simergy.getSimTime());
			r1.accept(simergy);
			simergy.getEventHistory().add(e);
			System.out.println("Patient Registered");
		}

		else {
			throw new ObjectNotFoundException("No nurse available for registration");
			
		
		}

	}
	public void setPatientInsurance(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException, NumberFormatException  {
		if (argumentsArray.length!=4 ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		int patientId1= Integer.parseInt(argumentsArray[2]); //numberformat exception
		Patient p=ed.findPatientById(patientId1);
		if (p==null) {
			throw new ObjectNotFoundException("There is no Patient with Id "+ argumentsArray[2]);
		}
		if(argumentsArray[3]!="none" && argumentsArray[3]!="silver" && argumentsArray[3]!="gold" ){
			throw new BadInstructionException("This insurance type is not defined");
		}
		p.setInsurance(argumentsArray[3]);
		System.out.println("Insurance set");
	}
	public void executeNextEvent(String[] argumentsArray) throws BadInstructionException, ObjectNotFoundException {
		if (argumentsArray.length!=2 ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(argumentsArray[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ argumentsArray[1]);
		}
		ED ed = simergy.getED();
		 simergy.getNewEnabledQueue(simergy.getED()); //Crée la nouvelle enabledEventQueue à partir du nouvel état du system
		 simergy.getEventQueue().sort(); // Retrie l'eventQueue dans l'ordre chronologique
		 Event nextEvent = simergy.dequeue(); //On retire l'event de la queue
		 if (nextEvent== null) {
			 throw new ObjectNotFoundException("There is no Event in queue");
		 }
		 nextEvent.getEventType().accept(simergy);// On visit l'event pour mettre à jour l'état du système
		 simergy.getEventHistory().add(nextEvent);
		 simergy.setSimTime(nextEvent.getTimestamp());
		 System.out.println("Next Event executed");
	}
	public void display(String[] args) throws BadInstructionException, ObjectNotFoundException {
		if (args.length!=2 ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		
		String res = ed.toString()+"\n"+"\n"+"\n";
		if(!Double.isNaN(simergy.dtdTime())) {
			res+= "Average Door to door time is " + simergy.dtdTime() +"\n";
		}
		else {res+="No patient was consulted yet";}
		if(!Double.isNaN(simergy.lengthOfStayTime())) {
			res+= "Average Length of Stay time is " +simergy.lengthOfStayTime() +"\n";
		}
		else {res+="No patient was released yet";}
		System.out.println(res);
	}
	public void addConsultationService(String[] args) throws BadInstructionException, ObjectNotFoundException, NumberFormatException  {
		if (args.length!=4 && args.length!=5  ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		
		switch (args[2]) {
		case "uniform":
			ed.getConsultationService().setP(new UnifDistribution(Double.parseDouble(args[3]), Double.parseDouble(args[4])));
			break;
		case "exp":
			ed.getConsultationService().setP(new ExpDistribution(Double.parseDouble(args[3])));
			break;
		case "determinist":
			ed.getConsultationService().setP(new DetDistribution(Double.parseDouble(args[3])));	
			break;
		default:
			throw new BadInstructionException("This Distribution type is not defined");
			
		}
		System.out.println("Consulation service added");
	}
	public void addTransporter(String[] args) throws BadInstructionException, ObjectNotFoundException {
		if (args.length!=2 && args.length!=4  ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		
		if (args.length==3) {
			ed.getTransporterList().add(new Transporter(args[2], args[3]));
		}
		else {
			ed.getTransporterList().add(new Transporter());
		}
		System.out.println("Transporter added");
	}
	
	//The command auto 
	public void auto(String[] args) throws BadInstructionException, ObjectNotFoundException {
		if (args.length!=3 ) {
			throw new BadInstructionException("Wrong number of arguments");
		}
		SimErgy simergy=edList.getSimbyName(args[1]);
		if (simergy==null) {
			throw new ObjectNotFoundException("There is no ED with name "+ args[1]);
		}
		ED ed = simergy.getED();
		
		double maxTime= Double.parseDouble(args[2]);
		//Tant qu'on n'a pas atteint le temps max on exécute le prochain event
		while (simergy.getSimTime() < maxTime) {
			 simergy.getNewEnabledQueue(simergy.getED()); //Créer la nouvelle enabledEventQueue à partir du nouvel état du system
			 simergy.getEventQueue().sort(); // Retrie l'eventQueue dans l'ordre chronologique
			 Event nextEvent = simergy.dequeue(); //On retire l'event de la queue
			 simergy.setSimTime(nextEvent.getTimestamp());// On actualise le temps de simulation
			 nextEvent.getEventType().accept(simergy);//On visit l'event pour mettre à jour l'état du système
			 simergy.getEventHistory().add(nextEvent);//On ajoute l'event à l'historique
	}
		System.out.println("Simulation was automated until time "+ simergy.getSimTime());
}
	
	
	
	public void runTest(String fileName) throws BadInstructionException, ObjectNotFoundException, NumberFormatException, IOException{
		BufferedReader fileInput = new BufferedReader(new FileReader(fileName));
		
		System.out.println("CLUI waiting for command");
		
		String command="";
	
		while(!command.equals("stop")){
		command = fileInput.readLine();
		String [] comArray = command.split(" |,");
		try {
		switch(comArray[0]){
		
		case "createED":
			creatED(comArray[1]);
			break;
		case "addRoom":
			addRoom(comArray[1], comArray[2], comArray[3]);
			break;
				
		case "addRadioService":
			addRadioService(comArray );
			break;
		case "addMRI":
			this.addMRIService(comArray);
			break;
		case "addBloodTest":
			this.addBloodTestService(comArray);
			break;
		case "addConsultation":
			this.addConsultationService(comArray);
			break;
		case "addNurse":
			this.addNurse(comArray);
			break;

		case "addPhysi":
			this.addPhysi(comArray);
			break;
		case "addTransporter":
			this.addTransporter(comArray);
			break;
		case "setL1arrivalDist":
			this.setL1ArrivalDist(comArray);
			break;
		case "setL2arrivalDist":
			this.setL2ArrivalDist(comArray);
			break;
		case "setL3arrivalDist":
			this.setL3ArrivalDist(comArray);
			break;
		case "setL4arrivalDist":
			this.setL4ArrivalDist(comArray);
			break;
		case "setL5arrivalDist":
			this.setL5ArrivalDist(comArray);
			break;
		case "addPatient":
			this.addPatient(comArray);
			break;
		case "registerPatient":
			this.registerPatient(comArray);
			break;
		case "setPatientInsurance":
			this.setPatientInsurance(comArray);
			break;
		case "executeEvent":
			this.executeNextEvent(comArray);
			break;
		case "display":
			this.display(comArray);
			break;
		case "auto":
			this.auto(comArray);
			break;
		case "stop":
			System.out.println("Command File treated");
			break;
		default:
			throw new BadInstructionException ("Command undefined");
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		
		}
		//We generate the outputFile
		String res="";
		
		for (SimErgy simergy : edList.getsList()) {
			String outputFile =fileName.replaceAll(".ini", "");
			outputFile =outputFile.replaceAll(".txt", "");
			outputFile+= "output.txt";
			ED ed = simergy.getED();
			res = ed.toString()+"\n"+"\n"+"\n";
			if(!Double.isNaN(simergy.dtdTime())) {
				res+= "Average Door to door time is " + simergy.dtdTime() +"\n";
			}
			else {res+=" No patient was consulted yet";}
			if(!Double.isNaN(simergy.lengthOfStayTime())) {
				res+= "Average Length of Stay time is " +simergy.lengthOfStayTime() +"\n";
			}
			else {res+=" No patient was released yet";}
			ArrayList<String> lines = new ArrayList<>(Arrays.asList(res.split("\n")));
			Path file = Paths.get(outputFile);
			Files.write(file, lines, Charset.forName("UTF-8"));
		}
		fileInput.close();
	}
	//interpreter method
	
	public void start() throws BadInstructionException, ObjectNotFoundException, NumberFormatException, IOException, NameAlreadyUsedException{

		System.out.println("CLUI waiting for command");
		
		Scanner sc= new Scanner(System.in);
		String command="";
	
		while(!command.equals("stop")){
		
	
		command = sc.nextLine();
		String [] comArray = command.split(" |,");
		try {
		switch(comArray[0]){
		
		case "createED":
			creatED(comArray[1]);
			break;
		case "addRoom":
			addRoom(comArray[1], comArray[2], comArray[3]);
			break;
				
		case "addRadioService":
			addRadioService(comArray );
			break;
		case "addMRI":
			this.addMRIService(comArray);
			break;
		case "addBloodTest":
			this.addBloodTestService(comArray);
			break;
		case "addConsultation":
			this.addConsultationService(comArray);
			break;
		case "addNurse":
			this.addNurse(comArray);
			break;

		case "addPhysi":
			this.addPhysi(comArray);
			break;
		case "addTransporter":
			this.addTransporter(comArray);
			break;
		case "setL1arrivalDist":
			this.setL1ArrivalDist(comArray);
			break;
		case "setL2arrivalDist":
			this.setL2ArrivalDist(comArray);
			break;
		case "setL3arrivalDist":
			this.setL3ArrivalDist(comArray);
			break;
		case "setL4arrivalDist":
			this.setL4ArrivalDist(comArray);
			break;
		case "setL5arrivalDist":
			this.setL5ArrivalDist(comArray);
			break;
		case "addPatient":
			this.addPatient(comArray);
			break;
		case "registerPatient":
			this.registerPatient(comArray);
			break;
		case "setPatientInsurance":
			this.setPatientInsurance(comArray);
			break;
		case "executeEvent":
			this.executeNextEvent(comArray);
			break;
		case "display":
			this.display(comArray);
			break;
		case "stop":
			System.out.println("Stopping the simulation");
			break;
		case "auto":
			this.auto(comArray);
			break;
		case "runTest":
			System.out.println(comArray[1]);
			this.runTest(comArray[1]);
			break;
		default:
			throw new BadInstructionException("Command undefined");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
		sc.close();

		
}

		
	public static void main(String[] args) throws NumberFormatException, BadInstructionException, ObjectNotFoundException, IOException, NameAlreadyUsedException {
		CLUI c= new CLUI();
		c.runTest("init.ini");
		c.start();
	}}

