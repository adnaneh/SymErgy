package healthservice;

import probability.UnifDistribution;

public class ConsultationService extends HealthService {
	public ConsultationService() {
		super();
		this.setName("ConsultationService");
		this.setP(new UnifDistribution(5, 20));
		this.setCost(1);
	}
}
