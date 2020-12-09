package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import eventspackage.Event;

public class EventQueue {
	
	//Attribut queue qui stocke la liste des event qui vont �tre effectu�s prochainement par ordre croissant de date d'op�ration
	public ArrayList<Event> queue = new ArrayList<Event>();
	
	
	
	
	
	//Methode pour trier la liste par ordre chronologique des events
	public void sort() {
		Collections.sort(this.queue, new EventTimestampComparator());
	}
	//utilisation de Comparator pour pouvoir trier facilement les event par ordre chronologique
	class EventTimestampComparator implements Comparator<Event> {
	    public int compare(Event e1, Event e2) {
    	    if (e1.getTimestamp() < e2.getTimestamp()) {return -1;}
            if (e1.getTimestamp() > e2.getTimestamp()) {return 1;}
            return 0;
	    }
	}
	
	//m�thode toString
	public String toString() {
		String res="";
		for (Event e: this.queue) {
			res+= "Time: " + e.getTimestamp();
			res+= e.getEventType();
		}
		return res;
	}
	
	// Getters & Setters
	public ArrayList<Event> getQueue() {return queue;}
	public void setQueue(ArrayList<Event> queue) {this.queue = queue;}
	
	
}
