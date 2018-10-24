package test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

enum Couleur {
	BLANC('P', -1), NOIR('p', 1), ESPACE(' ', 0);

	private char caractere;
	private int sens;

	private Couleur(char caractere, int sens) {
		this.caractere = caractere;
		this.sens = sens;
	}

	public char getCaractere() {
		return this.caractere;
	}

	public int getSens() {
		return sens;
	}

	/**
	 * Retourne la couleur opposee a la couleur courante.
	 * 
	 * @return la couleur opposee a la couleur courante
	 */
	public Couleur getOpposite() {
		switch (this) {
		case BLANC:
			return NOIR;
		case NOIR:
			return BLANC;
		default:
			return ESPACE;
		}
	}
}

class Combinaison {

	private char[][] tableau; // Tableau de caracteres
	private String code; // Identifiant de la configuration (concatenation des caracteres du tableau)
	private Couleur joueur; // Joueur qui joue

	public Combinaison(char[][] tableau, Couleur joueur) {
		this.tableau = tableau;

		// Construction du code de la configuration
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tableau.length; i++) {
			for (int j = 0; j < tableau[i].length; j++) {
				sb.append(tableau[i][j]);
			}
		}
		this.code = sb.toString();
		this.joueur = joueur;
	}

	/**
	 * Retourne la couleur adequate suivant le caractere specifie.
	 * 
	 * @param p le caractere specifie
	 * @return la couleur adequate
	 */
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
	 * Construit la liste des possibilites en un tour pour le joueur courant sur le
	 * plateau dans la combinaison courante.
	 * 
	 * @return la liste des combinaisons possibles apres un tour joue par le joueur
	 *         courant.
	 */
	public List<Combinaison> calculerPossibilites() {
		List<Combinaison> possibilites = new LinkedList<Combinaison>();

		// SI [adversaire deja chez moi]
		for (int i = 0; i < this.getNbColonnes(); i++) {

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

				// SI [case courante est de la couleur du joueur] # sinon on ignore
				if (this.getCase(i, j) == this.joueur) {

					// SI [case peut manger un ennemi a gauche]
					if (this.peutAttaquerAGauche(i, j)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneAGauche(i, j));
					}

					// SI [case peut manger un ennemi a droite]
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
	 * Verifie si la case courante peut attaquer un ennemi en haut a droite
	 * 
	 * @param ligne   le numero de ligne de la case courante
	 * @param colonne le numero de colonne de la case courante
	 * @return true si la case courante peut attaquer en haut a droite, false sinon
	 */
	private boolean peutAttaquerADroite(int ligne, int colonne) {
		return this.estDansPlateau(ligne + this.joueur.getSens(), colonne + 1)
				&& this.getCase(ligne + this.joueur.getSens(), colonne + 1) == this.joueur.getOpposite();
	}

	/**
	 * Verifie si la case courante peut attaquer un ennemi en haut a gauche
	 * 
	 * @param ligne   le numero de ligne de la case courante
	 * @param colonne le numero de colonne de la case courante
	 * @return true si la case courante peut attaquer en haut a gauche, false sinon
	 */
	private boolean peutAttaquerAGauche(int ligne, int colonne) {
		return this.estDansPlateau(ligne + this.joueur.getSens(), colonne - 1)
				&& this.getCase(ligne + this.joueur.getSens(), colonne - 1) == this.joueur.getOpposite();
	}

	/**
	 * Verifie si le pion situe sur la case courante peut avancer vers l'avant (donc
	 * s'il y a une case vide devant lui)
	 * 
	 * @param ligne   le numero de ligne de la case courante
	 * @param colonne le numero de colonne de la case courante
	 * @return true si la case peut avancer, false sinon.
	 */
	private boolean peutAvancer(int ligne, int colonne) {
		return this.estDansPlateau(ligne + this.joueur.getSens(), colonne)
				&& this.getCase(ligne + this.joueur.getSens(), colonne) == Couleur.ESPACE;
	}

	/**
	 * Renvoie un clone de la combinaison courante apres que la case courante ait
	 * mange un ennemi en haut a droite.
	 * 
	 * @param ligne   le numero de ligne de la case courante.
	 * @param colonne le numero de colonne de la case courante.
	 * @return le clone de la combinaison courante apres que la case courante ait
	 *         mange un ennemi en haut a droite.
	 */
	private Combinaison cloneADroite(int ligne, int colonne) {
		char[][] copyTab = this.copyTab();

		copyTab[ligne][colonne] = Couleur.ESPACE.getCaractere();

		copyTab[ligne + this.joueur.getSens()][colonne + 1] = this.joueur.getCaractere();
		return new Combinaison(copyTab, this.joueur.getOpposite());
	}

	/**
	 * Renvoie un clone de la combinaison courante apres que la case courante ait
	 * mange un ennemi en haut a gauche.
	 * 
	 * @param ligne   le numero de ligne de la case courante.
	 * @param colonne le numero de colonne de la case courante.
	 * @return le clone de la combinaison courante apres que la case courante ait
	 *         mange un ennemi en haut a gauche.
	 */
	private Combinaison cloneAGauche(int ligne, int colonne) {
		char[][] copyTab = this.copyTab();

		copyTab[ligne][colonne] = Couleur.ESPACE.getCaractere();

		copyTab[ligne + this.joueur.getSens()][colonne - 1] = this.joueur.getCaractere();
		return new Combinaison(copyTab, this.joueur.getOpposite());
	}

	/**
	 * Renvoie un clone de la combinaison courante apres que la case courante ait
	 * avance vers l'avant.
	 * 
	 * @param ligne   le numero de ligne de la case courante.
	 * @param colonne le numero de colonne de la case courante.
	 * @return le clone de la combinaison courante apres que la case courante ait
	 *         avance vers l'avant.
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
	 * Verifie si la case courante est situee dans le plateau de jeu
	 * 
	 * @param ligne   le numero de ligne de la case courante
	 * @param colonne le numero de colonne de la case courante
	 * @return true si la case courante est situee dans le plateau de jeu, false
	 *         sinon.
	 */
	private boolean estDansPlateau(int ligne, int colonne) {
		return (ligne >= 0 && ligne < this.tableau.length) && (colonne >= 0 && colonne < this.tableau[0].length);
	}

	public int hashCode() {
		// hashcode base sur le code et le joueur courant
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((joueur == null) ? 0 : joueur.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		// Equals base sur le code et le joueur courant
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

	// ----------------------------------------------------------------------
	// Algorithmes
	// ----------------------------------------------------------------------

	public int trouverCoupsNaif() {
		// CONSTRUIRE tableau des combinaisons possibles apres un tour
		List<Combinaison> possibilites = this.calculerPossibilites();

		// SI [aucune autre possibilite] (cas de base)
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
			// CALCULER valeur de la possibilite (recursivite)
			int valCombi = possibilite.trouverCoupsNaif();

			// SI [Tout positif]
			if (tousPositif) {
				// maxPositif = valCombi [SI valCombi est + grand]
				maxPositif = valCombi > maxPositif ? valCombi : maxPositif;

				// SI [valCombi est negatif] (je tombe pour la premiere fois sur un negatif)
				if (valCombi <= 0) {
					tousPositif = false;
					maxNegatif = valCombi;
				}
			}
			// SINON
			else {
				// maxNegatif = valCombi [SI valCombi est negatif et + grand que maxNegatif]
				maxNegatif = valCombi <= 0 && valCombi > maxNegatif ? valCombi : maxNegatif;
			}
		}

		// SI [tout le monde est positif] (je vais gagner)
		if (tousPositif) {
			// RETOURNER - (maxPositif +1)
			return -(maxPositif + 1); // application de la formule naive pour tous positif
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
		// CONSTRUIRE tableau des combinaisons possibles apres un tour
		List<Combinaison> possibilites = this.calculerPossibilites();

		// SI [aucune autre possibilite] (cas de base)
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

			// CALCULER valeur de la possibilite (recursivite)
			int valCombi = 0;

			// SI [la combinaison possible a deja ete calculee]
			if (memo.containsKey(possibilite)) {
				valCombi = memo.get(possibilite);
			}
			// SINON
			else {
				valCombi = possibilite.trouverCoupsMemo(memo);
				memo.put(possibilite, valCombi); // AJOUTER le resultat dans le dictionnaire
			}

			// SI [Tout positif]
			if (tousPositif) {
				// maxPositif = valCombi [SI valCombi est + grand]
				maxPositif = valCombi > maxPositif ? valCombi : maxPositif;

				// SI [valCombi est negatif] (je tombe pour la premiere fois sur un negatif)
				if (valCombi <= 0) {
					tousPositif = false;
					maxNegatif = valCombi;
				}
			}
			// SINON
			else {
				// maxNegatif = valCombi [SI valCombi est negatif et + grand que maxNegatif]
				maxNegatif = valCombi <= 0 && valCombi > maxNegatif ? valCombi : maxNegatif;
			}
		}

		// SI [tout le monde est positif] (joueur courant gagne)
		if (tousPositif) {
			// RETOURNER - (maxPositif +1)
			return -(maxPositif + 1); // application de la formule naive pour tous positif
		}
		// SINON (joueur courant perd)
		else {
			return Math.abs(maxNegatif) + 1;
		}
	}
}

/*
 * Test 1 Succès (0.104s) 
 * Test 2 Succès (0.1s) 
 * Test 3 Succès (0.136s) 
 * Test 4 Succès (0.18s) 
 * Test 5 Succès (0.176s) 
 * Test 6 Succès (2.176s) 
 * Test 7 Succès (4.164s) 
 * Test 8 Succès (0.124s)
 */
class Test {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			int lignes = Integer.parseInt(sc.nextLine().trim());
			int colonnes = Integer.parseInt(sc.nextLine().trim());
			char[][] combi = new char[lignes][colonnes];

			int i = 0;

			while (sc.hasNextLine() && i < lignes) {
				String line = sc.nextLine();

				char[] ligne = new char[colonnes];
				int j = 0;
				for (; j < line.length() && j < colonnes; j++)
					ligne[j] = line.charAt(j);

				for (; j < colonnes; j++)
					ligne[j] = ' ';

				combi[i] = ligne;
				i++;
			}

			Combinaison initiale = new Combinaison(combi, Couleur.BLANC);
			Map<Combinaison, Integer> memo = new HashMap<Combinaison, Integer>();

			// Affichage resultat
			System.out.println(initiale.trouverCoupsMemo(memo));
		} finally {
			sc.close();
		}
	}
}