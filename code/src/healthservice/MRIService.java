package healthservice;

import probability.UnifDistribution;

public class MRIService extends HealthService {

	public MRIService() {
		super();
		this.setP(new UnifDistribution(30,70));
		this.setName("MRIService");
		this.setCost(1);
	}


}
