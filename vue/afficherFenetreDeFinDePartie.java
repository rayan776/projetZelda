package application.vue;

import java.io.IOException;

import application.modele.Environnement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class afficherFenetreDeFinDePartie {
	
	private Stage st;
	private Environnement env;
	private Pane panneauSup;
	
	public afficherFenetreDeFinDePartie(Stage st, Environnement env, Pane pSup) {
		this.st = st;
		this.env = env;
		this.panneauSup = pSup;
	}
	
	public void afficherDefaiteOuVictoire(boolean victoire) throws IOException {
		Stage defaiteStage = new Stage();
		String vueName = victoire ? "vueVictoire.fxml" : "vueDefaite.fxml";
		BorderPane root = FXMLLoader.load(getClass().getResource(vueName));
		Scene scene = new Scene(root,580,65);
		defaiteStage.setScene(scene);
		defaiteStage.initModality(Modality.APPLICATION_MODAL);
		defaiteStage.show();
		defaiteStage.setOnHidden(event -> nettoyer());
	}
	
	public void nettoyer() {
		env.nettoyage();
		panneauSup.getChildren().clear();
		st.close();
	}

}
