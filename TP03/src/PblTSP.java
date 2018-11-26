
public class PblTSP extends PblDecision {

	private int n; // nombre de villes
	private int[][] matrice; // matrice des distances entre les villes
	private int l; // longueur maximale autorisée

	private CertificatTSP certifCorrect; // ce champs est rempli quand on trouve un certificat ok pour le probleme (en
											// lancant aUneSolution()

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
		
		while (!certificat.estDernier()) {
			if (certificat.estCorrect()) {
				this.certifCorrect = certificat;
				return true;
			}
			certificat.suivant();
		}
		
		// VERIFIER POUR LE DERNIER CERTIF
		if(certificat.estCorrect()) {
			this.certifCorrect = certificat;
			return true;
		}
		
		// FAUX si on ne trouve rien
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

	public CertificatTSP getCertifCorrect() {
		return certifCorrect;
	}
}
