package ressourcehr;

public class Transporter extends RessourceHR {

	public Transporter(String name, String surname) {
		super(name, surname);
	}
	public Transporter() {
		super();
		this.setName("transporter"+this.getId());
		this.setSurname("transporter"+this.getId());
	}
	public String toString () {
		String res="";
		res+= " Id: " + this.getId();
		res+= " Name: "+ this.getName();
		res+= " State: " + this.getState();
		res+= " Disponibility "+this.isDispo();
		
		return res;
	}
}
