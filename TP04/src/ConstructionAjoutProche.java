import java.util.HashSet;
import java.util.Set;

public class ConstructionAjoutProche extends Algorithme {

	public ConstructionAjoutProche(PblTsp problem) {
		super(problem);
	}

	public int[] algorithme() {
		// INITIALISER tournee AVEC ville 0
		int[] tournee = new int[this.problem.getN()];
		tournee[0] = 0;
		int tailleTournee = 1;

		// INITIALISER ensemble des villes a utiliser
		Set<Integer> toUse = new HashSet<Integer>();
		for (int i = 1; i < this.problem.getN(); i++) {
			toUse.add(i);
		}

		// TANT QUE je n'ai pas ma tournee complete
		while (tailleTournee < this.problem.getN()) {
			
			// CHERCHER la ville non utilisee la + proche PAR RAPPORT A la derniere ville DE tournee
			int plusProche = this.chercherVillePlusProche(toUse, tournee[tailleTournee-1]);

			// AJOUTER ville plus proche A tournee
			tournee[tailleTournee] = plusProche;
			tailleTournee++;
			
			// RETIRER DE villes a utiliser
			toUse.remove(plusProche);
		}
		
		// RETOURNER tournee
		return tournee;
	}

	/**
	 * Cherche la ville la plus proche de lastVille (en terme de distance par
	 * rapport a l'instance du problem de l'objet courant) contenue dans toUse.
	 * 
	 * @param toUse     l'ensemble des villes autorisees
	 * @param lastVille la derniere ville utilisee pour la tournee
	 * @return la ville la plus proche de lastVille
	 */
	private int chercherVillePlusProche(Set<Integer> toUse, int lastVille) {
		int minVille = -1; // recherche du minimum

		// POUR CHAQUE ville
		for (int i = 0; i < this.problem.getN(); i++) {
			// SI je ne dois pas utiliser la ville, JE PASSE
			if (!toUse.contains(i)) {
				continue;
			}

			// SI minVille N'A PAS ETE LANCE
			if (minVille == -1) {
				minVille = i;
			}

			// SI ville EST PLUS PROCHE QUE minVille
			if (this.problem.getMatrice()[lastVille][i] < this.problem.getMatrice()[lastVille][minVille]) {
				minVille = i;
			}
		}

		// RETOURNER minVille, la ville la plus proche
		return minVille;
	}

}
