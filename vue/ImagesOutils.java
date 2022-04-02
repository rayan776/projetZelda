package application.vue;

import application.modele.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImagesOutils {
	
	public static void addPic(Outils o, Environnement env, Pane p) {
		
		String url;
		
		if (o instanceof Baton)
			url = "/application/resources/baton.png";
		else if (o instanceof Epee)
			url = "/application/resources/epee.png";
		else if (o instanceof Arc)
			url = "/application/resources/arc.png";
		else
			url = "/application/resources/bouclier.png";
		
		Image imgv = new Image(url);
		ImageView img = new ImageView(imgv);
		img.setId(o.getId());
		
		p.getChildren().add(img);
		
		img.setTranslateX(Constantes.DIMENSION_TUILE*o.getX());
		img.setTranslateY(Constantes.DIMENSION_TUILE*o.getY());
		
	}
	
	public static void removePic(Outils o, Environnement env, Pane p) {
		ImageView img = (ImageView)p.lookup("#" + o.getId());
		p.getChildren().remove(img);
	}

}
