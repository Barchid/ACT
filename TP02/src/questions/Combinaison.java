package questions;

import java.util.LinkedList;
import java.util.List;

public class Combinaison implements Cloneable {

	private char[][] tableau;

	public Combinaison(char[][] tableau) {
		this.tableau = tableau;
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
	 * @param joueur la couleur du joueur courant (qui va jouer le tour)
	 * @return la liste des combinaisons possibles après un tour joué par le joueur
	 *         courant.
	 */
	public List<Combinaison> calculerPossibilites(Couleur joueur) {
		List<Combinaison> possibilites = new LinkedList<Combinaison>();

		// POUR CHAQUE [case du plateau]
		for (int i = 0; i < this.getNbLignes(); i++) {
			for (int j = 0; j < this.getNbColonnes(); j++) {

				// SI [case courante est de la couleur du joueur] # sinon on s'en fiche
				if (this.getCase(i, j) == joueur) {

					// SI [case peut manger un ennemi à gauche]
					if (this.peutAttaquerAGauche(i, j, joueur)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneAGauche(i, j, joueur));
					}

					// SI [case peut manger un ennemi à droite]
					if (this.peutAttaquerADroite(i, j, joueur)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneADroite(i, j, joueur));
					}

					// SI [case peut avancer vers l'avant]
					if (this.peutAvancer(i, j, joueur)) {
						// AJOUTER combinaison
						possibilites.add(this.cloneAvance(i, j, joueur));
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
	 * @param joueur  la couleur de la case courante
	 * @return true si la case courante peut attaquer en haut à droite, false sinon
	 */
	private boolean peutAttaquerADroite(int ligne, int colonne, Couleur joueur) {
		if (joueur == Couleur.BLANC) {
			return this.estDansPlateau(ligne + 1, colonne + 1) && this.getCase(ligne + 1, colonne + 1) == Couleur.NOIR;
		} else {
			return this.estDansPlateau(ligne - 1, colonne + 1) && this.getCase(ligne - 1, colonne + 1) == Couleur.BLANC;
		}
	}

	/**
	 * Vérifie si la case courante peut attaquer un ennemi en haut à gauche
	 * 
	 * @param ligne   le numéro de ligne de la case courante
	 * @param colonne le numéro de colonne de la case courante
	 * @param joueur  la couleur de la case courante
	 * @return true si la case courante peut attaquer en haut à gauche, false sinon
	 */
	private boolean peutAttaquerAGauche(int ligne, int colonne, Couleur joueur) {
		if (joueur == Couleur.BLANC) {
			return this.estDansPlateau(ligne + 1, colonne - 1) && this.getCase(ligne + 1, colonne - 1) == Couleur.NOIR;
		} else {
			return this.estDansPlateau(ligne - 1, colonne - 1) && this.getCase(ligne - 1, colonne - 1) == Couleur.BLANC;
		}
	}

	/**
	 * Vérifie si le pion situé sur la case courante peut avancer vers l'avant (donc
	 * s'il y a une case vide devant lui)
	 * 
	 * @param ligne   le numéro de ligne de la case courante
	 * @param colonne le numéro de colonne de la case courante
	 * @param joueur  la couleur de la case courante
	 * @return true si la case peut avancer, false sinon.
	 */
	private boolean peutAvancer(int ligne, int colonne, Couleur joueur) {
		if (joueur == Couleur.BLANC) {
			return this.estDansPlateau(ligne + 1, colonne) && this.getCase(ligne + 1, colonne) == Couleur.ESPACE;
		} else {
			return this.estDansPlateau(ligne - 1, colonne) && this.getCase(ligne - 1, colonne) == Couleur.ESPACE;
		}
	}

	/**
	 * Renvoie un clone de la combinaison courante après que la case courante ait
	 * mangé un ennemi en haut à droite.
	 * 
	 * @param ligne   le numéro de ligne de la case courante.
	 * @param colonne le numéro de colonne de la case courante.
	 * @param joueur  la couleur du joueur courant.
	 * @return le clone de la combinaison courante après que la case courante ait
	 *         mangé un ennemi en haut à droite.
	 */
	private Combinaison cloneADroite(int ligne, int colonne, Couleur joueur) {
		Combinaison clone = this.clone();

		clone.tableau[ligne][colonne] = Couleur.ESPACE.getCaractere();

		if (joueur == Couleur.BLANC) {
			clone.tableau[ligne + 1][colonne + 1] = joueur.getCaractere();
		} else {
			clone.tableau[ligne - 1][colonne + 1] = joueur.getCaractere();
		}

		return clone;
	}

	/**
	 * Renvoie un clone de la combinaison courante après que la case courante ait
	 * mangé un ennemi en haut à gauche.
	 * 
	 * @param ligne   le numéro de ligne de la case courante.
	 * @param colonne le numéro de colonne de la case courante.
	 * @param joueur  la couleur du joueur courant.
	 * @return le clone de la combinaison courante après que la case courante ait
	 *         mangé un ennemi en haut à gauche.
	 */
	private Combinaison cloneAGauche(int ligne, int colonne, Couleur joueur) {
		Combinaison clone = this.clone();

		clone.tableau[ligne][colonne] = Couleur.ESPACE.getCaractere();

		if (joueur == Couleur.BLANC) {
			clone.tableau[ligne + 1][colonne - 1] = joueur.getCaractere();
		} else {
			clone.tableau[ligne - 1][colonne - 1] = joueur.getCaractere();
		}

		return clone;
	}

	/**
	 * Renvoie un clone de la combinaison courante après que la case courante ait
	 * avancé vers l'avant.
	 * 
	 * @param ligne   le numéro de ligne de la case courante.
	 * @param colonne le numéro de colonne de la case courante.
	 * @param joueur  la couleur du joueur courant.
	 * @return le clone de la combinaison courante après que la case courante ait
	 *         avancé vers l'avant.
	 */
	private Combinaison cloneAvance(int ligne, int colonne, Couleur joueur) {
		Combinaison clone = this.clone();

		clone.tableau[ligne][colonne] = Couleur.ESPACE.getCaractere();

		if (joueur == Couleur.BLANC) {
			clone.tableau[ligne + 1][colonne] = joueur.getCaractere();
		} else {
			clone.tableau[ligne - 1][colonne] = joueur.getCaractere();
		}

		return clone;
	}

	@Override
	public Combinaison clone() {
		char[][] clonage = new char[this.tableau.length][];
		for (int i = 0; i < this.tableau.length; i++) {
			clonage[i] = this.tableau[i].clone();
		}
		return new Combinaison(clonage);
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
}
