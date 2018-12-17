import java.util.Arrays;

public class HillClimbing extends Algorithme {

	/**
	 * tournée "bonne" trouvée par un autre algorithme (ConstructionParAjoutProche)
	 */
	private int[] solution;
	
	private boolean traceMode;

	public HillClimbing(PblTsp problem, int[] solution, boolean traceMode) {
		super(problem);
		this.solution = solution;
		this.traceMode = traceMode;
	}

	@Override
	public int[] algorithme() {
		// PARTIR d'une solution bonne
		int distSolution = this.distanceTournee(solution);
		
		// TANT QUE...
		while (true) {
			boolean meilleureSolution = true;
			// POUR CHAQUE voisin DE solution
			for (int i = 1; i < this.solution.length - 2; i++) {
				for (int j = i + 2; j < this.solution.length - 1; j++) {
					int[] voisin = this.creerVoisin(i, j);

					// distVoisin = EVALUER DISTANCE DE voisin
					int distVoisin = this.distanceTournee(voisin);

					// SI voisin EST meilleur
					if (distVoisin < distSolution) {
						distSolution = distVoisin;
						this.solution = voisin;
						
						if(this.traceMode) {
							System.out.println(Arrays.toString(this.solution));
						}
						
						meilleureSolution = false; // on a trouvé un voisin donc la solution n'est plus la meilleure
					}
				}
			}

			// SI solution EST LA MEILLEURE
			if (meilleureSolution) {
				return this.solution;
			}
		}
	}

	/**
	 * Génère le voisin de la solution basé sur le couple (i,j)
	 * 
	 * @param i
	 * @param j
	 * @return le voisin de la solution basé sur le couple (i,j)
	 */
	public int[] creerVoisin(int i, int j) {
		// COPIE de solution
		int[] voisin = Arrays.copyOf(this.solution, this.solution.length);

		// T'(i+1) = T(j)
		voisin[i + 1] = this.solution[j];

		// T'(j+1-p) = T(i + p)
		for (int p = 1; p < j - i; p++) {
			voisin[j + 1 - p] = this.solution[i + p];
		}

		return voisin;
	}

}
