import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExactTresSimple extends Algorithme {
	private PblTsp problem;
	private int[] solution;
	private int distSolution;

	public ExactTresSimple(PblTsp problem) {
		super(problem);
		this.problem = problem;
	}

	@Override
	public int[] algorithme() {
		// INITIALISER debut de tournee
		int[] debut = new int[this.problem.getN()];
		for (int i = 0; i < debut.length; i++) {
			debut[i] = -1; // valeur sentinelle pour dire qu'il n'y a pas de ville choisie.
		}

		this.construirePossibilites(debut, 0, 0, this.problem.getN());
		return this.solution;
	}

	/**
	 * Construit l'arbre de toutes les possibilités et choisit toujours la meilleure
	 * solution possible (set this.solution)
	 * 
	 * @param tournee       le tableau représentant la tournée
	 * @param tailleTournee la taille logique de la tournée
	 * @param n             le nombre de villes
	 */
	private void construirePossibilites(int[] tournee, int tailleTournee, int longueurTournee, int n) {
		// SI [tournee EST COMPLETE]
		if (tailleTournee == n) {
			// AJOUTER tournee COMPLETE
			this.choisirSolution(tournee);
			return;
		}

		Set<Integer> villesNonPrises = this.initialiserVillesNonPrises(tournee, n);

		// POUR CHAQUE ville PAS PRISE
		for (int ville : villesNonPrises) {
			// AJOUTER ville pas prise A tournee
			int[] possibilite = Arrays.copyOf(tournee, n);
			possibilite[tailleTournee] = ville;

			// METTRE A JOUR la longueur de la tournee
			int newLongueurTournee = this.longueurTournee(tournee, tailleTournee, longueurTournee);

			// EVALUER borneInferieure
			int borneInferieure = this.borneInferieure(newLongueurTournee, villesNonPrises.size(), possibilite,
					tailleTournee);

			// SI [borne inferieure EST MEILLEURE QUE cout solution actuelle]
			if (this.solution == null || this.distSolution > borneInferieure) {
				// CONSTRUIRE NOUVELLES POSSIBILITES
				this.construirePossibilites(possibilite, tailleTournee + 1, newLongueurTournee, n);
			}
		}
	}

	/**
	 * Détermine la longueur du début de tournée en paramètre
	 * 
	 * @param tournee         le début de tournée en paramètre
	 * @param tailleTournee   la taille logique du début de tournée en apramètre
	 * @param longueurTournee la longueur du début de tournée à réévaluer
	 * @return la longueur du début de la tournée
	 */
	private int longueurTournee(int[] tournee, int tailleTournee, int longueurTournee) {
		if (tailleTournee == 0) {
			return 0;
		} else {
			return longueurTournee + this.problem.getMatrice()[tailleTournee - 1][tailleTournee];
		}
	}

	/**
	 * Initialise l'ensemble des villes non-prises par le début de tournée en
	 * paramètre
	 * 
	 * @param tournee le début de tournée
	 * @param n       le nombre de villes total
	 * @return l'ensemble des villes non-prises par le début de tournée en
	 *         paramètre.
	 */
	private Set<Integer> initialiserVillesNonPrises(int[] tournee, int n) {
		// Initialiser les villes non-prises
		Set<Integer> villesPasPrises = new HashSet<Integer>();
		for (int i = 0; i < n; i++) {
			villesPasPrises.add(i);
		}
		for (int villeDejaPrise : tournee) {
			villesPasPrises.remove(villeDejaPrise);
		}
		return villesPasPrises;
	}

	/**
	 * Compare le candidat tournée en paramètre avec la solution actuelle et change
	 * la solution si le candidat est meilleur
	 * 
	 * @param candidat la tournee candidate à comparer avec la solution actuelle
	 */
	private void choisirSolution(int[] candidat) {
		// EVALUER la valeur du candidat
		int distCandidat = this.distanceTournee(candidat);

		// SI [solution N'EXISTE PAS] OU [solution EST MOINS BONNE QUE candidat]
		if (this.solution == null || this.distSolution > distCandidat) {
			this.solution = candidat;
			this.distSolution = distCandidat;
		}
	}

	private int borneInferieure(int longueurTournee, int nbVillesPasPrises, int[] tournee, int tailleTournee) {
		// borne inferieure = longueur de la tournee + (x+1) * dmin
		return longueurTournee + (nbVillesPasPrises + 1) * dmin(tournee, tailleTournee);
	}

	private int dmin(int[] tournee, int tailleTournee) {
		if(tailleTournee == 0) {
			return 0;
		}
		
		int[][] matrice = this.problem.getMatrice();
		int dmin = matrice[tournee[0]][tournee[1]];
		for (int i = 0; i < tailleTournee; i++) {
			if (dmin > matrice[tournee[i]][tournee[i + 1]]) {
				dmin = matrice[tournee[i]][tournee[i + 1]];
			}
		}

		return dmin;
	}
}
