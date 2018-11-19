
public class PblTSP extends PblDecision {

	private int n; // nombre de villes
	private int[][] matrice; // matrice des distances entre les villes
	private int l; // longueur maximale autorisée

	public PblTSP(int n, int[][] matrice, int l) {
		super();
		this.n = n;
		this.matrice = matrice;
		this.l = l;
	}

	@Override
	public boolean aUneSolution() {
		CertificatTSP certificat = new CertificatTSP(this);
		certificat.reset();
		while (certificat != null) {
			if (certificat.estCorrect()) {
				return true;
			}
			certificat.suivant();
		}
		return false;
	}

	public int getN() {
		return n;
	}

	public int[][] getMatrice() {
		return matrice;
	}

	public int getL() {
		return l;
	}
}
