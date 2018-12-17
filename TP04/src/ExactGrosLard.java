import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExactGrosLard extends Algorithme {

	private int[] solution;
	private int distSolution;

	public ExactGrosLard(PblTsp problem) {
		super(problem);
	}

	@Override
	public int[] algorithme() {
		// INITIALISER debut de tournee
		int[] debut = new int[this.problem.getN()];
		for (int i = 0; i < debut.length; i++) {
			debut[i] = -1; // valeur sentinelle pour dire qu'il n'y a pas de ville choisie.
		}
		
		this.construirePossibilites(debut, 0, this.problem.getN());
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
	private void construirePossibilites(int[] tournee, int tailleTournee, int n) {
		// SI [tournee EST COMPLETE]
		if (tailleTournee == n) {
			// AJOUTER tournee COMPLETE
			this.choisirSolution(tournee);
			return;
		}

		// POUR CHAQUE ville PAS PRISE
		for (int ville : this.initialiserVillesNonPrises(tournee, n)) {
			// AJOUTER ville pas prise A tournee
			int[] possibilite = Arrays.copyOf(tournee, n);
			possibilite[tailleTournee] = ville;

			// CONSTRUIRE NOUVELLES POSSIBILITES
			this.construirePossibilites(possibilite, tailleTournee + 1, n);
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
}
