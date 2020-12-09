package healthservice;

import probability.UnifDistribution;

public class BloodTestService extends HealthService {

	public BloodTestService() {
		super();
		this.setP(new UnifDistribution(15, 90));
		this.setName("BloodTestService");
		this.setCost(1);
	}
	

}
