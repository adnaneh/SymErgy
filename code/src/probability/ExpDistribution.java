package probability;


import java.util.Random;

public class ExpDistribution extends ProbabilityDistribution {
	public ExpDistribution(double parameterOne) {
		super(parameterOne);
		// TODO Auto-generated constructor stub
	}
	//Distribution aléatoire exponentielle de parametre parameterOne
	@Override
	public double generateSample() {
		Random rand = new Random();
	    return -(1 / this.getParameterOne()) * Math.log( 1 - rand.nextDouble() );
	}

}