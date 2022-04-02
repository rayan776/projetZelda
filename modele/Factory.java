package application.modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Factory {
	
	private Random r;
	private Environnement env;
	private int nombreEnnemisMaximum;
	private int nombreOutilsMaximum;
	private HashMap<Integer,String> armeMap;
	
	// Ces deux listes seront réactualisées en permanence car quand Link pousse des pierres, elles peuvent changer.
	private ArrayList<int[]> placesDisponiblesEnnemis; // les ennemis peuvent apparaître sur l'eau, l'herbe et les ponts
	private ArrayList<int[]> placesHerbes; // les outils n'apparaitront que sur l'herbe
	
	public Factory(Environnement env) {
		this.env = env;
		r = new Random();
		placesDisponiblesEnnemis = new ArrayList<>();	
		placesHerbes = new ArrayList<>();
		remplirListePlacesEnnemis();
		remplirListePlacesHerbes();
		calculerEnnemisEtOutilsMaximum();
		armeMap = new HashMap<Integer,String>();
		armeMap.put(0, "Bouclier");
		armeMap.put(1, "Epee");
		armeMap.put(2, "Arc");
	}
	
	public void remplirListePlacesHerbes() {
		viderListe(placesHerbes);
		this.placesHerbes = env.remplirListeElements(Constantes.CODE_HERBE);
	}
	
	public void remplirListePlacesEnnemis() {
		// liste contenant les coordonnées de l'herbe, des ponts et de l'eau
		viderListe(placesDisponiblesEnnemis);
		placesDisponiblesEnnemis.addAll(env.remplirListeElements(Constantes.CODE_HERBE));
		placesDisponiblesEnnemis.addAll(env.remplirListeElements(Constantes.CODE_EAU));
		placesDisponiblesEnnemis.addAll(env.remplirListeElements(Constantes.CODE_PONT));
	}
	
	public void calculerEnnemisEtOutilsMaximum() {
		// calcule le nombre maximum d'outils/d'ennemis qu'on pourra faire apparaître en fonction des places disponibles
		this.nombreEnnemisMaximum = placesDisponiblesEnnemis.size()/200;
		this.nombreOutilsMaximum = placesHerbes.size()/20;
	}
	
	public int getEnnemisMax() {
		return this.nombreEnnemisMaximum;
	}
	
	public int getOutilsMax() {
		return this.nombreOutilsMaximum;
	}
	
	public void faireApparaitreLink() {
		Acteur a;
		
		int indiceCoordLnk = r.nextInt(placesHerbes.size());
		int xLnk = placesHerbes.get(indiceCoordLnk)[1];
		int yLnk = placesHerbes.get(indiceCoordLnk)[0];
		
		a = new Link(xLnk, yLnk, env);
		
		env.ajouterActeur(a);
	}
	
	public void faireApparaitreEnnemi(boolean débutDePartie) {
		Acteur a;
	
		int typeEnnemi = débutDePartie ? 0 : r.nextInt(2);
		
		int indiceCoordApparition = r.nextInt(placesDisponiblesEnnemis.size());
		int[] coordonneesApparition = new int[2];
		
		coordonneesApparition[1] = placesDisponiblesEnnemis.get(indiceCoordApparition)[0];
		coordonneesApparition[0] = placesDisponiblesEnnemis.get(indiceCoordApparition)[1];
		
		if (typeEnnemi==0) {
			Arme ar;
			int typeArme = r.nextInt(2);
			if (typeArme==0) ar = new Baton();
			else ar = new Epee();
			int indiceCoordDestination = r.nextInt(placesDisponiblesEnnemis.size());
			int[] coordDestination = new int[2];
			coordDestination[1] = placesDisponiblesEnnemis.get(indiceCoordDestination)[0];
			coordDestination[0] = placesDisponiblesEnnemis.get(indiceCoordDestination)[1];
			a = new EnnemiAuSol(coordonneesApparition[0], coordonneesApparition[1], coordDestination[0], coordDestination[1], env);
			a.ajouterOutil(ar);
			a.setArme(ar);
			env.ajouterActeur(a);
		}
		else if (env.getLink().possèdeOutil("Arc")) {
			a = new EnnemiQuiVole(coordonneesApparition[0], coordonneesApparition[1], env);
			Arc ar = new Arc(true);
			a.ajouterOutil(ar);
			a.setArme(ar);
			env.ajouterActeur(a);
		}
		
	}
	
	public void faireApparaitreOutil() {
		// fait apparaître un bouclier OU une arme que Link ne possède pas ET qui n'est pas déjà sur la map
		
		boolean outilApparu = false;
		int typeOutil;
		int indiceCoordOutil, xOutil, yOutil;
		
		while (!outilApparu) {
			typeOutil = r.nextInt(3);
			
			if (typeOutil==0||typeOutil>0&&!env.getLink().possèdeOutil(armeMap.get(typeOutil))&&!env.contientOutilParTerre(armeMap.get(typeOutil))) {
				indiceCoordOutil = r.nextInt(placesHerbes.size());
				xOutil = placesHerbes.get(indiceCoordOutil)[1];
				yOutil = placesHerbes.get(indiceCoordOutil)[0];
				
				if (typeOutil==0) {
					Bouclier b = new Bouclier(xOutil, yOutil);
					env.ajouterOutil(b);
				}
				else if (typeOutil==1) {
					Arme epee = new Epee();
					epee.setX(xOutil);
					epee.setY(yOutil);
					epee.setRamassable(true);
					env.ajouterOutil(epee);
				}
				else {
					Arme arc = new Arc();
					arc.setX(xOutil);
					arc.setY(yOutil);
					arc.setRamassable(true);
					env.ajouterOutil(arc);
				}
				
				outilApparu = true;
			}
		}
		
		
		
	}
	
	public void viderListe(ArrayList<int[]> liste) {
		for (int i=liste.size()-1; i>0; i--)
			liste.remove(i);
	}

}
