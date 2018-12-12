//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//public class ConstructionParArc {
//	
//	public static final int VALUE = 9999; 
//	private int nbVille;
//	private List<Arc> chemins;
//	
//	public ConstructionParArc(int[][] matrice) {
//		super();
//		this.nbVille = matrice.length;
//		construireListe(matrice);
//	}
//	
//	public void construireListe(int[][] matrice) {
//		this.chemins = new ArrayList<Arc>();
//		for(int i = 0; i < matrice.length; i++) {
//			for(int j = 0; j < matrice.length; j++) {
//				if(matrice[i][j] <= 0)
//					matrice[i][j] = VALUE;
//				
//				this.chemins.add(new Arc(i, j, matrice[i][j]));
//			}
//		}
//	}
//	
//	public void trierListe() {
//		Collections.sort(this.chemins, new Comparator<Arc>() {
//			@Override
//			public int compare(Arc a1, Arc a2) {
//				return a1.getDistance() - a2.getDistance();
//			}
//		});	
//	}
//	
//	public List<Arc> getAllArcByOrigin(int indexVille, boolean estDejaPasse[]) {
//		List<Arc> cheminsByOrigin = new ArrayList<Arc>();
//		
//		for(int i = 0; i< this.chemins.size(); i++) {
//			int origin = this.chemins.get(i).getOrigine();
//			int destination = this.chemins.get(i).getDestination();
//			if(origin == indexVille && !estDejaPasse[destination]) {
//				cheminsByOrigin.add(this.chemins.get(i));
//			}
//		}
//			
//		return cheminsByOrigin;
//	}
//	
//	public void constructionAjoutArcs() {
//		int i;
//		boolean estDejaPasse[] = new boolean[nbVille];
//		List<Arc> tour = new ArrayList<Arc>();
//		
//		for(i = 0; i < nbVille; i++) {
//			estDejaPasse[i] = false;
//		}
//		trierListe();
//		for(Arc arc : this.chemins) {
//			if(tour.size() == 0) {
//				tour.add(arc);
//				estDejaPasse[arc.getDestination()]
//				continue;
//			}
//			if()
//		}
//		
////		cheminsByOrigin = getAllArcByOrigin(this.chemins.get(0).getOrigine(), estDejaPasse);
////		
////		if(cheminsByOrigin.size() > 0) {
////			Arc newArc = cheminsByOrigin.get(0);
////			parcours.add(newArc);
////			estDejaPasse[newArc.getOrigine()] = true;
////		} else {
////			
////		}
//		
//	}
//	
//}
