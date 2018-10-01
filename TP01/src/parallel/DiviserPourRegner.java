package parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import questions.Point;

public class DiviserPourRegner implements Callable<Integer> {

	private Point[] points;
	private int gauche;
	private int droite;
	private int hauteur;

	public DiviserPourRegner(Point[] points, int gauche, int droite, int hauteur) {
		super();
		this.points = points;
		this.gauche = gauche;
		this.droite = droite;
		this.hauteur = hauteur;
	}

	@Override
	public Integer call() throws Exception {
		return recherche(points, gauche, droite, hauteur);
	}

	public static int recherche(Point points[], int gauche, int droite, int hauteur)
			throws InterruptedException, ExecutionException {
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
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<FutureTask<Integer>> futures = new ArrayList<FutureTask<Integer>>();

		FutureTask<Integer> taskGauche = new FutureTask<Integer>(new DiviserPourRegner(points, gauche, min, hauteur));
		futures.add(taskGauche);
		executor.execute(taskGauche);

		FutureTask<Integer> taskDroite = new FutureTask<Integer>(new DiviserPourRegner(points, min, droite, hauteur));
		futures.add(taskDroite);
		executor.execute(taskDroite);

		int maxGauche = futures.get(0).get();
		int maxDroite = futures.get(1).get();

		// CALCULER LE RECTANGLE AUTOUR DU MINIMUM
		int aireMin = points[min].getY() * (points[droite].getX() - points[gauche].getX());

		// RETOURNE aire maximale ENTRE droite, gauche ET aireMin
		return Math.max(maxGauche, Math.max(maxDroite, aireMin));
	}
}
