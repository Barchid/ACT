class PblCycleHamilton extends PblDecision {
	private int n;
	private boolean[][] matrice;

	public PblCycleHamilton(int n, boolean[][] matrice) {
		super();
		this.n = n;
		this.matrice = matrice;
	}

	/**
	 * Reduction polynomiale qui retourne une instance equivalente de TSP
	 * 
	 * @return instance equivalente de TSP
	 */
	public PblTSP redPolyToTSP() {
		int[][] distances = new int[this.n][this.n];

		// INITIALISER toutes les distances a 1
		for (int i = 0; i < this.matrice.length; i++) {
			for (int j = 0; j < this.matrice[0].length; j++) {
				distances[i][j] = 1;
			}
		}

		// SI il y a un arc entre n-1 et 0, distance = 0
		if (this.matrice[this.n - 1][0]) {
			distances[this.n - 1][0] = 0;
		}

		// distance(i,i+1) = 0 s'il y a un arc entre sommets i et i+1
		for (int i = 0; i < this.n - 2; i++) {
			if (this.matrice[i][i + 1]) {
				distances[i][i + 1] = 0;
			}
		}

		return new PblTSP(this.n, distances, 0);
	}

	public boolean aUneSolution() {
		return this.redPolyToTSP().aUneSolution();
	}
}