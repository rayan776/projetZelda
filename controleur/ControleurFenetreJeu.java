package application.controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import application.modele.*;
import application.vue.Coeurs;
import application.vue.AjouterSprite;
import application.vue.DessinerMap;
import application.vue.HashMapDesMaps;
import application.vue.HashMapElementsDecor;
import application.vue.afficherFenetreDeFinDePartie;

public class ControleurFenetreJeu implements Initializable {
	
	private HashMap<Integer,String> urlDecor;
	private HashMap<String,String> listeMaps;
	private TileMap tm;
	private Environnement env;
	private GameLoop gameLoop;
	private DessinerMap dm;
	private Factory fac;

    @FXML
    private BorderPane bordPane;
    
    @FXML
    private VBox scoreTimeBox;
   
    @FXML
    private Label scoreLabel;
	
	@FXML
    private TilePane tilePane;
	
    @FXML
    private Pane coeurs;

	@FXML
	private Button boutonUrlMap;
	
    @FXML
    private Pane panneauSup;
    
    @FXML
    private Label inventaireText;

    @FXML
    private Label timeText;
    
    @FXML
    private Pane inventairePane;
    
    @FXML
    private Label dernieresNewsLbl;
    
    @FXML
    private TextArea dernieresNews;
    
    @FXML
    private ImageView imgGoutteEau;
    
    @FXML
    private ProgressBar barrePvEau;
    
    @FXML
    private ProgressBar bouclierProgressBar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
			urlDecor = new HashMapElementsDecor().getDecor();
			listeMaps = new HashMapDesMaps().getListeMaps();
			
	}
 
	@FXML
	void choisirMap(ActionEvent event) throws MapException, IOException {
		
		// cette méthode devra être nettoyée d'ici la fin du projet. Pour l'instant, on initialise des données
		// liées au jeu ici (boucliers, armes, ennemis etc...) juste pour faire des tests, mais ça devra changer
		
		tm = new TileMap();
		
		dm = new DessinerMap(tilePane, panneauSup, tm, urlDecor, listeMaps);
		
		if (dm.dessiner()) {
			
			
			// choix de la difficulté
			int difficulte = choisirDifficulte();
			
			env = new Environnement(tm, difficulte);
			fac = new Factory(env);
			
			boutonUrlMap.setDisable(true);
			barrePvEau.setVisible(true);
			imgGoutteEau.setVisible(true);
			inventairePane.setVisible(true);
			bouclierProgressBar.setVisible(true);
			scoreTimeBox.setVisible(true);
			dernieresNews.setVisible(true);
			dernieresNewsLbl.setVisible(true);
			
			gameLoop = new GameLoop(dm, timeText, env, fac, System.currentTimeMillis(), dernieresNews, coeurs, panneauSup, tilePane, inventairePane, imgGoutteEau, barrePvEau, bouclierProgressBar);
			
			fac.faireApparaitreLink();
			
			gameLoop.nouvellePartie();
			
			Baton batonJoueur = new Baton();
			env.getLink().ajouterOutil(batonJoueur);
			env.getLink().setArme(batonJoueur);
		
			AjouterSprite asLink = new AjouterSprite(env.getLink(), panneauSup);
			asLink.ajouterSprite();
			
			LinkKeyHandler lkh = new LinkKeyHandler(panneauSup, env.getLink());
			lkh.createHandler();
			
			fac.faireApparaitreEnnemi(true);
			fac.faireApparaitreOutil();
			
			Coeurs dessinerCoeurs = new Coeurs(coeurs, env.getLink());
			dessinerCoeurs.ajouterCoeurs();
			
			scoreLabel.textProperty().bind(env.getLink().getScore().asString());
			
			dernieresNews.textProperty().addListener(new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
					dernieresNews.setScrollTop(Double.MAX_VALUE);
				}
			});
			
			env.partieFinieProperty().addListener((ob,ol,nouv)-> {
				
				if (ob.getValue()) {
					Stage stage = (Stage) tilePane.getScene().getWindow();
					boolean victoire = env.getLink()!=null;
					
					afficherFenetreDeFinDePartie vueFinPartie = new afficherFenetreDeFinDePartie(stage, env, panneauSup);
					
					try {
						vueFinPartie.afficherDefaiteOuVictoire(victoire);
					    
					} catch (IOException e) {
						
					}
				}
				
			});
		}
	
	}
	
	public int choisirDifficulte() {
		int difficulte=1;
		
		ArrayList<String> choices = new ArrayList<>();
		choices.add("Facile");
		choices.add("Moyen");
		choices.add("Difficile");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Facile", choices);
		dialog.setTitle("Jeu d'aventure");
		dialog.setHeaderText("Difficulté");
		dialog.setContentText("Choisissez la difficulté. Si vous quittez la fenêtre, la difficulté choisie sera facile.");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
		    
		    switch (result.get()) {
		    	case "Facile":
		    		difficulte=1;
		    		break;
		    	case "Moyen":
		    		difficulte=2;
		    		break;
		    	case "Difficile":
		    		difficulte=3;
		    		break;
		   }
		}
		
		return difficulte;
	}



}
