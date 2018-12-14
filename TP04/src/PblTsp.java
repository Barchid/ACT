
public class PblTsp {
	private int[][] matrice;
	private int n;
	
	public PblTsp(int[][] matrice, int n) {
		super();
		this.matrice = matrice;
		this.n = n;
	}

	public int[][] getMatrice() {
		return matrice;
	}

	public int getN() {
		return n;
	}

	public void setMatrice(int[][] matrice) {
		this.matrice = matrice;
	}

	public void setN(int n) {
		this.n = n;
	}
}
