package application.modele;

public class Arc extends Arme {
	
	public Arc() {
		super(15, 5);
	}
	
	public Arc(boolean ennemi) {
		super(15, 3);
	}
	
	public String toString() {
		return "Arc";
	}
}
