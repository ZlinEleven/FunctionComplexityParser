/**
 * This class represents a Big-Oh notation class that will hold the time complexity of a block of code. The complexities are limited to
 * n and log(n)
 * @author zhenb
 *
 */
public class Complexity {
	
	private int n_power;
	private int log_power;
	
	/**
	 * Constructor for a complexity object
	 * @param n The power in which Big-Oh of N is raised to
	 * @param log The power in which Big-Oh of log_N is raised to
	 */
	public Complexity(int n, int log) {
		n_power = n;
		log_power = log;
	}
	
	/**
	 * Default constructor for a complexity object. Automatically instantiates the complexity of a block of code to O(1)
	 */
	public Complexity() {
		n_power = 0;
		log_power = 0;
	}
	
	/**
	 * Gives the power in which O(n) is raised to
	 * @return The power O(n) is raised to
	 */
	public int getNPower() {
		return n_power;
	}
	
	/**
	 * Gives the power in which O(log_n) is raised to
	 * @return The power in which O(log_n) is raised to
	 */
	public int getLogPower() {
		return log_power;
	}
	
	/**
	 * Sets the power in which O(n) is raised to
	 * @param n The power to raise O(n) to
	 */
	public void setNPower(int n) {
		n_power = n;
	}
	
	/**
	 * Sets the power in which O(log_n) is raised to
	 * @param log The power to raise O(log_n) to
	 */
	public void setLogPower(int log) {
		log_power = log;
	}
	
	/**
	 * Creates a String representation of the complexity object. If n_power and log_power are both 0, the output will be O(1)
	 * If either is 1 and the other is 0, the output will be either O(n^?) or O(log_n^?). If both is greater than 0, the output will be O(n^? * log_n^?)
	 * If any 
	 */
	public String toString() {
		String ans = "O(";
		
		if(getNPower() > 0) {
			ans += "n";
			if(getNPower() > 1) {
				ans += "^" + getNPower();
			}
		}
		if(getLogPower() > 0) {
			if(!ans.equals("O(")) {
				ans += " * log(n)";
			}
			else {
				ans += "log(n)";
			}
			if(getLogPower() > 1) {
				ans += "^" + getLogPower();
			}
		}
		if(ans.equals("O(")) {
			ans += 1;
		}
		
		ans += ")";
		return ans;
	}
}

