package healthservice;

import probability.UnifDistribution;

public class RadiographyService extends HealthService{
	public RadiographyService() {
		super();
		this.setP(new UnifDistribution(10, 20));
		this.setName("RadiographyService");
		this.setCost(1);
	}


	

}
