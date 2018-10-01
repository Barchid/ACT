package questions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Main {

	public final static String[] nameFile = { "ex_N0_res10000", "ex_N2_res10000", "ex_N10_res24400144",
			"ex_N100_res5980", "ex_N100_res6741", "ex_N500_res7616", "ex_N500_res7854", "ex_N100000_res100000",
			"ex_N200000_res75141975", "ex=_N100000_res10000000", "ex=_N200000_res20000000", "exCodeChef_N5_res49997500",
			"exT_N100000_res30011389", "exT_N200000_res75141975" };

	public static Point points[];

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

		StringBuilder sb;

		for (int i = 1; i < nameFile.length; i++) {

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
					sb.append(rechMaxSurfRectDiviserRegner(points, 0, points.length - 1, h));
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

	public static int rechMaxSurfRectCubique(int l, int h) {
		int surface;
		int hauteurMin;
		int surfaceMax = 0;

		//Case de base, aucune point
		if(points.length == 2)
			return points[1].getX() * h;
		
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

	public static int rechMaxSurfRectQuadratique(int h) {
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

//	public static int rechMaxSurfRectDiviserRegner(Point points[], int indexStart, int indexEnd, int surfaceMax) {
//		int pivot = indexStart;
//		int surface;
//		if(indexStart < indexEnd) {
//			for(int i = indexStart + 1; indexEnd < indexEnd; i++)
//				if(points[pivot].getY() > points[i].getY())
//					pivot = i;
//			if()
//			
//			return Math.max(rechMaxSurfRectDiviserRegner(points, indexStart, pivot -1), rechMaxSurfRectDiviserRegner(points, pivot+1, indexEnd));
//		}
//		
//		return (points[indexEnd].getX() - points[indexStart].getX()) * Math.min(points[indexStart].getY(), points[indexEnd].getY());
//	}

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
	public static int rechMaxSurfRectLineaire(int h) {

		Stack<Integer> pile = new Stack<>();

		int surfaceMax = 0;
		int indexHauteurMax;
		int surface;

		int index = 0;

		while (index < points.length) {
			
			if(index > 0){
				surface = (points[index].getX() - points[index - 1].getX()) * h;
				if (surfaceMax < surface)
					surfaceMax = surface;
			}
			
			if (pile.empty()|| points[pile.peek()].getY() <= points[index].getY() ) {
				pile.push(index);
				index++;
			} else {
				indexHauteurMax = pile.pop();
				if (pile.empty())
					surface = points[indexHauteurMax].getY() * (points[indexHauteurMax].getX() - points[indexHauteurMax - 1].getX());
				else
					surface = points[indexHauteurMax].getY() * (points[index - 1].getX() - points[pile.peek()].getX());

				if (surfaceMax < surface)
					surfaceMax = surface;
			}
		}

		while (!pile.empty()) {
			indexHauteurMax = pile.pop();
			if (pile.empty())
				surface = points[indexHauteurMax].getY() * points[indexHauteurMax].getX();
			else
				surface = points[indexHauteurMax].getY() * (points[index - 1].getX() - points[pile.peek()].getX());

			if (surfaceMax < surface)
				surfaceMax = surface;
		}

		return surfaceMax;
	}
}