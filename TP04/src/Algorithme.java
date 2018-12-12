
public abstract class Algorithme {
	protected PblTsp problem;

	public Algorithme(PblTsp problem) {
		super();
		this.problem = problem;
	}

	abstract public int[] algorithme();

	public int distanceTournee(int[] tournee) {
		// CALCULER la longueur des distances entre les villes
		int[][] matrice = this.problem.getMatrice();
		int n = this.problem.getN();
		// D(tour[n-1], tour[0])
		int sum = matrice[tournee[n - 1]][tournee[0]];

		// SOMME i=0 -> n-2 D(tour[i], tour[i+1])
		for (int i = 0; i < n - 2; i++) {
			sum += matrice[tournee[i]][tournee[i + 1]];
		}

		// verifier que la somme des distanceq du certificat est <= a la longueur
		// maximale autorisee
		return sum;
	}
}
