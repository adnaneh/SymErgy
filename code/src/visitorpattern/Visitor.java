package visitorpattern;

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
import eventspackage.MRI;
import eventspackage.PatientArrival;
import eventspackage.Radiography;
import eventspackage.Registration;
import eventspackage.TransportationInstallation;
import eventspackage.TransportationTest;
import eventspackage.Verdict;
import eventspackage.WaitingResult;

public interface Visitor {

	  public void visit(BloodTest e);
	  public void visit(Consultation e);
	  public void visit(MRI e);
	  public void visit(PatientArrival e);
	  public void visit(Radiography e);
	  public void visit(Registration e);
	  public void visit(TransportationInstallation e);
	  public void visit(TransportationTest e);
	  public void visit(Verdict e);
	  public void visit(WaitingResult e);
	  public void visit(EndBloodTest endBloodTest);
	public void visit(EndConsultation endConsultation);
	public void visit(EndMRI endMRI);
	public void visit(EndRadiography endRadiography);
	public void visit(EndRegistration endRegistration);
	public void visit(EndTransportationInstallation endTransportationInstallation);
	public void visit(EndTransportationTest endTransportationTest);
	public void visit(EndVerdict endVerdict);
}
