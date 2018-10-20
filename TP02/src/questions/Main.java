package questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static final String[] fichiers = { "Config3x4_1", "Config3x4_minus2", "Config4x4_0", "Config4x4_11",
			"Config5x5_3", "Config5x5_minus2", "Config5X5_15" };

	public static void main(String[] args) {
		for (String fichier : fichiers) {
			appliquerAlgoSurFichier(fichier);
		}
	}

	/**
	 * Appliquer l'algorithme sur le fichier en paramètre
	 * 
	 * @param nomFichier le nom du fichier sur lequel appliquer l'algorithme
	 */
	private static void appliquerAlgoSurFichier(String nomFichier) {
		try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
			// nombre de lignes en premiere ligne de fichier
			int nbLignes = Integer.parseInt(br.readLine());
			int nbColonnes = Integer.parseInt(br.readLine());

			// initialiser le plateau
			char[][] plateau = new char[nbLignes][];
			int i = 0;

			for (String line; (line = br.readLine()) != null;) {
				// Parce que le fichier est mal fichu...
				if (i == plateau.length) {
					break;
				}

				char[] ligne = new char[nbColonnes];
				int j = 0;
				for (; j < line.length(); j++) {
					ligne[j] = line.charAt(j);
				}
				// Fichier encore mal fichu...
				for (; j < nbColonnes; j++) {
					ligne[j] = ' ';
				}

				plateau[i] = ligne;
				i++;
			}

			Combinaison initiale = new Combinaison(plateau, Couleur.BLANC);
			Map<Combinaison, Integer> memo = new HashMap<Combinaison, Integer>();
			// Mesurer le temps et appliquer l'algo
			long depart = System.currentTimeMillis();
//			int resultat = initiale.trouverCoupsNaif();
			int resultat = initiale.trouverCoupsMemo(memo);
			long fin = System.currentTimeMillis();

			// Affichage résultat
			System.out.println(nomFichier + ": configuration = " + resultat + " found in " + (fin - depart) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
