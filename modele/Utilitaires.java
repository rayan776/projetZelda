package application.modele;

import java.util.List;

public class Utilitaires {
	
	public static boolean longueurDesLignes(int min, int max, List<String> liste) {
		// retourne vrai si une liste de String contient des String dont la longueur est comprise entre deux bornes définies
		
		for (String ligne : liste)
			if (ligne.length()<min || ligne.length()>max)
				return false;
		
		return true;
	}
	
	public static boolean tousMemeLongueur(List<String> liste) {

		// retourne vrai si une liste de String ne contient que des String de même longueur.
		
		int longueurPrecedent = liste.get(0).length();
		int longueur;
		
		for (int i=1; i<liste.size(); i++) {
			longueur = liste.get(i).length();
			if (longueur!=longueurPrecedent) return false;
			longueurPrecedent = longueur;
		}
		
		return true;
	}


}
