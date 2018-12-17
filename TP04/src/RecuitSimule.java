import java.util.Arrays;

public class RecuitSimule extends Algorithme {
	private double temperature;
	private double refroidissement;
	private double limiteRefroidissement;
	private int[] solution;

	private boolean traceMode;

	public RecuitSimule(PblTsp problem, double temperature, double refroidissement, double limiteRefroidissement,
			int[] solution, boolean traceMode) {
		super(problem);
		this.temperature = temperature;
		this.refroidissement = refroidissement;
		this.limiteRefroidissement = limiteRefroidissement;
		this.solution = solution;

		this.traceMode = traceMode;
	}

	@Override
	public int[] algorithme() {
		int[] tourneeRetenue = this.solution;
		int distSolution = this.distanceTournee(this.solution);
		int distRetenue = distSolution;

		// TANT QUE temperature EST AU DESSUS DE LA LIMITE
		while (this.temperature > this.limiteRefroidissement) {
			// tournee = TIRER AU HASARD VOISIN DE solution choisie
			int[] tournee = this.tirerVoisinAuHasard(tourneeRetenue);
			int distTournee = this.distanceTournee(tournee);

			// SI [tournee EST MEILLEURE QUE la solution actuelle]
			if (distTournee < distRetenue) {
				// solution = tournee
				tourneeRetenue = tournee;
				distRetenue = distTournee;
			}
			// SINON
			else {
				// CALCULER delta
				double delta = distRetenue - distTournee;

				// accept = CALCULER ACCEPT (E^(-delta/T))
				double accept = Math.exp(-delta / this.temperature);

				// p = TIRER AU HASARD ENTRE 0 ET 1
				double p = Math.random();

				// SI [p EST INFERIEUR A accept]
				if (p < accept) {
					System.out.println("Accept " + distRetenue);
					// ACCEPTER tournee
					tourneeRetenue = tournee;
					distRetenue = distTournee;

					// REFROIDIR temperature PAR refroidissement
					this.temperature *= this.refroidissement;

				}
				// SINON
				else {
					// PASSER A L'AUTRE
					continue;
				}
			}
			if (distSolution > distRetenue) {
				distSolution = distRetenue;
				this.solution = tourneeRetenue;

				if (this.traceMode) {
					System.out.println(Arrays.toString(this.solution));
				}
			}
		}

		return this.solution;
	}

	/**
	 * Tire un voisin de la tournee en parametre au hasard
	 * 
	 * @param tournee
	 * @return le voisin tiré au hasard de la tournee en parametre
	 */
	private int[] tirerVoisinAuHasard(int[] tournee) {

		// 1 < i < n - 1
		int i = (int) (Math.random() * (((tournee.length - 2) - 1) + 1)) + 1;

		// j est un entier pris au hasard entre i + 2 et n - 2
		int j = (int) (Math.random() * (((tournee.length - 1) - (i + 1)) + 1)) + (i + 1);
		return this.creerVoisin(i, j, tournee);
	}

	/**
	 * Génère le voisin de la tournee (en param) basé sur le couple (i,j) solution
	 * 
	 * @param i
	 * @param j
	 * @param tournee
	 * @return le voisin de la solution basé sur le couple (i,j)
	 */
	public int[] creerVoisin(int i, int j, int[] tournee) {
		// COPIE de solution
		int[] voisin = Arrays.copyOf(tournee, tournee.length);

		// T'(i+1) = T(j)
		voisin[i + 1] = tournee[j];

		// T'(j+1-p) = T(i + p)
		for (int p = 1; p < j - i; p++) {
			voisin[j + 1 - p] = tournee[i + p];
		}

		return voisin;
	}
}
