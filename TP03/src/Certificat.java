public interface Certificat {
	/**
	 * Saisie au clavier de la valeur du certificat
	 */
	public void saisie();

	/**
	 * Affichage du certificat
	 */
	public void display();

	/**
	 * Modification aléatoire de la valeur du certificat
	 * Chaque valeur doit être équiprobable
	 */
	public void alea();

	// on munira les valeurs possibles du certificat pour une instance d’un ordre
	// total pour l’enumeratio
	// affecte au certificat la premiere valeur pour l’ordre choisi
	/**
	 * Affecte au certificat la première valeur pour l'ordre choisi
	 */
	public void reset();

	// retourne vrai si la valeur est la derniere dans l’ordre choisi, faux sinon
	/**
	 * Vérifie si la valeur du certificat est la dernière dans l'ordre choisi
	 * @return true si la valeur du certificat est la dernière pour l'ordre choisi, false sinon
	 */
	public boolean estDernier();

	// modifie la valeur du certificat en la suivante pour l’ordre, pas defini si la
	// certificat est le de
	/**
	 * Modifie la valeur du certificat en la suivante pour l'ordre choisi.
	 */
	public void suivant();

	// retourne True Ssi le certificat est correct pour l’instance du pb associée
	// remarque: un certificat est donc ici associé toujours a une instance du pb
	/**
	 * Vérifie si la valeur du certificat est correcte pour l'instance du problème associé.
	 * @return true si la valeur du certificat est correcte pour l'instance du problème associé.
	 */
	public boolean estCorrect();
}