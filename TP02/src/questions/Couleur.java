package questions;

public enum Couleur {
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
	 * Retourne la couleur opposée à la couleur courante.
	 * @return la couleur opposée à la couleur courante
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
