package eventspackage;

import ressourcehr.Patient;
import room.Room;
import visitorpattern.Visitor;

public class EndBloodTest extends EventType{
	
	public EndBloodTest(Patient patient,Room room) {//Constructeur pour créer un EndingEventType de bloodtest. Le string ne sert qu'à différencier ce constructeur de l'autre
		super();
		this.setType("ENDBLOOD");
		this.setPatient(patient);
		this.setLocation(room);
		this.setBeginning(false);
	}
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

}
