
public abstract class Algorithme {
	protected PblTsp problem;
	
	public Algorithme(PblTsp problem) {
		super();
		this.problem = problem;
	}
	
	abstract public int[] algorithme();
}
