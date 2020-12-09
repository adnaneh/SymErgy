package ressourcehr;

public class PatientMilestone {
	private String patientState;
	private double timestamp;
	
	
	
	
	public PatientMilestone(String patientState, double timestamp2) {
		super();
		this.patientState = patientState;
		this.timestamp = timestamp2;
	}
	public String getPatientState() {
		return patientState;
	}
	public void setPatientState(String patientState) {
		this.patientState = patientState;
	}
	public double getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
}
