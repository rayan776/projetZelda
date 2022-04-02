package application.modele;

public class Attaque {
	
	private Acteur attaquant;
	private Acteur victime;
	
	public Attaque(Acteur att, Acteur vic) {
		this.attaquant = att;
		this.victime = vic;
	}
	
	public String toString() {
		if (attaquant.getId().equals("A1"))
			return "Vous avez attaqué le personnage d'ID " + victime.getId() + "\navec : " + attaquant.getArme() + "\nPoints d'attaque : " + attaquant.getArme().getPtsAttaque() + "\nPV de la victime: " + victime.getPV();
		else
			return "Le joueur d'ID " + attaquant.getId() + "\nvous a attaqué\navec : " + attaquant.getArme() + "\nPoints d'attaque : " + attaquant.getArme().getPtsAttaque();
	}

}
