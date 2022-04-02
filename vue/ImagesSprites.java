package application.vue;

import java.util.HashMap;

import javafx.scene.image.Image;

public class ImagesSprites {
	
	private Image linkGauche;
	private Image linkBas;
	private Image linkDroite;
	private Image linkHaut;
	
	private Image ennemiSolGauche;
	private Image ennemiSolBas;
	private Image ennemiSolDroite;
	private Image ennemiSolHaut;
	
	private Image ennemiVoleGauche;
	private Image ennemiVoleBas;
	private Image ennemiVoleDroite;
	private Image ennemiVoleHaut;
	
	private HashMap<String,Image> hmSprites;
	
	public ImagesSprites() {
		
		hmSprites = new HashMap<String,Image>();
		
		linkGauche = new Image("/application/resources/Link4.png");
		linkBas = new Image("/application/resources/Link2.png");
		linkDroite = new Image("/application/resources/Link6.png");
		linkHaut = new Image("/application/resources/Link8.png");
		
		ennemiSolGauche = new Image("/application/resources/EnnemiAuSol4.png");
		ennemiSolBas = new Image("/application/resources/EnnemiAuSol2.png");
		ennemiSolDroite = new Image("/application/resources/EnnemiAuSol6.png");
		ennemiSolHaut = new Image("/application/resources/EnnemiAuSol8.png");
		
		ennemiVoleGauche = new Image("/application/resources/EnnemiQuiVole4.png");
		ennemiVoleBas = new Image("/application/resources/EnnemiQuiVole2.png");
		ennemiVoleDroite = new Image("/application/resources/EnnemiQuiVole6.png");
		ennemiVoleHaut = new Image("/application/resources/EnnemiQuiVole8.png");
		
		hmSprites.put("Link4", linkGauche);
		hmSprites.put("Link2", linkBas);
		hmSprites.put("Link6", linkDroite);
		hmSprites.put("Link8", linkHaut);
		
		hmSprites.put("EnnemiAuSol4", ennemiSolGauche);
		hmSprites.put("EnnemiAuSol2", ennemiSolBas);
		hmSprites.put("EnnemiAuSol6", ennemiSolDroite);
		hmSprites.put("EnnemiAuSol8", ennemiSolHaut);
		
		hmSprites.put("EnnemiQuiVole4", ennemiVoleGauche);
		hmSprites.put("EnnemiQuiVole2", ennemiVoleBas);
		hmSprites.put("EnnemiQuiVole6", ennemiVoleDroite);
		hmSprites.put("EnnemiQuiVole8", ennemiVoleHaut);
	}
	
	public Image getImg(String s) {
		return hmSprites.get(s);
	}
	

}
