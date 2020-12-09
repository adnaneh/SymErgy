package eventspackage;

import healthservice.HealthService;

public abstract class Examination extends EventType { //abstract because we never want to instanciate Examination class
	public Examination() {
		super();
	}

	public Examination (HealthService hs) {
		super(hs);
	}
	
}
