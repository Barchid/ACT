package questions;

public enum Couleur {
	BLANC('P'),
	NOIR('p'), 
	ESPACE(' ');
	
	private char caractere;
	
	private Couleur(char caractere) {
		this.caractere = caractere;
	}
	
	public char getCaractere() {
		return this.caractere;
	}
}
