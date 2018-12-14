import java.util.ArrayList;
import java.util.List;

public class Tabou extends Algorithme {

	private int[] tournee;
	private List<Transformation> tabous;
	private int limiteTabou;

	public Tabou(PblTsp problem, int[] tournee, int limiteTabou) {
		super(problem);
		this.tournee = tournee;
		this.limiteTabou = limiteTabou;
		this.tabous = new ArrayList<Transformation>();
	}

	@Override
	public int[] algorithme() {
		// TODO Auto-generated method stub
		return null;
	}

	private class Transformation {
		private int i;
		private int j;

		public Transformation(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		public boolean equals(int i, int j) {
			if (this.i == i && this.j == j) {
				return true;
			} else {
				return false;
			}
		}

	}
}