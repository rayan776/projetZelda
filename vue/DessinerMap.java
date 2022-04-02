package application.vue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.sun.prism.paint.Color;

import application.modele.Constantes;
import application.modele.MapException;
import application.modele.TileMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Path;

public class DessinerMap {
	
	private TilePane tp;
	private Pane panSup;
	private TileMap tm;
	private HashMap<Integer,String> decor;
	private HashMap<String,String> listeMaps;
	
	public DessinerMap(TilePane tp, Pane panSup, TileMap tm, HashMap<Integer,String> decor, HashMap<String,String> listeMaps) {
		this.tp = tp;
		this.panSup = panSup;
		this.tm = tm;
		this.decor = decor;
		this.listeMaps = listeMaps;
	}
	
	public void redessiner(int y, int x) {
		// met à jour la map graphique après d'éventuels changements de décor (quand on pousse une pierre ou un arbre devient fruitier)
		
		ImageView imageAChanger;
		Image newImg;
	
		imageAChanger = (ImageView)tp.lookup("#Y" + y + "X" + x);
		newImg = new Image(decor.get(tm.getNombre(y,x)));
		imageAChanger.setImage(newImg);

	}
	
	public boolean dessiner() throws MapException, IOException {
		try {
			String filename;
			
			ArrayList<String> choices = new ArrayList<>();
			choices.add("Plaine");
			choices.add("Ile");
			choices.add("Forêt");

			ChoiceDialog<String> dialog = new ChoiceDialog<>("Plaine", choices);
			dialog.setTitle("Jeu d'aventure");
			dialog.setHeaderText("Map");
			dialog.setContentText("Choisissez la map :");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
			    filename = this.listeMaps.get(result.get());
			}
			else {
				filename=null;
			}
			
			if (filename==null) return false;
			tm.chargerFichier(filename);
			
			Image image;
			
			tp.setPrefSize(tm.getLargeur()*Constantes.DIMENSION_TUILE, tm.getHauteur()*Constantes.DIMENSION_TUILE);
			panSup.setPrefSize(tp.getPrefWidth(), tp.getPrefHeight());
			
			panSup.setLayoutX(100);
			panSup.setLayoutY(200);
			
			for (int i=0; i<tm.getHauteur(); i++) {
				for (int j=0; j<tm.getLargeur(); j++) {
				
					image = new Image(decor.get(tm.getNombre(i, j)));
				
					ImageView imageView = new ImageView(image);
					imageView.setId("Y" + i + "X" + j);
					tp.getChildren().add(imageView);
					
				}
			}
			return true;
		}
		catch (MapException e) {
			Alert infoSprite = new Alert(AlertType.ERROR);
			
			infoSprite.setTitle("ERREUR");
			
			infoSprite.setHeaderText("Map non valide");
			
			infoSprite.setContentText("Veuillez réessayer.");
			
			infoSprite.showAndWait();
			return false;
		}
	}

}
