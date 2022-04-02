package application.modele;

import java.util.ArrayList;
import java.util.Collections;

public class EnnemiAuSol extends Ennemi {
	
	private ArrayList<int[]> déplacements; // liste de déplacements par x et y, avec tableaux de longueur 2
	private int numéroPas;
	
	public EnnemiAuSol(int x, int y, int xD, int yD, Environnement env) {
		super(x, y, env);
		this.déplacements = initialiserChemin(coordonnées(xD,yD));
		numéroPas = 1; // 1 parce que 0 c'est les coordonnées de départ (on y est deja sur ces coordonnées)
		this.setDir(6);
	}
	
	public void seDéplacer() {
	
		if(numéroPas >= this.déplacements.size()){ //Une fois arrivé au point de destination, faut faire un demi-tour
			Collections.reverse(this.déplacements);
			numéroPas = 1;
		}
		
		//On va définir la direction pour que les sprites changent
		//d'abord en x
		if(this.déplacements.get(numéroPas-1)[0] < this.déplacements.get(numéroPas)[0])
			setDir(6);
		else if(this.déplacements.get(numéroPas-1)[0] > this.déplacements.get(numéroPas)[0])
			setDir(4);
		//maintenant en y
		if(this.déplacements.get(numéroPas-1)[1] < this.déplacements.get(numéroPas)[1])
			setDir(2);
		else if(this.déplacements.get(numéroPas-1)[1] > this.déplacements.get(numéroPas)[1])
			setDir(8);
		
		this.setX(this.déplacements.get(numéroPas)[0]);
		this.setY(this.déplacements.get(numéroPas)[1]);
		numéroPas++;
		
		if (this.détecterPrésenceLink()) {
			this.attaquer();
			this.getEnv().enleverLesMorts();
		}
		
	}
	
	public ArrayList<int[]> initialiserChemin(int[] coordDestination) {
		ArrayList<ArrayList<int[]>> branchesDeplacement = new ArrayList<ArrayList<int[]>>();

		int xCaseCourrent = this.getX();
		int yCaseCourrent = this.getY();
		int[] destination = coordDestination;

		ArrayList<int[]> lesPasOccupés= new ArrayList<int[]>();// Pour que les branches ne se croisent pas
		lesPasOccupés.add(coordonnées(xCaseCourrent, yCaseCourrent));

		//début
		if(this.getEnv().estFranchissable(xCaseCourrent-1, yCaseCourrent)){
			branchesDeplacement.add(new ArrayList<>());
			branchesDeplacement.get(branchesDeplacement.size()-1).add(coordonnées(xCaseCourrent-1, yCaseCourrent));
		}
		if(this.getEnv().estFranchissable(xCaseCourrent, yCaseCourrent-1)){
			branchesDeplacement.add(new ArrayList<>());
			branchesDeplacement.get(branchesDeplacement.size()-1).add(coordonnées(xCaseCourrent, yCaseCourrent-1));
		}
		if(this.getEnv().estFranchissable(xCaseCourrent+1, yCaseCourrent)){
			branchesDeplacement.add(new ArrayList<>());
			branchesDeplacement.get(branchesDeplacement.size()-1).add(coordonnées(xCaseCourrent+1, yCaseCourrent));
		}
		if(this.getEnv().estFranchissable(xCaseCourrent, yCaseCourrent+1)){
			branchesDeplacement.add(new ArrayList<>());
			branchesDeplacement.get(branchesDeplacement.size()-1).add(coordonnées(xCaseCourrent, yCaseCourrent+1));
		}

		Integer indiceCheminTrouvé = null;

		while(indiceCheminTrouvé == null){
			for(int i = 0; i < branchesDeplacement.size(); i++){
				ArrayList<int[]> copieBranche = new ArrayList<>(branchesDeplacement.get(i));
				int[] lastPosition = branchesDeplacement.get(i).get(branchesDeplacement.get(i).size()-1);
				boolean premierPas = true;

				//gauche
				if(this.getEnv().estFranchissable(lastPosition[0]-1, lastPosition[1]) && pasEstLibre(coordonnées(lastPosition[0]-1, lastPosition[1]), lesPasOccupés)){
					branchesDeplacement.get(i).add(coordonnées(lastPosition[0]-1, lastPosition[1]));
					lesPasOccupés.add(coordonnées(lastPosition[0]-1, lastPosition[1]));
					premierPas = false;
					//Verifions si le chemin est trouvé
					if(lastPosition[0]-1 == destination[0] && lastPosition[1] == destination[1])
						indiceCheminTrouvé = i;
				}
				//haut
				if(this.getEnv().estFranchissable(lastPosition[0], lastPosition[1]-1) && pasEstLibre(coordonnées(lastPosition[0], lastPosition[1]-1), lesPasOccupés)){
					if(premierPas){
						branchesDeplacement.get(i).add(coordonnées(lastPosition[0], lastPosition[1]-1));
						premierPas = false;
						if(lastPosition[0] == destination[0] && lastPosition[1]-1 == destination[1])
							indiceCheminTrouvé = i;
					}else{
						ArrayList<int[]> nouvelleBranche = new ArrayList<>(copieBranche);
						nouvelleBranche.add(coordonnées(lastPosition[0], lastPosition[1]-1));
						branchesDeplacement.add(i+1,nouvelleBranche);
						i++;
						if(lastPosition[0] == destination[0] && lastPosition[1]-1 == destination[1])
							indiceCheminTrouvé = i;
					}
					lesPasOccupés.add(coordonnées(lastPosition[0], lastPosition[1]-1));
				}
				//droite
				if(this.getEnv().estFranchissable(lastPosition[0]+1, lastPosition[1]) && pasEstLibre(coordonnées(lastPosition[0]+1, lastPosition[1]), lesPasOccupés)){
					if(premierPas){
						branchesDeplacement.get(i).add(coordonnées(lastPosition[0]+1, lastPosition[1]));
						premierPas = false;
						if(lastPosition[0]+1 == destination[0] && lastPosition[1] == destination[1])
							indiceCheminTrouvé = i;
					}else{
						ArrayList<int[]> nouvelleBranche = new ArrayList<>(copieBranche);
						nouvelleBranche.add(coordonnées(lastPosition[0]+1, lastPosition[1]));
						branchesDeplacement.add(i+1,nouvelleBranche);
						i++;
						if(lastPosition[0]+1 == destination[0] && lastPosition[1] == destination[1])
							indiceCheminTrouvé = i;
					}
					lesPasOccupés.add(coordonnées(lastPosition[0]+1, lastPosition[1]));
				}
				//bas
				if(this.getEnv().estFranchissable(lastPosition[0], lastPosition[1]+1) && pasEstLibre(coordonnées(lastPosition[0], lastPosition[1]+1), lesPasOccupés)){
					if(premierPas){
						branchesDeplacement.get(i).add(coordonnées(lastPosition[0], lastPosition[1]+1));
						premierPas = false;
						if(lastPosition[0] == destination[0] && lastPosition[1]+1 == destination[1])
							indiceCheminTrouvé = i;
					}else{
						ArrayList<int[]> nouvelleBranche = new ArrayList<>(copieBranche);
						nouvelleBranche.add(coordonnées(lastPosition[0], lastPosition[1]+1));
						branchesDeplacement.add(i+1,nouvelleBranche);
						i++;
						if(lastPosition[0] == destination[0] && lastPosition[1]+1 == destination[1])
							indiceCheminTrouvé = i;
					}
					lesPasOccupés.add(coordonnées(lastPosition[0], lastPosition[1]+1));
				}
			}
		}
		branchesDeplacement.get(indiceCheminTrouvé).add(0, coordonnées(xCaseCourrent, yCaseCourrent));// on ajoute les coordonnées de départ au début
		return branchesDeplacement.get(indiceCheminTrouvé);
	}


	public boolean pasEstLibre(int[] coordonnées, ArrayList<int[]> pasOccupés) {
		int lim = pasOccupés.size();
		for(int ind = 0; ind < lim; ind++){
			if(coordonnées[0] == pasOccupés.get(ind)[0] && coordonnées[1] == pasOccupés.get(ind)[1])
				return false;
		}
		return true;
	}
	
	public int[] coordonnées(int x, int y) {
		return new int[] {x, y};
	}
	
	public String toString() {
		return "EnnemiAuSol";
	}


		
}

