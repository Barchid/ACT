class PblCheminHamilton extends PblDecision {
	private int n;
	private boolean[][] matrice;

	public PblCheminHamilton(int n, boolean[][] matrice) {
		super();
		this.n = n;
		this.matrice = matrice;
	}

	// les reductions polynomiales

	/**
	 * Genere un probleme CycleHamilton a partir du probleme CheminHamilton courant
	 * 
	 * @return le probleme CycleHamilton genere
	 */
	public PblCycleHamilton redPolyToCycleHamilton() {
		boolean[][] newMatrice = new boolean[this.n][this.n];

		// Recopier la matrice du probleme de chemin hamiltonien
		for (int i = 0; i < this.matrice.length; i++) {
			for (int j = 0; j < this.matrice[0].length; j++) {
				newMatrice[i][j] = this.matrice[i][j];
			}
		}

		// SI il y a un arc dans le Chemin Hamiltonien entre n-1 et 0, il n'y en a pas
		// dans le cycle correspondant
		newMatrice[n - 1][0] = !this.matrice[this.n - 1][0];

		return new PblCycleHamilton(this.n, newMatrice);
	}

	/**
	 * Genere un probleme TSP
	 * 
	 * @return
	 */
	public PblTSP redPolyToTSP() {
		return null;
	}

	public boolean aUneSolution() {
		return false;
	}
}