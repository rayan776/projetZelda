package application.vue;

import application.modele.Acteur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Coeurs {
	
	private Pane p;
	private Acteur link;
	
	public Coeurs(Pane p, Acteur link) {
		this.p = p;
		this.link = link;
	}
	
	public void ajouterCoeurs() {
		
		Image img;
		ImageView imgV;
		
		for (int i=0; i<10; i++) {
			img = new Image("application/resources/coeur.png");
			imgV = new ImageView(img);
			
			imgV.setId("I" + i);
		
			p.getChildren().add(imgV);
			
			imgV.setTranslateX(i*70);
			imgV.setTranslateY(0);
			
		}
		
		link.pvProperty().addListener((ob,ol,nouv)-> {
			
			double val = nouv.intValue()/10;
			ImageView imgCoeur;
			
			for (int i=0; i<10; i++) {
				imgCoeur = (ImageView)p.lookup("#" + "I" + i);
				
				if (imgCoeur!=null&&i!=0)
					if (val<i)
						imgCoeur.setVisible(false);
					else
						imgCoeur.setVisible(true);
			}
			
		});
		
	}

}
