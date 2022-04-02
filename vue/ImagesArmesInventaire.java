package application.vue;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagesArmesInventaire {
	
	private HashMap<String,String> hm;
	
	public ImagesArmesInventaire() {
		hm = new HashMap<String,String>();
		hm.put("Baton", "/application/resources/batonInventaire.png");
		hm.put("Epee", "/application/resources/epeeInventaire.png");
		hm.put("Arc", "/application/resources/arcInventaire.png");
	}
	
	public ImageView createImage(String nom) {
		
		Image img = new Image(hm.get(nom));
		ImageView imgV = new ImageView(img);
		if (nom.equals("Baton")) imgV.setTranslateY(0);
		else if (nom.equals("Epee")) imgV.setTranslateY(150);
		else if (nom.equals("Arc")) imgV.setTranslateY(300);
		imgV.setId(nom);
		
		return imgV;
	}

}
