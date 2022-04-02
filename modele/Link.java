package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Link extends Acteur {
	
	private final int PV_EAU_MAX = 10;
	private static final int PV_MAX = 100;
	private IntegerProperty pvEauActuel;
	private IntegerProperty ptsDef;
	private IntegerProperty score;
	
	public Link(int x, int y, Environnement env) {
		super(x, y, env, PV_MAX);
		this.pvEauActuel = new SimpleIntegerProperty(PV_EAU_MAX);
		this.ptsDef = new SimpleIntegerProperty(0);
		this.score = new SimpleIntegerProperty(0);
		this.setId("A1");
		this.setDir(6);
	}
	
	public int getPvEauMax() {
		return this.PV_EAU_MAX;
	}
	
	public IntegerProperty getScore() {
		return this.score;
	}
	
	public void augmenterScore(int n) {
		this.score.setValue(this.score.getValue()+n);
	}
	
	public void decrementerPvEau(int n) {
		this.pvEauActuel.setValue(this.pvEauActuel.getValue()-n);
	}
	
	public void ajouterPvEau(int n) {
		this.pvEauActuel.setValue(this.pvEauActuel.getValue()+n);
	}
	
	public IntegerProperty getPtsDefLnk() {
		return this.ptsDef;
	}
	
	public void decremPtsDefLnk(int n) {
		this.ptsDef.setValue(this.ptsDef.getValue()-n);
		if (ptsDef.getValue()<0) ptsDef.setValue(0);
	}
	
	public void ajouterPtsDefLnk(int n) {
		this.ptsDef.setValue(this.ptsDef.getValue()+n);
	}
	
	public IntegerProperty getPvEauProperty() {
		return this.pvEauActuel;
	}

	public void seDéplacer() {
		int[] newCoord = avancerDeUnPas(this.getX(), this.getY());
		
		if (!this.getEnv().horsLimites(newCoord[0], newCoord[1])) {
			if (this.getEnv().estFranchissable(newCoord[0], newCoord[1])) {
				this.setX(newCoord[0]);
				this.setY(newCoord[1]);
				
				if (this.getEnv().getTm().getNombre(this.getY(), this.getX())==2)
					this.setDansLeau(true);
				else
					this.setDansLeau(false);
			}
		}
		
	}
	
	public void attaquer() {
		
		int distance = this.getArme().getDistance()+1;
		
		ObservableList<Acteur> acteurs = this.getEnv().getListe();
		Acteur acteurLePlusProche = null;
		
		for (Acteur ac : acteurs) {
			if (ac instanceof EnnemiAuSol) {
				if (Math.abs(ac.getX()-this.getX())<distance&&Math.abs(ac.getY()-this.getY())<distance) {
					acteurLePlusProche = ac;
					break;
				}
			}
			else if (ac instanceof EnnemiQuiVole) {
				if (this.getArme() instanceof Arc)
					if (Math.abs(ac.getX()-this.getX())<distance&&Math.abs(ac.getY()-this.getY())<distance) {
						acteurLePlusProche = ac;
						break;
					}
			}
		}
		
		if (acteurLePlusProche!=null) {
			
			acteurLePlusProche.decrementePV(this.getArme().getPtsAttaque());
			
			if (acteurLePlusProche.getPV()<=0) {
				acteurLePlusProche.setPV(0);
				
				if (acteurLePlusProche instanceof EnnemiAuSol)
					this.augmenterScore(1);
				else
					this.augmenterScore(2);
				
				this.getEnv().enleverLesMorts();
			}
			
			this.getEnv().ajouterAttaque(new Attaque(this, acteurLePlusProche));
			
			
		}
			
		
	}
	
	public void ramasser() {
		
		Outils o = this.getEnv().outilLePlusProche(getX(), getY());
		
		if (o!=null) {
			
			// Link ne doit pas ramasser une arme qu'il possède déjà.
			
			if (!this.possèdeOutil(o.toString())) {
				this.ajouterOutil(o);
				this.getEnv().enleverOutil(o);
			}
			
			// Si c'est un bouclier, Link ne le ramasse que si ce dernier a plus de points de défense que le sien
			
			if (o instanceof Bouclier && this.getBouclier()!=null && ptsDef.getValue()<((Bouclier)o).getPtsDef()) {
				Bouclier b = (Bouclier)o;
				this.supprimerBouclier();
				decremPtsDefLnk(getPtsDefLnk().getValue());
				this.ajouterOutil(b);
				this.getEnv().enleverOutil(o);
				ptsDef.setValue(b.getPtsDef());
			}
			
		}
		
	}
	
	public void pousserPierre() {
		
		// Permet à Link de pousser une pierre à la case suivante par rapport à sa direction.
		
		/* il nous faut savoir deux choses :
			
			1) si la case qui se trouve après est libre (dans les limites & sans qu'un personnage/outil/obstacle ne s'y trouve)
			2) si une pierre se trouve à la case c+1 par rapport à sa direction
			
			il faut donc récupérer la direction puis tester une condition.
			
			si la condition est vraie, alors il faut dans un premier temps
			modifier le tableau de int [][] contenu dans TileMap et intervertir
			les valeurs de la case où se trouve la pierre avec la case qui se situe
			au rang c+1 par rapport à cette même pierre, en prenant en compte la direction
			de Link
			
			enfin, il faut faire appel à la classe DessinerMap dans vue pour mettre à jour
			l'interface graphique.
		*/
		
		// coordonnées de la case c+1 par rapport à Link
		int xPierre, yPierre;
		
		int[] coord = avancerDeUnPas(this.getX(), this.getY());
		
		xPierre = coord[0]; yPierre = coord[1];
		
		// la case en question est dans les limites
		if (!this.getEnv().horsLimites(xPierre, yPierre)) {
			// la case en question contient une pierre
			if (this.getEnv().getTm().getNombre(yPierre, xPierre)==Constantes.CODE_PIERRE) {
				// la case c+1 par rapport à la pierre est-elle dans les limites ?
				coord = avancerDeUnPas(xPierre, yPierre);
				
				if (!this.getEnv().horsLimites(coord[0], coord[1])) {
					// la case c+1 par rapport à la pierre est-elle libre ? (herbe uniquement, ni acteur, ni outil)
					if (this.getEnv().getTm().getNombre(coord[1], coord[0])==0)
						if (!this.getEnv().contientActeurOuOutil(coord[0], coord[1])) {
							// on intervertit les coordonnées dans le tableau de la TileMap
							this.getEnv().getTm().intervertir(yPierre, xPierre, coord[1], coord[0]);
							
							this.getEnv().ajouterDecorChange(coord);
							int[] coord2 = new int[2];
							coord2[0] = xPierre;
							coord2[1] = yPierre;
							this.getEnv().ajouterDecorChange(coord2);
							// le reste doit être fait dans la partie vue/contrôleur
						}
				}
				
			}
		}
		
		
	}
	
	public void cueillir() {
		int[] coord = estAProximitéDe(Constantes.CODE_ARBRE_FRUITIER);
		int[] coordAEnvoyer = new int[2];
		
		if (coord!=null) {
			if (getPV()<PV_MAX) {
				setPV(getPV()+getEnv().getPvFruits());
				if (getPV()>PV_MAX) setPV(PV_MAX);
				
				getEnv().getTm().setNombre(coord[0], coord[1], Constantes.CODE_ARBRE);
				coordAEnvoyer[0] = coord[1];
				coordAEnvoyer[1] = coord[0];
				getEnv().ajouterDecorChange(coordAEnvoyer);
			}
		}
	} 
	
	public int[] avancerDeUnPas(int x, int y) {
		// retourne une paire de coordonnées qui correspond à une avancée de un pas, selon la direction de Link
		
		int[] coord = new int[2];
		
		int newX = x;
		int newY = y;
		
		switch (this.getDir().getValue()) {
			case 6: // droite
				newX = x+1;
				newY = y;
				break;
			case 4: // gauche
				newX = x-1;
				newY = y;
				break;
			case 2: // bas
				newY = y+1;
				newX = x;
				break;
			default: // haut
				newX = x;
				newY = y-1;
		}
		
		coord[0] = newX;
		coord[1] = newY;
		
		return coord;
		
	}
	
	public int[] estAProximitéDe(int element) {
		// retourne les coordonnées de l'élément spécifié en paramètres le plus proche, si Link se trouve à une case de celui-ci
		
		int pointDepart, pointArrivee;
		int i;
		int[] coord = new int[2];
		
		// balayage horizontal
		pointDepart = getX()-1;
		pointArrivee = getX()+1;
		
		if (pointDepart<0) pointDepart=0;
		if (pointArrivee>=getEnv().getTm().getLargeur()) pointArrivee = getEnv().getTm().getLargeur()-1;
		
		i = pointDepart;
		
		while (i<=pointArrivee) {
			if (getEnv().getTm().getNombre(getY(), i)==element) {
				coord[0] = getY();
				coord[1] = i;
				return coord;
			}
			i++;
		}
		
		// balayage vertical
		pointDepart = getY()-1;
		pointArrivee = getY()+1;
				
		if (pointDepart<0) pointDepart=0;
		if (pointArrivee>=getEnv().getTm().getHauteur()) pointArrivee = getEnv().getTm().getHauteur()-1;
				
		i = pointDepart;
				
		while (i<=pointArrivee) {
			if (getEnv().getTm().getNombre(i, getX())==element) {
				coord[0] = i;
				coord[1] = getX();
				return coord;
			}
			i++;
		}
			
		// balayage diagonal
		int[][] diag = {{-1,-1}, {1,1}, {1,-1}, {-1,1}};
		
		for (i=0; i<=3; i++) {
			coord[0] = getY() + diag[i][0];
			coord[1] = getX() + diag[i][1];
			
			if (!getEnv().horsLimites(coord[1], coord[0]))
				if (getEnv().getTm().getNombre(coord[0], coord[1])==element) return coord;
		}
		
		return null;
	}
	
	public String toString() {
		return "Link";
	}


}
