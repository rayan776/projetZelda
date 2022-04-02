package application.modele;

public class Arme extends Outils {
	
	private boolean ramassable;
	private int pointsAttaque;
	private int distanceUtilisation;
	
	public Arme(int pa, int di) {
		this.incremId();
		this.pointsAttaque = pa;
		this.distanceUtilisation = di;
		this.ramassable = false;
	}
	
	public boolean getRamassable() {
		return this.ramassable;
	}
	
	public void setRamassable(boolean b) {
		this.ramassable = b;
	}
	
	public int getPtsAttaque() {
		return this.pointsAttaque;
	}
	
	public int getDistance() {
		return this.distanceUtilisation;
	}
	
	
	
	
}
