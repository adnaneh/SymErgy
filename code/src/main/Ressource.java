package main;

public class Ressource {
	//Constructeur
	public Ressource () {
		this.setId();
		this.disponibility=true;
	}
	//Nom
	private String name;
	//Last ID
	private static int last_id;
	//Unique Id 
	private int id;
	//set id method
	public void setId() {
		this.id = last_id+1;
		last_id= last_id+1;
	}
	//get id method
	public int getId() {
		return (this.id);
	}
	
	//Etat de la ressource : vaut true si la ressource est disponible, false sinon
	private boolean disponibility;
	
	
	//savoir si la ressource est utilisée
	public boolean isDispo() {
		return this.disponibility;
	}
	public void fill() { //indiquer que la ressource n'est pas disponible
		this.disponibility = false;
	}
	public void empty() { //Indiquer que la ressource est disponible
		this.disponibility = true;
	}
}
