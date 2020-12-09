package probability;

public  abstract  class ProbabilityDistribution {   
	
	//Constructeur
	public ProbabilityDistribution (double parameterOne) {
		this.parameterOne=parameterOne;
	}
	public ProbabilityDistribution (double parameterOne, double parameterTwo) {
		this.parameterOne=parameterOne;
		this.parameterTwo=parameterTwo;
	}
	//genere aleatoirement un element de la distribution de probabilité
	private double parameterOne;
	private double parameterTwo;
    public abstract  double generateSample() ;
    //getter & setter
	public double getParameterOne() {return parameterOne;}
	public void setParameterOne(double parameter1) {this.parameterOne = parameter1;}  
	public double getParameterTwo() {return parameterTwo;}
	public void setParameterTwo(double parameterTwo) {this.parameterTwo = parameterTwo;}
}