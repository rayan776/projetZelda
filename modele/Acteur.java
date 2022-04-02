package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Acteur {
	
	private static int numero = 0;
	private String id;
	private IntegerProperty pv;
	private IntegerProperty dir;
	private ObservableList<Outils> inventaire;
	private Arme armeActuelle;
	private IntegerProperty x, y;
	private Environnement env;
	private boolean dansLeau = false;
	
	public Acteur(int x, int y, Environnement env, int pv) {
		this.x = new SimpleIntegerProperty(x);
		this.y = new SimpleIntegerProperty(y);
		this.pv = new SimpleIntegerProperty(pv);
		this.dir = new SimpleIntegerProperty();
		this.inventaire = FXCollections.observableArrayList();
		this.numero++;
		this.id = "A" + this.numero;
		this.env = env;
	}
	
	public String getId() { return this.id; }
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Arme getArme() {
		return this.armeActuelle;
	}
	
	public int compterArmes() {
		int armes=0;
		for (Outils o : this.inventaire)
			if (o instanceof Arme) armes++;
		
		return armes;
	}
	
	public void setDansLeau(boolean b) {
		this.dansLeau = b;
	}
	
	public boolean estDansLeau() {
		return this.dansLeau;
	}
	
	public boolean possèdeOutil(String n) {
		// vérifie si l'acteur possède un outil, par nom
		
		for (Outils o : this.inventaire)
			if (o.toString().equals(n)) return true;
		
		
		return false;
	}
	
	public void changerArme() {
		// l'arme actuelle devient l'arme suivante dans l'inventaire. Si il n'y a qu'une seule arme, rien ne se passe
		
		boolean armeActuelleTrouvée = false;
		boolean armeChangée = false;
		int i=0;
		
		if (this.compterArmes()>1) {

			while (!armeActuelleTrouvée) {
				if (this.inventaire.get(i)==armeActuelle)
					break;
				
				if (i==this.inventaire.size()-1) i=0;
				else i++;
			}
			
			if (i==this.inventaire.size()-1) i=0;
			else i++;
			
			while (!armeChangée) {
				if (this.inventaire.get(i) instanceof Arme && this.inventaire.get(i) != armeActuelle) {
					armeActuelle = (Arme)this.inventaire.get(i);
					armeChangée = true;
				}
				if (i==this.inventaire.size()-1) i=0;
				else i++;
			}
		}
					
		
	}
	
	public ObservableList<Outils> getInventaire() {
		return this.inventaire;
	}
	
	public void ajouterOutil(Outils o) {
		if (!this.inventaire.contains(o))
			this.inventaire.add(o);
	}
	
	public void setArme(Arme a) {
		if (this.inventaire.contains(a))
			this.armeActuelle = a;
	}
	
	public IntegerProperty pvProperty() { 
		return this.pv; 
	}
	
	public int getPV() { 
		return this.pv.get(); 
	}
	
	public void decrementePV(int n) { 
		this.pv.setValue(this.pv.getValue()-n);
	}
	
	public void setPV(int n) {
		this.pv.setValue(n);
	}
	
	public Bouclier getBouclier() {
		for (Outils o : this.getInventaire())
			if (o instanceof Bouclier) return (Bouclier)o;
		
		return null;
	}
	
	public void supprimerBouclier() {
		if (this.getBouclier()!=null)
			for (int i=0; i<this.inventaire.size(); i++)
				if (this.inventaire.get(i) instanceof Bouclier) {
					this.inventaire.remove(i); 
					break;
				}
	}
	
	public IntegerProperty xProperty() { 
		return this.x; 
	}
	public IntegerProperty yProperty() { 
		return this.y; 
	}
	
	public int getX() { 
		return this.x.get(); 
	}
	
	public int getY() { 
		return this.y.get(); 
	}
	
	public void setX(int n) { 
		this.x.setValue(n);
	}
	public void setY(int n) { 
		this.y.setValue(n); 
	}
	
	public IntegerProperty getDir() {
		return this.dir;
	}
	
	public void setDir(int n) { 
		this.dir.setValue(n);
	}
	
	public Environnement getEnv() { 
		return this.env; 
	}
	
	public abstract void seDéplacer();
	
	public abstract void attaquer();

}
