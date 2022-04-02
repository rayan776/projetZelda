package application.modele;


public class EnnemiQuiVole extends Ennemi {
	
	int[] directions = {2,4,6,8};
	
	public EnnemiQuiVole(int x, int y, Environnement env) {
		super(x, y, env);
		this.setDir(6);
	}
	
	public void seDéplacer() {
		boolean déplacementEffectué = false;
		int newCoord;
		
		if (Math.random()<0.05) this.setDir(directions[r.nextInt(4)]);
		
		while (!déplacementEffectué) {
			
			switch (this.getDir().getValue()) {
				case 2: // bas
					newCoord = this.getY()+1;
					if (!this.getEnv().horsLimitesY(newCoord)) {
							this.setY(newCoord);
							déplacementEffectué = true;
					}
					break;
				
				case 6: // droite
					newCoord = this.getX()+1;
					if (!this.getEnv().horsLimitesX(newCoord)) {
							this.setX(newCoord);
							déplacementEffectué = true;
					}
					break;
				
				case 4: // gauche
					newCoord = this.getX()-1;
					if (!this.getEnv().horsLimitesX(newCoord)) {
							this.setX(newCoord);
							déplacementEffectué = true;
					}
					break;
				
				case 8: // haut
					newCoord = this.getY()-1;
					if (!this.getEnv().horsLimitesY(newCoord)) {
							this.setY(newCoord);
							déplacementEffectué = true;
					}
					break;
				default:
					break;
			}
			
			if (!déplacementEffectué) this.setDir(directions[r.nextInt(4)]);
		}
		
		if (this.détecterPrésenceLink()) {
			this.attaquer();
			this.getEnv().enleverLesMorts();
		}
	}
	
	public String toString() {
		return "EnnemiQuiVole";
	}

}
