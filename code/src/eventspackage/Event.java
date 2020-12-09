package eventspackage;


public class Event {
	//happening time
	private double timestamp;
	//EventType
	private EventType eventType;
	
	public Event() {}
	public Event(EventType eventType, double d) {
		super();
		this.eventType=eventType;
		this.timestamp = d;
	
	}
	

	
	//geters & setters
	public double getTimestamp() {return this.timestamp;}
	public void setTimestamp(int timestamp) {this.timestamp = timestamp;}
	public EventType getEventType() {return eventType;}
	public void setEventType(EventType eventType) {this.eventType = eventType;}

}
