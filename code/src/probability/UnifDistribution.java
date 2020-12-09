package probability;



public class UnifDistribution extends ProbabilityDistribution {
	//Distribution al�atoire uniforme entre parameterOne et parameterTwo
	

	
	public UnifDistribution(double parameterOne, double parameterTwo) {
		super(parameterOne, parameterTwo);
	}

	@Override
	public double generateSample() {
		double rand = Math.random();
	    return this.getParameterOne()+(rand*(this.getParameterTwo()-this.getParameterOne()));
	}
	

	

}
