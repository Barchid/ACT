package questions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Combinaison {

	private char[][] tableau; // Tableau de caractères
	private String code; // Identifiant de la configuration (concaténation des caractères du tableau)
	private Couleur joueur; // Joueur qui joue

	public Combinaison(char[][] tableau, Couleur joueur) {
		this.tableau = tableau;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tableau.length; i++) {
			for (int j = 0; j < tableau[i].length; j++) {
				sb.append(tableau[i][j]);
			}
		}
		this.code = sb.toString();
		this.joueur = joueur;
	}

	private Couleur getCouleurOfChar(char p) {
		switch (p) {
		case 'p':
			return Couleur.NOIR;
		case 'P':
			return Couleur.BLANC;
		default:
			return Couleur.ESPACE;
		}
	}

	public Couleur getCase(int ligne, int colonne) {
		return this.getCouleurOfChar(this.tableau[ligne][colonne]);
	}

	public int getNbLignes() {
		return this.tableau.length;
	}

	public int getNbColonnes() {
		return this.tableau[0].length;
	}

	/**
	 * Construit la liste des possibilités en un tour pour le joueur courant sur le
	 * plateau dans la combinaison courante.
	 * 
	 * @return la liste des combinaisons possibles après un tour joué par le joueur
	 *         courant.
	 */
	public List<Combinaison> calculerPossibilites() {
		List<Combinaison> possibilites = new LinkedList<Combinaison>();

		// SI [adversaire deja chez moi]
		for (int i = 0; i < this.getNbColonnes(); i++) {
			
			//TODO : refactor
			if (this.joueur == Couleur.BLANC) {
				if (this.getCase(this.getNbLignes() - 1, i) == Couleur.NOIR) {
					return possibilites;
				}
			}
			if (this.joueur == Couleur.NOIR) {
				if (this.getCase(0, i) == Couleur.BLANC) {
					return possibilites;
				}
			}
		}

		// POUR CHAQUE [case du plateau]
		for (int i = 0; i < this.getNbLignes(); i++) {
			for (int j = 0; j < this.getNbColonnes(); j++) {

				// SI [case courante est de la couleur du joueur] # sinon on s'en fiche
				if (this.getCase(i, j) == this.joueur) {

					// SI [case peut manger un ennemi à gauche]
					if (this.peutAttaquerAGauche(i, j)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneAGauche(i, j));
					}

					// SI [case peut manger un ennemi à droite]
					if (this.peutAttaquerADroite(i, j)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneADroite(i, j));
					}

					// SI [case peut avancer vers l'avant]
					if (this.peutAvancer(i, j)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneAvance(i, j));
					}
				}
			}
		}
		// RETOURNER [la liste des combinaisons possibles]
		return possibilites;
	}

	/**
	 * Vérifie si la case courante peut attaquer un ennemi en haut à droite
	 * 
	 * @param ligne   le numéro de ligne de la case courante
	 * @param colonne le numéro de colonne de la case courante
	 * @return true si la case courante peut attaquer en haut à droite, false sinon
	 */
	private boolean peutAttaquerADroite(int ligne, int colonne) {
		return this.estDansPlateau(ligne + this.joueur.getSens(), colonne + 1)
				&& this.getCase(ligne + this.joueur.getSens(), colonne + 1) == this.joueur.getOpposite();
	}

	/**
	 * Vérifie si la case courante peut attaquer un ennemi en haut à gauche
	 * 
	 * @param ligne   le numéro de ligne de la case courante
	 * @param colonne le numéro de colonne de la case courante
	 * @return true si la case courante peut attaquer en haut à gauche, false sinon
	 */
	private boolean peutAttaquerAGauche(int ligne, int colonne) {
		return this.estDansPlateau(ligne + this.joueur.getSens(), colonne - 1)
				&& this.getCase(ligne + this.joueur.getSens(), colonne - 1) == this.joueur.getOpposite();
	}

	/**
	 * Vérifie si le pion situé sur la case courante peut avancer vers l'avant (donc
	 * s'il y a une case vide devant lui)
	 * 
	 * @param ligne   le numéro de ligne de la case courante
	 * @param colonne le numéro de colonne de la case courante
	 * @return true si la case peut avancer, false sinon.
	 */
	private boolean peutAvancer(int ligne, int colonne) {
		return this.estDansPlateau(ligne + this.joueur.getSens(), colonne)
				&& this.getCase(ligne + this.joueur.getSens(), colonne) == Couleur.ESPACE;
	}

	/**
	 * Renvoie un clone de la combinaison courante après que la case courante ait
	 * mangé un ennemi en haut à droite.
	 * 
	 * @param ligne   le numéro de ligne de la case courante.
	 * @param colonne le numéro de colonne de la case courante.
	 * @return le clone de la combinaison courante après que la case courante ait
	 *         mangé un ennemi en haut à droite.
	 */
	private Combinaison cloneADroite(int ligne, int colonne) {
		char[][] copyTab = this.copyTab();

		copyTab[ligne][colonne] = Couleur.ESPACE.getCaractere();

		copyTab[ligne + this.joueur.getSens()][colonne + 1] = this.joueur.getCaractere();
		return new Combinaison(copyTab, this.joueur.getOpposite());
	}

	/**
	 * Renvoie un clone de la combinaison courante après que la case courante ait
	 * mangé un ennemi en haut à gauche.
	 * 
	 * @param ligne   le numéro de ligne de la case courante.
	 * @param colonne le numéro de colonne de la case courante.
	 * @return le clone de la combinaison courante après que la case courante ait
	 *         mangé un ennemi en haut à gauche.
	 */
	private Combinaison cloneAGauche(int ligne, int colonne) {
		char[][] copyTab = this.copyTab();

		copyTab[ligne][colonne] = Couleur.ESPACE.getCaractere();

		copyTab[ligne + this.joueur.getSens()][colonne - 1] = this.joueur.getCaractere();
		return new Combinaison(copyTab, this.joueur.getOpposite());
	}

	/**
	 * Renvoie un clone de la combinaison courante après que la case courante ait
	 * avancé vers l'avant.
	 * 
	 * @param ligne   le numéro de ligne de la case courante.
	 * @param colonne le numéro de colonne de la case courante.
	 * @return le clone de la combinaison courante après que la case courante ait
	 *         avancé vers l'avant.
	 */
	private Combinaison cloneAvance(int ligne, int colonne) {
		char[][] copyTab = this.copyTab();

		copyTab[ligne][colonne] = Couleur.ESPACE.getCaractere();

		copyTab[ligne + this.joueur.getSens()][colonne] = this.joueur.getCaractere();
		return new Combinaison(copyTab, this.joueur.getOpposite());
	}

	/**
	 * Copie la table de la combinaison courante
	 * 
	 * @return la table de la combinaison courante
	 */
	public char[][] copyTab() {
		char[][] clonage = new char[this.tableau.length][];
		for (int i = 0; i < this.tableau.length; i++) {
			clonage[i] = this.tableau[i].clone();
		}
		return clonage;
	}

	/**
	 * Vérifie si la case courante est située dans le plateau de jeu
	 * 
	 * @param ligne   le numéro de ligne de la case courante
	 * @param colonne le numéro de colonne de la case courante
	 * @return true si la case courante est située dans le plateau de jeu, false
	 *         sinon.
	 */
	private boolean estDansPlateau(int ligne, int colonne) {
		return (ligne >= 0 && ligne < this.tableau.length) && (colonne >= 0 && colonne < this.tableau[0].length);
	}

	// hashcode basé sur le code et le joueur courant
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((joueur == null) ? 0 : joueur.hashCode());
		return result;
	}

	// Equals basé sur le code et le joueur courant
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combinaison other = (Combinaison) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (joueur != other.joueur)
			return false;
		return true;
	}
	
//	########################################################################################################
//	########################################################################################################
//	########################################################################################################
//	########################################################################################################
//	########################################################################################################
//	Algorithmes
	
	public int trouverCoupsNaif() {
		// CONSTRUIRE tableau des combinaisons possibles après un tour
		List<Combinaison> possibilites = this.calculerPossibilites();

		// SI [aucune autre possibilité] (cas de base)
		if (possibilites.isEmpty()) {
			// RETOURNER 0
			return 0;
		}

		// VARIABLES UTILES
		boolean tousPositif = true;
		int maxNegatif = 0;
		int maxPositif = 0;

		// POUR CHAQUE combinaison possible
		for (Combinaison possibilite : possibilites) {
			// CALCULER valeur de la possibilité (récursivité)
			int valCombi = possibilite.trouverCoupsNaif();

			// SI [Tout positif]
			if (tousPositif) {
				// maxPositif = valCombi [SI valCombi est + grand]
				maxPositif = valCombi > maxPositif ? valCombi : maxPositif;

				// SI [valCombi est négatif] (je tombe pour la première fois sur un negatif)
				if (valCombi <= 0) {
					tousPositif = false;
					maxNegatif = valCombi;
				}
			}
			// SINON
			else {
				// maxNegatif = valCombi [SI valCombi est négatif et + grand que maxNegatif]
				maxNegatif = valCombi <= 0 && valCombi > maxNegatif ? valCombi : maxNegatif;
			}
		}

		// SI [tout le monde est positif] (je vais gagner)
		if (tousPositif) {
			// RETOURNER - (maxPositif +1)
			return -(maxPositif + 1); // application de la formule naïve pour tous positif
		}
		// SINON (je vais perdre)
		else {
			return Math.abs(maxNegatif) + 1;
		}
	}

	/**
	 * 
	 * @param combinaison
	 * @param joueur
	 * @return
	 */
	public int trouverCoupsMemo(Map<Combinaison, Integer> memo) {
		// CONSTRUIRE tableau des combinaisons possibles après un tour
		List<Combinaison> possibilites = this.calculerPossibilites();

		// SI [aucune autre possibilité] (cas de base)
		if (possibilites.isEmpty()) {
			// RETOURNER 0
			return 0;
		}

		// VARIABLES UTILES
		boolean tousPositif = true;
		int maxNegatif = 0;
		int maxPositif = 0;

		// POUR CHAQUE combinaison possible
		for (Combinaison possibilite : possibilites) {

			// CALCULER valeur de la possibilité (récursivité)
			int valCombi = 0;
			if (memo.containsKey(possibilite)) {
				valCombi = memo.get(possibilite);
			} else {
				valCombi = possibilite.trouverCoupsMemo(memo);
				memo.put(possibilite, valCombi);
			}

			// SI [Tout positif]
			if (tousPositif) {
				// maxPositif = valCombi [SI valCombi est + grand]
				maxPositif = valCombi > maxPositif ? valCombi : maxPositif;

				// SI [valCombi est négatif] (je tombe pour la première fois sur un negatif)
				if (valCombi <= 0) {
					tousPositif = false;
					maxNegatif = valCombi;
				}
			}
			// SINON
			else {
				// maxNegatif = valCombi [SI valCombi est négatif et + grand que maxNegatif]
				maxNegatif = valCombi <= 0 && valCombi > maxNegatif ? valCombi : maxNegatif;
			}
		}

		// SI [tout le monde est positif] (je vais gagner)
		if (tousPositif) {
			// RETOURNER - (maxPositif +1)
			return -(maxPositif + 1); // application de la formule naïve pour tous positif
		}
		// SINON (je vais perdre)
		else {
			return Math.abs(maxNegatif) + 1;
		}
	}
}
