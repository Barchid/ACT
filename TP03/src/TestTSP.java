import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestTSP {
	public static void main(String[] arg) throws Exception {
		if (arg.length < 3)
			System.out.println("java test mode file.atsp lg");
		else {
			int lg = Integer.parseInt(arg[2]); // la longueur maximale est le dernier argument

			// lire l’instance du probleme dans le fichier de donnees dont le nom est en
			// argument et créer l’instance du probleme...
			PblTSP problem = lireFichier(arg[1], lg);
		}
		// les differents modes
		if (arg[0].equals("-verif")) {
			// lire un cetificat proposé, sortir le résultat de la vérification
			
		} else if (arg[0].equals("-nondet")) {
			// générer aléatoirement un certificat
			// sortir le résultat de la vérification et evt le certificat
		} else if (arg[0].equals("-exhaust")) {
			// générer tous les certificats jusqu’au dernier ou jusqu’à un trouver un de
			// valide
			// sortir le résultat et evt le certificat valide
		} else
			System.out.println("erreur de mode");
	}

	private static PblTSP lireFichier(String filename, int l) throws IOException {
		File fichier = new File(filename);
		FileReader reader = new FileReader(fichier);
		BufferedReader br = new BufferedReader(reader);

		String ligne = "";
		// Passer les lignes jusqu'a DIMENSION:
		while (!ligne.contains("DIMENSION:")) {
			ligne = br.readLine();
		}

		// ici, ligne est celle qui possede le n du probleme
		// On supprime le DIMENSION: qui fait 10 caractere
		ligne = ligne.substring(0, 10);

		// On supprime les espaces en trop
		ligne = ligne.trim();
		int n = Integer.parseInt(ligne); // On trouve le n
		int[][] matrice = new int[n][n];

		// Passer les lignes jusqu'a EDGE_WEIGHT_...
		while (!ligne.contains("EDGE_WEIGHT_SECTION")) {
			ligne = br.readLine();
		}
		// ici, on est au debut de la matrice
		for (int i = 0; i < n; i++) {
			// LIRE la prochaine ligne de la matrice
			ligne = br.readLine();
			int idx = 0;
			for (int j = 0; j < n; j++) {
				// LIRE le prochain nombre
				// PASSER les espaces qui separent deux chiffres
				for (; idx < ligne.length() && ligne.charAt(idx) == ' '; idx++)
					;

				// LECTURE du nombre
				String nbreStr = "";
				for (; idx < ligne.length() && ligne.charAt(idx) != ' '; idx++) {
					nbreStr += ligne.charAt(idx);
				}
				matrice[i][j] = Integer.parseInt(nbreStr);
			}
		}
		return new PblTSP(n, matrice, l);
	}

	private static int getProchainNombre(String ligne) {
		int i;
		int nombre = -1;
		// passer tous les espaces qui peuvent separer deux nombres
		for (i = 0; i < ligne.length() && ligne.charAt(i) == ' '; i++)
			;

		return nombre;
	}
}
