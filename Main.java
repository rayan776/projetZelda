/*
 * 
 * Projet S2 - Groupe 4 - IUT de Montreuil
 * 
 * Auteurs:
 * 
 * Rayane BOUFENGHOUR
 * Aslan VICHEGOUROV
 * Hermann KAMGUIN
 * 
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = FXMLLoader.load(getClass().getResource("vue/vueFenetrePrincipale.fxml"));
			Scene scene = new Scene(root,782,396);
			primaryStage.setScene(scene);
			scene.getStylesheets().addAll(this.getClass().getResource("vue/styleFenetrePrincipale.css").toExternalForm());
			primaryStage.setTitle("Jeu d'aventure");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
