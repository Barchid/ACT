import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class CertificatTSP implements Certificat {

	// tableau dont les éléments sont les numéros des villes
	// (représente la permutation entre les villes)
	private int[] tableau;

	// instance du probleme de decision
	private PblTSP probleme;

	public CertificatTSP(PblTSP probleme) {
		super();
		this.probleme = probleme;
		this.tableau = new int[probleme.getN()];
	}

	@Override
	public void saisie() {
		Scanner scanner = new Scanner(System.in);
		Set<Integer> used = new HashSet<Integer>(); // ensemble des numéros de ville déjà utilisés
		
		// POUR CHAQUE element du certificat
		for (int i = 0; i < this.tableau.length; i++) {
			// scanner un numéro de ville valide
			System.out.println("Entrez un numéro de ville :");
			int num = scanner.nextInt();
			while(num < 0 || num > this.tableau.length || used.contains(num)) {
				System.out.println("Numéro de ville incorrecte.\nEntrez un numéro de ville :");
				num = scanner.nextInt();
			}
			this.tableau[i] = num;
			used.add(num);
		}
		scanner.close();
	}

	@Override
	public void display() {
		System.out.println(Arrays.toString(this.tableau));
	}

	@Override
	public void alea() {
		Random r = new Random();
		
		// CREER une liste des entiers a utiliser
		List<Integer> toUse = new ArrayList<Integer>();
		for(int i=0;i<this.tableau.length;i++) {
			toUse.add(i);
		}
		
		// POUR CHAQUE element du tableau
		for (int i = 0; i < this.tableau.length; i++) {
			
			// TROUVER un nombre aleatoire non utilise
			int aleatoire = r.nextInt(toUse.size());

			// AJOUTER l'aleatoire dans le tableau
			this.tableau[i] = toUse.get(aleatoire);
			
			toUse.remove(aleatoire);
		}
	}
	
	@Override
	public void reset() {
		// Le premier element de l'ordre choisi est toujours compose des elements de 0 a
		// n-1 trie par ordre croissant
		for (int i = 0; i < this.tableau.length; i++) {
			this.tableau[i] = i;
		}
	}

	@Override
	public boolean estDernier() {
		// Pour un tableau de 5 elements, notre dernier element selon l'ordre choisi
		// serait : [4,3,2,1,0]
		// Il ne reste qu'a comparer que les elements de 0 a n-1 sont bien tries en
		// ordre decroissant
		int j = 0;
		for (int i = this.tableau.length - 1; i >= 0; i++) {
			if (j != this.tableau[i]) {
				return false;
			}
			j++;
		}
		return true;
	}

	@Override
	public void suivant() {
		this.tableau = this.genererCertificatSuivant();
	}

	/**
	 * Trouve l'index minimum d'un tableau entre les bornes inf et sup. Le minimum
	 * est superieur a value
	 * 
	 * @param array
	 * @param value
	 * @param inf
	 * @param sup
	 * @return
	 */
	private int minArray(int[] array, int value, int inf, int sup) {
		int indexMin = inf;
		for (int i = inf + 1; i < sup; i++) {
			if (array[indexMin] > array[i] && array[i] > value) {
				indexMin = i;
			}
		}

		return indexMin;
	}

	/**
	 * Permute les elements aux indices x et y dans la table
	 * 
	 * @param array
	 * @param x
	 * @param y
	 */
	private void permutation(int[] array, int x, int y) {
		int temp = array[x];
		array[x] = array[y];
		array[y] = temp;
	}

	/**
	 * @param array
	 * @param inf
	 * @param sup
	 */
	private void reverseBetween(int[] array, int inf, int sup) {
		while (inf < sup) {
			int temp = array[inf];
			array[inf] = array[sup];
			array[sup] = temp;
			inf++;
			sup--;
		}
	}

	/**
	 * Genere la valeur suivante du certificat sur base de l'ordre choisi
	 * 
	 * @return la valeur suivante du certificat sur base ou null s'il n'y en a plus
	 */
	private int[] genererCertificatSuivant() {
		int[] certificat = Arrays.copyOf(this.tableau, this.tableau.length);
		for (int i = certificat.length - 1; i > 0; i--) {

			// SI certificat n'est pas croissant
			if (certificat[i] > certificat[i - 1]) {
				// TROUVER l'index minimum dans certif[i ... n-1] et > element qui decroît
				int min = minArray(certificat, certificat[i - 1], i, certificat.length - 1);

				// PERMUTER min et i - 1
				permutation(certificat, i - 1, min);

				// INVERSER certificat[i .. n-1]
				reverseBetween(certificat, i, certificat.length - 1);

				// RETOURNER certificat
				return certificat;
			}
		}
		// Tous les certificats ont ete generes selon l'ordre defini
		return null;
	}

	@Override
	public boolean estCorrect() {
		// CALCULER la longueur des distances entre les villes
		int[][] matrice = this.probleme.getMatrice();
		int n = this.probleme.getN();
		// D(tour[n-1], tour[0])
		int sum = matrice[this.tableau[n - 1]][this.tableau[0]];

		// SOMME i=0 -> n-2 D(tour[i], tour[i+1])
		for (int i = 0; i < n - 2; i++) {
			sum += matrice[this.tableau[i]][this.tableau[i + 1]];
		}

		// verifier que la somme des distanceq du certificat est <= à la longueur
		// maximale autorisee
		return sum <= this.probleme.getL();
	}
}