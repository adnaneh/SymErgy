package probability;


public class DetDistribution extends ProbabilityDistribution{
	public DetDistribution(double parameterOne) {
		super(parameterOne);
		// TODO Auto-generated constructor stub
	}

	//Distribution d�terministe, vaut toujours le param�tre donn�
	@Override
	public double generateSample() {
		return(this.getParameterOne());
	}
	
}
