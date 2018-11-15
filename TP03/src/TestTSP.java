
public class TestTSP {
	public static void main(String[] arg) throws Exception {
		if (arg.length < 3)
			System.out.println("java test mode file.atsp lg");
		else {
			int lg = Integer.parseInt(arg[2]); // la longueur maximale est le dernier argument
			// lire l’instance du probleme dans le fichier de donnees dont le nom est en
			// argument
			// créer l’instance du probleme...
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
}
