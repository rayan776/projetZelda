package application.vue;

import application.modele.Acteur;
import application.modele.Constantes;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class AjouterSprite {
	
	private Acteur a;
	private Pane p;
	private Image img;
	private ImagesSprites imgSp;
	
	public AjouterSprite(Acteur a, Pane p) {
		this.a = a;
		this.p = p;
		this.imgSp = new ImagesSprites();
	}
	
	public void ajouterSprite() {
	
		img = imgSp.getImg(a.toString() + a.getDir().getValue().intValue());
		ImageView c = new ImageView(img);
		
		c.setTranslateX(a.getX()*Constantes.DIMENSION_TUILE);
		c.setTranslateY(a.getY()*Constantes.DIMENSION_TUILE);
		c.setId(a.getId());
		
		c.translateXProperty().bind(a.xProperty().multiply(Constantes.DIMENSION_TUILE));
		c.translateYProperty().bind(a.yProperty().multiply(Constantes.DIMENSION_TUILE));
		
		a.getDir().addListener((ob,ol,nouv) -> {
			img = imgSp.getImg(a.toString() + a.getDir().getValue().intValue());
    		c.setImage(img);
    	});
    	
    	p.getChildren().add(c);
		
	}
	

}
