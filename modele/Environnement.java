package application.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environnement {
	
	private Random r;
	
	private ObservableList<Acteur> listeActeurs;
	private ObservableList<Attaque> listeAttaques;
	private ObservableList<Outils> listeOutils; // outils qu'on peut trouver par terre sur la carte
	private ObservableList<int[]> listeDecorChangé; // dès qu'un élément du décor change, un entier est ajouté ici
	private ArrayList<int[]> coordonneesArbres; // contient des couples (Y,X) correspondant aux coordonnées des arbres de la map
	private int hauteur, largeur;
	private TileMap tm;
	private HashMap<Integer,Boolean> elementsDecor; // lie les numéros aux éléments du décor, et indique si ils sont franchissables
	private BooleanProperty partieFinie;
	private int pvFruits;
	private double probArbreFruitier;
	private int perdrePvEauToutesLesNpar10Secondes;
	private int gagnerPvEauToutesLesNpar10Secondes;
	private int vitesseEnnemis;
	private double probApparitionEnnemi;
	private double probApparitionOutil;
	
	public Environnement(TileMap tm, int difficulte) {
		this.r = new Random();
		this.tm = tm;
		this.hauteur = this.tm.getHauteur();
		this.largeur = this.tm.getLargeur();
		this.listeActeurs = FXCollections.observableArrayList();
		this.listeOutils = FXCollections.observableArrayList();
		this.listeAttaques = FXCollections.observableArrayList();
		this.listeDecorChangé = FXCollections.observableArrayList();
		this.coordonneesArbres = this.remplirListeElements(Constantes.CODE_ARBRE);
		this.elementsDecor = new HashMap<Integer,Boolean>();
		this.elementsDecor.put(Constantes.CODE_HERBE, true); // herbe
		this.elementsDecor.put(Constantes.CODE_PIERRE, false); // pierre
		this.elementsDecor.put(Constantes.CODE_EAU, true); // eau
		this.elementsDecor.put(Constantes.CODE_ARBRE, false); // arbre
		this.elementsDecor.put(Constantes.CODE_PONT, true); // pont
		this.elementsDecor.put(Constantes.CODE_ARBRE_FRUITIER, false); // arbre fruitier
		this.partieFinie = new SimpleBooleanProperty(false);
		this.reglerParametres(difficulte);
	}
	
	public int getPvFruits() {
		return this.pvFruits;
	}
	
	public void setPvFruits(int n) {
		this.pvFruits = n;
	}
	
	public double getProbArbres() {
		return this.probArbreFruitier;
	}
	
	public int getPerdrePvEauNSecondes() {
		return this.perdrePvEauToutesLesNpar10Secondes;
	}

	public int getGagnerPvEauNSecondes() {
		return this.gagnerPvEauToutesLesNpar10Secondes;
	}
	
	public int getVitEnnemis() {
		return this.vitesseEnnemis;	
	}
	
	public double getProbApparitionEnnemi() {
		return this.probApparitionEnnemi;
	}
	
	public double getProbApparitionOutil() {
		return this.probApparitionOutil;
	}
	
	public BooleanProperty partieFinieProperty() {
		return this.partieFinie;
	}
	
	public boolean contientOutilParTerre(String nom) {
		for (Outils o : this.listeOutils)
			if (o.toString().equals(nom)) return true;
		
		return false;
	}
	
	public ArrayList<int[]> remplirListeElements(int code) {
		ArrayList<int[]> liste = new ArrayList<>();
		
		int[] yx;
		for (int i=0; i<tm.getHauteur(); i++) {
			for (int j=0; j<tm.getLargeur(); j++) {
				if (tm.getNombre(i, j)==code) {
					yx = new int[2]; 
					yx[0]=i; 
					yx[1]=j;
					liste.add(yx);
				}
			}
		}
		
		return liste;
	}
	
	public ArrayList<int[]> getListeArbres() {
		return this.coordonneesArbres;
	}
	
//	public int[] transformerArbreAleatoireEnFruitier() {
//		
//		if (coordonneesArbres.size()>0) {
//			int indiceArbre = r.nextInt(coordonneesArbres.size());
//			
//			int[] yx = coordonneesArbres.get(indiceArbre);
//			
//			if (tm.getNombre(yx[0], yx[1])!=Constantes.CODE_ARBRE_FRUITIER) {
//				tm.setNombre(yx[0], yx[1], Constantes.CODE_ARBRE_FRUITIER);
//				return yx;
//			}
//		}
//		
//		return null;
//			
//	}

	public ObservableList<Acteur> getListe() {
		return this.listeActeurs;
	}
	
	public ObservableList<int[]> getDecorChange() {
		return this.listeDecorChangé;
	}
	
	public ObservableList<Attaque> getAttaques () {
		return this.listeAttaques;
	}
	
	public ObservableList<Outils> getOutils() {
		return this.listeOutils;
	}
	
	public int nombreDelements(int code) {
		// retourne le nombre d'objets (code spécifié en paramètre) présents dans le décor
		
		int nombreElements = 0;
		
		for (int i=0; i<tm.getHauteur(); i++)
			for (int j=0; j<tm.getLargeur(); j++)
				if (tm.getNombre(i,j)==code) nombreElements++;
		
		return nombreElements;
	}

	public TileMap getTm() {
		return this.tm;
	}
	
	public Outils outilLePlusProche(int x, int y) {
		// retourne l'outil se situant à moins de 2 cases des coordonnées envoyées en paramètre
		
		for (Outils o : listeOutils)
			if (Math.abs(o.getX()-x)<=1&&Math.abs(o.getY()-y)<=1)
				return o;
		
		return null;
	}
	
	public boolean contientActeurOuOutil(int x, int y) {
		// retourne vrai si la case spécifiée en paramètres contient un acteur ou un outil
		
		for (Acteur a : this.listeActeurs)
			if (a.getX()==x&&a.getY()==y)
				return true;
		
		for (Outils o : this.listeOutils)
			if (o.getX()==x&o.getY()==y)
				return true;
		
		return false;
	}
	
	public void ajouterAttaque(Attaque a) {
		listeAttaques.add(a);
	}
	
	public void supprimerAttaque(Attaque a) {
		listeAttaques.remove(a);
	}
	
	public void ajouterActeur(Acteur a) {
		if (!this.listeActeurs.contains(a)) this.listeActeurs.add(a);
	}
	
	public void ajouterOutil(Outils o) {
		if (!this.listeOutils.contains(o)) this.listeOutils.add(o);
	}
	
	public void enleverActeur(Acteur a) {
		if (this.listeActeurs.contains(a)) this.listeActeurs.remove(a);
	}
	
	public void enleverOutil(Outils o) {
		if (this.listeOutils.contains(o)) this.listeOutils.remove(o);
	}
	
	public void ajouterDecorChange(int[] t) {
		
		this.listeDecorChangé.add(t);
	}
	
	public Link getLink() {
		for (Acteur ac : this.listeActeurs)
			if (ac instanceof Link) return (Link)ac;
		
		return null;
	}
	
	public boolean partieEstTerminée() {
		// si il n'y a plus de Link ou si il n'y a plus d'ennemis, retourne true et supprime tous les acteurs
		
		for (Acteur ac : this.listeActeurs) {
			if (ac instanceof Link) {
				return this.listeActeurs.size()==1;
			}
		}
		
		return true;
	}
	
	public void nettoyage() {
		// enlève tous les acteurs, les outils et les relevés d'attaques
		int i;
		
		for (i=this.listeActeurs.size()-1; i>=0; i--)
			enleverActeur(this.listeActeurs.get(i));
		
		for (i=this.listeOutils.size()-1; i>=0; i--)
			enleverOutil(this.listeOutils.get(i));
		
		for (i=this.listeAttaques.size()-1; i>=0; i--)
			supprimerAttaque(this.listeAttaques.get(i));
			
	}
	
	public void reglerParametres(int difficulte) {
		// règle certains paramètres en fonction de la difficulté.
		
		if (difficulte==1) {
			this.perdrePvEauToutesLesNpar10Secondes = 5;
			this.gagnerPvEauToutesLesNpar10Secondes = 10;
			this.pvFruits = 20;
			this.probArbreFruitier = 0.15;
			this.vitesseEnnemis = 600;
			this.probApparitionEnnemi = 0.25;
			this.probApparitionOutil = 0.075;
		}
		else if (difficulte==2) {
			this.perdrePvEauToutesLesNpar10Secondes = 3;
			this.gagnerPvEauToutesLesNpar10Secondes = 30;
			this.pvFruits = 10;
			this.probArbreFruitier = 0.075;
			this.vitesseEnnemis = 400;
			this.probApparitionEnnemi = 0.5;
			this.probApparitionOutil = 0.05;
		}
		else {
			this.perdrePvEauToutesLesNpar10Secondes = 1;
			this.gagnerPvEauToutesLesNpar10Secondes = 50;
			this.pvFruits = 5;
			this.probArbreFruitier = 0.025;
			this.vitesseEnnemis = 200;
			this.probApparitionEnnemi = 0.75;
			this.probApparitionOutil = 0.025;
		}
	}
	
	public void toutLeMondeSeDeplaceSaufLink() {

		for (int i=0; i<this.listeActeurs.size(); i++)
			if (!(this.listeActeurs.get(i) instanceof Link))
				this.listeActeurs.get(i).seDéplacer();
	}
	
	public void ajouterArmeDunActeur(Acteur a) {
		// crée une nouvelle instance de la classe Arme qui correspond à l'arme d'un acteur qu'il fait tomber par terre
		// si Link possède déjà l'arme en question, inutile de la faire tomber, ça encombre la map.
	
		if (a.getArme() instanceof Epee && !getLink().possèdeOutil("Epee")) {
			Arme arm = new Epee();
			arm.setX(a.getX());
			arm.setY(a.getY());
			arm.setRamassable(true);
			ajouterOutil(arm);
		}
		else if (a.getArme() instanceof Arc && !getLink().possèdeOutil("Arc")) {
			Arme arm = new Arc();
			arm.setX(a.getX());
			arm.setY(a.getY());
			arm.setRamassable(true);
			ajouterOutil(arm);
		}
	}
	
	public void enleverLesMorts() {
		Acteur a;
		for (int i=0; i<listeActeurs.size(); i++) {
			if (listeActeurs.get(i).getPV()<1) {
				// acteur mort repéré
				a = listeActeurs.get(i);
				// si l'acteur est un ennemi, alors il fait tomber son arme par terre
				if (!(a instanceof Link))
					ajouterArmeDunActeur(a);
				// l'acteur est retiré de la liste
				enleverActeur(a);
			}
		}
	}
	
	
	public boolean estFranchissable(int x, int y) {
		
		return !this.horsLimites(x, y) && this.elementsDecor.get(this.tm.getNombre(y,x));
	}
	
	public boolean horsLimitesX(int x) {
		return x>=this.largeur||x<0;
	}
	
	public boolean horsLimitesY(int y) {
		return y>=this.hauteur||y<0;
	}
	
	public boolean horsLimites(int x, int y) {
		return x>=this.largeur||x<0||y>=this.hauteur||y<0;
	}
	
	public int[] transformerArbreAleatoireEnFruitierV2() {

		// transforme un arbre aléatoire de l'environnement en arbre fruitier.
		// si un arbre choisi aléatoirement est déjà fruitier, alors on recommence et on essaye avec un autre arbre,
		// jusqu'à ce que l'on teste tous les arbres de l'environnement.
		
		// si on voit qu'il n'y a pas d'arbres ou que tous les arbres sont fruitiers on retourne null directement pour ne
		// pas perdre du temps
		
		int nombreArbres = coordonneesArbres.size();
		int[] yx = new int[2];
		
		if (nombreArbres>0) {
			if (nombreDelements(Constantes.CODE_ARBRE_FRUITIER)==nombreArbres) {
				return null;
			}
			else {
				boolean arbreTransformé = false;
				boolean indiceArbreJamaisVérifié = false;
				ArrayList<Integer> indicesArbresVérifiés = new ArrayList<>();

				int indiceArbre=0;
				
				while (!arbreTransformé) {
					indiceArbreJamaisVérifié = false;
					while (!indiceArbreJamaisVérifié) {
						
						indiceArbre = r.nextInt(coordonneesArbres.size());
						
						if (!indicesArbresVérifiés.contains(indiceArbre)) {
							indiceArbreJamaisVérifié = true;
						}
					}
					
					
					yx = coordonneesArbres.get(indiceArbre);
					
					if (tm.getNombre(yx[0], yx[1])!=Constantes.CODE_ARBRE_FRUITIER) {
						tm.setNombre(yx[0], yx[1], Constantes.CODE_ARBRE_FRUITIER);
						arbreTransformé = true;
						
					}
					else {
						indicesArbresVérifiés.add(indiceArbre);
					}
				}
				
				return yx;
			}
		}
		else return null;
			
	}
	

}
