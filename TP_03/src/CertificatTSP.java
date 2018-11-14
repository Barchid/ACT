import java.util.Arrays;

class CertificatTSP implements Certificat {
	
	private int[] tableau;
	
	public CertificatTSP(int[] tableau) {
		super();
		this.tableau = tableau;
	}

	@Override
	public void saisie() {
				
	}

	@Override
	public void display() {
		System.out.println(Arrays.toString(this.tableau));
	}

	@Override
	public void alea() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean estDernier() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void suivant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean estCorrect() {
		// TODO Auto-generated method stub
		return false;
	}
}