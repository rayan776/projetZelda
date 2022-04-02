package application.modele;

import java.util.Random;

public abstract class Ennemi extends Acteur {
	
	Random r;
	
	public Ennemi (int x, int y, Environnement env) {
		super(x, y, env, 50);
		r = new Random();
		this.setDir(r.nextInt(4));
	}
	
	public boolean détecterPrésenceLink() {
		Acteur lnk = this.getEnv().getLink();
		
		if (lnk!=null)
			return Math.abs(lnk.getX()-this.getX())<=this.getArme().getDistance()&&Math.abs(lnk.getY()-this.getY())<=this.getArme().getDistance();
		else return false;
	}
	
	public abstract void seDéplacer();
	
	public void attaquer() {
		Link lnk = this.getEnv().getLink();
		if (lnk!=null) {
			
			// on vérifie si Link a un bouclier
			
			Bouclier bLnk = lnk.getBouclier();
			
			// si Link a un bouclier, l'ennemi enlève des points au bouclier puis à Link. sinon, il enlève des pv à Link directement
			
			if (bLnk!=null) {
				/* pv a Enlever = PA arme acteur - pts Def du bouclier de Link. Si PA - pts Def est inférieur ou égal à 0, alors 
				 ça signifie que le bouclier a tout encaissé. Sinon, alors le bouclier encaisse jusqu'à se détruire 
				 puis Link encaisse le reste. */
				
				int pvAEnlever = this.getArme().getPtsAttaque() - bLnk.getPtsDef();
				
				bLnk.decrementerPtsDef(this.getArme().getPtsAttaque());
				lnk.decremPtsDefLnk(this.getArme().getPtsAttaque());
				
				if (bLnk.getPtsDef()<=0)
					lnk.supprimerBouclier();
				
				if (pvAEnlever>0)
					lnk.decrementePV(pvAEnlever);
			}
			else {
				// attaque classique quand Link ne possède pas de bouclier, il encaisse tous les points d'attaque directement
				
				lnk.decrementePV(this.getArme().getPtsAttaque());
			}
			this.getEnv().ajouterAttaque(new Attaque(this, lnk));
		}
	}

}
