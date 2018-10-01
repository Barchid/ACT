package parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import questions.Point;

public class Main {

	final static String[] nameFile = { "ex_N0_res10000", "ex_N2_res10000", "ex_N5_res153", "ex_N10_res24400144",
			"ex_N100_res5980", "ex_N100_res6741", "ex_N500_res7616", "ex_N500_res7854", "ex_N100000_res100000",
			"ex_N200000_res75141975", "ex=_N100000_res10000000", "ex=_N200000_res20000000", "exCodeChef_N5_res49997500",
			"exT_N100000_res30011389", "exT_N200000_res75141975" };

//	private final static String[] nameFile = { "ex_N5_res153" };

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		int l;
		int h;
		int index;
		long start;

		Point points[];
		String line;
		String[] parts;

		File f;
		FileReader fr;
		BufferedReader br;

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < nameFile.length; i++) {

			try {

				f = new File(nameFile[i]);
				fr = new FileReader(f);
				br = new BufferedReader(fr);

				try {
					line = br.readLine();
					parts = line.split(" ");
					l = Integer.parseInt(parts[0]);
					h = Integer.parseInt(parts[1]);

					line = br.readLine();
					parts = line.split(" ");

					points = new Point[Integer.parseInt(parts[0]) + 2];

					points[0] = new Point(0, 0);

					index = 1;

					line = br.readLine();

					while (line != null) {
						parts = line.split(" ");
						if (parts.length == 2) {
							points[index] = new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
							index++;
						}
						line = br.readLine();
					}

					br.close();
					fr.close();

					points[points.length - 1] = new Point(l, 0);

					sb = new StringBuilder();
					sb.append(nameFile[i]);
					sb.append(": Max surface = ");
					start = System.currentTimeMillis();
					sb.append(DiviserPourRegner.recherche(points, 0, points.length - 1, h));
					sb.append(" find in ");
					sb.append(System.currentTimeMillis() - start);
					sb.append(" ms.");

					System.out.println(sb.toString());
				} catch (IOException exception) {
					System.out.println("Erreur lors de la lecture : " + exception.getMessage());
				}
			} catch (FileNotFoundException exception) {
				System.out.println("Le fichier n'a pas été trouvé");
			}
		}

	}
}
