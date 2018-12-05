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

				PblTsp pbm = new PblTsp(matrice, n);
				ConstructionAjoutProche construction = new ConstructionAjoutProche(pbm);
				int[] tournee = construction.algorithme();
				
				HillClimbing hillClimbing = new HillClimbing(pbm, tournee);
				
				System.out.println(Arrays.toString(tournee));
				System.out.println(construction.distanceTournee(tournee));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.toString());
			}
		}
	}
}
