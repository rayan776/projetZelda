package application.controleur;

import application.modele.Acteur;
import application.modele.Arme;
import application.modele.Attaque;
import application.modele.EnnemiAuSol;
import application.modele.EnnemiQuiVole;
import application.modele.Environnement;
import application.modele.Link;
import application.modele.Outils;
import application.vue.ImagesOutils;
import application.vue.AjouterSprite;
import application.vue.DessinerMap;
import application.vue.ImagesArmesInventaire;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class ObservateurListe {
	
	private ListChangeListener<Acteur> listObs;
	private ListChangeListener<Attaque> listAtt;
	private ListChangeListener<Outils> listInventaireLink;
	private ListChangeListener<Outils> listInventaireEnv;
	private ListChangeListener<int[]> listDecorChange;
	private ImagesArmesInventaire imgArmesInv;
	
	public ListChangeListener<Acteur> getObs() {
		return this.listObs;
	}
	
	public ListChangeListener<Attaque> getObsAtt() {
		return this.listAtt;
	}
	
	public ListChangeListener<Outils> getObsOutils() {
		return this.listInventaireLink;
	}
	
	public ListChangeListener<Outils> getObsOutilsEnv() {
		return this.listInventaireEnv;
	}
	
	public ListChangeListener<int[]> getObsDecorChange() {
		return this.listDecorChange;
	}
	
	public ObservateurListe(DessinerMap dm, Pane panneauCoeurs, Pane panneauSup, TilePane tp, Environnement env, TextArea infosArea, Pane inventairePane) {
		
		imgArmesInv = new ImagesArmesInventaire();
		
		listObs = new ListChangeListener<Acteur>() {
			
			@Override
			public void onChanged(ListChangeListener.Change<? extends Acteur> c) {
				
				while (c.next()) {
					
					if (c.wasRemoved()) {
						for (Acteur a : c.getRemoved()) {
							infosArea.setText(infosArea.getText() + "\n\nL'acteur d'ID " + a.getId() + " est mort.");
							infosArea.appendText("");
							panneauSup.getChildren().remove(panneauSup.lookup("#" + a.getId()));
							
						}
					}
					
					if (c.wasAdded()) {
						for (Acteur a : c.getAddedSubList()) {
							AjouterSprite as = new AjouterSprite(a, panneauSup);
							as.ajouterSprite();
						}
					}
				}
			
		
			}
		};
		
		listAtt = new ListChangeListener<Attaque>() {
			
			@Override
			public void onChanged(ListChangeListener.Change<? extends Attaque> c) {
				
				while (c.next()) {
					
					if (c.wasAdded()) {
						for (Attaque a : c.getAddedSubList()) {
							infosArea.setText(infosArea.getText() + "\n\n" + a);
							infosArea.appendText("");
						}
					}
					
				}
			
			}
		};
		
		listInventaireLink = new ListChangeListener<Outils>() {
			
			@Override
			public void onChanged(ListChangeListener.Change<? extends Outils> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						for (Outils o : c.getAddedSubList()) {
							if (o instanceof Arme)
								inventairePane.getChildren().add(imgArmesInv.createImage(o.toString()));
						}
					}
					
					if (c.wasRemoved()) {
						for (Outils o : c.getRemoved()) {
							ImageView img = (ImageView)inventairePane.lookup("#" + o.toString());
							inventairePane.getChildren().remove(img);
						}
					}
				}
			
			}
		};
		
		listInventaireEnv = new ListChangeListener<Outils>() {
			
			@Override
			public void onChanged(ListChangeListener.Change<? extends Outils> c) {
				
				while (c.next()) {
					if (c.wasAdded()) {
						for (Outils o : c.getAddedSubList()) {
							// ajouter une image d'outil (arme ou bouclier) sur la map avec la vue
							ImagesOutils.addPic(o, env, panneauSup);
						}
					}
					
					if (c.wasRemoved()) {
						for (Outils o : c.getRemoved()) {
							ImagesOutils.removePic(o, env, panneauSup);
						}
					}
				}
			
			}
		};
		
		listDecorChange = new ListChangeListener<int[]>() {
			
			@Override
			public void onChanged(ListChangeListener.Change<? extends int[]> c) {
				while (c.next()) {
					if (c.wasAdded()) {
						for (int[] t : c.getAddedSubList()) {
							dm.redessiner(t[1], t[0]);
						}
					}
				}
			}
		};
	}
	
}

