package test;


import org.junit.jupiter.api.Test;

import eventspackage.Consultation;
import healthservice.ConsultationService;
import ressourcehr.Patient;
import ressourcehr.Physician;
import room.WaitingRoomSpot;

class ConsultationTest {

	@Test
	void testMessagePhysi() {
		//On initialise
		double timestamp= 10;
		Patient p = new Patient(1);
		Physician ph = new Physician();
		ConsultationService cs= new ConsultationService();
		WaitingRoomSpot spot= new WaitingRoomSpot();
		Consultation c= new Consultation(p,ph,spot,cs);
		//On applique
		c.messagePhysi(timestamp);
		//On regarde si le message ajouté correspond aux propriétés voulu (contenu, temps)
		String content = p.getName()+ " was affected to you and is waiting consultation";
		assert(ph.getMsgs().get(ph.getMsgs().size()-1).getStr().equals(content));
		assert(ph.getMsgs().get(ph.getMsgs().size()-1).getD()==timestamp);
	}

}
