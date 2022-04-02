package application.modele;

public class Bouclier extends Outils {
	
	private int ptsDef;
	
	public Bouclier(int x, int y) {
		this.incremId();
		setX(x);
		setY(y);
		this.ptsDef = Constantes.PTS_BOUCLIER;
	}
	
	public void decrementerPtsDef(int n) {
		this.ptsDef -= n;
	}
	
	public int getPtsDef() {
		return this.ptsDef;
	}
	
	public String toString() {
		return "Bouclier";
	}

}
