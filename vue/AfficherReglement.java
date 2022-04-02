package application.vue;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AfficherReglement {
	
	public AfficherReglement() throws IOException {
		Stage stage = new Stage();
		BorderPane root = FXMLLoader.load(getClass().getResource("/application/vue/vueRegles.fxml"));
		
		Scene scene = new Scene(root, 700, 700);
		stage.setScene(scene);
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

}
