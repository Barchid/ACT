public interface Certificat {
	// saisie au clavier de la valeur du certificat
	public void saisie();

	// affichage du certificat
	public void display();

	// modification aleatoire de la valeur du certificat
	// chaque valeur possible doit pouvoir etre generee
	public void alea();

	// on munira les valeurs possibles du certificat pour une instance d’un ordre
	// total pour l’enumeratio
	// affecte au certificat la premiere valeur pour l’ordre choisi
	public void reset();

	// retourne vrai si la valeur est la derniere dans l’ordre choisi, faux sinon
	public boolean estDernier();

	// modifie la valeur du certificat en la suivante pour l’ordre, pas defini si la
	// certificat est le de
	public void suivant();

	// retourne True Ssi le certificat est correct pour l’instance du pb associée
	// remarque: un certificat est donc ici associé toujours a une instance du pb
	public boolean estCorrect();
}