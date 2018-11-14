import java.util.Arrays;

public class Main {

	/**
	 * Trouve l'index minimum d'un tableau entre les bornes inf et sup. Le minimum
	 * est supérieur à value
	 * 
	 * @param array
	 * @param value
	 * @param inf
	 * @param sup
	 * @return
	 */
	private static int minArray(int[] array, int value, int inf, int sup) {
		int indexMin = inf;
		for (int i = inf + 1; i < sup; i++) {
			if (array[indexMin] > array[i] && array[i] > value) {
				indexMin = i;
			}
		}

		return indexMin;
	}

	/**
	 * Permute array
	 * 
	 * @param array
	 * @param place1
	 * @param place2
	 */
	private static void permutation(int[] array, int place1, int place2) {
		int temp = array[place1];
		array[place1] = array[place2];
		array[place2] = temp;
	}

	/**
	 * @param array
	 * @param inf
	 * @param sup
	 */
	private static void reverseBetween(int[] array, int inf, int sup) {
		while (inf < sup) {
			int temp = array[inf];
			array[inf] = array[sup];
			array[sup] = temp;
			inf++;
			sup--;
		}
	}

	/**
	 * 
	 * @param oldCertificate
	 * @return
	 */
	public static int[] generateNewCertificate(int[] oldCertificate) {
		int[] newCertificate = Arrays.copyOf(oldCertificate, oldCertificate.length);
		for (int i = newCertificate.length - 1; i > 0; i--) {

			// SI certificat n'est pas croissant
			if (newCertificate[i] > newCertificate[i - 1]) {
				// TROUVER l'index minimum dans certif[i ... n-1] et > élément qui décroît
				int min = minArray(newCertificate, newCertificate[i - 1], i, newCertificate.length - 1);

				// PERMUTER min et i - 1
				permutation(newCertificate, i - 1, min);

				// INVERSER certificat[i .. n-1]
				reverseBetween(newCertificate, i, newCertificate.length - 1);

				// RETOURNER certificat
				return newCertificate;
			}
		}
		// Tous les certificats ont été généré selon l'ordre défini
		return null;
	}

	public static void main(String[] args) {
		int[] certif = { 1, 2, 5, 4, 3, 0 };
		certif = generateNewCertificate(certif);
		System.out.println(Arrays.toString(certif));
	}

}
