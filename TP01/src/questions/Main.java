package questions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class Main {

	final static String[] nameFile = { "ex_N0_res10000", "ex_N2_res10000", "ex_N5_res153", "ex_N10_res24400144",
			"ex_N100_res5980", "ex_N100_res6741", "ex_N500_res7616", "ex_N500_res7854", "ex_N100000_res100000",
			"ex_N200000_res75141975", "ex=_N100000_res10000000", "ex=_N200000_res20000000", "exCodeChef_N5_res49997500",
			"exT_N100000_res30011389", "exT_N200000_res75141975" };

//	private final static String[] nameFile = { "ex_N5_res153" };

	public static void main(String[] args) {

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
//					sb.append(rechMaxSurfRectDiviserRegner(points, 0, points.length - 1, h));
//					sb.append(rechMaxSurfRectCubique(h, points));
//					sb.append(rechMaxSurfRectQuadratique(h, points));
					sb.append(rechercheLinéaire(points, h));
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

	/**
	 * Recherche de l'aire maximum du rectangle en O(n³)
	 * 
	 * @param h      la hauteur du graphe
	 * @param points les points du graphe
	 * @return l'aire maximale que l'on peut obtenir
	 */
	public static int rechMaxSurfRectCubique(int h, Point points[]) {
		int surface;
		int hauteurMin;
		int surfaceMax = 0;

		for (int i = 0; i < points.length; i++) {
			for (int j = i + 1; j < points.length; j++) {

				// Recherche de la hauteur maximal pour ce couple de points i,j courant
				hauteurMin = h;
				for (int k = i + 1; k < j; k++)
					if (points[k].getY() < hauteurMin)
						hauteurMin = points[k].getY();

				// Calcul de la surface pour le couple de points i,j courant
				surface = hauteurMin * (points[j].getX() - points[i].getX());

				// Recherche de la plus grande surface
				if (surfaceMax < surface)
					surfaceMax = surface;
			}
		}
		return surfaceMax;
	}

	/**
	 * Recherche de l'aire maximale du rectangle en O(n²)
	 * 
	 * @param h      la hauteur du graphe
	 * @param points les points du graphe
	 * @return l'aire maximale d'un rectangle
	 */
	public static int rechMaxSurfRectQuadratique(int h, Point points[]) {
		int surfaceMax = 0;
		int hauteurMin;
		int surface;
		int surfaceTmp;

		for (int i = 0; i < points.length; i++) {
			// Init. des variables
			hauteurMin = h;
			surface = 0;

			for (int j = i + 1; j < points.length; j++) {

				// Calculer la surface pour le couple de points courant
				surfaceTmp = hauteurMin * (points[j].getX() - points[i].getX());

				if (surface < surfaceTmp)
					surface = surfaceTmp;

				// La hauteur minimale pour le prochain couple de points est
				// l'ordonnée du deuxième point si celle-ci est plus petite
				if (hauteurMin > points[j].getY()) {
					hauteurMin = points[j].getY();
				}
			}

			if (surfaceMax < surface) {
				surfaceMax = surface;
			}
		}

		return surfaceMax;
	}

	/**
	 * Recherche l'aire maximale d'un rectangle en utilisant le paradigme "Diviser
	 * pour régner"
	 * 
	 * @param points  les points
	 * @param gauche  la borne de gauche (initialisée au départ à 0)
	 * @param droite  la borne de droite (initialisée au départ à points.length -1)
	 * @param hauteur la hauteur du graphe
	 * @return l'aire maximale du rectangle
	 */
	public static int rechMaxSurfRectDiviserRegner(Point points[], int gauche, int droite, int hauteur) {
		// CAS DE BASE : pas de points
		if (droite - 1 == gauche) {
			// RETOURNE aire du rectangle sans points
			return (points[droite].getX() - points[gauche].getX()) * hauteur;
		}

		// TROUVER POINT MINIMUM en ordonnée entre ]droite, gauche[
		// (déterminer la division)
		int min = gauche + 1;
		for (int i = gauche + 1; i < droite; i++) {
			if (points[i].getY() < points[min].getY()) {
				min = i;
			}
		}

		// CALCULER RECTANGLE MAX A GAUCHE/DROITE (récursivité)
		int maxGauche = rechMaxSurfRectDiviserRegner(points, gauche, min, hauteur);
		int maxDroite = rechMaxSurfRectDiviserRegner(points, min, droite, hauteur);

		// CALCULER LE RECTANGLE AUTOUR DU MINIMUM
		int aireMin = points[min].getY() * (points[droite].getX() - points[gauche].getX());

		// RETOURNE aire maximale ENTRE droite, gauche ET aireMin
		return Math.max(maxGauche, Math.max(maxDroite, aireMin));
	}

	/**
	 * Recherche l'aire maximale d'un rectangle avec un algorithme en O(n)
	 * 
	 * @param points les points du graphe
	 * @param h      la hauteur du graphe
	 * @return l'aire maximale d'un rectangle du graphe
	 */
	public static int rechercheLinéaire(Point points[], int h) {
		Stack<Point> pile = new Stack<Point>();
		int aireMax = 0;

		// On ajoute directement le premier point (0,0)
		pile.push(points[0]);

		// POUR CHAQUE POINT de 1 à n-1
		for (int i = 1; i < points.length; i++) {
			
			// SI [le point décroit en ordonnée]
			Point top = pile.peek();
			if (points[i].getY() < top.getY()) {
				int hauteurMin = h;
				
				// TANT QUE la pile n'est pas vide
				while (!pile.empty()) {
					// Retirer le sommet (pop)
					top = pile.pop();
					
					// Je calcule l'aire du rectangle entre point[i] et le point qui a été "poppé"
					int aire = (points[i].getX() - top.getX()) * hauteurMin;
					
					// VERIFIER si aire max change
					if(aire > aireMax) {
						aireMax = aire;
					}
					
					// La hauteur minimale devient l'ordonnée du point poppé
					hauteurMin = top.getY();
				}
				
			}
			
			// Ajouter le point courant dans la pile 
			pile.push(points[i]);
		}
		// VIDER la pile s'il reste des trucs
		// RETIRER dernier point (à n-1)
		Point last = pile.pop();
		int hauteurMin = h;
		while(!pile.empty()) {
			Point top = pile.pop();
			int aire = (last.getX() - top.getX()) * hauteurMin;
			// VERIFIER si aire max change
			if(aire > aireMax) {
				aireMax = aire;
			}
			// La hauteur minimale devient l'ordonnée du point poppé
			hauteurMin = top.getY();
		}
		
		return aireMax;
	}
}