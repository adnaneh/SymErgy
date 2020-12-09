package CLUI;

import java.util.ArrayList;

import main.SimErgy;

public class EDList {
	private ArrayList<SimErgy> sList= new ArrayList<SimErgy> ();
	public SimErgy getSimbyName (String name) {
		//find a simulator by the name of the ED it simulates
			
			for (int i=0;i<sList.size();i++) {
			if (sList.get(i).getED().getName().equals(name)) {
				return(sList.get(i));
				
			}
			}
			return null;
	
	}
	public void addSim(SimErgy s) {
		this.sList.add(s);
	}
	// getters et setters
	public ArrayList<SimErgy> getsList() {
		return sList;
	}
	public void setsList(ArrayList<SimErgy> sList) {
		this.sList = sList;
	}
}
		
	
		

