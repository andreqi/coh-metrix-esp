package pucp.edu.cohmetrixesp.structs;

public class CohStats {
	double mean, stdDeviation;
	public CohStats(){}
	public CohStats(double mean, double stdDeviation) { 
		this.mean = mean;
		this.stdDeviation = stdDeviation;
	}
	
	public void setMean(double mean) {
		this.mean = mean;
	}
	
	public double getMean() {
		return mean;
	}

	public double getStdDeviation() {
		return stdDeviation;
	}

	public void setStdDeviation(double stdDeviation) {
		this.stdDeviation = stdDeviation;
	}
	
	public String toString () {
		StringBuilder ans = new StringBuilder();
		ans.append("[");
		ans.append("mean: " + mean);
		ans.append(", ");
		ans.append("stdDev: " +stdDeviation);
		ans.append("]");
		return ans.toString();
	}
	
}
