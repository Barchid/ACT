import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	private static final String[] fichiers = { "exe5.atsp", "exe7.atsp", "exe7b.atsp", "br17.atsp", "bays29.tsp",
			"bayg29.tsp", "ftv47.atsp" };

	public static void main(String[] args) {
		int i;
		int n;
		String line;
		int[][] matrice;

		String param = "--tabou";
		if (args.length < 1) {
			System.out.println("Pas d'argument ! Param vaut : " + param);
		} else {
			param = args[0];
		}

		for (String fichier : fichiers) {
			try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {

				do {
					line = br.readLine();
				} while (line.indexOf("DIMENSION:") < 0);

				n = Integer.parseInt(line.replace("DIMENSION:", "").trim());
				matrice = new int[n][n];

				do {
					line = br.readLine();
				} while (line.indexOf("EDGE_WEIGHT_SECTION") < 0);

				line = br.readLine();

				i = 0;
				while (line != null && !line.trim().equals("EOF") && i < n) {
					ArrayList<String> lineMatrix = new ArrayList<String>();
					String[] parts = line.split(" ");
					for (int j = 0; j < parts.length; j++) {
						if (!parts[j].trim().equals("")) {
							lineMatrix.add(parts[j].trim());
						}
					}

					for (int j = 0; j < lineMatrix.size(); j++)
						matrice[i][j] = Integer.parseInt(lineMatrix.get(j));

					line = br.readLine();
					i++;
				}

				PblTsp pbl = new PblTsp(matrice, n);

				System.out.println("############################################################");
				System.out.println("Solution du problème : " + fichier);

				switch (param) {
				case "--ajoutProche":
					ajoutProche(pbl);
					break;
				case "--hillClimbing":
					hillClimbing(pbl);
					break;
				case "--tabou":
					tabou(pbl);
					break;
				case "--recuit":
					recuit(pbl);
					break;
				case "--exacte":
					exacte(pbl);
					break;
				case "--constructionParArc":
					constructionParArc(pbl);
					break;
				}
				System.out.println("############################################################\n");

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		}
	}

	private static void ajoutProche(PblTsp pbl) {
		ConstructionAjoutProche construction = new ConstructionAjoutProche(pbl);
		int[] tournee = construction.algorithme();

		System.out.println("Ajout proche donne : " + construction.distanceTournee(tournee));
		System.out.println(Arrays.toString(tournee));
	}

	private static void hillClimbing(PblTsp pbl) {
		ConstructionAjoutProche construction = new ConstructionAjoutProche(pbl);
		int[] tournee = construction.algorithme();

		System.out.println("Ajout proche donne : " + construction.distanceTournee(tournee));

		HillClimbing hillClimbing = new HillClimbing(pbl, tournee);
		tournee = hillClimbing.algorithme();

		System.out.println("Hill climbing donne : " + hillClimbing.distanceTournee(tournee));
		System.out.println(Arrays.toString(tournee));
	}

	private static void tabou(PblTsp pbl) {
		ConstructionAjoutProche construction = new ConstructionAjoutProche(pbl);
		int[] tournee = construction.algorithme();

		System.out.println("Ajout proche donne : " + construction.distanceTournee(tournee));
		
		HillClimbing hillClimbing = new HillClimbing(pbl, tournee);
		tournee = hillClimbing.algorithme();
		System.out.println("Hill climbing donne : " + hillClimbing.distanceTournee(tournee));

		Tabou tabou = new Tabou(pbl, tournee, 500, 1000);
		tournee = tabou.algorithme();

		System.out.println("Tabou donne : " + tabou.distanceTournee(tournee));
		System.out.println(Arrays.toString(tournee));
	}

	private static void exacte(PblTsp pbl) {

	}

	private static void constructionParArc(PblTsp pbl) {

	}

	private static void recuit(PblTsp pbl) {

	}
}
