import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Tabou extends Algorithme {

	private int[] solution; // solution bonne sur laquelle part l'algorithme du tabou

	// file du tabou
	private LinkedList<Transformation> tabous;

	// ensemble qui indique si une transformation se trouve dans la file du tabou ou
	// pas
	private Set<Transformation> tabousIndicateur;

	// taille limite de la file du tabou
	private int limiteTabou;

	// nombre d'iterations limite au bout du quel
	private int limiteIterations;

	public Tabou(PblTsp problem, int[] tournee, int limiteTabou, int limiteIterations) {
		super(problem);
		this.solution = tournee;
		this.limiteTabou = limiteTabou;
		this.limiteIterations = limiteIterations;
		this.tabous = new LinkedList<Transformation>();
		this.tabousIndicateur = new HashSet<Transformation>();
	}

	@Override
	public int[] algorithme() {
		// PARTIR d'une solution bonne
		int distSolution = this.distanceTournee(this.solution);

		int[] meilleur = this.creerVoisin(1, 3, this.solution); // on commence direct à un voisin de la solution
		int distMeilleur = this.distanceTournee(meilleur);
		Transformation transfoMeilleur = new Transformation(1, 3);
		this.ajouterDansTabou(transfoMeilleur);

		// (Sert à trouver le meilleur voisin non-tabou)
		// PENDANT LE NOMBRE D'ITERATIONS PARAMETRE...
		for (int cpt = 0; cpt < this.limiteIterations; cpt++) {

			// SI [pas premiere etape]
			if (cpt != 0) {
				meilleur = this.creerVoisin(1, 3, meilleur);
				distMeilleur = this.distanceTournee(meilleur);
				transfoMeilleur = new Transformation(1, 3);
				this.ajouterDansTabou(transfoMeilleur);
			}

			// TROUVER meilleur voisin non tabou
			// --> POUR CHAQUE [voisin]
			for (int i = 1; i < this.solution.length - 2; i++) {
				for (int j = i + 2; j < this.solution.length - 1; j++) {
					Transformation transformation = new Transformation(i, j);

					// SI [voisin EST TABOU], on passe
					if (this.tabousIndicateur.contains(transformation)) {
						continue;
					}

					int[] voisin = this.creerVoisin(i, j, meilleur);
					int distVoisin = this.distanceTournee(voisin);

					// SI [pas encore de meilleur] OU [voisin EST MEILLEUR QUE meilleur actuel]
					if (distMeilleur > distVoisin) {
						meilleur = voisin;
						distMeilleur = distVoisin;
						transfoMeilleur = transformation;
					}
				}
			}

			// AJOUTER meilleur DANS TABOU
			this.ajouterDansTabou(transfoMeilleur);

			// SI [meilleur non tabou EST MEILLEUR QUE solution]
			if (distSolution > distMeilleur) {
				// RETENIR le nouvel optimum
				this.solution = meilleur;
				distSolution = distMeilleur;
			}
		}

		return this.solution;
	}

	/**
	 * Ajoute la transformation en paramètre dans la file de tabou
	 * 
	 * @param transformation la transformation de la solution à ajouter dans le
	 *                       tabou
	 */
	private void ajouterDansTabou(Transformation transformation) {
		// SI le tabou est déjà plein
		if (this.tabous.size() == this.limiteTabou) {
			// retirer la transformation la plus ancienne
			Transformation toRemove = this.tabous.removeFirst();
			this.tabousIndicateur.remove(toRemove);
		}

		this.tabous.addLast(transformation);
		this.tabousIndicateur.add(transformation);
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

	/**
	 * Classe interne qui représente les transformations utilisées
	 */
	private class Transformation {
		private int i;
		private int j;

		public Transformation(int i, int j) {
			super();
			this.i = i;
			this.j = j;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + i + j; // une transformation est égale à elle-même et à son inverse
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Transformation other = (Transformation) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;

			// une transformation est égale à elle-même et à son inverse
			if ((this.i == other.i && this.j == other.j) || (this.i == other.j && this.j == other.i)) {
				return true;
			}

			return false;
		}

		private Tabou getOuterType() {
			return Tabou.this;
		}
	}
}