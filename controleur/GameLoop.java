package application.controleur;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import application.modele.Bouclier;
import application.modele.Constantes;
import application.modele.Environnement;
import application.modele.Factory;
import application.vue.DessinerMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

public class GameLoop {
	
	private int compteurEau = 0;
	private int[] yx;
	private DessinerMap dm;
	private Label timeLabel;
	private Environnement env;
	private Factory fac;
	private long beginTime;
	private TextArea infosText;
	private Pane coeurs;
	private Pane panneauSup;
	private TilePane tilePane;
	private Pane inventairePane;
	private ImageView imgGoutteEau;
	private ProgressBar barrePvEau;
	private ProgressBar bouclierProgressBar;
	private DateFormat timeFormat;
	private ImageView cercleArmeActu;
	
	
	public GameLoop(DessinerMap dm, Label timeLabel, Environnement env, Factory fac, long beginTime, TextArea infosText, Pane coeurs, Pane panneauSup, TilePane tilePane, Pane inventairePane, ImageView imgGoutteEau, ProgressBar barrePvEau, ProgressBar bouclierProgressBar) {
		
		yx = new int[2];
		this.timeFormat = new SimpleDateFormat("mm:ss");
		this.dm = dm;
		this.timeLabel = timeLabel;
		this.env = env;
		this.fac = fac;
		this.beginTime = beginTime;
		this.infosText = infosText;
		this.coeurs = coeurs;
		this.panneauSup = panneauSup;
		this.tilePane = tilePane;
		this.inventairePane = inventairePane;
		this.imgGoutteEau = imgGoutteEau;
		this.barrePvEau = barrePvEau;
		this.bouclierProgressBar = bouclierProgressBar;
		Image cercleR = new Image("/application/resources/cercle.png");
		this.cercleArmeActu = new ImageView(cercleR);
		this.cercleArmeActu.setTranslateY(0);
		this.inventairePane.getChildren().add(cercleArmeActu);	
		
	}
	
	public void nouvellePartie() {
		ObservateurListe obsListe = new ObservateurListe(dm, coeurs, panneauSup, tilePane, env, infosText, inventairePane);
		
		env.getListe().addListener(obsListe.getObs());
		env.getAttaques().addListener(obsListe.getObsAtt());
		env.getLink().getInventaire().addListener(obsListe.getObsOutils());
		env.getOutils().addListener(obsListe.getObsOutilsEnv());
		env.getDecorChange().addListener(obsListe.getObsDecorChange());
		barrePvEau.setProgress(1);
		
		env.getLink().getPvEauProperty().addListener((ob,ol,nouv)-> {
			barrePvEau.setProgress(ob.getValue().doubleValue()/10);
		});
		
		env.getLink().getPtsDefLnk().addListener((ob,ol,nouv) -> {
			bouclierProgressBar.setProgress(nouv.doubleValue()/Constantes.PTS_BOUCLIER);
		});
		
		
		final Timeline chrono = new Timeline(
			
		    new KeyFrame(
		        Duration.millis(1000),
		        event -> {
		        	final long diff = System.currentTimeMillis() - beginTime;
		        	 timeLabel.setText(timeFormat.format(diff));
		        }
		    )
		);
		
		final Timeline deplaceEnnemis = new Timeline(
				
			    new KeyFrame(
			        Duration.millis(env.getVitEnnemis()),
			        event -> {
			        	env.toutLeMondeSeDeplaceSaufLink();
			        }
			    )
		);
		
		final Timeline apparitions = new Timeline(
				
				new KeyFrame(
						Duration.seconds(1),
						event -> {
							if (env.getLink()!=null) {
								// les arbres donnent des fruits selon une probabilité
								if (Math.random()<env.getProbArbres()) {
									
									yx = env.transformerArbreAleatoireEnFruitierV2();
									if (yx!=null)
										dm.redessiner(yx[0], yx[1]);
								}
								
								// un ennemi apparait selon une probabilité
								if (Math.random()<env.getProbApparitionEnnemi()&&env.getListe().size()-1<fac.getEnnemisMax()) {
									fac.faireApparaitreEnnemi(false);
								}
								
								// un outil apparait selon une probabilité
								if (Math.random()<env.getProbApparitionOutil()&&env.getOutils().size()<fac.getOutilsMax()) {
									fac.faireApparaitreOutil();
								}
							}
						}
				)
	);
		
		final Timeline deroulementPartie = new Timeline(
				new KeyFrame(
						Duration.millis(100),
						event -> {
							
							// gérer la fin de partie
							if (env.partieEstTerminée()) {
						
								coeurs.getChildren().clear();
								
								for (int i=0; i<panneauSup.getChildren().size(); i++) {
									if (!(panneauSup.getChildren().get(i) instanceof TilePane))
										panneauSup.getChildren().remove(i);
								}
								
								inventairePane.getChildren().clear();
								barrePvEau.setVisible(false);
								imgGoutteEau.setVisible(false);
								
								deplaceEnnemis.stop();
								chrono.stop();
								apparitions.stop();
								env.partieFinieProperty().setValue(true);
								
								
							}
							else {
								if (env.getLink()!=null) {
									
									
									// gérer la nage de Link
									if (env.getLink().estDansLeau()) {
										
										compteurEau++;
										
										if (compteurEau%env.getPerdrePvEauNSecondes()==0) {
											
											if (env.getLink().getPvEauProperty().getValue()>0)
												env.getLink().decrementerPvEau(1);
											else
												env.getLink().decrementePV(1);
										}
									}
									else {
										if (env.getLink().getPvEauProperty().getValue()<env.getLink().getPvEauMax()) {
											
											compteurEau++;
											
											if (compteurEau%env.getGagnerPvEauNSecondes()==0)
												env.getLink().ajouterPvEau(1);
										}
									}
									
									// affichage de l'arme actuelle
									
									ImageView imgArm = (ImageView)inventairePane.lookup("#" + env.getLink().getArme().toString());
									if (imgArm!=null) cercleArmeActu.setTranslateY(imgArm.getTranslateY());
									
							
								}
							}
					
						}
				
				)
		);
		
		final Timeline timelineGeneral = new Timeline(
			    new KeyFrame(
				        Duration.ONE,
				        event -> {
				        	chrono.setCycleCount( Animation.INDEFINITE );
				    		chrono.play();
				    		deplaceEnnemis.setCycleCount(Animation.INDEFINITE);
				    		deplaceEnnemis.play();
				    		deroulementPartie.setCycleCount(Animation.INDEFINITE);
				    		deroulementPartie.play();
				    		apparitions.setCycleCount(Animation.INDEFINITE);
				    		apparitions.play();
				        }
				    )
		);
		
		env.partieFinieProperty().addListener((ob,ol,nouv)-> {
			if (ob.getValue()) {
				deroulementPartie.stop();
			}
		});
		
		timelineGeneral.play();
		
	}
	
		
	
	

}
