package application.vue;

import java.util.HashMap;

import application.modele.Constantes;

public class HashMapElementsDecor {
	
	private HashMap<Integer,String> decor;
	
	public HashMapElementsDecor() {
		decor = new HashMap<Integer,String>();	
	
		decor.put(Constantes.CODE_HERBE, "/application/resources/herbe.jpg");
		decor.put(Constantes.CODE_PIERRE, "/application/resources/pierre.jpg");
		decor.put(Constantes.CODE_EAU, "/application/resources/eau.jpg");
		decor.put(Constantes.CODE_ARBRE, "/application/resources/arbre.jpg");
		decor.put(Constantes.CODE_PONT, "/application/resources/pont.jpg");
		decor.put(Constantes.CODE_ARBRE_FRUITIER, "/application/resources/arbrefruitier.jpg");
	}
	
	public HashMap<Integer,String> getDecor() {
		return this.decor;
	}
	
	

}
