package questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

	private static final String[] fichiers = { "Config3x4_1", "Config3x4_minus2", "Config4x4_0", "Config4x4_11",
			"Config5x5_15", "Config5x5_3", "Config5x5_minus2" };

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
				if(i == plateau.length) {
					break;
				}
				
				char[] ligne = new char[nbColonnes];
				int j = 0;
				for(;j<line.length();j++) {
					ligne[j] = line.charAt(j);
				}
				// Fichier encore mal fichu...
				for(;j<nbColonnes;j++) {
					ligne[j] = ' ';
				}
				
				plateau[i] = ligne;
				i++;
			}
			
			Combinaison initiale = new Combinaison(plateau);

			// Mesurer le temps et appliquer l'algo
			long depart = System.currentTimeMillis();
			int resultat = trouverCoupsNaif(initiale, Couleur.BLANC);
			long fin = System.currentTimeMillis();

			// Affichage résultat
			System.out.println(nomFichier + ": configuration = " + resultat + " found in " + (fin - depart) + "ms");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	private static int trouverCoupsNaif(Combinaison combinaison, Couleur joueur) {
		// CONSTRUIRE tableau des combinaisons possibles après un tour
		List<Combinaison> possibilites = combinaison.calculerPossibilites(joueur);

		// SI [aucune autre possibilité] (cas de base)
		if (possibilites.isEmpty()) {
			// RETOURNER 0
			return 0;
		}

		// VARIABLES UTILES
		boolean tousPositif = true;
		int maxNegatif = 0;
		int maxPositif = 0;
		Couleur adversaire = joueur == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC;

		// POUR CHAQUE combinaison possible
		for (Combinaison possibilite : possibilites) {
			// CALCULER valeur de la possibilité (récursivité)
			int valCombi = trouverCoupsNaif(possibilite, adversaire);

			// SI [Tout positif]
			if (tousPositif) {
				// maxPositif = valCombi [SI valCombi est + grand]
				maxPositif = valCombi > maxPositif ? valCombi : maxPositif;

				// SI [valCombi est négatif] (je tombe pour la première fois sur un negatif)
				if (valCombi < 0) {
					tousPositif = false;
					maxNegatif = valCombi;
				}
			}
			// SINON
			else {
				// maxNegatif = valCombi [SI valCombi est négatif et + grand que maxNegatif]
				maxNegatif = valCombi < 0 && valCombi > maxNegatif ? valCombi : maxNegatif;
			}
		}

		// SI [tout le monde est positif] (je vais gagner)
		if (tousPositif) {
			// RETOURNER - (maxPositif +1)
			return -(maxPositif + 1); // application de la formule naïve pour tous positif
		}
		// SINON (je vais perdre)
		else {
			return Math.abs(maxNegatif) + 1;
		}
	}
}
