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
				// distances[i][j] = 0 SI il y a un arc entre i et j
				// SINON 1
				distances[i][j] = this.matrice[i][j] ? 0 : 1;
			}
		}

		// n = n
		// l = 0
		// D = distances
		return new PblTSP(this.n, distances, 0);
	}

	public boolean aUneSolution() {
		return this.redPolyToTSP().aUneSolution();
	}
}