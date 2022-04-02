package application.controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.vue.AfficherReglement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControleurFenetrePrincipale implements Initializable {
	
	@FXML
	private BorderPane bordPane;
	 
    @FXML
    private Button nouvPartieButton;
    
    @FXML
    private Button buttonRegles;

    @FXML
    private Button buttonQuit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

	}
	
    @FXML
    void nouvPartie(ActionEvent event) throws IOException {
    	Stage nouvellePartieFenetre = new Stage();
    	nouvellePartieFenetre.setTitle("Nouvelle partie");
    	BorderPane root = FXMLLoader.load(getClass().getResource("/application/vue/vueFenetreJeu.fxml"));
    	Scene scene = new Scene(root,1200,1050);
    	nouvellePartieFenetre.setScene(scene);
    	nouvellePartieFenetre.initModality(Modality.APPLICATION_MODAL);
    	scene.getStylesheets().addAll(this.getClass().getResource("/application/vue/styleFenetreJeu.css").toExternalForm());
    	nouvellePartieFenetre.show();
    }
    



    @FXML
    void afficherRegles(ActionEvent event) throws IOException {
    	new AfficherReglement();
    }

    @FXML
    void quitter(ActionEvent event) {
    	Stage stage = (Stage) nouvPartieButton.getScene().getWindow();
    	stage.close();
    }

}
