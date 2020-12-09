package ressourcehr;

public class Nurse extends RessourceHR {

	public Nurse(String name, String surname) {
		super(name, surname);
	}
	public Nurse() {
		super();
		this.setName("nurse"+this.getId());
		this.setSurname("nurse"+this.getId());
	}
	
	public String toString() {
		String res="";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " State: " + this.getState();
		res+= " Disponibility "+this.isDispo();
		
		return res;
	}
}
