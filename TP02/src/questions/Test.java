package questions;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			int lignes = Integer.parseInt(sc.nextLine().trim());
			int colonnes = Integer.parseInt(sc.nextLine().trim());
			char[][] combi = new char[lignes][colonnes];

			int i = 0;
			while (sc.hasNextLine()) {
				combi[i] = sc.nextLine().toCharArray();
				i++;
			}
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				
				// Parce que le fichier est mal fichu...
				if (i == combi.length) {
					break;
				}

				char[] ligne = new char[colonnes];
				int j = 0;
				for (; j < line.length(); j++) {
					ligne[j] = line.charAt(j);
				}
				// Fichier encore mal fichu...
				for (; j < colonnes; j++) {
					ligne[j] = ' ';
				}

				combi[i] = ligne;
				i++;
			}

			Combinaison initiale = new Combinaison(combi, Couleur.BLANC);
			Map<Combinaison, Integer> memo = new HashMap<Combinaison, Integer>();

			// Affichage résultat
			System.out.println(initiale.trouverCoupsMemo(memo));
		} finally {
			sc.close();
		}
	}
}
