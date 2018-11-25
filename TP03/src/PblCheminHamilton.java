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
		// On reprend le graphe du chemin et on y ajoute un sommet 'FIN', qui est pointe
		// par tous les autres sommets et pointe tous les autres sommets
		boolean[][] matriceCycle = new boolean[this.n + 1][this.n + 1]; // matrice pour gerer le sommet FIN en +

		// RECOPIER matrice DANS matriceCycle
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				matriceCycle[i][j] = this.matrice[i][j];
			}
		}

		// Gestion du sommet FIN
		for (int i = 0; i < this.n; i++) {
			// RAJOUTER UN ARC DE fin VERS i
			matriceCycle[this.n][i] = true;
			// RAJOUTER UN ARC DE i VERS fin
			matriceCycle[i][this.n] = true;
		}

		return new PblCycleHamilton(this.n + 1, matriceCycle);
	}

	/**
	 * Genere un probleme TSP
	 * 
	 * @return
	 */
	public PblTSP redPolyToTSP() {
		// on utilise le fait que la relation de reduction polynomiale soit transitive
		return this.redPolyToCycleHamilton().redPolyToTSP();
	}

	public boolean aUneSolution() {
		// il suffit de transformer le probleme en un probleme TSP et de verifier si le probleme transforme a une solution
		return this.redPolyToTSP().aUneSolution();
	}
}