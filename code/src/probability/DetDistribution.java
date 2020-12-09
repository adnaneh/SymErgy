package probability;


public class DetDistribution extends ProbabilityDistribution{
	public DetDistribution(double parameterOne) {
		super(parameterOne);
		// TODO Auto-generated constructor stub
	}

	//Distribution déterministe, vaut toujours le paramètre donné
	@Override
	public double generateSample() {
		return(this.getParameterOne());
	}
	
}
